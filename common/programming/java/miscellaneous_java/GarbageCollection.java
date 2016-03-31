// -----------------------------------------------------------------------------
// GarbageCollection.java
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
 
import java.util.Vector;

/**
 * -----------------------------------------------------------------------------
 * A Java program that creates many objects to force the garbage collector
 * to work, and also includes some synchronous calls to the garbage collector.
 *
 * 
 * COMPILE:
 *      javac GarbageCollection.java
 *  
 * RUN:
 *      java -verbosegc GarbageCollection
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class GarbageCollection {


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        int SIZE = 4000;
        StringBuffer s;
        Vector v;

        // Create some objects to give the garbage collector something to do
        for (int i = 0; i < SIZE; i++) {
            s = new StringBuffer(50);
            v = new Vector(30);
            s.append(i).append(i+1).append(i+2).append(i+3);
        }

        s= null;
        v = null;
        System.out.println("Staring explicit Garbage Collection.");
        long time = System.currentTimeMillis();
        System.gc();

        System.out.println(
                "Garbage Collection took " + 
                (System.currentTimeMillis()-time) + " ms");

        int[] arr = new int[SIZE*10];

        // null out the variable so that the array can be garbage collected
        time = System.currentTimeMillis();
        arr = null;
        System.out.println("Staring explicit Garbage Collection.");
        System.gc();

        System.out.println(
                "Garbage Collection took " + 
                (System.currentTimeMillis()-time) + " ms");
        
    }


}
