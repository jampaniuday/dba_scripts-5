// -----------------------------------------------------------------------------
// AbsoluteRelativePath.java
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
 * This program demonstrates how to get an Absolute Filename Path from a
 * Relative Filename Path. Note that the filename (in the case of this example:
 * README_InputFile.txt) does not have to exist on the filesystem.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class AbsoluteRelativePath {

    private static void doConversion() {

        // Create a File object
        File fileName = new File("README_InputFile.txt");
        System.out.println();
        System.out.println("Original Filename              = " + fileName + "\n");

        fileName = fileName.getAbsoluteFile();
        System.out.println("Absolute Filename              = " + fileName + "\n");

        fileName = new File("dir" + File.separatorChar + "README_InputFile.txt");
        fileName = fileName.getAbsoluteFile();
        System.out.println("Absolute Filename with \"dir\"   = " + fileName + "\n");

        fileName = new File(".." + File.separatorChar + "README_InputFile.txt");
        fileName = fileName.getAbsoluteFile();
        System.out.println("Absolute Filename with \"..\"    = " + fileName + "\n");

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doConversion();
    }

}
