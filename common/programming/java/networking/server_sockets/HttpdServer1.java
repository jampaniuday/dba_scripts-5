// -----------------------------------------------------------------------------
// HttpdServer1.java
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
import java.io.*;
import java.util.*;

/**
 * -----------------------------------------------------------------------------
 * This program demonstrates how to write a simple HTTP server.
 * It's only capability is to pass back a requested file in the server's 
 * directory or sub-directory.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class HttpdServer1 {

    private static final String httpName    = "Httpd Server";
    private static final String httpVersion = "Version 1.0";

    private static void usage() {
        System.err.println();
        System.err.println("Usage:");
        System.err.println("  java HttpdServer1 port");
        System.err.println();
    }

    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            usage();
            System.exit(1);
        }

        int servPortNumber = Integer.parseInt(args[0]);
        
        // System.setSecurityManager(new HttpdServer1SecurityManager());
        
        ServerSocket servSock = new ServerSocket(servPortNumber);



        System.out.println(
                "Starting " + 
                httpName + " " + httpVersion + 
                " on port " + servPortNumber + "...");

        while (true) {
            new HttpdServer1Connection(servSock.accept()).start();
            System.out.println("New connection");
        }    

    }

}



/**
 * A thread class to handle the clients as they are accepted by the
 * ServerSocket in HttpdServer1
 */

class HttpdServer1Connection extends Thread {

    Socket client;
  
    HttpdServer1Connection(Socket client) throws SocketException {
        this.client = client;
        setPriority(NORM_PRIORITY-1);
    }


    public void run() {
    
        String contentType="uninit";
        
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(),"8859_1"));
            OutputStream out = client.getOutputStream();
            PrintWriter printOut = new PrintWriter(new OutputStreamWriter(out,"8859_1"),true);
            String request=in.readLine();
            System.out.println("Request: " + request);
            StringTokenizer sToken = new StringTokenizer(request);
      
            boolean sendMIME = false;
            
            if (request.indexOf("HTTP/") != -1) {
                sendMIME=true;
            }
      
            if ((sToken.countTokens() >= 2) && (sToken.nextToken().equals("GET"))) {

                if ( (request=sToken.nextToken()).startsWith("/") ) {
                    request=request.substring(1);
                }
                
                if ( (request.endsWith("/") || request.equals("")) ) {
                    request += "index.html";
                }
                
                if ( (request.endsWith(".html"))
                     ||
                     (request.endsWith(".htm"))
                     ||
                     (request.endsWith(".shtml")) ) {

                    contentType = ("text/html");
                    
                } else if (request.endsWith(".bat")) {

                    System.out.println(request);
                    
                    try {
                        Runtime runT = Runtime.getRuntime();
                        Process proc = runT.exec(request);
                        contentType=("text/plain");
                    } catch(IOException ioe){
                        System.out.println(ioe);
                    }
                    
                } else {
                
                    contentType=("text/plain");
                    
                }

                try {
                
                    if ( !(request.endsWith(".bat")) ) {
                        FileInputStream fInStream = new FileInputStream(request);
                        if (sendMIME) {
                            PrintStream outStream = new PrintStream(out);
                            outStream.print("HTTP/1.0 200 OK\r\n");
                            Date now = new Date();
                            outStream.print("Date: " + now + "\r\n");
                            outStream.print("Server: HttpdServer 1.0\r\n");
                            outStream.print("Content-length: " + fInStream.available() + "\r\n");
                            outStream.print("Content-type: " + contentType +"\r\n\r\n");
                        }
                        byte[] data = new byte[fInStream.available()];
                        fInStream.read(data);
                        out.write(data);
                        out.flush();
                    } else {
                        printOut.println("Running program...");
                    }
                    
                } catch (FileNotFoundException e) {
                    printOut.println("404 Object Not Found");
                }
                //        catch(SecurityException e) {
                //          printOut.println("403 Forbidden");
                //        }
            } else {
                printOut.println("400 Bad Request");
            }
            client.close();
            
        } catch(IOException e) {
            System.out.println("I/O error" + e);
        }
    }

}
