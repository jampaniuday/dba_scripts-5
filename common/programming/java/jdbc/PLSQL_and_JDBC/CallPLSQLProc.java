// -----------------------------------------------------------------------------
// CallPLSQLProc.java
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
import java.sql.CallableStatement;
import java.sql.SQLException;


/**
 * -----------------------------------------------------------------------------
 * The PL/SQL language allows you to write procedures, functions, and
 * packages allowing you to centralize business logic and store the code
 * directly in the database. This business logic may be called in many
 * different ways including from a JDBC program.
 * 
 * The following class provides an example of using JDBC to to call a PL/SQL
 * procedure.
 * 
 * In short, there are three steps involved in calling a PL/SQL Procedure from
 * within a JDBC application:
 * 
 *      1.) Create and prepare a JDBC CallableStatement object that contains
 *          a call to your PL/SQL procedure. The CallableStatement is similar
 *          to the PreparedStatement.
 *          
 *      2.) Provide all of the required parameter values to your PL/SQL
 *          procedure.
 *          
 *      3.) Call the execute() method for your CallableStatement object,
 *          which then performs the call to your PL/SQL procedure.
 * 
 * NOTE: In order to successfully use this class, you will need to run the
 *       create_all_ddl.sql file included in the same section this example class
 *       is located.
 * -----------------------------------------------------------------------------
 */

public class CallPLSQLProc {

    final static String driverClass    = "oracle.jdbc.driver.OracleDriver";
    final static String connectionURL  = "jdbc:oracle:thin:@localhost:1521:CUSTDB";
    final static String userID         = "scott";
    final static String userPassword   = "tiger";
    Connection   con                   = null;


    /**
     * Construct a CallPLSQLProc object. This constructor will create an Oracle
     * database connection.
     */
    public CallPLSQLProc() {

        try {

            System.out.print("  Loading JDBC Driver  -> " + driverClass + "\n");
            Class.forName(driverClass).newInstance();

            System.out.print("  Connecting to        -> " + connectionURL + "\n");
            this.con = DriverManager.getConnection(connectionURL, userID, userPassword);
            System.out.print("  Connected as         -> " + userID + "\n\n");

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
     * Method to call the PL/SQL procedure "set_employee_salary"
     */
    public void performProcCall() {

        Statement          stmt         = null;
        ResultSet          rset         = null;
        CallableStatement  cstmt        = null;
        String             queryString  = "SELECT name, monthly_salary " +
                                          "FROM   emp " +
                                          "WHERE  emp_id = 1001";

        try {

            stmt = con.createStatement ();

            // ---------------------------------------
            // Obtain and print current monthly salary
            // ---------------------------------------
            rset = stmt.executeQuery(queryString);
            rset.next();
            System.out.println("  Name           : " + rset.getString(1));
            System.out.println("  Monthly Salary : $" + rset.getFloat(2) + "\n");

            // -----------------------------------------------------
            // Call PL/SQL Procedure to give an employee a %20 raise
            // -----------------------------------------------------
            System.out.println("  Lets give this employee a 20% raise.\n");
            cstmt = con.prepareCall("{call set_employee_salary(?, ?)}");
            cstmt.setInt(1, 1001);
            cstmt.setDouble(2, 1.2);
            cstmt.execute();
            cstmt.close();
            

            // -------------------------------------------
            // Obtain and print NEW current monthly salary
            // -------------------------------------------
            rset = stmt.executeQuery(queryString);
            rset.next();
            System.out.println("  Name           : " + rset.getString(1));
            System.out.println("  Monthly Salary : $" + rset.getFloat(2) + "\n");


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

        CallPLSQLProc mainPrg = new CallPLSQLProc();
        mainPrg.performProcCall();
        mainPrg.closeConnection();

    }

}
