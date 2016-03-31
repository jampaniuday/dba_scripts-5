// -----------------------------------------------------------------------------
// DDLExample.java
// -----------------------------------------------------------------------------

/*
 * =============================================================================
 * Copyright (c) 1998-2011 Jeffrey M. Hunter. All rights reserved.
 * 
 * All source code and material located at the Internet address of
 * http://www.idevelopment.info is the copyright of Jeffrey M. Hunter and
 * is protected under copyright laws of the United States. This source code may
 * not be hosted on any other site without my express, prior, written
 * permission. Application to host any of the material elsewhere can be made by
 * contacting me at jhunter@idevelopment.info.
 *
 * I have made every effort and taken great care in making sure that the source
 * code and other content included on my web site is technically accurate, but I
 * disclaim any and all responsibility for any loss, damage or destruction of
 * data or any other property which may arise from relying on it. I will in no
 * case be liable for any monetary damages arising from such loss, damage or
 * destruction.
 * 
 * As with any code, ensure to test this code in a development environment 
 * before attempting to run it in production.
 * =============================================================================
 */
 
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * -----------------------------------------------------------------------------
 * The following class provides an example of using JDBC to perform DDL 
 * statements in an Oracle database.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class DDLExample {

    final static String driverClass    = "oracle.jdbc.driver.OracleDriver";
    final static String connectionURL  = "jdbc:oracle:thin:@localhost:1521:CUSTDB";
    final static String userID         = "system";
    final static String userPassword   = "manager";
    Connection   con                   = null;


    /**
     * Construct a DDLExample object. This constructor will create an Oracle
     * database connection.
     */
    public DDLExample() {

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
     * Method used to create a table, insert rows into it, query all records,
     * delete all records and finally drop the example table.
     */
    public void performDDL() {

        Statement stmt      = null;
        ResultSet rset      = null;
        int insertResults;
        int deleteResults;
    
        try {

            System.out.print("  Creating Statement...\n");
            stmt = con.createStatement ();


            /*
             * -----------------------------------------------------------------
             *  CREATE TABLE
             * -----------------------------------------------------------------
             */
            System.out.print("\n");
            System.out.print("+-------------------------------+\n");
            System.out.print("| CREATE TABLE                  |\n");
            System.out.print("+-------------------------------+\n");
            System.out.print("\n");

            System.out.print("Creating Table [TEST_JDBC]\n");

            stmt.executeUpdate("CREATE TABLE test_jdbc (" +
                             "    test_jdbc_intr_no     NUMBER(15) " +
                             "  , test_jdbc_name        VARCHAR2(100) " +
                             "  , test_jdbc_null_value  VARCHAR2(100))");
            System.out.print("Table Created...\n");


            /*
             * -----------------------------------------------------------------
             *  INSERT INTO TABLE
             * -----------------------------------------------------------------
             */
            System.out.print("\n");
            System.out.print("+-------------------------------+\n");
            System.out.print("| INSERT VALUES                 |\n");
            System.out.print("+-------------------------------+\n");
            System.out.print("\n");

            insertResults = stmt.executeUpdate("INSERT INTO test_jdbc VALUES(" +
                                             "    100" +
                                             "  , 'James Smith'" +
                                             "  , null)");
            System.out.print("  RESULTS            -> " + insertResults + " row created.\n");
            insertResults = stmt.executeUpdate("INSERT INTO test_jdbc VALUES(" +
                                             "    200" +
                                             "  , 'Amy Miller'" +
                                             "  , null)");
            System.out.print("  RESULTS            -> " + insertResults + " row created.\n");
            insertResults = stmt.executeUpdate("INSERT INTO test_jdbc VALUES(" +
                                             "    300" +
                                             "  , 'Andy Phillips'" +
                                             "  , null)");
            System.out.print("  RESULTS            -> " + insertResults + " row created.\n");
            insertResults = stmt.executeUpdate("INSERT INTO test_jdbc VALUES(" +
                                             "    400" +
                                             "  , 'Jimmy Black'" +
                                             "  , null)");
            System.out.print("  RESULTS            -> " + insertResults + " row created.\n");
            insertResults = stmt.executeUpdate("INSERT INTO test_jdbc VALUES(" +
                                             "    500" +
                                             "  , 'Jane Dee'" +
                                             "  , null)");
            System.out.print("  RESULTS            -> " + insertResults + " row created.\n");


            /*
             * -----------------------------------------------------------------
             *  COMMIT TRANSACTION
             * -----------------------------------------------------------------
             */
            System.out.print("Commiting Transaction...\n");
            con.commit();


            /*
             * -----------------------------------------------------------------
             *  EXECUTE QUERY
             * -----------------------------------------------------------------
             */
            System.out.print("\n");
            System.out.print("+-------------------------------+\n");
            System.out.print("| EXECUTE QUERY (Forward)       |\n");
            System.out.print("+-------------------------------+\n");
            System.out.print("\n");

            System.out.print("Opening ResultsSet...\n");
            rset = stmt.executeQuery ("SELECT * FROM test_jdbc ORDER BY test_jdbc_intr_no");

            while (rset.next ()) {

                int     rowNumber;
                int     test_jdbc_intr_no;
                String  test_jdbc_name;
                String  test_jdbc_null_value;

                rowNumber = rset.getRow();

                test_jdbc_intr_no = rset.getInt(1);
                if ( rset.wasNull() ) {
                    test_jdbc_intr_no = -1;
                }

                test_jdbc_name = rset.getString(2);
                if ( rset.wasNull() ) {
                    test_jdbc_name = "<null>";
                }

                test_jdbc_null_value = rset.getString(3);
                if ( rset.wasNull() ) {
                    test_jdbc_null_value = "<null>";
                }

                System.out.print(
                    "  RESULTS            -> [R" + rowNumber + "] " + 
                    test_jdbc_intr_no + " - " + test_jdbc_name +
                    " - " + test_jdbc_null_value + "\n");
            }


            /*
             * -----------------------------------------------------------------
             *  DELETE ALL RECORDS FROM TABLE
             * -----------------------------------------------------------------
             */

            System.out.print("\n");
            System.out.print("+-------------------------------+\n");
            System.out.print("| DELETE ALL RECORDS FROM TABLE |\n");
            System.out.print("+-------------------------------+\n");
            System.out.print("\n");

            deleteResults = stmt.executeUpdate("DELETE FROM test_jdbc");
            System.out.print("  RESULTS            -> " + deleteResults + " rows deleted.\n");


            /*
             * -----------------------------------------------------------------
             *  COMMIT TRANSACTION
             * -----------------------------------------------------------------
             */
            System.out.print("Commiting Transaction...\n");
            con.commit();


            /*
             * -----------------------------------------------------------------
             *  DROP TABLE
             * -----------------------------------------------------------------
             */
            System.out.print("\n");
            System.out.print("+-------------------------------+\n");
            System.out.print("| DROP TABLE                    |\n");
            System.out.print("+-------------------------------+\n");
            System.out.print("\n");

            System.out.print("Dropping Table\n");
            stmt.executeUpdate("DROP TABLE test_jdbc");


            /*
             * -----------------------------------------------------------------
             *  CLOSE RESULTSET AND STATEMENT OBJECTS
             * -----------------------------------------------------------------
             */
            System.out.println();
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

        DDLExample ddlExample = new DDLExample();
        ddlExample.performDDL();
        ddlExample.closeConnection();

    }

}
