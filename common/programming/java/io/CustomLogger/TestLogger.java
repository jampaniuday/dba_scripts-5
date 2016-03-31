//------------------------------------------------------------------------------
// TestLogger.java
//------------------------------------------------------------------------------

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
 
public class TestLogger {

    public static void main (String[] args) {

        int c = 0;
        final int MAX = 90000000;

        Logger1 log = new Logger1("TestLogger.log");
        log.start();
        log.writeln("main : < Starting with counter at " + c);

        for (int i = 0; i < MAX; i++) {
            if ( (i % 10000000) == 0) {
                c += 1;
            }
        }

        log.writeln("main : > Ending main with counter at " + c);
        log.stop();

    }

}
