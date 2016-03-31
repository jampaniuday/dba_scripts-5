// -----------------------------------------------------------------------------
// JdbcExampleThin.java
// -----------------------------------------------------------------------------

package JdbcExamples;

import java.sql.*;

/**
 * Used to test the functionality of the JDBC API. This example uses
 * the Oracle JDBC thin driver to perform the following actions:
 * <ul>
 *      <li> Obtain a connection to an Oracle database.
 *      <li> Create a table to perform all DML and DDL tests against.
 *      <li> Insert some same date into the test tables.
 *      <li> Perform two different types of queries against the test table (1)
 *           default FORWARD and (2) using a REVERSE scrollable result set.
 *      <li> Delete all records from the test table.
 *      <li> Drop the test table.
 *      <li> Disconnect from the database.
 * </ul>
 * <p>
 * @author Jeffrey Hunter
 * @author <a href="mailto:jhunter@iDevelopment.info">jhunter@iDevelopment.info</a>
 * @author <a target="_blank" href="http://www.iDevelopment.info">www.iDevelopment.info</a>
 * @version 2.0,  &nbsp; 26-SEP-2004
 * @since SDK1.4
 */
 
public class JdbcExampleThin  {

    static final String programName = "JdbcExamples.JdbcExampleThin";
    
    String      driver_class  = null;
    String      connectionURL = null;
    String      userID        = null;
    String      userPassword  = null;
    Connection  conn          = null;


