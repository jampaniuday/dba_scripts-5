// -----------------------------------------------------------------------------
// TraceDebugMessages.java
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
 * This program demonstrates how to turn on the ability to print out trace 
 * statements from Java's security model.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class TraceDebugMessages {

    private String fileName    = null;

    /**
     * Construct a TraceDebugMessages object given the passed in file name.
     * @param fName Name of the file to create/delete for the newly created
     *              object.
     */
    public TraceDebugMessages(String fName) {
        this.fileName = fName;
    }

    /**
     * No-arg constructor
     */
    public TraceDebugMessages() {
        this("defaultFile.txt");
    }

    /**
     * Creates the object file.
     */
    public void createFile() {

        try {
        
            File fileHandler = new File(fileName);
            
            if (fileHandler.createNewFile()) {
                System.out.println("File (" + fileName + ") did not exist and was created.\n");
            } else {
                System.out.println("File (" + fileName + ") already exists.\n");
            }
            
        } catch (IOException e) {
            System.out.println("Could not create file: " + fileName);
            e.printStackTrace();
        }

    }

    /**
     * Deletes the object file.
     */
    public void deleteFile() {

        File fileHandler = new File(fileName);
        
        boolean success = fileHandler.delete();

        if (success) {
            System.out.println("File (" + fileName + ") was successfully deleted.\n");
        } else {
            System.out.println("File (" + fileName + ") was not successfully deleted.\n");
        }

    }

    public String getFileName() {
        return fileName;
    }

    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        TraceDebugMessages trc = new TraceDebugMessages("testFile.txt");
        trc.createFile();
        trc.deleteFile();
    }

}
