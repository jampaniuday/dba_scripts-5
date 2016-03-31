// -----------------------------------------------------------------------------
// SystemProperties.java
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
 
import java.util.Properties;
import java.util.Enumeration;

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of how to retrieve and set system properties using
 * the Java System class.
 * 
 * The System class contains several useful class fields and methods. It cannot
 * be instantiated. 
 * 
 * Among the facilities provided by the System class are standard input, 
 * standard output, and error output streams; access to externally defined 
 * "properties"; a means of loading files and libraries; and a utility method
 * for quickly copying a portion of an array.
 * <p>
 * The Properties class represents a persistent set of properties. The
 * Properties can be saved to a stream or loaded from a stream. Each key and
 * its corresponding value in the property list is a string. 
 * 
 * A property list can contain another property list as its "defaults"; this
 * second property list is searched if the property key is not found in the 
 * original property list. 
 * 
 * Because Properties inherits from Hashtable, the "put" and "putAll" methods
 * can be applied to a Properties object. Their use is strongly discouraged as 
 * they allow the caller to insert entries whose keys or values are not Strings.
 * The setProperty method should be used instead. If the store or save method is
 * called on a "compromised" Properties object that contains a non-String key or
 * value, the call will fail. 
 * 
 * When saving properties to a stream or loading them from a stream, the
 * ISO 8859-1 character encoding is used. For characters that cannot be directly
 * represented in this encoding, Unicode escapes are used; however, only a 
 * single 'u' character is allowed in an escape sequence. The native2ascii tool
 * can be used to convert property files to and from other character encodings.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class SystemProperties {

    private static void setGetSystemProperty() {

        System.out.println();
        System.out.println("+-------------------------+");
        System.out.println("| SET SYSTEM PROPERTIES   |");
        System.out.println("+-------------------------+");

        System.out.println();
        System.out.println("  -----------------------");
        System.out.println("  Setting System Property");
        System.out.println("  -----------------------");

        System.setProperty("test.value1", "This is test #1");
        System.setProperty("test.value2", "This is test #2");


        System.out.println();
        System.out.println("  ----------------------------");
        System.out.println("  Retrieving System Properties");
        System.out.println("  ----------------------------");
        System.out.println();

        System.out.println("  {test.value1} = " + System.getProperty("test.value1"));
        System.out.println("  {test.value2} = " + System.getProperty("test.value2"));

    }


    private static void querySystemProperties() {

        System.out.println();
        System.out.println("+-------------------------+");
        System.out.println("| QUERY SYSTEM PROPERTIES |");
        System.out.println("+-------------------------+");

        Properties sysProps = System.getProperties();

        System.out.println();
        System.out.println("  ------------------------------------------------");
        System.out.println("  Use list() method to list all property values...");
        System.out.println("  ------------------------------------------------");
        System.out.println();
        
        sysProps.list(System.out);

        System.out.println();
        System.out.println("  ------------------------------------------------");
        System.out.println("  Get Property Names and Enumerate through them...");
        System.out.println("  ------------------------------------------------");
        System.out.println();

        Enumeration enProps = sysProps.propertyNames();
        String key = "";
        while ( enProps.hasMoreElements() ) {
            key = (String) enProps.nextElement();
            System.out.println("  " + key + "  ->  " + sysProps.getProperty(key));
        }

    }



    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        setGetSystemProperty();
        querySystemProperties();

    }

}
