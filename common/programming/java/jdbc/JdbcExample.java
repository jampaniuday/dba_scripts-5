// -----------------------------------------------------------------------------
// JdbcExample.java
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
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * -----------------------------------------------------------------------------
 * The following class provides a convenient template which demonstrates some of
 * the more helpful JDBC API calls while using an Oracle Database. The methods
 * defined in this class will be used to create a test table in the SCOTT 
 * schema. A set of random values will then be inserted into the new table and 
 * then queried back using basic JDBC calls. The random values being used to 
 * populate the test table will come from the dictionary view ALL_OBJECTS.
 * <p>
 * This class is useful in demonstrating how to fetch integers, floating-point
 * numbers, strings, and dates from a result set and furthermore, how to 
 * manipulate and print the data from the database.
 * <p>
 * The class concludes by dropping the test table and then logging off from the
 * database.
 * <p>
 * @author Jeffrey Hunter, OCP
 * @author <a href="mailto:jhunter@iDevelopment.info">jhunter@iDevelopment.info</a>
 * @author <a target="_blank" href="http://www.iDevelopment.info">www.iDevelopment.info</a>
 * @version 1.0,  &nbsp; 08-MAY-2009
 * @since SDK1.5
 * -----------------------------------------------------------------------------
 */

public class JdbcExample {

    final static String driverClass = "oracle.jdbc.driver.OracleDriver";
    final static String connectionURL = "jdbc:oracle:thin:@vmlinux1:1521:TESTDB1";
    final static String userID = "scott";
    final static String userPassword = "tiger";
    Connection oraConnection  = null;
    SimpleDateFormat datePrintFormatPattern = null;
    SimpleDateFormat dateOracleFormatPattern = null;
    DecimalFormat decimalFormatPattern = null;


    /**
     * Custom application exception class.
     */
    class JdbcExampleException extends Exception {
        
        /**
         * Internal error number for exception. Value will be -1 if not
         * specified during object creation.
         */
        private int intError;
        
        /**
         * Default constructor used to create a JdbcExampleException exception
         * object.
         */
         JdbcExampleException() {
         }

        /**
         * Constructor used to create a JdbcExampleException exception object
         * specifying only the exception message string.
         * @param strMessage  Message text for the custom application exception.
         */
        JdbcExampleException(String strMessage) {
            super(strMessage);
        }

        /**
         * Constructor used to create a JdbcExampleException exception object
         * specifying only the internal error number for the exception.
         * @param intErrorNum  Internal error number for the custom application
         *                     exception.
         */
        JdbcExampleException(int intErrorNum) {
            intError = intErrorNum;
        }

        /**
         * Constructor used to create a JdbcExampleException exception object
         * specifying both the exception message string and the internal error 
         * number for the exception.
         * @param strMessage   Message text for the custom application exception.
         * @param intErrorNum  Internal error number for the custom application
         *                     exception.
         */
        JdbcExampleException(String strMessage, int intErrorNum) {
            super(strMessage);
            intError = intErrorNum;
        }

        /**
         * Returns a custom string representation of the object.
         * @return  The string which represents the object.
         */
        public String toString() {
            return "JdbcExampleException ["+intError+"]";
        }

    }


    /**
     * Sole constructor used to create a JdbcExample object. This constructor
     * will create a database connection to an Oracle database using static 
     * class members.
     * <p>
     * It is also responsible for setting other member variables used by 
     * instances of this class.
     * @throws JdbcExampleException  Custom application exception thrown when 
     *                               handling exceptions from the JDBC API
     *                               classes.
     */
    public JdbcExample() throws JdbcExampleException {

        datePrintFormatPattern = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        dateOracleFormatPattern = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a zZ");
        decimalFormatPattern = new DecimalFormat("#,###.##");
        
        try {

            System.out.print("  Loading JDBC Driver    -> " + driverClass + "\n");
            Class.forName(driverClass).newInstance();

            System.out.print("  Connecting to          -> " + connectionURL + "\n");
            this.oraConnection = DriverManager.getConnection(connectionURL, userID, userPassword);
            System.out.print("  Connected as           -> " + userID + "\n");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JdbcExampleException("ERROR: Class Not Found");
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new JdbcExampleException("ERROR: Instantiation Error");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new JdbcExampleException("ERROR: Illegal Access");
        } catch (SQLException sqlException) {
            handleDatabaseException(sqlException, "Loading JDBC Driver", 1001);
        }

    }


