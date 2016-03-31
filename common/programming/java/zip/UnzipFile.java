// -----------------------------------------------------------------------------
// UnzipFile.java
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
 
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.Enumeration;

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of extracting the contents (files) from a zip 
 * file.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class UnzipFile {

    private static void doUnzipFiles(String zipFileName) {

        try {

            ZipFile zf = new ZipFile(zipFileName);

            System.out.println("Archive:  " + zipFileName);
            
            // Enumerate each entry
            for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
                
                // Get the entry and its name
                ZipEntry zipEntry = (ZipEntry)entries.nextElement();
                String zipEntryName = zipEntry.getName();
                System.out.println("  inflating: " + zipEntryName);
                
                int lastDirSep;
                if ( (lastDirSep = zipEntryName.lastIndexOf('/')) > 0 ) {
                    String dirName = zipEntryName.substring(0, lastDirSep);
                    (new File(dirName)).mkdirs();
                }
                
                
                if (!zipEntryName.endsWith("/")) {
                    OutputStream out = new FileOutputStream(zipEntryName);
                    InputStream in = zf.getInputStream(zipEntry);
                    
                    byte[] buf = new byte[1024];
                    int len;
                    while((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
    
                    // Close streams
                    out.close();
                    in.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java UnzipFile zipfilename");
        } else {
            doUnzipFiles(args[0]);
        }

    }

}

