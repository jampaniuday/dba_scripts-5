// -----------------------------------------------------------------------------
// ParallelismExample.java
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
 * Used to provide an example of parallel threads. The ParallelismExample 
 * program creates 3 Egoist threads. Each thread loops 50 times, writing out its
 * name. In any 2 runs, you should get slightly different output. The OutChar 
 * class exists to force serialized access to System.out. The ParallelismExample
 * thread has an infinite loop, which would normally make the program run until 
 * cancelled. This is prevented by making the thread into a daemon, which allows
 * the program to terminate when all normal threads terminate.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

class ParallelismExample extends Thread {

    public void run() {

        for (;;) {
            try {
                sleep(5);
            } catch(InterruptedException e) {
                // Swallow the exception
            }
        }

    }


    public static void main(String[] s) {

        Thread q;
        OutChar out = new OutChar();
        for (int i = 'A'; i <= 'C'; i++) {
            q = new Egoist((char)i, out);
            q.setPriority(Thread.NORM_PRIORITY - 1);
            q.start();
        }
        q = new ParallelismExample();
        q.setPriority(Thread.MAX_PRIORITY);
        q.setDaemon(true);
        q.start();

    }
}

class OutChar {
    int len = 0;
    public synchronized void out(char c) {
        System.out.print(c);
        if (++len % 50 == 0) {
            System.out.println();
        }
    }
}


class Egoist extends Thread {

    char me;
    OutChar out;

    public Egoist(char ch, OutChar o) {
        me = ch;
        out = o;
    }

    public void run() {
        for (int i=0; i<300; i++) {
            out.out(me);
        }
    }
}