    /**
     * Create a new test table named "JDBC_EXAMPLE". If the table already 
     * exists, it will be dropped along with all of the data in that table.
     * @throws JdbcExampleException  Custom application exception thrown when 
     *                               handling exceptions from the JDBC API
     *                               classes.
     */
    public void createTable() throws JdbcExampleException {

        Statement stmt1 = null;
        ResultSet rset1 = null;

        String checkTableSQL = "SELECT 'EXISTS' " +
                               "FROM user_tables " +
                               "WHERE table_name = 'JDBC_EXAMPLE'";
        
        String createTableSQL =  "CREATE TABLE jdbc_example (" +
                                 "    object_id             NUMBER(15) " +
                                 "  , object_name           VARCHAR2(100) " +
                                 "  , real_number           NUMBER(15,2) " +
                                 "  , create_date           DATE " +
                                 "  , null_date             DATE " +
                                 "  , null_value            VARCHAR2(100))";
                                   
        try {

            stmt1 = oraConnection.createStatement();
            rset1 = stmt1.executeQuery(checkTableSQL);

            if (rset1.next()) {
                if (rset1.getString(1).equals("EXISTS")) {
                    System.out.print("  Found Existing Table   -> Calling dropTable()\n");
                    dropTable();
                }
            }

            rset1.close();
            stmt1.close();
            
            System.out.print("  Creating Table         -> ");
            stmt1 = oraConnection.createStatement();
            stmt1.executeUpdate(createTableSQL);
            System.out.print("Table created.\n");

        } catch (SQLException sqlException) {
            if (sqlException.getErrorCode() == 955) {
                // ORA-00955: name is already used by an existing object
                System.out.print("Table already exists!\n");
                throw new JdbcExampleException("ERROR: It appears the table \"jdbc_example\" still exists!");
            } else {
                handleDatabaseException(sqlException, "Creating Table \"jdbc_example\"", 1002);
            }
        } finally {
            try {
                stmt1.close();
            } catch (SQLException sqlException) {
                handleDatabaseException(sqlException, "ERROR: Closing statement.", 1003);
            }
        }

    }


    /**
     * Generate a set of random values (sourced from the data dictionary view
     * ALL_OBJECTS), that will be used to populate the new test table.
     * @throws JdbcExampleException  Custom application exception thrown when 
     *                               handling exceptions from the JDBC API
     *                               classes.
     */
    public void populateTable() throws JdbcExampleException {

        Statement stmt = null;
        int insertResults = 0;
        int totalRecordsInserted = 0;
        Date startDate;
        long startTimeMillis = 0;
        long endTimeMillis = 0;

        String insertSQLStatement = "INSERT INTO jdbc_example ( " +
                                    "      object_id " +
                                    "    , object_name " +
                                    "    , real_number " +
                                    "    , create_date " +
                                    "    , null_date " +
                                    "    , null_value) " +
                                    "  SELECT " +
                                    "      object_id " +
                                    "    , object_name " +
                                    "    , 3.14 " +
                                    "    , created " +
                                    "    , null " +
                                    "    , null " +
                                    "  FROM all_objects";
        
        try {

            startTimeMillis = System.currentTimeMillis();
            startDate = new Date(startTimeMillis);
            System.out.println();
            System.out.print("  Start Date / Time      -> " + datePrintFormatPattern.format(startDate) + "\n");

            System.out.print("  Populating Table       -> \n\n");

            stmt = oraConnection.createStatement();
            
            for (int i=0; i < 75; i++) {            
                insertResults = stmt.executeUpdate(insertSQLStatement);
                totalRecordsInserted += insertResults;
                System.out.print(".");
            }
    
            oraConnection.commit();
            System.out.println("\n");
            System.out.print("  # Rows Processed       -> " + decimalFormatPattern.format(totalRecordsInserted) + "\n");

            endTimeMillis = System.currentTimeMillis();
            printElapsedTime(startTimeMillis, endTimeMillis);

        } catch (SQLException sqlException) {
            handleDatabaseException(sqlException, "Populating Table \"jdbc_example\"", 1004);
        } finally {
            try {
                stmt.close();
            } catch (SQLException sqlException) {
                handleDatabaseException(sqlException, "ERROR: Closing statement.", 1005);
            }
        }

    }


