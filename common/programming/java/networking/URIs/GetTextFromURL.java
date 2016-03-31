// -----------------------------------------------------------------------------
// GetTextFromURL.java
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

/**
 * -----------------------------------------------------------------------------
 * This class provides an example of how to read text from a given URL.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class GetTextFromURL {


    public static void main(String[] args) {

        try {

            // Create a URL object
            URL url = new URL("http://www.idevelopment.info:80/html/PK_JeffHunter.shtml");

            // Read all of the text returned by the HTTP server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String htmlText;

            while ((htmlText = in.readLine()) != null) {
                // Keep in mind that readLine() strips the newline characters
                System.out.println(htmlText);
            }

            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
