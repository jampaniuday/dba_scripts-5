// -----------------------------------------------------------------------------
// JNDIFileSystemDemo1.java
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
 
import javax.naming.Context;            // Contect Interface
import javax.naming.InitialContext;     // InitialContext Class
import javax.naming.NamingException;    // NamingException Class
import java.util.Hashtable;

/**
 * -----------------------------------------------------------------------------
 * This example class shows you how to write a program that looks up an object 
 * whose name is passed in as a command-line argument. It uses a service 
 * provider for the file system (fscontext.jar). Therefore the name that you 
 * supply to the program must be a filename.
 * 
 * We first create an initial context. We indicate that we're using the file 
 * system service provider by setting the environment properties parameter
 * (represented by a Hashtable class) to the InitialContext constructor.
 * 
 *  Context.INITIAL_CONTEXT_FACTORY = com.sun.jndi.fscontext.RefFSContextFactory
 * 
 * This class requires the file system service provider. For example, we will
 * use Sun's implementation (fscontext.jar). Keep in mind that use of this 
 * library will also require the (providerutil.jar) library.
 * 
 * To compile this example:
 * 
 *      % javac JNDIFileSystemDemo1.java
 * 
 * To run the example:
 * 
 *      % java -classpath "$JAVALIB/fscontext.jar:$JAVALIB/providerutil.jar:." JNDIFileSystemDemo1 /temp
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class JNDIFileSystemDemo1 {


    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println(
                "\nUsage: ALEX: java " +
                "-classpath " +
                "\"$JAVALIB/fscontext.jar;$JAVALIB/providerutil.jar;.\" " +
                "JNDIFileSystemDemo1 /temp\n");
            System.exit(1);
        }
        
        String name = args[0];

        System.out.println();
        System.out.println("Running JNDI File System Demo #1");
        System.out.println("File / Directory : " + name);
        System.out.println("==================================\n");

        System.out.println("  - Creating the environment Hashtable");
        Hashtable env = new Hashtable();
        env.put(     Context.INITIAL_CONTEXT_FACTORY
                  , "com.sun.jndi.fscontext.RefFSContextFactory");

        try {
        
            System.out.println("  - Creating the initial context");
            Context ctx = new InitialContext(env); 


            System.out.println("  - Look up an object");
            Object obj = ctx.lookup(name);

        
            System.out.println("  - Print it");
            System.out.println("  - " + name + " is bound to: " + obj);

        } catch (NamingException e) {
            System.err.println("Problem looking up " + name + ":" + e);
        }


        System.out.println();
        
    }

}