    /**
     * Perform a simple query from the new test table while capturing and
     * displaying timings.
     * @throws JdbcExampleException  Custom application exception thrown when 
     *                               handling exceptions from the JDBC API
     *                               classes.
     */
    public void performQuery() throws JdbcExampleException {

        Statement stmt = null;
        ResultSet rset = null;
        int rowCount = 0;
        Date startDate;
        long startTimeMillis = 0;
        long endTimeMillis = 0;
        int numTableRowsPerDot = 1000;
        int numDotsPerLine = 80;
        String queryString  = "SELECT " +
                              "   object_id " +
                              "  , object_name " +
                              "  , real_number " +
                              "  , create_date " +
                              "  , null_date " +
                              "  , null_value " +
                              "FROM jdbc_example " +
                              "ORDER BY object_id";

        try {

            startTimeMillis = System.currentTimeMillis();
            startDate = new Date(startTimeMillis);
            System.out.println();
            System.out.print("  Start Date / Time      -> " + datePrintFormatPattern.format(startDate) + "\n");

            stmt = oraConnection.createStatement();
            rset = stmt.executeQuery(queryString);
            ResultSetMetaData rsMeta = rset.getMetaData();
            System.out.print("  # of Columns in Query  -> " + rsMeta.getColumnCount() + "\n");

            while (rset.next()) {

                int rowNumber = rset.getRow();
                rowCount++;

                if (rowCount == 1) {
                    System.out.println();
                    System.out.println("  Printing First Row...");
                    System.out.println("  Row Number [" + rowNumber + "]");
                    System.out.println("  Row Count  [" + rowCount + "]");
                    System.out.println("  ---------------------");

                    int objectId = rset.getInt(1);
                    if (rset.wasNull()) {objectId = -1;}

                    String objectName = rset.getString(2);
                    if (rset.wasNull()) {objectName = "<null>";}
                    
                    float realNumber = rset.getFloat(3);
                    if (rset.wasNull()) {realNumber = 0.0f;}
                    
                    Date createDate = rset.getTimestamp(4);
                    if (rset.wasNull()) {createDate = new Date(24L*60L*60L*1000L);} // 1/1/1970 GMT

                    Date nullDateValue = rset.getTimestamp(5);
                    if (rset.wasNull()) {nullDateValue = new Date(24L*60L*60L*1000L);} // 1/1/1970 GMT

                    String nullStringValue = rset.getString(6);
                    if (rset.wasNull()) {nullStringValue = "<null>";}

                    System.out.println("      Object ID          -> " + objectId);
                    System.out.println("      Object Name        -> " + objectName);
                    System.out.println("      Real Number        -> " + realNumber);
                    System.out.println("      Create Date        -> " + dateOracleFormatPattern.format(createDate));
                    System.out.println("      Null Date          -> " + dateOracleFormatPattern.format(nullDateValue));
                    System.out.println("      Null Value         -> " + nullStringValue);
                    System.out.println();
                }

                if ((rowCount % numTableRowsPerDot) == 0 ) {
                    System.out.print(".");
                }

                if ((rowCount % (numTableRowsPerDot*numDotsPerLine)) == 0) {
                    System.out.println(" (" + decimalFormatPattern.format(rowCount) + " rows processed.)");
                }

            }

            rset.close();
            stmt.close();
                                                                                 
            System.out.println("\n");
            System.out.print("  # Rows Processed       -> " + decimalFormatPattern.format(rowCount) + "\n");
            
            endTimeMillis = System.currentTimeMillis();
            printElapsedTime(startTimeMillis, endTimeMillis);

        } catch (SQLException sqlException) {
            handleDatabaseException(sqlException, "Performing Query", 1006);
        }

    }


    /**
     * Drop the table test table.
     * @throws JdbcExampleException  Custom application exception thrown when 
     *                               handling exceptions from the JDBC API
     *                               classes.
     */
    public void dropTable() throws JdbcExampleException {

        Statement stmt = null;

        String dropTableSQL = "DROP TABLE jdbc_example";

        try {

            System.out.print("  Dropping Table         -> ");
            stmt = oraConnection.createStatement();
            stmt.executeUpdate(dropTableSQL);
            System.out.print("Table dropped.\n");

        } catch (SQLException sqlException) {
            handleDatabaseException(sqlException, "Dropping Table \"jdbc_example\"", 1007);
        } finally {
            try {
                stmt.close();
            } catch (SQLException sqlException) {
                handleDatabaseException(sqlException, "ERROR: Closing statement.", 1008);
            }
        }

    }


    /**
     * Close down the database connection.
     * @throws JdbcExampleException  Custom application exception thrown when 
     *                               handling exceptions from the JDBC API
     *                               classes.
     */
    public void closeConnection() throws JdbcExampleException {

        try {
            System.out.print("  Closing Connection     -> ");
            if (this.oraConnection != null) {
                this.oraConnection.close();
            }
            System.out.print("Done.\n");
        } catch (SQLException sqlException) {
            handleDatabaseException(sqlException, "Loading JDBC Driver", 1009);
        }

    }


