// -----------------------------------------------------------------------------
// GenerateSymmetricKeys.java
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
 
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * -----------------------------------------------------------------------------
 * The following example generates a key for various symmetric cipher
 * algorithms. Some of the more popular algorithms are:
 *      Blowfish
 *      DES
 *      DESede
 *      PBEWithMD5AndDES
 *      PBEWithMD5AndTripleDES
 *      TripleDES
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class GenerateSymmetricKeys {

    private static void generateKey(String keyAlgorithm) {

        try {

            KeyGenerator keyGen = KeyGenerator.getInstance(keyAlgorithm);
            SecretKey key = keyGen.generateKey();

            System.out.println(
                    "\n" +
                    "Generating symmetric key using " +
                    key.getAlgorithm() + 
                    " algorithm");

            // Get the bytes of the key
            byte[] keyBytes = key.getEncoded();
            int numBytes = keyBytes.length;

            System.out.println(
                    "  The number of bytes in the key = " +
                    numBytes + ".");

            // The bytes can be converted back to a SecretKey
            SecretKey key2 = new SecretKeySpec(keyBytes, keyAlgorithm);
            System.out.println(
                "  Are both symmetric keys equal? " + key.equals(key2));

        } catch (NoSuchAlgorithmException e) {

            System.out.println("Exception");
            System.out.println("No such algorithm: " + keyAlgorithm);

        }

    }


    public static void main(String[] args) {

        // Generate a DES key
        generateKey("DES");

        // Generate a Blowfish key
        generateKey("Blowfish");

        // Generate a DESede key
        generateKey("DESede");
    }

}

