// -----------------------------------------------------------------------------
// ThreadJoin2.java
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
 
/**
 * -----------------------------------------------------------------------------
 * Another example of how to join two threads together. For a detailed example
 * of how the join() method works, see the ThreadJoin class in this section of
 * examples.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ThreadJoin2 implements Runnable {

    Thread t;

    /**
     * No-arg constructor
     */
    public ThreadJoin2() {
        t = new Thread(this);
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }

    /**
     * Overriden run() method. This will perform some lon
     */
    public void run() {
        String tmpStr = "";
        for (int i=0; i<20000; i++) {
            tmpStr += Integer.toString(i);
        }
    }

    
    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        ThreadJoin2 jt = new ThreadJoin2();
        try {
            System.out.println("Before join...");
            jt.t.join();
            System.out.println("After join...");
        } catch (InterruptedException ie) {
            System.out.println("Main exception: " + ie);
            ie.printStackTrace();
        }
        

    }

}
