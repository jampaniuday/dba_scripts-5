// -----------------------------------------------------------------------------
// DetermineTwoFilenamePathsSameFile.java
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
import java.io.IOException;

/**
 * -----------------------------------------------------------------------------
 * This program demonstrates how to determine if two filename paths refer to
 * the same file. A filename path may include redundant names such as '.' or
 * '..' or symbolic links (on UNIX platforms). File.getCanonicalFile()
 * converts a filename path to a unique canonical form suitable for 
 * comparisons. Note that the filename (in the case of this example:
 * README_InputFile.txt) does not have to exist on the filesystem.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class DetermineTwoFilenamePathsSameFile {

    private static void doTest() {

        // Create a File object
        File fileName1 = new File("./README_InputFile.txt");
        File fileName2 = new File("README_InputFile.txt");
        System.out.println();
        System.out.println("Original Filename1 = " + fileName1 + "\n");
        System.out.println("Original Filename2 = " + fileName2 + "\n");

        // Filename paths are not equal
        boolean b = fileName1.equals(fileName2);
        System.out.println(
                    "Check to determine if the two filename paths are equal: (" + 
                    b + ")\n");

        // Normalize the paths
        try {
            fileName1 = fileName1.getCanonicalFile();
            fileName2 = fileName2.getCanonicalFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Normalized Path for Filename1 = " + fileName1 + "\n");
        System.out.println("Normalized Path for Filename2 = " + fileName2 + "\n");

        // Filename paths are now equal
        b = fileName1.equals(fileName2);
        System.out.println(
                    "Check to determine if the two filename paths are equal: (" + 
                    b + ")\n");

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doTest();
    }

}
