// -----------------------------------------------------------------------------
// ThreadCountdownExtThread.java
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
 * Used to provide an example of how to test the Java Thread facilities by 
 * issuing an array of threads that perform looping operations. The loop was
 * used to utilize as much CPU as possible with very little I/O.
 * 
 * Threading Concepts
 * ------------------
 * While it is true that the start() method eventually calls the run() method,
 * it does so in another thread of control; this new thread, after dealing
 * with some initialization details, then calls the run() method. After the
 * run() method completes, this new thread also deals with the details of 
 * terminating the thread. Keep in mind that the start() method of the original
 * thread returns immediately. Thus, the run() method will be executing in the
 * newly formed thread at about the same time the start() method returns in the
 * first thread.
 * 
 * In this example, we create a thread of control by extending the Thread class.
 * This is one of two methods for creating threads. Creating another thread of 
 * control using this method is a two-step process. First we must create the 
 * code that executes in the new thread by overriding the run() method in our 
 * subclass. Then we create the actual subclassed object using its
 * constructor (which calls the default constructor of the Thread class
 * in this case) and begin execution of its run() method by calling the
 * start() method of the subclass.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ThreadCountdownExtThread extends Thread {

    private int countDown = 5;
    private static int threadCount = 0;
    private int threadNumber = ++threadCount;

    /**
     * Constructs a ThreadCountdown object that will executed in a separate 
     * thread.
     */
    public ThreadCountdownExtThread() {
        System.out.println("\nStarting thread number => " + threadCount + "\n");
    }

    /**
     * A callback method that will be called by the start() method of the Thread 
     * class. This method is very similar to the main() method of a typical 
     * class. A new thread begins execution with the run() method in the same 
     * way a program begins execution with the main() method.
     * <p>
     * While the main() method receives its arguments from the argv parameter
     * (which is typically set from the command line), the newly created thread
     * must receive its arguments programmatically from the originating thread.
     * Hence, parameters can be passed in via the constructor, static instance
     * variables, or any other technique designed by the developer.
     */
    public void run() {
        while(true) {

            System.out.println(
                "  - Thread number " + 
                threadNumber + 
                " ( Current Countdown = " + countDown + " )");

            for (int j = 0; j < 300000000; j++) {
                // This is a test...
            }
            
            if (--countDown == 0) {
                System.out.println(
                    "\nEnding thread number => " + threadNumber + "\n");
                return;
            }
        }
    }


    /**
     * Starts all five threads. This method will perform a sleep operation 
     * before starting each thread to allow the output to reflect how each
     * thread operates.
     * @exception java.lang.InterruptedException Thrown from the Thread class.
     */
    private static void doThreadCountdown()
        throws java.lang.InterruptedException {
        
        for (int i = 0; i < 5; i++) {
            Thread.sleep(2000);
            new ThreadCountdownExtThread().start();
        }
        System.out.println("\n<< All threads have now been started!!! >>\n");
    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     * @exception java.lang.InterruptedException Thrown from the Thread class.
     */
    public static void main(String[] args)
        throws java.lang.InterruptedException {

        System.out.println("\n<< MAIN METHOD (Begin) >>");
        
        doThreadCountdown();
        
        System.out.println("<< MAIN METHOD (End) >>\n");

    }

}
