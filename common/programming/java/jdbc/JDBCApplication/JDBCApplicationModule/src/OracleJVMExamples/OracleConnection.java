// -----------------------------------------------------------------------------
// OracleConnection.java
// -----------------------------------------------------------------------------

package OracleJVMExamples;

import java.sql.*;
import java.io.*;
import java.util.Properties;
import oracle.jdbc.driver.*;

/**
 * Factory class that conatins a single public method responsible for returning
 * a Connection object for a database connection. This class will determine if
 * it is already running inside of the database. If it is, then it simply
 * creates and returns a Connection object using the jdbc:default:connection URL
 * (the kprb driver). If this class determines that it is not already connected 
 * inside of the database, it will make a connection to the database using 
 * the properties contained in the file "ojvm_connection.properties" and then
 * return the Connection object to the calling class..
 * <p>
 * @author Jeffrey Hunter
 * @author <a href="mailto:jhunter@iDevelopment.info">jhunter@iDevelopment.info</a>
 * @author <a target="_blank" href="http://www.iDevelopment.info">www.iDevelopment.info</a>
 * @version 2.0,  &nbsp; 26-SEP-2004
 * @since SDK1.4
 */
 
public class OracleConnection  {

    static boolean debug = true;

    /**
      * Return a JDBC connection appropriately either inside or outside the 
      * database.
      * 
      * @return     Connection object for a database connection using either the
      *             internal (Oracle JVM kprb drivers) or a new Connection
      *             object using hardcoded connection attributes.
      */
    public static Connection getConnection() throws SQLException {

        String      driverClass  = "oracle.jdbc.driver.OracleDriver";
        Connection  conn         = null;

        try {
        
            Class.forName(driverClass).newInstance();

            printDebug("Attempting connection.");
            
            if (connectedToDatabase()) { 
            
                /*
                 * We are within the Oracle JVM and should simply return a
                 * Connection object using the built-in kprb driver. You can
                 * use either of the two URLs to obtain a default connection:
                 *      jdbc:default:connection:
                 *      jdbc:oracle:kprb:
                 */
                
                String connectionURL = "jdbc:default:connection:";
        
                printDebug("-----------------------------------------");
                printDebug("INTERNAL ORACLE JVM (kprb) CONNECTION");
                printDebug("-----------------------------------------");
                printDebug("Loading JDBC Driver  : " + driverClass);
                printDebug("Connecting to        : " + connectionURL);
                printDebug("Database Version     : " + System.getProperty("oracle.server.version"));
                
                conn = DriverManager.getConnection(connectionURL);
                
            } else {
            
                /*
                 * We are not within the Oracle JVM and should create a
                 * Connection Object using parameters from a properties file.
                 */
                Properties properties = new Properties();
                String     fileName   = "oracle.db.properties";
                
                try {
                
                    String nonOJVMPropFIleName = "conf" + File.separator + fileName;
                    
                    printDebug("Not within Oracle JVM.");
                    printDebug("First, attempt to open file [" + nonOJVMPropFIleName + "] from the file system.");
                    printDebug("Current working directory \"" + System.getProperty("user.dir") + "\".");
                    
                    properties.load(new FileInputStream(nonOJVMPropFIleName));
                    
                    printDebug("Found file [" + nonOJVMPropFIleName + "] on the file system!");
                    
                } catch (IOException e) {
                
                    try {
                        
                        String nonOJVMPropFIleName = "/conf/" + fileName;
                        
                        printDebug("Didn't find properties file on file system.");
                        printDebug("Attempting to open file [" + nonOJVMPropFIleName + "] from within the JAR file.");
                        
                        InputStream fis = OracleConnection.class.getResourceAsStream(nonOJVMPropFIleName);
                        properties.load(fis);
                        
                        printDebug("Found file [" + nonOJVMPropFIleName + "] within the JAR file!");
                    
                    } catch (IOException e2) {
                     
                        e2.printStackTrace();
                        throw new SQLException("Error opening the properties file " + fileName);
                        
                    }
                    
                }

                String      userName        = properties.getProperty("oracle.db.thin.user.value");
                String      userPassword    = properties.getProperty("oracle.db.thin.password.value");
                String      databaseServer  = properties.getProperty("oracle.db.thin.host.value");
                String      listenerPort    = properties.getProperty("oracle.db.thin.listener.value");
                String      oracleSID       = properties.getProperty("oracle.db.thin.sid.value");
                String      connectionURL   = "jdbc:oracle:thin:@" + databaseServer + ":" + listenerPort + ":" + oracleSID;
        
                if (debug) {
                    printDebug("-----------------------------------------");
                    printDebug("THIN CLIENT CONNECTION");
                    printDebug("-----------------------------------------");
                    printDebug("Loading JDBC Driver  : " + driverClass);
                    printDebug("Connecting to        : " + connectionURL + ", " + userName + ", " + userPassword);
                }
                conn = DriverManager.getConnection(connectionURL, userName, userPassword);
                
            }

            System.out.println();
            conn.setAutoCommit(false);
            
            return conn;

        } catch (IllegalAccessException e) {
        
            e.printStackTrace();
            throw new SQLException("Illegal Access Error"); 
            
        } catch (InstantiationException e) {
        
            e.printStackTrace();
            throw new SQLException("Instantiation Error"); 
            
        } catch (ClassNotFoundException e) {
        
            e.printStackTrace();
            throw new SQLException("Class Not Found"); 
            
        } catch (SQLException e) {
        
            e.printStackTrace();
            throw new SQLException("Error loading JDBC Driver"); 
            
        }
    
    }


    /**
     * Utility method to print out debugging messages to stdout.
     * 
     * @param msg Message to print to stdout.
     */
    private static void printDebug(String msg) {
    
        if (debug) {
            String className = "[OracleConnection] : ";
            System.out.println(className + msg);
        }
    }

    /**
     * Method to determine if we are running in the database. If the System 
     * parameter oracle.server.version is null, we are not running in the 
     * database. If the System parameter oracle.server.version does have a
     * value assigned to it, then we are running inside the database.
     * 
     * @returns     <code>true</code> if it is determined we are connected
     *              inside the Oracle database (via Oracle JVM); 
     *              <code>false</code> otherwise.
     */
    private static boolean connectedToDatabase() {
        String version = System.getProperty("oracle.server.version");
        return (version != null && !version.equals(""));
    }

}
