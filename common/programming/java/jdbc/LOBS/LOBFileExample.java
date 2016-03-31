// -----------------------------------------------------------------------------
// LOBFileExample.java
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
 
import java.sql.*;
import java.io.*;
import java.util.*;

//including this import makes the code easier to read
import oracle.jdbc.driver.*;

// needed for new BFILE class
import oracle.sql.*;


/**
 * -----------------------------------------------------------------------------
 * The following class provides an example of dumping the contents of a BLOB.
 * 
 * This example creates a DIRECTORY named "LOB_DEMO_DIR" in order to 
 * successfully run the tests.
 * 
 * IMPORTANT: You will need to create two files named "file1" and "file2". The
 *            content of the files could be created as follows in the 
 *            "LOB_DEMO_DIR" directory.
 *
 *             -------
 *            | file1 |
 *            --------------------------------
 *            This is file1.
 *            
 *             -------
 *            | file2 |
 *            --------------------------------
 *            This is file2.
 *            This a two line file.
 * 
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class LOBFileExample {

    public static void main (String args []) throws Exception {

        // Register the Oracle JDBC driver
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

        String url = "jdbc:oracle:oci8:@CUSTDB";
        try {
            String url1 = System.getProperty("JDBC_URL");
            if (url1 != null) {
                url = url1;
            }
        } catch (Exception e) {
          // If there is any security exception, ignore it and use the default
        }

        // Connect to the database
        Connection conn = DriverManager.getConnection (url, "scott", "tiger");

        // It's faster when auto commit is off
        conn.setAutoCommit (false);

        // Create a Statement
        Statement stmt = conn.createStatement ();

        try {
            stmt.execute ("DROP DIRECTORY LOB_DEMO_DIR");
        }
        catch (SQLException e) {
            // An error is raised if the directory does not exist. 
            // Just ignore it.
        }
        
        stmt.execute("CREATE DIRECTORY LOB_DEMO_DIR AS '/u01/app/oracle/lobs'");

        try {
            stmt.execute("DROP TABLE test_dir_table");
        } catch (SQLException e) {
            // An error is raised if the table does not exist. Just ignore it.
        }

        try {
            // Create and populate a table with files.
            // The files: "file1" and "file2" must exist in the directory 
            //     LOB_DEMO_DIR created above as symbolic name for 
            //     /u01/app/oracle/lobs.
            stmt.execute("CREATE TABLE test_dir_table (x VARCHAR2 (30), b BFILE)");
            
            stmt.execute(
                    "INSERT INTO test_dir_table " +
                    "VALUES ('one', bfilename ('LOB_DEMO_DIR', 'file1'))");
            
            stmt.execute(
                    "INSERT INTO test_dir_table " +
                    "VALUES ('two', bfilename ('LOB_DEMO_DIR', 'file2'))");
            
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // An error is raised if the table does not exist. Just ignore it.
        }

        // Select the file from the table
        ResultSet rset = stmt.executeQuery ("select * from test_dir_table");
        while (rset.next ()) {
            String x = rset.getString (1);
            BFILE bfile = ((OracleResultSet)rset).getBFILE (2);
            System.out.println (x + " " + bfile);

            // Dump the file contents
            dumpBfile (conn, bfile);
        }

        // Close all resources
        rset.close();
        stmt.close();
        conn.close();
    }


    /*
     * -------------------------------------------------------------------------
     * Utility function to dump the contents of a Bfile
     * -------------------------------------------------------------------------
     */
    static void dumpBfile (Connection conn, BFILE bfile) throws Exception {

        System.out.println ("Dumping file " + bfile.getName());
        System.out.println ("File exists: " + bfile.fileExists());
        System.out.println ("File open: " + bfile.isFileOpen());

        System.out.println ("Opening File: ");

        bfile.openFile();

        System.out.println ("File open: " + bfile.isFileOpen());

        long length = bfile.length();
        System.out.println ("File length: " + length);

        int chunk = 10;

        InputStream instream = bfile.getBinaryStream();

        // Create temporary buffer for read
        byte[] buffer = new byte[chunk];

        // Fetch data  
        while ((length = instream.read(buffer)) != -1) {
        
            System.out.print("Read " + length + " bytes: ");

            for (int i=0; i<length; i++) {
                System.out.print(buffer[i]+" ");
            }
            System.out.println();
        }

        // Close input stream
        instream.close();
 
        // close file handler
        bfile.closeFile();
    }
}
