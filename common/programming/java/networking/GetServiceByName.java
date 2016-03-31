// -----------------------------------------------------------------------------
// GetServiceByName.java
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
 
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;

/**
 * -----------------------------------------------------------------------------
 * This program demonstrates how to lookup port numbers in the
 * /etc/services file (UNIX) or
 * NIS service lookup isn't provided in this implementation.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class GetServiceByName {

    final static private String SERVICES_FILENAME = "/etc/services";

    // UNIX
    // ----
    // /etc/services ---> aka /etc/inet/services
    
    // WINDOWS NT/2000
    // ---------------
    // c:\winnt\system32\drivers\etc\services on Win NT/2000


    /**
     * The <code>parseServicesLine()</code> method is called by 
     * <code>getPortNumberForTcpIpService()</code> to parse a non-comment line 
     * in the <tt>/etc/services</tt> file and save the values.
     *
     * @param line
     * A line to compare from the <tt>/etc/services</tt> file.
     *
     * @param tcpipService
     * The name of a TCP/IP "well-known" service found in the
     * <tt>/etc/services</tt> file
     *
     * @param tcpipClass
     * Either "tcp" or "udp", depending on the TCP/IP service desired.
     *
     * @return
     * A port number for a TCP or UDP service (depending on tcpipClass).
     * Return -1 on error.
     */
    static private int parseServicesLine(   String line
                                          , String tcpipService
                                          , String tcpipClass) {
        // Parse line
        StringTokenizer st = new StringTokenizer(line, " \t/#");

        // First get the name on the line (parameter 1):
        if (! st.hasMoreTokens()) {
            return -1; // error
        }
        String name = st.nextToken().trim();

        // Next get the service name on the line (parameter 2):
        if (! st.hasMoreTokens()) {
            return -1; // error
        }
        String portValue = st.nextToken().trim();

        // Finally get the class on the line (parameter 3):
        if (! st.hasMoreTokens()) {
            return -1; // error
        }
        String classValue = st.nextToken().trim();

        // Class doesn't match--reject:
        if (! classValue.equals(tcpipClass)) {
            return -1; // error
        }

        // Return port number, if name on this line matches:
        if (name.equals(tcpipService)) {
            try { // Convert the port number string to integer
                return (Integer.parseInt(portValue));
            } catch (NumberFormatException nfe) {
                // Ignore corrupt /etc/services lines:
                return -1; // error
            }
        } else {
            return -1; // no match
        }

    }


    /**
     * The <code>getServiceByName()</code> method
     * Search the /etc/services file for a service name and class.
     * Return the port number.
     * <p>
     * For example, given this line in <tt>/etc/services</tt>,
     * <pre>
     * farkle        4545/udp
     * </pre>
     * In this example, a search for service "farkle" and class "udp"
     * will return 4545.
     *
     * @param tcpipService
     * The name of a TCP/IP "well-known" service found in the
     * <tt>/etc/services</tt> file
     *
     * @param tcpipClass
     * Either "tcp" or "udp", depending on the TCP/IP service desired.
     *
     * @return
     * A port number for a TCP or UDP service
     * (depending on tcpipClass).
     * Return -1 on error.
     */
    static public int getServiceByName(   String tcpipService
                                        , String tcpipClass) {

        int    port = -1;

        // Look for our service, line-by-line:
        try {
            String line;
            BufferedReader br = new BufferedReader(
                        new InputStreamReader(new FileInputStream(SERVICES_FILENAME)));

            // Read /etc/services file.
            // Skip comments and empty lines.
            while ( ((line = br.readLine()) != null)
                    &&
                    (port == -1)
                  ) {

                if ((line.length() != 0) && (line.charAt(0) != '#')) {
                    port = parseServicesLine(line, tcpipService, tcpipClass);
                }

            }

            br.close();

            return (port); // port number (or -1 on error)

        } catch (IOException ioe) {

            // File doesn't exist or is otherwise not available. Keep defaults
            // error
            return -1;

        }
    }

     
    /**
     * This main() method is for use as an "internal test driver only".
     * To test this class, execute:
     * 
     * java GetServiceByName [tcpipService tcpipClass]
     * 
     *      where:  "service" - is a TCP/IP service name for which the port
     *                          number is desired
     *              "class"   - is the TCP/IP class (either tcp or udp)
     * 
     * Example:
     * 
     *      java GetServiceByName ftp tcp
     *      
     * @param tcpipService  The service name for which the port number is
     *                      desired.
     * @param tcpipClass    The TCP/IP class (either "tcp" or "udp")
     */
    public static void main(String[] args) throws IOException {

        int portUserTest, portOk, portFail;
        int errorCount = 0;

        try {

            // -----------------------------------
            // Check for arguments on command line
            // -----------------------------------
            if ((args.length != 0) && (args.length != 2)) {
                System.err.println(
                    "Test driver for this class\n\n" +
                    "Usage: \n" +
                    "    java GetServiceByName [<tcpipService> <tcpipclass>]\n\n" +
                    "Example: \n" +
                    "    java GetServiceByName ftp tcp");
                System.exit(1);
            }

            System.out.println();
            System.out.println("+------------------------------------------+");
            System.out.println("| Internal testing of GetServiceByName !!! |");
            System.out.println("+------------------------------------------+");
            

            /*
             * -----------------------------------------------------------------
             * Optional user test
             * ------------------
             * This particular code segment will only be called when the user 
             * passes in a TCP/IP Service and a TCP/IP Class from the 
             * command-line.
             * -----------------------------------------------------------------
             */ 
            if (args.length == 2) {

                System.out.println();
                System.out.println("USER TEST (optional)");
                System.out.println("--------------------");

                portUserTest = getServiceByName(args[0], args[1]);

                System.out.println(
                    "getServiceByName(\"" + args[0] + "\", \"" + args[1] + "\") "+
                    " returned port " + portUserTest);
            }


            /*
             * -----------------------------------------------------------------
             * TEST #1 / PASS TEST
             * -------------------
             * This particular code segment will test that the TCP/IP service
             * "ntp" is found on port 123.
             * -----------------------------------------------------------------
             */
            System.out.println();
            System.out.println("TEST #1 (test should pass)");
            System.out.println("--------------------------");
                    
            portOk = getServiceByName("ntp", "udp");

            if (portOk == 123) {
                System.out.println("getServiceByName(\"ntp\", \"udp\") PASSED");
            } else {
                System.out.println("getServiceByName(\"ntp\", \"udp\") FAILED: returned " + portOk);
                ++errorCount;
            }


            /*
             * -----------------------------------------------------------------
             * TEST #2 / FAILURE TEST
             * ----------------------
             * This particular code segment verifies error checking works
             * -----------------------------------------------------------------
             */

            System.out.println();
            System.out.println("TEST #2 (test should fail)");
            System.out.println("--------------------------");
    
            portFail = getServiceByName("nonExistentServiceTest", "udp");

            if (portFail == -1) {
                System.out.println("getServiceByName() error test  PASSED");
            } else {
                System.out.println("getServiceByName() error test  FAILED: returned " + portFail);
                ++errorCount;
            }

        } catch (Exception e) {

            System.err.println("Java Exception: " + e + " (exiting)");
            e.printStackTrace(System.err);
            ++errorCount;
            
        }

        System.exit(errorCount);

    }

}
