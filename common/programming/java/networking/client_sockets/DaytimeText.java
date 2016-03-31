// -----------------------------------------------------------------------------
// DaytimeText.java
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
 
import java.net.Socket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * -----------------------------------------------------------------------------
 * This program demonstrates how to read/write textual data between a server
 * process and a client using Java sockets.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class DaytimeText {

    public static final short TIME_PORT = 13;

    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) throws IOException {

        String hostName = args.length == 0 ? "localhost" : args[0];

        try {

            // Open a socket to the daytime server
            Socket serverSocket = new Socket(hostName, TIME_PORT);

            // Get Server's Canonical Name
            InetAddress inetAddr       = serverSocket.getInetAddress();
            String xCanonicalHostName  = inetAddr.getCanonicalHostName();

            // Open an input (buffered) stream that can be used to read from
            // the socket.
            BufferedReader is = new BufferedReader(
                                    new InputStreamReader(serverSocket.getInputStream()));

            // Read from the socket
            String remoteTime = is.readLine();

            System.out.println();
            System.out.println("Time on " + xCanonicalHostName + " is " + remoteTime);
            System.out.println();

        } catch (IOException e) {

            e.printStackTrace();
            
        }


    }

}
