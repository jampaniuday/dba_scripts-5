// -----------------------------------------------------------------------------
// CreateDirectory.java
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
 * This program demonstrates how to create a directory.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class CreateDirectory {

    private static void doCreateDir() {

        // Create a directory; all ancestor directories must exist if you
        // use an absolute path. If you are using a relative path, the 
        // directory will be created in the current working directory.
        String newDir = "new_dir";
        boolean success = (new File(newDir)).mkdir();

        if (success) {
            System.out.println("Successfully created directory: " + newDir);
        } else {
            System.out.println("Failed to create directory: " + newDir);
        }

        // Create a directory; all non-existent ancestor directories are
        // automatically created.
        newDir = "c:/export/home/jeffreyh/new_dir1/new_dir2/new_dir3";
        success = (new File(newDir)).mkdirs();

        if (success) {
            System.out.println("Successfully created directory: " + newDir);
        } else {
            System.out.println("Failed to create directory: " + newDir);
        }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doCreateDir();
    }

}
