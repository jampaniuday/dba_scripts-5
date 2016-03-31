// -----------------------------------------------------------------------------
// ToStringObjectExample.java
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
 * Many Java developers have a need to print objects using a nice default
 * format. The solution is to override the toString() method inherited from
 * java.lang.Object.
 * 
 * If you have ever tried to pass an object to System.out.println() or
 * any equivalent method, or invoke it in string concatenation, Java will
 * automatically call its toString() method. The default implementation for 
 * toString isn't very pretty. It just prints the class name, an @ sign,
 * and the object's hashCode() value. For example:
 * 
 *      MyClass@991ca51ca
 * 
 * Java knows that every object has a toString() method, since java.lang.Object 
 * has one and all classes are ultimately subclasses of Object.
 * 
 * The following example code demonstrates overriding and not overriding the
 * toString() method.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ToStringObjectExample {


    public static void main(String[] args) {

        // Not overriding the equals() method
        ToStringClassWithout tsc1 = new ToStringClassWithout("John", "Doe");
        System.out.println("Class without toString() method : " + tsc1);

        // Overriding the equals() method
        ToStringClass tsc2 = new ToStringClass("Jane", "Doe");
        System.out.println("Class with toString() method    : " + tsc2);


    }

}


class ToStringClassWithout {

    String firstName;
    String lastName;

    public ToStringClassWithout(String fname, String lname) {
        this.firstName = fname;
        this.lastName = lname;
    }

}

class ToStringClass {

    String firstName;
    String lastName;

    public ToStringClass(String fname, String lname) {
        this.firstName = fname;
        this.lastName = lname;
    }

    // Override the toString() method
    public String toString() {
        return lastName + ", " + firstName;
    }

}
