// -----------------------------------------------------------------------------
// RenamingFileOrDir.java
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
 * This program demonstrates how to rename a File or Directory.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class RenamingFileOrDir {

    private static void doRename() {

        // A File (or Directory) with the old name
        File file1 = new File("OldFile.txt");
        System.out.println("File1 = " + file1);

        // A File (or Directory) with the new name
        File file2 = new File("NewFile.txt");
        System.out.println("File2 = " + file2);

        // Rename File (or Directory)
        boolean success = file1.renameTo(file2);

        if (success) {
            System.out.println("File was successfully renamed.\n");
        } else {
            System.out.println("File was not successfully renamed.\n");
        }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doRename();
    }

}
