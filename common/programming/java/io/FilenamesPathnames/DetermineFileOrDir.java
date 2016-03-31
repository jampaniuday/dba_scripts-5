// -----------------------------------------------------------------------------
// DetermineFileOrDir.java
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

/**
 * -----------------------------------------------------------------------------
 * This program demonstrates how to determine if a filename path is a File or
 * a Directory.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class DetermineFileOrDir {

    private static void doTest() {

        // Create a File object
        File fileName = new File("README_InputFile.txt");
        System.out.println();
        System.out.println("Original Filename = " + fileName);

        boolean isDir = fileName.isDirectory();

        System.out.println("Is " + fileName + " a directory? (" + isDir + ")");

        // Keep in mind that the following pathSeparator character '/' can be 
        // used in both MS Windows and UNIX environments. You could have also
        // used:
        //   fileName = new File("c:\\export\\home\\jeffreyh\\JDeveloper\\IsrvWorkspace\\JavaProgramming\\src\\io\\FilenamesPathnames");
        fileName = new File("c:/export/home/jeffreyh/JDeveloper/IsrvWorkspace/JavaProgramming/src/io/FilenamesPathnames");

        isDir = fileName.isDirectory();

        System.out.println("Is " + fileName + " a directory? (" + isDir + ")");

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doTest();
    }

}
