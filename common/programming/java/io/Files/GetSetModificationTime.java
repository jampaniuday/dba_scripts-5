// -----------------------------------------------------------------------------
// GetSetModificationTime.java
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
import java.util.Date;

/**
 * -----------------------------------------------------------------------------
 * This program demonstrates how to get and set the modification time of a file
 * or directory.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class GetSetModificationTime {

    private static void doModifyTime() {

        // Create a File object
        File file = new File("README_InputFile.txt");

        // Get the last modified time.
        // 0L is returned if the file does not exist.
        long modifiedTime = file.lastModified();

        System.out.println();
        System.out.println("The last modified time of file " + file);
        System.out.println("---------------------------------------------------");
        System.out.println("  - milliseconds since midnight, January 1, 1970, GMT = " + modifiedTime);
        System.out.println("  - date = " + new Date(modifiedTime));
        System.out.println();


        // Set the last modified time
        long newModifiedTime = System.currentTimeMillis();
        boolean success = file.setLastModified(newModifiedTime);

        System.out.println("Setting a new modified time for the file " + file);
        System.out.println("---------------------------------------------------");
        System.out.println("  - new milliseconds = " + newModifiedTime);
        System.out.println("  - date = " + new Date(newModifiedTime));
        System.out.println();

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doModifyTime();
    }

}
