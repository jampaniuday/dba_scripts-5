// -----------------------------------------------------------------------------
// ConvertBetweenFilenamePathAndURL.java
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
 
import java.io.File;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedOutputStream;
import java.net.MalformedURLException;
import java.io.IOException;

/**
 * -----------------------------------------------------------------------------
 * This program demonstrates how to convert between a filename path and a URL.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ConvertBetweenFilenamePathAndURL {

    private static void doConversionTest() {

            // Create a File object
            File fileName = new File("README_InputFile.txt");

            // Convert the file object to a URL
            URL url = null;
            try {

                // The file need not exist. It is made into an absolute path
                // by prefixing the current working directory
                url = fileName.toURL();

                System.out.println();
                System.out.println("Converted fileName to URL");
                System.out.println("-------------------------");
                System.out.println("  fileName = " + fileName);
                System.out.println("  URL      = " + url);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            // Convert the URL to a file object
            fileName = new File(url.getFile());

            System.out.println();
            System.out.println("Converted URL to fileName");
            System.out.println("-------------------------");
            System.out.println("  URL      = " + url);
            System.out.println("  fileName = " + fileName);

            // Read the file contents usign the URL
            try {

                // Open an input stream
                InputStream is = url.openStream();

                byte[] buf = new byte[1024];
                int j = 0;
                while((j=is.read(buf))!=-1) {
                    // Perform any actions with this InputStream...
                }
                
                is.close();
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doConversionTest();
    }

}
