// -----------------------------------------------------------------------------
// RefCursorExample.java
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
import java.sql.Types;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleCallableStatement;
import oracle.jdbc.driver.OracleTypes;
import oracle.jdbc.driver.OracleResultSet;


/**
 * -----------------------------------------------------------------------------
 * This class provides an example on the use of REF Cursors to execute SQL from 
 * a JDBC program, simulating dynamic SQL.
 * 
 * ===================
 * DYNAMIC SQL IN JAVA
 * ===================
 * 
 * JDBC provides APIs for executing Dynamic SQL using PreparedStatement. 
 * For example:
 * 
 *      PreparedStatement pstmt;
 *      pstmt=conn.prepareStatement("SELECT name FROM dept WHERE deptno > ?");
 *      pstmt.setInt(1,104);
 *      ResultSet c1;
 *      c1=pstmt.executeQuery();
 *      pstmt.setInt(1,10)
 *      while (c1.next ()) {System.out.println (c1.getInt(1));} 
 * 
 * 
 * ==========
 * REF CURSOR
 * ==========
 * 
 * Another option of executing dynamic SQL from JDBC is provided in this
 * example. Keep in mind that this example will only work with Oracle8i and
 * higher. In this case, the  procedure uses a PL/SQL procedure which returns 
 * a REF CURSOR.
 * 
 * A REF CURSOR is similar a pointer in the C programming language. It points
 * to rows retrieved from the database using a PL/SQL cursor. The example I
 * provide in this class uses a REF CURSOR to point to the result set
 * returned by a SELECT statement that retrieves rows from the DEPT table
 * using a PL/SQL cursor.
 * 
 * In this example, I call a PL/SQL procedure named "get_dept_ref_cursor" which
 * returns a variable of type "t_ref_cursor".
 * 
 * Stored procedures can return user-defined types, or cursor variables, of the 
 * REF CURSOR category.  This output is equivalent to a database cursor or a 
 * JDBC result set. A REF CURSOR essentially encapsulates the results of a 
 * query.
 * 
 * Advantages of using a REF CURSOR are:
 * 
 *      1.) Code Reusability
 *      
 *          The same package procedure could be used for other Java and non-Java
 *          applications.
 *          
 *      2.) Load Balancing. 
 * 
 * 
 * =============================
 * OracleCallableStatement CLASS
 * =============================
 * 
 * You will notice that in this example, I use an OracleCallableStatement class
 * in place of our typical CallableStatement class. This class defines a method
 * named getCursor() that enables you to read Oracle cursors.
 * 
 * 
 * =================
 * OracleTypes CLASS
 * =================
 * 
 * You will also notice the oracle.jdbc.driver.OracleTypes is also used
 * when registering the OutParameter. This class defines those special TYPEs
 * offered by the Oracle database. This class is similar to java.sql.Types.
 * 
 * 
 * ===========================================================
 * NOT USING OracleCallableStatement and OracleResultSet CLASS
 * ===========================================================
 * 
 * Note that you are not required to use the OracleCallableStatement and
 * OracleResultSet classes; you could use the regular CallableStatement
 * and ResultSet classes found in java.sql. However, you will need to 
 * use the getObject() method to read the Oracle cursor. An example of this is
 * provided in this example with the performRefCursor2() method.
 * 
 * 
 * -----------------------------------------------------------------------------
 * 
 * NOTE: Opening a REF CURSOR for a statement present in a variable is only 
 *       supported with Oracle8i and higher. 
 * 
 * NOTE: In order to successfully use this class, you will need to run the
 *       create_all_ddl.sql file included in the same section this example class
 *       is located.
 * 
 * -----------------------------------------------------------------------------
 */

public class RefCursorExample {

    final static String driverClass    = "oracle.jdbc.driver.OracleDriver";
    final static String connectionURL  = "jdbc:oracle:thin:@localhost:1521:CUSTDB";
    final static String userID         = "scott";
    final static String userPassword   = "tiger";
    Connection   con                   = null;


    /**
     * Construct a RefCursorExample object. This constructor will create an Oracle
     * database connection.
     */
    public RefCursorExample() {

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
     * This method is used to return a REF CURSOR that will be used to retrieve 
     * data from a result set. This REF CUSROR is retrieved by the JDBC program 
     * into a ResultSet.
     * 
     * This method Uses the OracleCallableStatement and OracleResultSet classes.
     */
    public void performRefCursor() {

        OracleCallableStatement oraCallStmt   = null;
        OracleResultSet         deptResultSet = null;

        System.out.println("Using OracleCallableStatement / OracleResultSet");
        System.out.println("-----------------------------------------------");

        try {

            oraCallStmt = (OracleCallableStatement) con.prepareCall(
                "{? = call ref_cursor_package.get_dept_ref_cursor(?)}"
            );
            oraCallStmt.registerOutParameter(1, OracleTypes.CURSOR);
            oraCallStmt.setInt(2, 104);
            oraCallStmt.execute();

            deptResultSet = (OracleResultSet) oraCallStmt.getCursor(1);

            while (deptResultSet.next()) {
                System.out.println(
                    " - " +
                    deptResultSet.getString(2) + " (" + deptResultSet.getInt(1) + "), " + 
                    deptResultSet.getString(3)
                );
            }
            System.out.println();
            
            oraCallStmt.close();
            
        } catch (SQLException e) {

            e.printStackTrace();

        }

    }


    /**
     * This method is used to return a REF CURSOR that will be used to retrieve 
     * data from a result set. This REF CUSROR is retrieved by the JDBC program 
     * into a ResultSet.
     * 
     * This method Uses the the regular CallableStatement and ResultSet classes.
     */
    public void performRefCursor2() {

        CallableStatement cstmt = null;
        ResultSet         rset  = null;

        System.out.println("Using CallableStatement / ResultSet");
        System.out.println("-----------------------------------");

        try {

            cstmt = con.prepareCall(
                "{? = call ref_cursor_package.get_dept_ref_cursor(?)}"
            );
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.setInt(2, 104);
            cstmt.execute();

            rset = (ResultSet) cstmt.getObject(1);
            
            while (rset.next()) {
                System.out.println(
                    " - " +
                    rset.getString(2) + " (" + rset.getInt(1) + "), " + 
                    rset.getString(3)
                );
            }
            System.out.println();
                    
            cstmt.close();
            
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

        RefCursorExample mainPrg = new RefCursorExample();
        mainPrg.performRefCursor();
        mainPrg.performRefCursor2();
        mainPrg.closeConnection();

    }

}