    /**
     * Sole constructor used to create this object. This method will first
     * walk through each of the command-line arguments to determine if the user
     * supplied all required arguments in order to construct the JDBC URL.
     * After verifying all required command-line parameters, this constructor 
     * will set attributes for the JDBC Connection object.
     * @param args Array of String of command-line arguments passed in for the
     *             program.
     */
    public JdbcExampleThin(String[] args) {

        boolean foundSid       = false;
        boolean foundUser      = false;
        boolean foundPassword  = false;
        
        String  host      = "localhost";
        String  sid       = null;
        String  listener  = "1521";
        String  user      = null;
        String  password  = null;

        // ------------------------------------
        // Did the user request help?
        // ------------------------------------
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("-help")) {
                printUsage();
            }
            if (args[0].equalsIgnoreCase("--help")) {
                printUsage();
            }
        }

        // ------------------------------------------------
        // Walk through each of the command-line parameters
        // ------------------------------------------------
        for (int i=0; i < args.length; i++) {
        
            if (args[i].equalsIgnoreCase("-host")) {
                host = args[++i];
            }

            if (args[i].equalsIgnoreCase("-sid")) {
                sid = args[++i];
                foundSid = true;
            }

            if (args[i].equalsIgnoreCase("-listener")) {
                listener = args[++i];
            }

            if (args[i].equalsIgnoreCase("-user")) {
                user = args[++i];
                foundUser = true;
            }

            if (args[i].equalsIgnoreCase("-password")) {
                password = args[++i];
                foundPassword = true;
            }

        }

        // --------------------------------------------------------
        // Are all of the required values for this application
        // set? If not, exit out of the application with a usage
        // statement to the user.
        // --------------------------------------------------------
        if (!foundSid || !foundUser || !foundPassword) {
            printUsage();
        }

        driver_class  = "oracle.jdbc.driver.OracleDriver";
        connectionURL = "jdbc:oracle:thin:@" + host + ":" + listener + ":" + sid;
        userID        = user;
        userPassword  = password;
    }


    /**
     * This method prints out the usage message for the application when the 
     * user requests it or when the correct parameters were not supplied.
     */
    private static void printUsage() {
        System.out.println(
            "Usage:\n\n" +
            "java " + programName + "\n" +
            "     -sid           Oracle SID\n" +
            "     -user          database user with DBA privileges\n" +
            "     -password      database user password\n" +
            "     [options]\n\n" +
            "Options: \n" +
            "      -host         hostname           (defaults to localhost)\n" +
            "      -listener     TNS Listener Port  (defaults to 1521)\n" +
            "\n"
        );
        System.exit(1);
    }

    /**
     * Obtain a JDBC Connection using the attributes set by the constructor.
     * @exception java.sql.SQLException             for all SQL statements.
     * @exception java.lang.ClassNotFoundException  if the Driver Class cannot
     *                                              be found.
     * @exception java.lang.IllegalAccessException  if the Driver Class cannot
     *                                              be accessed.
     * @exception java.lang.InstantiationException  if the Driver Class cannot
     *                                              be initiated.
     */
    public void getConnection()
        throws    SQLException
                , ClassNotFoundException
                , IllegalAccessException
                , InstantiationException {
        
        System.out.print("\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("| SETUP CONNECTION              |\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("\n");

        /*
        ** LOAD AND REGISTER THE JDBC DRIVER
        ** There are two basic ways to handle this:
        **   - DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        **   - Class.forName ("oracle.jdbc.driver.OracleDriver").newInstance();
        */
        System.out.print("Loading JDBC Driver  -> " + driver_class + "\n");
        Class.forName (driver_class).newInstance();

        System.out.print("Connecting to        -> " + connectionURL + "\n");
        conn = DriverManager.getConnection(connectionURL, userID, userPassword);
        System.out.print("Connected as         -> " + userID + "\n");

        System.out.print("Turning Off AutoCommit...\n");
        conn.setAutoCommit(false);        
        
    }


    /**
     * Create a test table that can be used to demonstrate all DML and DDL
     * commands throughout this example.
     * @exception java.sql.SQLException for all SQL statements.
     */
    public void createTable() throws SQLException {

        Statement statement = null;
        ResultSet resultSet = null;
        
        System.out.print("\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("| CREATE TABLE                  |\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("\n");

        System.out.print("Creating Table [TEST_JDBC]\n");

        System.out.print("Creating Statement...\n");
        statement = conn.createStatement();

        statement.executeUpdate(
                "CREATE TABLE test_jdbc (" +
                "    test_jdbc_intr_no     NUMBER(15) " +
                "  , test_jdbc_name        VARCHAR2(100) " +
                "  , test_jdbc_null_value  VARCHAR2(100))");
        System.out.print("Table Created...\n");

        System.out.print("Closing Statement...\n");
        statement.close();

    }


    /**
     * Insert sample rows into the test table.
     * @exception java.sql.SQLException for all SQL statements.
     */
    public void insertValues() throws SQLException {

        Statement statement = null;
        int       insertResults;
        
        System.out.print("\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("| INSERT VALUES                 |\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("\n");
        
        System.out.print("Inserting Values into Table\n");
        
        System.out.print("Creating Statement...\n");
        statement = conn.createStatement();
        
        insertResults = statement.executeUpdate(
                "INSERT INTO test_jdbc VALUES(" +
                "    100" +
                "  , 'Jeffrey Hunter'" +
                "  , null)");
        System.out.print("  RESULTS            -> " + insertResults + " row created.\n");
        insertResults = statement.executeUpdate("INSERT INTO test_jdbc VALUES(" +
                "    200" +
                "  , 'Melody Hunter'" +
                "  , null)");
        System.out.print("  RESULTS            -> " + insertResults + " row created.\n");
        insertResults = statement.executeUpdate("INSERT INTO test_jdbc VALUES(" +
                "    300" +
                "  , 'Alex Hunter'" +
                "  , null)");
        System.out.print("  RESULTS            -> " + insertResults + " row created.\n");
        insertResults = statement.executeUpdate("INSERT INTO test_jdbc VALUES(" +
                "    400" +
                "  , 'Mackenzie Leskovac'" +
                "  , null)");
        System.out.print("  RESULTS            -> " + insertResults + " row created.\n");
        insertResults = statement.executeUpdate("INSERT INTO test_jdbc VALUES(" +
                "    500" +
                "  , 'Lauren Leskovac'" +
                "  , null)");
        System.out.print("  RESULTS            -> " + insertResults + " row created.\n");
        
        System.out.print("Closing Statement...\n");
        statement.close();
        
        /*
        ** COMMIT TRANSACTION
        */
        System.out.print("Commiting Transaction...\n");
        conn.commit();

    }


    /**
     * Execute a query against the test table using the default (FORWARD)
     * option. The option (although default) is specified on the Connection
     * object.
     * @exception java.sql.SQLException for all SQL statements.
     */
    public void queryForward() throws SQLException {

        Statement   statement       = null;
        ResultSet   rset            = null;
        String      queryString     = "SELECT * " +
                                      "FROM test_jdbc " +
                                      "ORDER BY test_jdbc_intr_no";

        System.out.print("\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("| EXECUTE QUERY (Forward)       |\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("\n");
        
        System.out.print("Executing Forward (NORMAL) Query...\n");
        
        System.out.print("  USING DEFAULTS:\n");
        System.out.print("    - (P1) ResultSet.TYPE_FORWARD_ONLY\n");
        System.out.print("    - (P2) ResultSet.CONCUR_READ_ONLY\n");
        statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        
        System.out.print("  Opening ResultsSet...\n");
        rset = statement.executeQuery(queryString);
        
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
        
            System.out.print("    RESULTS            -> [R" + rowNumber + "] " + test_jdbc_intr_no + " - " + test_jdbc_name + " - " + test_jdbc_null_value + "\n");
        }
        
        System.out.print("Closing ResultSet...\n");
        rset.close();
        
        System.out.print("Closing Statement...\n");
        statement.close();

    }


    /**
     * Execute a query against the test table using the REVERSE option of
     * a scrollable ResultSet.
     * @exception java.sql.SQLException for all SQL statements.
     */
    public void queryReverse() throws SQLException {

        Statement statement = null;
        ResultSet rset      = null;
        
        System.out.print("\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("| EXECUTE QUERY (Reverse)       |\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("\n");
        
        System.out.print("Executing Reverse (SCROLLABLE) Query...\n");
        System.out.print("  USING:\n");
        System.out.print("    - (P1) ResultSet.TYPE_SCROLL_INSENSITIVE\n");
        System.out.print("    - (P2) ResultSet.CONCUR_READ_ONLY\n");
        statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
        System.out.print("  Opening ResultsSet...\n");
        rset = statement.executeQuery ("SELECT * FROM test_jdbc ORDER BY test_jdbc_intr_no");
        
        System.out.print("    - Got results. The ResultPointer Pointer is before the first row.\n");
        System.out.print("    - Position ResultSet Pointer after the last row.\n");
        rset.afterLast();
        
        while (rset.previous ()) {
        
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
            
            System.out.print("    RESULTS            -> [R" + rowNumber + "] " + test_jdbc_intr_no + " - " + test_jdbc_name + " - " + test_jdbc_null_value + "\n");
        }
        
        System.out.print("Closing ResultSet...\n");
        rset.close();
        
        System.out.print("Closing Statement...\n");
        statement.close();
        
    }


    /**
     * Delete all rows from the test table.
     * @exception java.sql.SQLException for all SQL statements.
     */
    public void deleteRows() throws SQLException {

        Statement statement = null;
        int deleteResults;
        
        System.out.print("\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("| DELETE ALL RECORDS FROM TABLE |\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("\n");
        
        System.out.print("Delete All Records From Table\n");
        
        System.out.print("Creating Statement...\n");
        statement = conn.createStatement();
        
        deleteResults = statement.executeUpdate("DELETE FROM test_jdbc");
        System.out.print("  RESULTS            -> " + deleteResults + " rows deleted.\n");
        
        System.out.print("Closing Statement...\n");
        statement.close();
        
        System.out.print("Commiting Transaction...\n");
        conn.commit();

    }


    /**
     * Drop the test table.
     * @exception java.sql.SQLException for all SQL statements.
     */
    public void dropTable() throws SQLException {

        Statement statement = null;
        
        System.out.print("\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("| DROP TABLE                    |\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("\n");
        
        System.out.print("Creating Statement...\n");
        statement = conn.createStatement();
        
        System.out.print("Dropping Table\n");
        statement.executeUpdate("DROP TABLE test_jdbc");
        
        System.out.print("Closing Statement...\n");
        statement.close();
        
    }


    /**
     * Perform a generic query using all default options for the Statement
     * object.
     * @exception java.sql.SQLException for all SQL statements.
     */
    public void genericQuery() throws SQLException {

        Statement   statement     = null;
        ResultSet   rset          = null;
        String      query_string  = "select 'Hello JDBC: ' || " +
                                    "TO_CHAR(sysdate, 'DD-MON-YYYY HH24:MI:SS') " +
                                    "from dual";
            
        System.out.print("\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("| EXECUTE GENERIC QUERY         |\n");
        System.out.print("+-------------------------------+\n");
        System.out.print("\n");
        
        System.out.print("Executing Generic (SYSDATE) Query...\n");
        
        System.out.print("Creating Statement...\n");
        statement = conn.createStatement();
        
        System.out.print("Opening ResultsSet...\n");
        rset = statement.executeQuery(query_string);
        
        while (rset.next ()) {
            System.out.print("  RESULTS            -> " + rset.getString (1) + "\n");
        }
        
        System.out.print("Closing ResultSet...\n");
        rset.close();
        
        System.out.print("Closing Statement...\n");
        statement.close();
        
    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        
        JdbcExampleThin jdbcExampleThin = null;
        
        try {
            
            jdbcExampleThin = new JdbcExampleThin(args);
            jdbcExampleThin.getConnection();
            jdbcExampleThin.createTable();
            jdbcExampleThin.insertValues();
            jdbcExampleThin.queryForward();
            jdbcExampleThin.queryReverse();
            jdbcExampleThin.deleteRows();
            jdbcExampleThin.dropTable();
            jdbcExampleThin.genericQuery();
            
        } catch (SQLException e) {
            System.out.println("ERROR: SQLException");
            if (jdbcExampleThin.conn != null) {
                try {
                    jdbcExampleThin.conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: ClassNotFoundException");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("ERROR: IllegalAccessException");
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("ERROR: InstantiationException");
            e.printStackTrace();
        } finally {
            if (jdbcExampleThin.conn != null) {
                try {
                    System.out.print("\n");
                    System.out.print("+-------------------------------+\n");
                    System.out.print("| CLOSING CONNECTION            |\n");
                    System.out.print("+-------------------------------+\n");
                    System.out.print("\n");
                    jdbcExampleThin.conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

}
