// -----------------------------------------------------------------------------
// WriteFileToTable.java
// -----------------------------------------------------------------------------

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.StringTokenizer;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.text.ParseException;


/**
 * -----------------------------------------------------------------------------
 * The following class provides an example of how to read a simple text file
 * of records and then insert them into a table in a database. A text file 
 * named Employee.txt will contain employee records to be inserted into the
 * following table:
 * 
 *      SQL> desc emp
 *      
 *      Name              Null?    Type
 *      ------------------- -------- --------------
 *      EMP_ID              NOT NULL NUMBER
 *      DEPT_ID                      NUMBER
 *      NAME                NOT NULL VARCHAR2(30)
 *      DATE_OF_BIRTH       NOT NULL DATE
 *      DATE_OF_HIRE        NOT NULL DATE
 *      MONTHLY_SALARY      NOT NULL NUMBER(15,2)
 *      POSITION            NOT NULL VARCHAR2(100)
 *      EXTENSION                    NUMBER
 *      OFFICE_LOCATION              VARCHAR2(100)
 * 
 * NOTE: This example will provide and call a method that creates the EMP
 *       table. The name of the method is called createTable() and is called
 *       from the main() method.
 * -----------------------------------------------------------------------------
 */

public class WriteFileToTable {

    final static String driverClass    = "oracle.jdbc.driver.OracleDriver";
    final static String connectionURL  = "jdbc:oracle:thin:@localhost:1521:CUSTDB";
    final static String userID         = "scott";
    final static String userPassword   = "tiger";
    final static String inputFileName  = "Employee.txt";
    final static String TABLE_NAME     = "EMP";
    final static String DELIM          = ",";
    Connection   con                   = null;


