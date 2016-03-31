// -----------------------------------------------------------------------------
// PasswordManagement.java
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
import java.sql.SQLWarning;

/**
 * -----------------------------------------------------------------------------
 * The following class provides an example of using the new PasswordManagement 
 * and Password Aging features in JDBC for Oracle.
 * 
 * Password Management first appeared in version 8.1.7 and at that time was
 * only available in the JDBC OCI driver. From what I can see, these features
 * will also work with the thin driver in Oracle9i.
 * 
 * This example provides simple class that connects to the database and looks
 * for specfic warnings from the Connection object. In this case, we will be 
 * looking for when a user's password is about to expire. Notice what happens
 * when this user logs into sqlplus with a password that is about to expire:
 * 
 *     % sqlplus scott/tiger
 * 
 *     SQL*Plus: Release 9.0.1.0.1 - Production on Tue Jun 3 23:26:46 2003
 * 
 *     (c) Copyright 2001 Oracle Corporation.  All rights reserved.
 * 
 *     ERROR:
 *     ORA-28002: the password will expire within 2 days
 * 
 *     Connected to:
 *     Oracle9i Enterprise Edition Release 9.2.0.3.0 - Production
 *     With the Partitioning, OLAP and Oracle Data Mining options
 *     JServer Release 9.2.0.3.0 - Production
 * 
 *     SQL>
 *
 * Assume that, in this example, we create an Oracle profile named 
 * DEV_PROFILE as follows:
 * 
 *     SQL> CREATE PROFILE dev_profile
 *       2  LIMIT
 *       3  PASSWORD_GRACE_TIME 2 PASSWORD_LIFE_TIME 1;
 *
 *     Profile created.
 *     
 *     SQL> ALTER USER scott PROFILE dev_profile;
 *     
 *     User altered.
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class PasswordManagement {

    final static String driverClass    = "oracle.jdbc.driver.OracleDriver";
    final static String connectionURL  = "jdbc:oracle:thin:@localhost:1521:CUSTDB";
    final static String userID         = "scott";
    final static String userPassword   = "tiger";
    Connection   con                   = null;


    /**
     * Construct a PasswordManagement object. This constructor will create an Oracle
     * database connection.
     */
    public PasswordManagement() {

        try {

            System.out.print("  Loading JDBC Driver  -> " + driverClass + "\n");
            Class.forName(driverClass).newInstance();

            System.out.print("  Connecting to        -> " + connectionURL + "\n");
            this.con = DriverManager.getConnection(connectionURL, userID, userPassword);
            System.out.print("  Connected as         -> " + userID + "\n");

            System.out.print("  Looking for Warnings\n");
            SQLWarning sqlw = con.getWarnings();

            if (sqlw != null && sqlw.getErrorCode() == 28002) {
                System.out.println("    WARNING: ORA-28002: Your password will expire soon!");
                System.out.println("    MESSAGE: " + sqlw.getMessage());
            }
            System.out.print("  Warning check process completed\n");



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

        PasswordManagement pm = new PasswordManagement();
        pm.closeConnection();

    }

}
