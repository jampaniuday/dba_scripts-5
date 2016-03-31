// -----------------------------------------------------------------------------
// ClassExample.java
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
 * The Class class is one of the best measures as to the level of abstraction of
 * class structures in Java. We already know that every object in Java is an 
 * instance of a particular class, but what exactly is a class? In C++, objects 
 * are formulated by and instantiated from classes just like in Java, but in 
 * C++, classes are really just artifacts of the compiler. Therefore, in C++ you
 * will only see classes mentioned in source code, not at runtime. Java, on the 
 * other hand, uses a two-tiered system that uses Class objects to create 
 * objects instances.
 * 
 * Classes in Java source code are represented at runtime by instances of the 
 * java.lang.Class class. There is a Class object for every class you use; this
 * Class object is responsible for producing instances for its class. Developers
 * would typically not be concerned with any of this unless they are interested 
 * in loading new kinds of classes dynamically at runtime. You can also use 
 * Class objects as the basis for “reflecting” on a class to find out its 
 * methods and other properties.
 * 
 * The getClass() method, a method of Object, is used to return a reference to 
 * the Class object that produced the Object instance:
 * 
 *      String myString = "Alex";
 *      Class c = myString.getClass();
 * 
 * We can also get the Class reference for a particular class statically, using
 * the special .class notation:
 * 
 *      Class c = String.class;
 * 
 * The .class reference looks a lot like a static attribute that exists in every
 * class. However, it is really resolved by the compiler.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ClassExample {

    private String firstName = null;
    private String lastName  = null;

    public ClassExample() {
        this.firstName = "Alex";
        this.lastName  = "Hunter";
    }

    public String toString() {
        return lastName + ", " + firstName;
    }

    public static void main(String[] args) {

        String name = "Alex Hunter";

        /*
         * GET CLASS ASSOCIATE WITH OBJECT
         * -------------------------------
         * Get the class associated with a particular object with the getClass()
         * method.
         */
        Class c1 = name.getClass();
        System.out.println();
        System.out.println("[STRING  - name]: " + name);
        System.out.println("[CLASS   - c1  ]: " + c1 + "\n");


        /*
         * USING THE .CLASS REFERENCE
         * --------------------------
         * The .class reference looks like a static field. This is really
         * resolved by the compiler.
         */
        Class c2 = String.class;
        System.out.println("[CLASS   - c2  ]: " + c2 + "\n");


        /*
         * GET NAME OF OBJECT'S CLASS
         * --------------------------
         * Look for the name of the object's class
         */
        Class c3 = name.getClass();
        String s1 = c3.getName();
        System.out.println("[STRING  - s1  ]: " + s1);
        System.out.println("[CLASS   - c3  ]: " + c3 + "\n");


        /*
         * PRODUCE NEW INSTANCE OBJECT FROM CLASS OBJECT
         * ---------------------------------------------
         * We can also ask a Class to produce a new instance of its type of
         * object. Since newInstance() has a return type of Object, we have to
         * cast it to a reference of the appropriate type. (newInstance() has to
         * be able to return any kind of already constructed object.) Also note
         * that newInstance() can only create an instance of a class that has an
         * accessible default constructor. It doesn't allow us to pass any
         * arguments to a constructor. 
         */
        try {
            ClassExample  ce  = new ClassExample();
            Class         cce = ce.getClass();
            ClassExample  ce2 = (ClassExample)cce.newInstance();
            System.out.println();
            System.out.println("Original Instance    - ce  : " + ce);
            System.out.println("Created New Instance - ce2 : " + ce2);
            
            String s2 = (String)c3.newInstance();
            System.out.println("Create New Instance  - s2  : " + s2);
        } catch (InstantiationException e) {
            // Swallow the exception
        } catch (IllegalAccessException e) {
            // Swallow the exception
        }


        /*
         * USING Class.forName();
         * ----------------------
         * Using the Class class becomes more meaningful when we add the 
         * capability to look up a class by name. forName() is a static
         * method of Class that returns a Class object given its name as a
         * String. If the class cannot be found, it throws a 
         * ClassNotFoundException exception.
         * 
         * When you combine this with the example from above, you can see
         * that it is easy to load new kinds of classes dynamically.
         */
        try {
            Class newClass = Class.forName("ClassExample");
            ClassExample ce4 = (ClassExample)newClass.newInstance();
            System.out.println("Class.forName        - ce4 : " + ce4);
        } catch (ClassNotFoundException e) {
            // Swallow the exception
        } catch (InstantiationException e) {
            // Swallow the exception
        } catch (IllegalAccessException e) {
            // Swallow the exception
        }


    }

}


