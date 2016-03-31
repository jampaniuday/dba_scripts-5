// -----------------------------------------------------------------------------
// GeneratePublicPrivateKeys.java
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
 
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyFactory;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.NoSuchAlgorithmException;

/**
 * -----------------------------------------------------------------------------
 * The following example generates a key pair for various public/private
 * key algorithms. Some of the more popular algorithms are:
 *     1.2.840.10040.4.1
 *     1.3.14.3.2.12
 *     DH
 *     DSA
 *     DiffieHellman
 *     OID.1.2.840.10040.4.1
 *     RSA
 *     
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class GeneratePublicPrivateKeys {

    private static void generateKeys(  String keyAlgorithm
                                     , int    numBits) {

        try {

            // Get the public/private key pair
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyAlgorithm);
            keyGen.initialize(numBits);
            KeyPair keyPair = keyGen.genKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey  publicKey  = keyPair.getPublic();

            System.out.println(
                    "\n" +
                    "Generating key/value pair using " +
                    privateKey.getAlgorithm() + 
                    " algorithm");

            // Get the bytes of the public and private keys
            byte[] privateKeyBytes = privateKey.getEncoded();
            byte[] publicKeyBytes  = publicKey.getEncoded();

            // Get the formats of the encoded bytes
            String formatPrivate = privateKey.getFormat(); // PKCS#8
            String formatPublic  = publicKey.getFormat(); // X.509

            System.out.println("  Private Key Format : " + formatPrivate);
            System.out.println("  Public Key Format  : " + formatPublic);

            // The bytes can be converted back to public and private key objects
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey2 = keyFactory.generatePrivate(privateKeySpec);

            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey2 = keyFactory.generatePublic(publicKeySpec);

            // The original and new keys are the same
            System.out.println(
                "  Are both private keys equal? " + privateKey.equals(privateKey2));

            System.out.println(
                "  Are both public keys equal? " + publicKey.equals(publicKey2));

        } catch (InvalidKeySpecException specException) {

            System.out.println("Exception");
            System.out.println("Invalid Key Spec Exception");
            
        } catch (NoSuchAlgorithmException e) {

            System.out.println("Exception");
            System.out.println("No such algorithm: " + keyAlgorithm);

        }

    }


    public static void main(String[] args) {

        // Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
        generateKeys("DSA", 1024);

        // Generate a 576-bit DH key pair
        generateKeys("DH", 576);

        // Generate a 1024-bit RSA key pair
        generateKeys("RSA", 1024);
    }

}

