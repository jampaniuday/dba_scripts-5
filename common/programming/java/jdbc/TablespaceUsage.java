// -----------------------------------------------------------------------------
// TablespaceUsage.java
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

import java.text.NumberFormat;

/**
 * -----------------------------------------------------------------------------
 * The following class allows the user to query the "true" size (in bytes) of
 * selected tablespaces.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class TablespaceUsage {

    final static String DELIM          = ", ";
    final static int    blockSize      = 8192;

    public String       driverClass    = "oracle.jdbc.driver.OracleDriver";
    public String       connectionURL  = null;
    public String       dbServer       = null;
    public String       dbPort         = null;
    public String       oraSID         = null;
    public String       userID         = null;
    public String       userPassword   = null;
    public Connection   con            = null;


    /**
     * Construct a TablespaceUsage object. This constructor will create an
     * Oracle database connection.
     */
    public TablespaceUsage(String[] args) {

        dbServer      = args[0];
        dbPort        = args[1];
        oraSID        = args[2];
        userID        = args[3];
        userPassword  = args[4];

        connectionURL    = "jdbc:oracle:thin:@" + dbServer + ":" + dbPort + ":" + oraSID;

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
     * Method to perform the segment size calculations on given tablespaces
     */
    public void performQuery() {

        NumberFormat    defaultFormat  = NumberFormat.getInstance();
        Statement       stmt            = null;
        ResultSet       rset            = null;

        String queryString  = "SELECT " + 
                              " d.status, d.tablespace_name, d.contents, d.extent_management, NVL(a.bytes, 0), NVL(a.bytes - NVL(f.bytes, 0), 0), NVL((a.bytes - NVL(f.bytes, 0)) / a.bytes * 100, 0) pct_used" +
                              " FROM  sys.dba_tablespaces d " + 
                              "      , ( select tablespace_name, sum(bytes) bytes " +
                              "          from dba_data_files " +
                              "          group by tablespace_name " + 
                              "        ) a " + 
                              "      , ( select tablespace_name, sum(bytes) bytes " +
                              "          from dba_free_space " + 
                              "          group by tablespace_name " + 
                              "        ) f " + 
                              "WHERE " +
                              "      d.tablespace_name = a.tablespace_name(+) " +
                              "  AND d.tablespace_name = f.tablespace_name(+) " +
                              "  AND NOT ( " +
                              "    d.extent_management like 'LOCAL' " +
                              "    AND " +
                              "    d.contents like 'TEMPORARY' " +
                              "  ) " +
                              "UNION ALL  " +
                              "SELECT " +
                              "    d.status " +
                              "  , d.tablespace_name " +
                              "  , d.contents       " +
                              "  , d.extent_management " +
                              "  , NVL(a.bytes, 0)    " +
                              "  , NVL(t.bytes, 0)    " +
                              "  , NVL(t.bytes / a.bytes * 100, 0) " +
                              "FROM " +
                              "    sys.dba_tablespaces d " +
                              "  , ( select tablespace_name, sum(bytes) bytes " +
                              "      from dba_temp_files " +
                              "      group by tablespace_name " +
                              "    ) a " +
                              "  , ( select tablespace_name, sum(bytes_cached) bytes " +
                              "      from v$temp_extent_pool " +
                              "      group by tablespace_name " +
                              "    ) t " +
                              "WHERE " +
                              "      d.tablespace_name = a.tablespace_name(+) " +
                              "  AND d.tablespace_name = t.tablespace_name(+) " +
                              "  AND d.extent_management like 'LOCAL' " +
                              "  AND d.contents like 'TEMPORARY' ";


        try {

            stmt = con.createStatement ();
            rset = stmt.executeQuery(queryString);

            while (rset.next()) {

                String tsStatus     = rset.getString(1);
                String tsName       = rset.getString(2);
                String tsType       = rset.getString(3);
                String tsExtentMgt  = rset.getString(4);
                long   tsSize       = rset.getLong(5);
                long   tsUsed       = rset.getLong(6);
                int    tsPctUsed    = rset.getInt(7);

                System.out.println();
                System.out.println("  Tablespace " + tsName);
                System.out.println("  ------------------------------------------");
                System.out.println("      Status                    -> " + tsStatus);
                System.out.println("      Type                      -> " + tsType);
                System.out.println("      Extend Mgt.               -> " + tsExtentMgt);
                System.out.println("      TS Size                   -> " + defaultFormat.format(tsSize));
                System.out.println("      Used                      -> " + defaultFormat.format(tsUsed));
                System.out.println("      Pct. Used                 -> " + tsPctUsed + "%");

            }

            System.out.println();

            rset.close();
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

        if (args.length < 5) {
            System.out.println();
            System.out.println("usage: java TablespaceUsage <db server> <port> <oracle sid> <db user> <db password>");
            System.out.println();
            System.out.println("EXAMPLE:");
            System.out.println("    java TablespaceUsage sundev5 1521 TRUESRC system manager");
            System.out.println();
            System.exit(1);
        }

        TablespaceUsage ts = new TablespaceUsage(args);
        ts.performQuery();
        ts.closeConnection();

    }

}
