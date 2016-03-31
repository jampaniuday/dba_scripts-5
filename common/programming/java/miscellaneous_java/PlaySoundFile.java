// -----------------------------------------------------------------------------
// PlaySoundFile.java
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
 
import java.applet.*;
import java.net.*;

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of how to use the applet package to play standard 
 * sound files.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */
 
public class PlaySoundFile {

    private static void doPlaySoundFile(String[] args) {

        String defaultSoundFile[] = {
              "file:///WINNT/Media/Windows Logon Sound.wav"
            , "file:///WINNT/Media/Windows Logoff Sound.wav"
        };

        if (args.length == 0) {

            main(defaultSoundFile);

        } else {

            for (int i=0; i<args.length; i++) {

                System.out.println("Playing sound file: " + args[i]);

                try {
                    URL soundFile = new URL(args[i]);
                    Applet.newAudioClip(soundFile).play();
                } catch (Exception e) {
                    System.err.println(e);
                }
                System.out.println("Completed sound file: " + args[i] + "\n");

            }
            

            // I have not been able to figure this one out. The program hangs
            // if I do not explicitly put an exit here. Putting the exit here
            // though, causes the program to exit before or during the play back.

            // System.exit(0);

        }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doPlaySoundFile(args);
    }

}
