// -----------------------------------------------------------------------------
// LDAPSearchExample.java
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
 * Used to provide an example that demonstrates how to use the Netscape LDAP
 * SDK for Java. In this example, we perform a simple search.
 * 
 * To compile this program:
 *      javac -classpath "$JAVALIB/ldapjdk.jar:." LDAPSearchExample.java
 * 
 * To call this program:
 *      java -classpath "$JAVALIB/ldapjdk.jar:." LDAPSearchExample <host> <port> <authdn> <authpw> <baseDN> <filter>
 *      
 * Example call:
 *      java -classpath "$JAVALIB/ldapjdk.jar:." LDAPSearchExample ldap.idevelopment.info 389 "" "" "o=airius.com" "(cn=*carter)"
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class LDAPSearchExample {

    String host       = null;
    int port          = 389;
    String authid     = null;
    String authpw     = null;
    String base       = null;
    String filter     = null;
    String[] ATTRS    = {"cn", "mail", "telephonenumber"};
    LDAPConnection ld = null;

    /**
     * Used to print the usage for running this program from the command-line.
     * This method will normally be called when the user doesn't pass in the
     * correct number of arguments.
     */
    public static void printUsage() {
        String str = null;
        str = "java LDAPSearchExample <host> <port> <authdn> <authpw> <baseDN> <filter>";
        System.out.println(str);
    }

    /**
     * Constructor used to create this object.  Responsible for setting
     * this object's attributes used to login to the LDAP server as well as
     * the search string and baseDN.
     * @param args An array of strings that were passed into the main method
     *             of this program by the user. The proper attributes to this 
     *             object will be assigned from this array.
     */
    public LDAPSearchExample(String[] args) {
        this.host    = args[0];
        this.port    = Integer.parseInt(args[1]);
        this.authid  = args[2];
        this.authpw  = args[3];
        this.base    = args[4];
        this.filter  = args[5];

        ld = new LDAPConnection();
    }

    /**
     * Key method for this program. This method will perform the actual search
     * from the LDAP directory given the parameters provided by the user.
     */
    public void performSearch() {
        int status = -1;
        try {
            //Connect to the LDAP Server
            ld.connect(host, port, authid, authpw);

            System.out.println("Search filter=" + filter);
            System.out.println("---------------------------------------------");
            LDAPSearchResults res = ld.search(   base
                                               , ld.SCOPE_SUB
                                               , filter
                                               , ATTRS
                                               , false);

            // Loop on results until complete
            while (res.hasMoreElements()) {
                try {
                    // The next entry
                    LDAPEntry entry = res.next();
                    printEntry(entry, ATTRS);
                    status = 0;
                } catch (LDAPReferralException e) {
                    // Ignore any referrals
                    continue;
                } catch (LDAPException e) {
                    System.out.println(e.toString());
                    continue;
                }
            }
        } catch (LDAPException e) {
            System.out.println(e.toString());
        }

        // Finally, disconnect from the LDAP server
        if ( (ld != null) && ld.isConnected() ) {
            try {
                ld.disconnect();
            } catch (LDAPException e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Method used to print the names and values of attributes of an entry.
     * 
     * @param entry The entry that contains the attributes
     * @param attrs An array of attribute names to display
     */
    public void printEntry(LDAPEntry entry, String[] attrs) {
    
        System.out.println("DN: " + entry.getDN());

        // Now, use the array to look through the attributes. It would also
        // have been possible to enumerate the attributes using getAttributes(),
        // but by passing them in this way, we have control of the display 
        // order. After printing all of the attributes using this method, I will
        // also provide a section below on how to enumerate the attributes using
        // the getAttributes() method.

        System.out.println();
        System.out.println("  Iterate through all attributes using hard-coded array");

        for (int i=0; i < attrs.length; i++) {
            LDAPAttribute attr = entry.getAttribute(attrs[i]);
            if (attr == null) {
                System.out.println("    [" + attrs[i] + ": not present]");
                continue;
            }

            Enumeration enumVals = attr.getStringValues();
            boolean hasVals = false;
            while ( (enumVals != null) && (enumVals.hasMoreElements()) ) {
                String val = (String) enumVals.nextElement();
                System.out.println("    [" + attrs[i] + ": " + val + "]");
                hasVals = true;
            }
            if (!hasVals) {
                System.out.println("    [" + attrs[i] + ": has no values]");
            }
        }


        // Now lets print out all of the attributes (again) using the 
        // getAttributes() method from an LDAPEntry. It is easy to print out
        // all attribute names and values in this way, but we don't really
        // have control of the display order.

        System.out.println();
        System.out.println("  Iterate through all attributes using getAttributes()");

        LDAPAttributeSet entryAttrs = entry.getAttributeSet();
        System.out.println("  Found " + entryAttrs.size() + " attributes.");
        Enumeration attrEnum = entryAttrs.getAttributes();
        while ( (attrEnum != null) && (attrEnum.hasMoreElements()) ){

            // Get the attribute
            LDAPAttribute attr = (LDAPAttribute) attrEnum.nextElement();

            if (attr == null) {
                System.out.println("    [" + attr.getName() + ": not present]");
                continue;
            }

            Enumeration enumVals = attr.getStringValues();
            boolean hasVals = false;
            while ( (enumVals != null) && (enumVals.hasMoreElements()) ) {
                String val = (String) enumVals.nextElement();
                System.out.println("    [" + attr.getName() + ": " +  val + "]");
                hasVals = true;
            }
            if (!hasVals) {
                System.out.println("    [" + attr.getName() + ": has no values]");
            }
        }
        
        System.out.println("---------------------------------------------");
    }

    /**
     * Method that overrides the toString() method of the core Object class.
     * This method is used to print an object, which in this case, will print
     * the LDAP server login and search parameters provided by the user from
     * the command-line.
     */
    public String toString() {
        String str = null;

        str = "Host   : " + this.host + "\n" +
              "Port   : " + this.port + "\n" +
              "AuthDN : " + this.authid + "\n" +
              "AuthPW : " + this.authpw + "\n" +
              "BaseDN : " + this.base + "\n" + 
              "Filter : " + this.filter;
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
        
        LDAPSearchExample se = new LDAPSearchExample(args);
        se.performSearch();
        //System.out.println(se);

    }

}
