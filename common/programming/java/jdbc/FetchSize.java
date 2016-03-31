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

/**
 * -----------------------------------------------------------------------------
 * The JDBC fetch size gives the JDBC driver a hint as to the number of rows 
 * that should be fetched from the database when more rows are needed. For 
 * large queries that return a large number of objects you can configure the 
 * row fetch size used in the query to improve performance by reducing the 
 * number database hits required to satisfy the selection criteria. Most 
 * JDBC drivers (including Oracle) default to a fetch size of 10, so if you are 
 * reading 1000 objects, increasing the fetch size to 256 can significantly 
 * reduce the time required to fetch the query's results. The optimal fetch 
 * size is not always obvious. Usually, a fetch size of one half or one quarter 
 * of the total expected result size is optimal. Note that if you are unsure of 
 * the result set size, incorrectly setting a fetch size too large or too small
 * can decrease performance.
 *
 * In this example application, I print out the default fetch size and then 
 * increase it to 50 using the setFetchSize(int) method of a Statement object.
 * When you execute the query, the JDBC driver retrieves the first 50 rows from 
 * the database (or all rows if less than 50 rows satisfy the selection 
 * criteria). As you iterate over the first 50 rows, each time you call 
 * rset.next(), the JDBC driver returns a row from local memory – it does not 
 * need to retrieve the row from the database. When you try to access the fifty 
 * first row (assuming there are more than 50 rows that satisfy the selection 
 * criteria), the JDBC driver again goes to the database and retrieves another 
 * 50 rows. In this way, 100 rows are returned with only two database hits.
 * 
 * Alternatively, you can use the method setMaxRows() to set the limit for
 * the maximum number of rows that any ResultSet can contain.
 *
 * If you specify a value of zero, then the hint is ignored: the JDBC driver 
 * returns one row at a time. The default value is zero.
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class FetchSize {

    static final String driver_class  = "oracle.jdbc.driver.OracleDriver";
    static final String connectionURL = "jdbc:oracle:thin:@linux1:1521:orcl1";
    static final String userID        = "scott";
    static final String userPassword  = "tiger";
                                        
    public FetchSize() {
    }

    public void runTest() {

        Connection  con = null;
        Statement   stmt = null;
        ResultSet   rset = null;
        String      query_string = "SELECT * FROM tables WHERE rownum < 200 ORDER BY owner, table_name";
        int         newFetchSize = 50;

        try {

            System.out.println("+-------------------------------+");
            System.out.println("| SETUP CONNECTION              |");
            System.out.println("+-------------------------------+");

            System.out.println("Loading JDBC Driver  -> " + driver_class);
            Class.forName (driver_class).newInstance();

            System.out.println("Connecting to        -> " + connectionURL);
            con = DriverManager.getConnection(connectionURL, userID, userPassword);
            System.out.println("Connected as         -> " + userID);

            System.out.println("Turning Off AutoCommit...");
            con.setAutoCommit(false);

            /*
            **  EXECUTE GENERIC QUERY
            */
            System.out.println("+-------------------------------+");
            System.out.println("| EXECUTE GENERIC QUERY         |");
            System.out.println("+-------------------------------+");

            System.out.println("Executing Generic (SYSDATE) Query...");

            System.out.println("Creating Statement...");
            stmt = con.createStatement ();
            
            System.out.println("Get Default Fetch Size:" + stmt.getFetchSize());
            System.out.println("Manually Set Default Fetch Size to " + newFetchSize);
            
            stmt.setFetchSize(newFetchSize);
            System.out.println("Get New Fetch Size:" + stmt.getFetchSize());

            System.out.println("Opening ResultsSet...");
            rset = stmt.executeQuery (query_string);

            while (rset.next ()) {
                System.out.println("  RESULTS            -> " + rset.getString (2));
            }

            System.out.println("Closing ResultSet...");
            rset.close();

            System.out.println("Closing Statement...");
            stmt.close();

        }  catch (SQLException e) {

            e.printStackTrace();

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        
        } finally {

            if (con != null) {
          
                try {

                    System.out.println("+-------------------------------+");
                    System.out.println("| CLOSE DOWN ALL CONNECTIONS    |");
                    System.out.println("+-------------------------------+");

                    System.out.println("Closing down all connections...");
                    con.close();
                
                } catch (SQLException e) {
                
                    e.printStackTrace();
                    
                }
            }
          
        } // FINALLY
    }

    public static void main(String[] args) {
        FetchSize fetchSize = new FetchSize();
        fetchSize.runTest();
    }
}
