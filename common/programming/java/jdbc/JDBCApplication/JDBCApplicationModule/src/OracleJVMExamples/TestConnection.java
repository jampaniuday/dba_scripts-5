// -----------------------------------------------------------------------------
// TestConnection.java
// -----------------------------------------------------------------------------

package OracleJVMExamples;

import java.sql.*;

/**
 * Used to test the functionality of a custom database connection method that
 * is responsible for returning an Oracle database Connection object. If the
 * custom method determines it is already connected to the database (via Oracle 
 * JVM), then it simply uses the the jdbc:default:connection URL (the kprb 
 * driver). If the custom method determines it is not connected to the database,
 * (not using Oracle JVM), it then uses a hardcoded set of attributes (database
 * username and password) to connect to the database and return that Connection
 * object. This method will then use that Connection object to query the 
 * current user from the database.
 * <p>
 * Here is how to use this test class:
 * <p>
 * <ul>
 *      <li> If you are connected to the Oracle database and want to test how to
 *           use the internal (kprb driver), use the following:
 *           <pre>CONNECT scott/tiger
 * 
 *SET SERVEROUTPUT ON
 *CALL dbms_java.set_output(5000);
 *CALL oracle_jvm_mgr.show_user();</pre>
 *      <p>
 *      <li> If you want to test using the JDBC Thin driver, then simply 
 *           call this class:
 *           
 *           <pre>java OracleJVMExamples.TestConnection</pre>
 *      
 * </ul>
 * <p>
 * @author Jeffrey Hunter
 * @author <a href="mailto:jhunter@iDevelopment.info">jhunter@iDevelopment.info</a>
 * @author <a target="_blank" href="http://www.iDevelopment.info">www.iDevelopment.info</a>
 * @version 2.0,  &nbsp; 26-SEP-2004
 * @since SDK1.4
 */
 
public class TestConnection  {


    public static void showUser() {

        Connection  conn       = null;
        Statement   statement  = null;
        ResultSet   rset       = null;

        System.out.println();
        System.out.println("+-------------------------------+");
        System.out.println("| METHOD: showUser              |");
        System.out.println("+-------------------------------+");
        System.out.println();

        try {
    
          // Connect to Oracle using custom Oracle Connection Manager
          conn = OracleConnection.getConnection();
    
          statement = conn.createStatement (ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    
          System.out.println("Opening ResultsSet...");
          rset = statement.executeQuery("SELECT user, TO_CHAR(sysdate, 'DD-MON-YYYY HH24:MI:SS') FROM dual");
    
          while (rset.next ()) {
    
            int     rowNumber;
            String  user;
            String  sysdate;
            
            rowNumber = rset.getRow();
            
            user = rset.getString(1);
            if ( rset.wasNull() ) {
                user = "<null>";
            }
            
            sysdate = rset.getString(2);
            if ( rset.wasNull() ) {
                sysdate = "<null>";
            }
            
            System.out.println("RESULTS      --> [Row Number  : " + rowNumber + "]");
            System.out.println("RESULTS      --> [User        : " + user + "]");
            System.out.println("RESULTS      --> [Date/Time   : " + sysdate + "]");
            }
            
            System.out.println("Closing ResultSet...");
            rset.close();
            
            System.out.println("Closing Statement...");
            statement.close();


        } catch (SQLException e) {
            
            System.out.println("ERROR: SQLException");
            e.printStackTrace();

        }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        showUser();
    }

}
