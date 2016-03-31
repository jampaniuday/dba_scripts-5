// -----------------------------------------------------------------------------
// SearchCompareExample.java
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
 
import netscape.ldap.*;
import java.util.Enumeration;

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example that demonstrates how to compare a value in memory
 * to the value of an attribute of an entry without actually retrieving the
 * entry. This is what is known as a "compare" operation. Think of it as a 
 * simulation of using a "search" by setting the scope of the search to
 * SCOPE_BASE and providing a search filter with which the value can be
 * compared; if an entry is returned, the "compare" operation was successful.
 * A "compare", however, may improve performance since the returned data from
 * the LDAP server is a small packet that says either that the value is the same
 * of that it is different.
 * 
 * This class provides a very small and hardcoded example of how to use the
 * "compare" method. We will setup a specific record and see if the "l"
 * attribute (the LDAP attribute for specifying a location) has the value
 * "Sunnyvale". Consider this a very hardcoded example to simply show how
 * to use the "compare" method of the LDAPConnection class.
 * 
 * 
 * 
 * To compile this program:
 *      javac -classpath "$JAVALIB/ldapjdk.jar:." SearchCompareExample.java
 * 
 * To call this program:
 *      java -classpath "$JAVALIB/ldapjdk.jar:." SearchCompareExample <host> <port> <authdn> <authpw> <dn> <location>
 *      
 * Example call:
 *      java -classpath "$JAVALIB/ldapjdk.jar:." SearchCompareExample ldap.idevelopment.info 389 "" "" "uid=ahunter, ou=People, o=airius.com" "Sunnyvale"
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class SearchCompareExample {

    String host       = null;
    int port          = 389;
    String authid     = null;
    String authpw     = null;
    String dn         = null;
    String location   = null;
    LDAPConnection ld = null;

    /**
     * Used to print the usage for running this program from the command-line.
     * This method will normally be called when the user doesn't pass in the
     * correct number of arguments.
     */
    public static void printUsage() {
        String str = null;
        str = "java SearchCompareExample <host> <port> <authdn> <authpw> <dn> <location>";
        System.out.println(str);
    }

    /**
     * Constructor used to create this object.  Responsible for setting
     * this object's attributes used to login to the LDAP server as well as
     * the search string (which for this example is the location we want to
     * compare to the passed in DN).
     * @param args An array of strings that were passed into the main method
     *             of this program by the user. The proper attributes to this 
     *             object will be assigned from this array.
     */
    public SearchCompareExample(String[] args) {
        this.host     = args[0];
        this.port     = Integer.parseInt(args[1]);
        this.authid   = args[2];
        this.authpw   = args[3];
        this.dn       = args[4];
        this.location = args[5];

        ld = new LDAPConnection();
    }

    /**
     * Key method for this program. This method will perform the actual search
     * from the LDAP directory given the parameters provided by the user. For
     * the purpose of this example, we simply provide the DN to the read()
     * method of the LDAPEntry class.
     */
    public void performSearch() {

        try {
        
            //Connect to the LDAP Server
            ld.connect(host, port, authid, authpw);

            // Create a simply Attribute object
            LDAPAttribute attr = new LDAPAttribute("l", location);
            
            // Use the LDAPConnection object to compare the value for location
            boolean attrMatch = ld.compare(dn, attr);

            System.out.println("Search DN       = [" + dn + "]");
            System.out.println("Search location = [" + location + "]");
            System.out.println("---------------------------------------------------");

            if (attrMatch) {
                System.out.println("Values matched!");
            } else {
                System.out.println("No match!");
            }

        } catch (LDAPReferralException e) {
            // Ignore referrals...
        } catch (LDAPException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Method that overrides the toString() method of the core Object class.
     * This method is used to print an object, which in this case, will print
     * the LDAP server login and search parameters provided by the user from
     * the command-line.
     */
    public String toString() {
        String str = null;

        str = "Host     : " + this.host + "\n" +
              "Port     : " + this.port + "\n" +
              "AuthDN   : " + this.authid + "\n" +
              "AuthPW   : " + this.authpw + "\n" +
              "dn       : " + this.dn + "\n" +
              "location : " + this.location;
        return(str);
    }

    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        if (args.length != 6) {
            printUsage();
            System.exit(1);
        }
        
        SearchCompareExample se = new SearchCompareExample(args);
        se.performSearch();
        //System.out.println(se);

    }

}
