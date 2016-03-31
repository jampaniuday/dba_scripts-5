// -----------------------------------------------------------------------------
// TreeSetExample.java
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
 
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of storing and retrieving objects from a
 * Set. As implied by its name, this interface models the mathematical set 
 * abstraction.
 * 
 * A Set is a collection (an interface) that contains no duplicate elements. A 
 * more formal definition: A set contains no pair of elements (e1 and e2) such
 * that e1.equals(e2) and contains at most one null element.
 *  
 * The Set interface places additional stipulations, beyond those inherited from
 * the Collection interface, on the contracts of all constructors and on the 
 * contracts of the add, equals and hashCode methods. Declarations for other 
 * inherited methods are also included here for convenience. 
 * (The specifications accompanying these declarations have been tailored to the
 * Set interface, but they do not contain any additional stipulations.)
 *
 * The additional stipulation on constructors is, not surprisingly, that all 
 * constructors must create a set that contains no duplicate elements 
 * (as defined above).
 * 
 * NOTE: Great care must be exercised if mutable objects are used as set 
 * elements. The behavior of a set is not specified if the value of an object 
 * is changed in a manner that affects equals comparisons while the object is an
 * element in the set. A special case of this prohibition is that it is not 
 * permissible for a set to contain itself as an element. 
 * 
 * Some set implementations have restrictions on the elements that they may 
 * contain. For example, some implementations prohibit null elements, and some 
 * have restrictions on the types of their elements. Attempting to add an 
 * ineligible element throws an unchecked exception, typically 
 * NullPointerException or ClassCastException. Attempting to query the presence 
 * of an ineligible element may throw an exception, or it may simply return 
 * false; some implementations will exhibit the former behavior and some will 
 * exhibit the latter. More generally, attempting an operation on an ineligible 
 * element whose completion would not result in the insertion of an ineligible 
 * element into the set may throw an exception or it may succeed, at the option 
 * of the implementation. Such exceptions are marked as "optional" in the 
 * specification for this interface.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class TreeSetExample {


    /**
     * Provides an example of how to work with the TreeSet container.
     */
    public static void doTreeSetExample() {

        final int MAX = 10;

        System.out.println("+--------------------------------------------------+");
        System.out.println("| Create/Store objects in a TreeSet container.     |");
        System.out.println("+--------------------------------------------------+");
        System.out.println();

        Set ss = new TreeSet();

        for (int i = 0; i < MAX; i++) {
            System.out.println("  - Storing Integer(" + i + ")");
            ss.add(new Integer(i));
        }

        /*
         * I am adding the following comment to indicate that since we have a
         * Set collection of Integers, we cannot now attempt to add either 
         * "Object" or "String" elements. Attempting to add any of the following
         * elements will result in a "java.lang.ClassCastException" runtime 
         * exception:
         * 
         *   ss.add((Object)"Melody");
         *   ss.add("Jeff");
         */

        System.out.println();
        System.out.println("+----------------------------------------------------------------+");
        System.out.println("| Retrieve objects in a TreeSet container using an Iterator.     |");
        System.out.println("+----------------------------------------------------------------+");
        System.out.println();

        Iterator i = ss.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doTreeSetExample();
    }

}
