// -----------------------------------------------------------------------------
// EchoClientOneLine.java
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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * -----------------------------------------------------------------------------
 * This program will demonstrate how to read and write on the same Socket. The
 * <code>Echo</code> simply echoes back whatever lines of text you send to it.
 * It's not a very clever server, but it is a useful one: it helps in network
 * testing, and also in testing clients of this type.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class EchoClientOneLine {

    public static final short ECHO_PORT = 7;

    private  String hostName = null;
    private  String message  = null;


    /**
     * Constructor (no-arg)
     */
    public EchoClientOneLine(String host, String msg) {
        this.hostName = host;
        this.message  = msg;
    }


    /**
     * Hold one conversation across the network
     */
    protected void converse() {

        try {

            Socket sock = new Socket(hostName, ECHO_PORT);

            BufferedReader is = new BufferedReader(
                    new InputStreamReader(sock.getInputStream()));

            PrintWriter os = new PrintWriter(sock.getOutputStream(), true);

            // Do the CRLF ourself since println appends only a \r on platforms
            // where that is the native line ending.
            os.print(message + "\r\n");
            os.flush();

            String reply = is.readLine();

            System.out.println("Send \"" + message + "\"");
            System.out.println("Got  \"" + reply   + "\"");


        } catch (IOException e) {

            e.printStackTrace();
            
        }
        
    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println();
            System.out.println("usage: java EchoClientOneLine <server> <message>");
            System.out.println();
            System.exit(1);
        }

        new EchoClientOneLine(args[0], args[1]).converse();

    }
    
}
