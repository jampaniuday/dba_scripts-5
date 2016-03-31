// -----------------------------------------------------------------------------
// ParentsOfFilenamePath.java
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
 * This program demonstrates how to get the parents of a filename path. Note
 * that the filename (in the case of this example: README_InputFile.txt) does 
 * not have to exist on the filesystem.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ParentsOfFilenamePath {

    private static void doTest() {

        // Get the parent of a relative filename path
        File fileName = new File("README_InputFile.txt");
        String parentPath = fileName.getParent();
        File   parentDir  = fileName.getParentFile();
        System.out.println();
        System.out.println("Original Filename   = " + fileName);
        System.out.println("Parent Path         = " + parentPath);
        System.out.println("Parent Directory    = " + parentDir);

        // Get the parents of an absolute filename path
        fileName   = new File("/u01/app/oracle/README_InputFile.txt");
        parentPath = fileName.getParent();
        parentDir  = fileName.getParentFile();
        System.out.println();
        System.out.println("Original Filename   = " + fileName);
        System.out.println("Parent Path         = " + parentPath);
        System.out.println("Parent Directory    = " + parentDir);

        // The remaining portion of the code will keep stripping out
        // the parent from each filename path.
        System.out.println();
        System.out.println("Original Filename   = " + parentPath);
        parentPath = parentDir.getParent();
        parentDir  = parentDir.getParentFile();
        System.out.println("Parent Path         = " + parentPath);
        System.out.println("Parent Directory    = " + parentDir);

        System.out.println();
        System.out.println("Original Filename   = " + parentPath);
        parentPath = parentDir.getParent();
        parentDir  = parentDir.getParentFile();
        System.out.println("Parent Path         = " + parentPath);
        System.out.println("Parent Directory    = " + parentDir);

        System.out.println();
        System.out.println("Original Filename   = " + parentPath);
        parentPath = parentDir.getParent();
        parentDir  = parentDir.getParentFile();
        System.out.println("Parent Path         = " + parentPath);
        System.out.println("Parent Directory    = " + parentDir);

        System.out.println();
        System.out.println("Original Filename   = " + parentPath);
        parentPath = parentDir.getParent();
        parentDir  = parentDir.getParentFile();
        System.out.println("Parent Path         = " + parentPath);
        System.out.println("Parent Directory    = " + parentDir);

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doTest();
    }

}
