// -----------------------------------------------------------------------------
// CloningExample.java
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
 
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * -----------------------------------------------------------------------------
 * The Object class includes the clone() method for objects to make copies of 
 * themselves. A copied object will be a new object instance, separate from the 
 * original. The copied object may or may not contain exactly the same state 
 * (the same instance variable values) as the original. The state is controlled 
 * by the object being copied. One last important fact is that the decision as 
 * to whether the object allows itself to be cloned at all is up to the object.
 * 
 * The mechanisms provided by the Java Object class is used to make a simply 
 * copy of an object including all of its state; basically a bitwise copy. But
 * by default, this capability is turned off. In order for an object to be
 * considered cloneable, an object must implement the java.lang.Cloneable
 * interface. This is another one of those "flag interfaces" that indicates to
 * Java that the object wants to cooperate in being cloned. The Cloneable 
 * interface does not actually contain any methods.
 * 
 * If the object isn't Cloneable, the clone() method throws a
 * CloneNotSupportedException exception.
 * 
 * The clone() method is declared as "protected" - meaning that by default
 * it can be called only by an object on itself, an object in the same
 * package, or another object of the same type or a subtype. If we need to make
 * an Object Cloneable by everyone, we have to override its clone() method
 * and make it public.
 * 
 * The example below has one instance variable, a LinkedList called names.
 * Our example class implements Cloneable interface, indicating that it is okay
 * to copy CloningExample and it has overriden the clone() method to make it
 * public. The clone() example simple returns the object created by the 
 * super-class's clone() method - a copy of our CloneExample. Unfortunately,
 * the compiler is not smart enough to understand that since we implemented
 * the Cloneable interface and that it will never throw the
 * CloneNotSupportedException, so we have to guard against it anyway.
 * 
 * SHALLOW COPY
 * -----------------------------------------------------------------------------
 * 
 *      At the point we cloned the ce1 object (to ce2):
 *          
 *          CloningExample ce2 = (CloningExample)ce1.clone();
 *      
 *      we will have two distinct objects: ce1 and ce2. As you can see in
 *      the example, we can see that using our equals() method, it tells us
 *      that the "names" are equivalent, but == tells us that they aren't
 *      equal - that is, that are two distinct objects. This is what is 
 *      refered to as a "Shallow Copy". Java simply copied the bits of our
 *      variables. That means that the "names" instance variable is each of
 *      our objects (ce1 and ce2) still holds the same information - that is,
 *      both objects have a reference to teh same LinkedList.
 *
 *
 *      +----------------+                 +----------------+
 *      | CloningExample |                 | CloningExample |
 *      | -------------- |     clone()     | -------------- |
 *      |    (names)     |  ------------>  |    (names)     |
 *      |       |        |                 |       |        |
 *      +-------+--------+                 +-------+--------+
 *              |                                  |
 *               __________________________________
 *                               |
 *                       +----------------+
 *                       |   LinkedList   |
 *                       +----------------+
 * 
 * 
 * DEEP COPY
 * -----------------------------------------------------------------------------
 * 
 *      Given the above Shallow Copy, this may or may not be what you intended.
 *      We can instead have not only distinct objects (i.e. ce1 and ce2) but
 *      also having separate copies of all of its instance variables
 *      (or something in between if required). We can take total control
 *      of this process in anyway we please.
 *      
 *      The example below will perform a deep copy of the ce1 to ce3. You will
 *      see that the first part of the clone in the deep copy is the same as
 *      it was for the shallow copy. Our deepClone() method, although, now
 *      clones the LinkedList as well. Now, when our CloningExample object is
 *      cloned, the situation looks more like this:
 *      
 *      +----------------+                 +----------------+
 *      | CloningExample |                 | CloningExample |
 *      | -------------- |     clone()     | -------------- |
 *      |    (names)     |  ------------>  |    (names)     |
 *      |       |        |                 |       |        |
 *      +-------+--------+                 +-------+--------+
 *              |                                  |
 *              |                                  |
 *              |                                  |
 *      +----------------+     clone()     +----------------+
 *      |   LinkedList   |  ------------>  |   LinkedList   |
 *      +----------------+                 +----------------+
 *      
 *      Each CloningExample object (ce1 and ce3 in our example below) now has
 *      its own LinkedList.
 * 
 * This should provide an explanation as to why objects are not cloneable by 
 * default. It would make no sense to assume that all objects can be sensibly 
 * deplicated with a shallow copy. Likewise, it makes no sense to assume that a
 * deep copy would be necessary, or even correct. In this case we probably don't
 * need a deep copy; the LinkedList contains the same elements no matter which
 * CloningExample object you are looking at; so there's no need to copy the
 * LinkedList. But this decision depends on the object itself and its 
 * requirements.
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class CloningExample implements Cloneable {

    private LinkedList names = new LinkedList();


    public CloningExample() {
        names.add("Alex");
        names.add("Melody");
        names.add("Jeff");
    }


    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator i = names.iterator();
        while (i.hasNext()) {
            sb.append("\n\t" + i.next());
        }
        return sb.toString();
    }


    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error("This should not occur since we implement Cloneable");
        }
    }


    public Object deepClone() {
        try {
            CloningExample copy = (CloningExample)super.clone();
            copy.names = (LinkedList)names.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new Error("This should not occur since we implement Cloneable");
        }
    }

    public boolean equals(Object obj) {

        /* is obj reference this object being compared */
        if (obj == this) {
            return true;
        }

        /* is obj reference null */
        if (obj == null) {
            return false;
        }

        /* Make sure references are of same type */
        if (!(this.getClass() == obj.getClass())) {
            return false;
        } else {
            CloningExample tmp = (CloningExample)obj;
            if (this.names == tmp.names) {
                return true;
            } else {
                return false;
            }
        }
        
    }
    

    public static void main(String[] args) {

        CloningExample ce1 = new CloningExample();
        System.out.println("\nCloningExample[1]\n" + 
                           "-----------------" + ce1);

        CloningExample ce2 = (CloningExample)ce1.clone();
        System.out.println("\nCloningExample[2]\n" +
                           "-----------------" + ce2);

        System.out.println("\nCompare Shallow Copy\n" +
                           "--------------------\n" +
                           "    ce1 == ce2      : " + (ce1 == ce2) + "\n" +
                           "    ce1.equals(ce2) : " + ce1.equals(ce2));

        CloningExample ce3 = (CloningExample)ce1.deepClone();
        System.out.println("\nCompare Deep Copy\n" +
                           "--------------------\n" +
                           "    ce1 == ce3      : " + (ce1 == ce3) + "\n" +
                           "    ce1.equals(ce3) : " + ce1.equals(ce3));

        System.out.println();

    }

}


