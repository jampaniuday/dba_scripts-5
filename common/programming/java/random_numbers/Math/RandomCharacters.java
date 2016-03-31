// -----------------------------------------------------------------------------
// RandomCharacters.java
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
 * Used to provide an example of producing random characters by using the random
 * method from the Math class.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class RandomCharacters {

    private static void doRandomCharacters() {

        double randomNumber;
        double randomNumberSetup;
        char randomCharacter;

        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < 10; i++) {
            randomNumber = Math.random();
            randomNumberSetup = (randomNumber * 26 + 'a');
            randomCharacter = (char) randomNumberSetup;
            System.out.print(randomCharacter + ": ");
            switch(randomCharacter) {
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u': System.out.print("vowel");
                    break;
                case 'y':
                case 'w': System.out.print("sometimes a vowel");
                    break;
                default:  System.out.print("consonant");
            }
            System.out.println(" - Random number was (" + randomNumber + ")");
        }

        System.out.println("----------------------------------------------------------------------");
        System.out.println("\n");
    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doRandomCharacters();
    }

}

