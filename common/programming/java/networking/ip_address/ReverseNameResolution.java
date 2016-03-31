// -----------------------------------------------------------------------------
// ReverseNameResolution.java
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
 
import java.net.*;

/**
 * -----------------------------------------------------------------------------
 * This class is an example of how to perform reverse name resolution given
 * an IP address provided on the command-line.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ReverseNameResolution {


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        String ipAddress = args.length == 0 ? "127.0.0.1" : args[0];
        InetAddress inetAddress = null;

        // Get the host name given textual representation of the IP address
        try {
            inetAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host Exception");
            e.printStackTrace();
        }


        // The InetAddress object
        System.out.println("InetAddress object " + ipAddress.toString());


        // Get machine's host name
        System.out.println(
            "Host name of " + ipAddress +
            " is " + inetAddress.getHostName()
        );


        // Get machine's canonical host name
        System.out.println(
            "canonical host name of " + ipAddress +
            " is " + inetAddress.getCanonicalHostName()
        );

    }

}
