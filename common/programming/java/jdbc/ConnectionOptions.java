// -----------------------------------------------------------------------------
// ConnectionOptions.java
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
import java.util.Properties;

/**
 * -----------------------------------------------------------------------------
 * The following class provides an example of using JDBC to connect to an
 * Oracle database using several advanced options. For example, one of the most
 * used option is connecting to a database as SYSDBA. The following table
 * contains 
 * 
 * Connection Properties Recognized by Oracle JDBC Drivers 
 * -------------------------------------------------------
 * Name                Short Name Type     Description  
 * ------------------- ---------- -------- -----------------------------------
 * user                n/a        String   The user name for logging into the
 *                                         database.
 * password            n/a        String   The password for logging into the
 *                                         database.
 * database            server     String   The connect string for the database.
 * internal_logon      n/a        String   A role, such as SYSDBA or SYSOPER,
 *                                         that allows you to log on as SYS.
 * defaultRowPrefetch  prefetch   String   (containing integer value)  
 *                                         The default number of rows to
 *                                         prefetch from the server.
 *                                         (default value is "10")  
 * remarksReporting    remarks    String   (containing boolean value)  
 *                                         "true" if getTables() and 
 *                                         getColumns() should report 
 *                                         TABLE_REMARKS; equivalent to using 
 *                                         setRemarksReporting().
 *                                         (default value is "false")  
 * defaultBatchValue   batchvalue String   (containing integer value)  
 *                                         The default batch value that triggers
 *                                         an execution request.
 *                                         (default value is "10")  
 * includeSynonyms     synonyms   String   (containing boolean value)
 *                                         "true" to include column information 
 *                                         from predefined "synonym" SQL 
 *                                         entities when you execute a 
 *                                         DataBaseMetaData getColumns() call; 
 *                                         equivalent to connection 
 *                                         setIncludeSynonyms() call.
 *                                         (default value is "false")  
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ConnectionOptions {

    final static String driverClass    = "oracle.jdbc.driver.OracleDriver";
    final static String connectionURL  = "jdbc:oracle:thin:@localhost:1521:TRUESRC";
    final static String userID         = "scott";
    final static String userPassword   = "tiger";
    Connection   con                   = null;


    /**
     * Construct a QueryExample object. This constructor will create an Oracle
     * database connection.
     */
    public ConnectionOptions() {

        Properties conProps = new Properties();
        conProps.put("user", userID);
        conProps.put("password", userPassword);
        conProps.put("defaultRowPrefetch", "15");
        conProps.put("internal_logon", "sysdba");

        try {

            System.out.print("  Loading JDBC Driver  -> " + driverClass + "\n");
            Class.forName(driverClass).newInstance();

            System.out.print("  Connecting to        -> " + connectionURL + "\n");
            this.con = DriverManager.getConnection(connectionURL, conProps);
            System.out.print("  Connected as         -> " + userID + "\n");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }


    /**
     * Method to check which database user we are logged in as
     */
    public void performUserQuery() {

        Statement stmt      = null;
        ResultSet rset      = null;
        String queryString  = "SELECT user, TO_CHAR(sysdate, 'DD-MON-YYYY HH24:MI:SS') " +
                              "FROM   dual";

        try {

            System.out.print("  Creating Statement...\n");
            stmt = con.createStatement ();

            System.out.print("  Opening ResultsSet...\n");
            rset = stmt.executeQuery(queryString);

            rset.next();
            System.out.println("      Database User  -> " + rset.getString(1));
            System.out.println("      Date / Time    -> " + rset.getString(2));

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
     */
    public static void main(String[] args) {

        ConnectionOptions co = new ConnectionOptions();
        co.performUserQuery();
        co.closeConnection();

    }

}