    /**
     * Used to handle all SQL exceptions. First, print the exception stack 
     * trace. Next, print the database message and error code from the
     * SQLException. Finally throw a new JdbcExampleException exception to the
     * main program.
     * @param exception              SQLException object which holds the 
     *                               database exception that will be handled by
     *                               this method.
     * @param strMessage             Message text for the custom application
     *                               exception.
     * @param intErrorNum            Internal error number for the custom
     *                               application exception.
     * @throws JdbcExampleException  Custom application exception thrown when 
     *                               handling exceptions from the JDBC API
     *                               classes.
     */
    public void handleDatabaseException(SQLException exception, String strMessage, int intErrorNum)
        throws JdbcExampleException {
        
        System.err.println();
        System.err.println("+-------------------------------------------+");
        System.err.println("| Printing Exception Trace                  |");
        System.err.println("+-------------------------------------------+");
        exception.printStackTrace();
        
        System.err.println();
        System.err.println("+-------------------------------------------+");
        System.err.println("| Database Error Message                    |");
        System.err.println("+-------------------------------------------+");
        // System.err.println("Message    : " + exception.getMessage());
        System.err.println("Error Code : " + exception.getErrorCode());
        System.err.println("SQL State  : " + exception.getSQLState());

        System.err.println();
        System.err.println("+-------------------------------------------+");
        System.err.println("| Throwing Application Exception            |");
        System.err.println("+-------------------------------------------+");
        throw new JdbcExampleException("ERROR: " + strMessage, intErrorNum);

    }


    /**
     * Used to handle all SQL exceptions that do not specify an internal error
     * number for the custom application exception. This method will simply
     * call the handleDatabaseException method by passing a value of -1
     * for the internal error number for the exception.
     * @param exception              SQLException object which holds the 
     *                               database exception that will be handled by
     *                               this method.
     * @param strMessage             Message text for the custom application
     *                               exception.
     * @throws JdbcExampleException  Custom application exception thrown when 
     *                               handling exceptions from the JDBC API
     *                               classes.
     */
    public void handleDatabaseException(SQLException exception, String strMessage)
        throws JdbcExampleException {
        handleDatabaseException(exception, strMessage, -1);
    }


    /**
     * Using a start/stop time (in milliseconds), print the total duration
     * between the two values in an easy to read format.
     * @param startTimeMillis  Start time in milliseconds.
     * @param endTimeMillis    Start time in milliseconds.
     */
    public void printElapsedTime(long startTimeMillis, long endTimeMillis) {

        long totalTimeMillis;

        totalTimeMillis = (endTimeMillis - startTimeMillis);
        long totalTimeSeconds = totalTimeMillis / 1000;
        String totalTimeSecondsStr = Integer.toString((int)(totalTimeSeconds % 60));  
        String totalTimeMinutesStr = Integer.toString((int)((totalTimeSeconds % 3600) / 60));  
        String totalTimeHoursStr = Integer.toString((int)(totalTimeSeconds / 3600));  
        for (int i = 0; i < 2; i++) {  
            if (totalTimeSecondsStr.length() < 2) {  
                totalTimeSecondsStr = "0" + totalTimeSecondsStr;  
            }  
            if (totalTimeMinutesStr.length() < 2) {
                totalTimeMinutesStr = "0" + totalTimeMinutesStr;
            }
            if (totalTimeHoursStr.length() < 2) {
                totalTimeHoursStr = "0" + totalTimeHoursStr;
            }
        }

        System.out.print("  Processing Time        -> " +
                         totalTimeHoursStr + " hours " +
                         totalTimeMinutesStr  + " minutes " +
                         totalTimeSecondsStr + " seconds\n");
        System.out.print("                         -> " + totalTimeMillis + " milliseconds\n");

    }


    /**
     * Sole entry point to the class and application.
     * @param args  Array of String arguments.
     */
    public static void main(String[] args) {

        JdbcExample jdbcExample = null;
        
        try {
            jdbcExample = new JdbcExample();
            jdbcExample.createTable();
            jdbcExample.populateTable();
            jdbcExample.performQuery();
            jdbcExample.dropTable();
        } catch (JdbcExampleException e) {
            System.err.println();
            System.err.println("+-------------------------------------------+");
            System.err.println("| Caught application exception in main().   |");
            System.err.println("| Printing trace.                           |");
            System.err.println("+-------------------------------------------+");
            e.printStackTrace();
        } finally {
            if (jdbcExample.oraConnection != null) {
                try {
                    jdbcExample.closeConnection();            
                } catch (JdbcExampleException e) {
                    e.printStackTrace();
                }
            }
        } // finally
    } // main()

}
