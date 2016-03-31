// -----------------------------------------------------------------------------
// ClientSocketTest.java
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
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * -----------------------------------------------------------------------------
 * This program will be used to test Socket functionality in Java.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ClientSocketTest {

    public static final short TIME_PORT = 13;

    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) throws IOException {

        String hostName = args.length == 0 ? "localhost" : args[0];

        System.out.println();
            
        try {

            // --------------------------------------
            // Open a socket to the daytime server
            // --------------------------------------
            Socket serverSocket = new Socket(hostName, TIME_PORT);


            System.out.println("-------------------------");
            System.out.println("Server Socket Information");
            System.out.println("-------------------------");
            System.out.println("serverSocket         : " + serverSocket);
            System.out.println("Keep Alive           : " + serverSocket.getKeepAlive());
            System.out.println("Receive Buffer Size  : " + serverSocket.getReceiveBufferSize());
            System.out.println("Send Buffer Size     : " + serverSocket.getSendBufferSize());
            System.out.println("Is Socket Bound?     : " + serverSocket.isBound());
            System.out.println("Is Socket Connected? : " + serverSocket.isConnected());
            System.out.println("Is Socket Closed?    : " + serverSocket.isClosed());
            System.out.println("So Timeout           : " + serverSocket.getSoTimeout());
            System.out.println("So Linger            : " + serverSocket.getSoLinger());
            System.out.println("TCP No Delay         : " + serverSocket.getTcpNoDelay());
            System.out.println("Traffic Class        : " + serverSocket.getTrafficClass());
            System.out.println("Socket Channel       : " + serverSocket.getChannel());
            System.out.println("Reuse Address?       : " + serverSocket.getReuseAddress());
            System.out.println("\n");


            // --------------------------------------
            // Get (Server) InetAddress / Socket Information
            // --------------------------------------
            InetAddress inetAddrServer = serverSocket.getInetAddress();

            System.out.println("---------------------------");
            System.out.println("Remote (Server) Information");
            System.out.println("---------------------------");
            System.out.println("InetAddress - (Structure) : " + inetAddrServer);
            System.out.println("Socket Address - (Remote) : " + serverSocket.getRemoteSocketAddress());
            System.out.println("Canonical Name            : " + inetAddrServer.getCanonicalHostName());
            System.out.println("Host Name                 : " + inetAddrServer.getHostName());
            System.out.println("Host Address              : " + inetAddrServer.getHostAddress());
            System.out.println("Port                      : " + serverSocket.getPort());
            
            System.out.print("RAW IP Address - (byte[]) : ");
                byte[] b1 = inetAddrServer.getAddress();
                for (int i=0; i< b1.length; i++) {
                    if (i > 0) {System.out.print(".");}
                    System.out.print(b1[i] & 0xff);
                }
                System.out.println();

            System.out.println("Is Loopback Address?      : " + inetAddrServer.isLoopbackAddress());
            System.out.println("Is Multicast Address?     : " + inetAddrServer.isMulticastAddress());
            System.out.println("\n");






            // ---------------------------------------------
            // Get (Client) InetAddress / Socket Information
            // ---------------------------------------------
            InetAddress inetAddrClient = serverSocket.getLocalAddress();

            System.out.println("--------------------------");
            System.out.println("Local (Client) Information");
            System.out.println("--------------------------");
            System.out.println("InetAddress - (Structure) : " + inetAddrClient);
            System.out.println("Socket Address - (Local)  : " + serverSocket.getLocalSocketAddress());
            System.out.println("Canonical Name            : " + inetAddrClient.getCanonicalHostName());
            System.out.println("Host Name                 : " + inetAddrClient.getHostName());
            System.out.println("Host Address              : " + inetAddrClient.getHostAddress());
            System.out.println("Port                      : " + serverSocket.getLocalPort());
            
            System.out.print("RAW IP Address - (byte[]) : ");
                byte[] b2 = inetAddrClient.getAddress();
                for (int i=0; i< b2.length; i++) {
                    if (i > 0) {System.out.print(".");}
                    System.out.print(b2[i] & 0xff);
                }
                System.out.println();

            System.out.println("Is Loopback Address?      : " + inetAddrClient.isLoopbackAddress());
            System.out.println("Is Multicast Address?     : " + inetAddrClient.isMulticastAddress());
            System.out.println("\n");


            // -------------------------------------------------------------
            // Open an input (buffered) stream that can be used to read from
            // the socket.
            // -------------------------------------------------------------
            BufferedReader is = new BufferedReader(
                                    new InputStreamReader(
                                            serverSocket.getInputStream()));

            // --------------------
            // Read from the socket
            // --------------------
            String remoteTime = is.readLine();

            // ---------------------------------
            // Close the input (buffered) stream
            // ---------------------------------
            is.close();

            System.out.println("Remote Server Time : " + remoteTime);
            System.out.println();

        } catch (UnknownHostException e) {

            e.printStackTrace();
        
        } catch (IOException e) {

            e.printStackTrace();
            
        }


    }

}
