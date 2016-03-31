// -----------------------------------------------------------------------------
// CallPLSQLExample.java
// -----------------------------------------------------------------------------

/*
 * =============================================================================
 * Copyright (c) 1998-2009 Jeffrey M. Hunter. All rights reserved.
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
 * The PL/SQL language allows you to write procedures, functions, and
 * packages allowing you to centralize business logic and store the code
 * directly in the database. This business logic may be called in many
 * different ways including from a JDBC program.
 * 
 * In short, there are three steps involved in calling a PL/SQL procedure
 * from a JDBC application:
 *
 *      1.) Create and prepare a JDBC CallableStatement object containing a
 *          a call to your PL/SQL procedure.
 *      2.) Provide all required parameter values to your PL/SQL procedure.
 *      3.) Call the execute() method of your CallableStatement object, which
 *          then performs the call to your PL/SQL procedure.
 * 
 * If you have a lot of database intensive code - code that performs a lot of
 * database operations, you should consider writing that code in PL/SQL as many
 * test have shown that PL/SQL outperforms Java when it comes to heavy database
 * operations. Obviously, you should perform your own benchmark tests to ensure
 * this is true for your particular application before releasing it to
 * production.
 * 
 * The following class provides an example of using JDBC to to call PL/SQL
 * procedures and functions.
 * 
 * In order to successful use this class, you will need to load the following
 * PL/SQL procedure into your database:

CREATE TABLE emp (
    emp_id           NUMBER
  , dept_id          NUMBER
  , name             VARCHAR2(30)
  , date_of_birth    DATE
  , date_of_hire     DATE
  , monthly_salary   NUMBER(15,2)
  , position         VARCHAR2(100)
  , extension        NUMBER
  , office_location  VARCHAR2(100)
)
/

INSERT INTO emp VALUES (
    1001
  , 105
  , 'Jeff Hunter'
  , '31-DEC-1967'
  , '07-JUL-1994'
  , 8700.00
  ,'Sr. Software Engineer'
  , 6007
  , 'Butler, PA'
)
/

COMMIT;

CREATE OR REPLACE PROCEDURE update_employee_salary (
    p_emp_id  IN emp.emp_id%TYPE
  , p_factor  IN NUMBER
) AS

  v_monthly_salary  emp.monthly_salary%TYPE;

BEGIN

  -- Query employees current salary given the supplied emp_id.
  SELECT NVL(monthly_salary, -999)
  INTO   v_monthly_salary
  FROM   emp
  WHERE  emp_id = p_emp_id;

  -- If the record exists for the given employee, update their salary
  IF (v_monthly_salary != -999) THEN
    UPDATE emp
    SET    monthly_salary = monthly_salary * p_factor;
    COMMIT;
  END IF;

  
END;
/
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class CallPLSQLExample {

    final static String driverClass    = "oracle.jdbc.driver.OracleDriver";
    final static String connectionURL  = "jdbc:oracle:thin:@localhost:1521:CUSTDB";
    final static String userID         = "scott";
    final static String userPassword   = "tiger";
    Connection   con                   = null;


    /**
     * Construct a CallPLSQLExample object. This constructor will create an Oracle
     * database connection.
     */
    public CallPLSQLExample() {

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
     * Method to perform a simply query from the "emp" table.
     */
    public void performQuery() {

        Statement stmt      = null;
        ResultSet rset      = null;
        String queryString  = "SELECT name, date_of_hire, monthly_salary " +
                              "FROM   emp " +
                              "ORDER BY name";

        try {

            System.out.print("  Creating Statement...\n");
            stmt = con.createStatement ();

            System.out.print("  Opening ResultsSet...\n");
            rset = stmt.executeQuery(queryString);

            int counter = 0;
            
            while (rset.next()) {
                System.out.println();
                System.out.println("  Row [" + ++counter + "]");
                System.out.println("  -----------");
                System.out.println("      Name             -> " + rset.getString(1));
                System.out.println("      Date of Hire     -> " + rset.getString(2));
                System.out.println("      Monthly Salary   -> " + rset.getFloat(3));
            }

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

        CallPLSQLExample mainPrg = new CallPLSQLExample();
        mainPrg.performQuery();
        mainPrg.closeConnection();

    }

}