    /**
     * Construct a WriteFileToTable object. This constructor will create an 
     * Oracle database connection.
     */
    public WriteFileToTable() {

        try {

            System.out.print("  Loading JDBC Driver  -> " + driverClass + "\n");
            Class.forName(driverClass).newInstance();

            System.out.print("  Connecting to        -> " + connectionURL + "\n");
            this.con = DriverManager.getConnection(connectionURL, userID, userPassword);
            System.out.print("  Connected as         -> " + userID + "\n");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Method used to create the initial EMP table. Before attempting to create
     * the table, this method will first try to drop the table.
     */
    public void createTable() {

        Statement stmt = null;

        try {
            stmt = con.createStatement();
            
            System.out.print("  Dropping Table: " + TABLE_NAME + "\n");
            stmt.executeUpdate("DROP TABLE " + TABLE_NAME);

            System.out.print("    - Dropped Table...\n");
            
            System.out.print("  Closing Statement...\n");
            stmt.close();

        } catch (SQLException e) {
            System.out.print("    - Table " + TABLE_NAME + " did not exist.\n");
        }


        try {

            stmt = con.createStatement();

            System.out.print("  Creating Table: " + TABLE_NAME + "\n");

            stmt.executeUpdate("CREATE TABLE emp (" +
                             "    emp_id           NUMBER NOT NULL " +
                             "  , dept_id          NUMBER " +
                             "  , name             VARCHAR2(30)  NOT NULL " +
                             "  , date_of_birth    DATE          NOT NULL " +
                             "  , date_of_hire     DATE          NOT NULL " +
                             "  , monthly_salary   NUMBER(15,2)  NOT NULL " +
                             "  , position         VARCHAR2(100) NOT NULL " +
                             "  , extension        NUMBER " +
                             "  , office_location  VARCHAR2(100))");

            System.out.print("    - Created Table...\n");

            System.out.print("  Closing Statement...\n");
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method used to read records from Employee.txt file then write the records
     * to an Oracle table within the database named "EMP".
     */
    public void performLoadWrite() {

        Statement stmt          = null;
        int       insertResults = 0;

        StringTokenizer st = null;

        String  emp_id;
        String  dept_id;
        String  name;
        String  date_of_birth;
        String  date_of_hire;
        String  monthly_salary;
        String  position;
        String  extension;
        String  office_location;

        try {

            System.out.print("  Creating Statement...\n");
            stmt = con.createStatement ();
            
            System.out.print("  Create FileReader Object for file: " + inputFileName + "...\n");
            FileReader inputFileReader = new FileReader(inputFileName);

            System.out.print("  Create BufferedReader Object for FileReader Object...\n");
            BufferedReader inputStream   = new BufferedReader(inputFileReader);

            String inLine = null;
            while ((inLine = inputStream.readLine()) != null) {

                st = new StringTokenizer(inLine, DELIM);
                
                emp_id   = st.nextToken();
                dept_id  = st.nextToken();
                name     = st.nextToken();
                date_of_birth = st.nextToken();
                date_of_hire = st.nextToken();
                monthly_salary = st.nextToken();
                position = st.nextToken();
                extension = st.nextToken();
                office_location = st.nextToken();

                System.out.print("  Inserting value for [" + name + "]\n");

                insertResults = stmt.executeUpdate(
                        "INSERT INTO " + TABLE_NAME + " VALUES (" +
                                  emp_id + 
                        "  ,  " + dept_id +
                        "  , '" + name + "'" +
                        "  , '" + date_of_birth + "'" +
                        "  , '" + date_of_hire + "'" +
                        "  ,  " + monthly_salary +
                        "  , '" + position + "'" +
                        "  ,  " + extension +
                        "  , '" + office_location + "')");

                System.out.print("    " + insertResults + " row created.\n");
            }


            System.out.print("  Commiting Transaction...\n");
            con.commit();

            System.out.print("  Closing inputString...\n");
            inputStream.close();

            System.out.print("  Closing Statement...\n");
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Method used to query records from the database table EMP. This method
     * can be used to verify all records have been correctly loaded from the
     * example text file "Employee.txt".
     */
    public void queryRecords() {

        Statement stmt           = null;
        ResultSet rset           = null;
        int       deleteResults  = 0;
        int       rowNumber      = 0;

        int     emp_id;
        int     dept_id;
        String  name;
        String  date_of_birth;
        Date    date_of_birth_p;
        String  date_of_hire;
        Date    date_of_hire_p;
        float   monthly_salary;
        String  position;
        int     extension;
        String  office_location;

        
        try {

            SimpleDateFormat formatter      = new SimpleDateFormat("yyyy-MM-dd");
            NumberFormat     defaultFormat  =     NumberFormat.getCurrencyInstance();

            System.out.print("  Creating Statement...\n");
            stmt = con.createStatement ();
            
            System.out.print("  Opening query for table: " + TABLE_NAME + "...\n");
            rset = stmt.executeQuery ("SELECT * FROM emp ORDER BY emp_id");

            while (rset.next ()) {

                rowNumber = rset.getRow();


                emp_id = rset.getInt(1);
                if ( rset.wasNull() )   {emp_id = -1;}


                dept_id = rset.getInt(2);
                if ( rset.wasNull() )   {dept_id = -1;}
        

                name = rset.getString(3);
                if ( rset.wasNull() )   {name = "<null>";}


                date_of_birth = rset.getString(4);
                if ( rset.wasNull() ) {date_of_birth = "1900-01-01";}
                try {
                    date_of_birth_p = formatter.parse(date_of_birth);
                } catch (ParseException e) {
                    date_of_birth_p = new Date(0);
                }   


                date_of_hire = rset.getString(5);
                if ( rset.wasNull() ) {date_of_hire = "1900-01-01";}
                try {
                    date_of_hire_p = formatter.parse(date_of_hire);
                } catch (ParseException e) {
                    date_of_hire_p = new Date(0);
                }  


                monthly_salary = rset.getFloat(6);
                if ( rset.wasNull() ) {monthly_salary = 0;}


                position = rset.getString(7);
                if ( rset.wasNull() ) {position = "<null>";}


                extension = rset.getInt(8);
                if ( rset.wasNull() )   {extension = -1;}


                office_location = rset.getString(9);
                if ( rset.wasNull() ) {office_location = "<null>";}


                System.out.print(
                    "\n" +
                    "  RESULTS -> [R" + rowNumber + "] " + "\n" +
                    "      Employee ID     : " + emp_id + "\n" +
                    "      Department ID   : " + dept_id + "\n" +
                    "      Employee Name   : " + name + "\n" +
                    "      D.O.B.          : " + date_of_birth_p + "\n" +
                    "      Date of Hire    : " + date_of_hire_p + "\n" +
                    "      Monthly Salary  : " + defaultFormat.format(monthly_salary) + "\n" +
                    "      Position        : " + position + "\n" +
                    "      Extension       : x" + extension + "\n" +
                    "      Office Location : " + office_location +
                    "\n");
            }


            System.out.print("  Closing ResultSet...\n");
            rset.close();

            System.out.print("  Closing Statement...\n");
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    


    /**
     * Close down Oracle connection.
     */
    public void closeConnection() {

        try {
            System.out.print("  Closing Connection...\n");
            con.close();
            
        } catch (SQLException e) {
        
            e.printStackTrace();
            
        }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     * @exception java.lang.InterruptedException
     *            Thrown from the Thread class.
     */
    public static void main(String[] args)
            throws java.lang.InterruptedException {

        WriteFileToTable runExample = new WriteFileToTable();
        runExample.createTable();
        runExample.performLoadWrite();
        runExample.queryRecords();
        runExample.closeConnection();

    }

}
