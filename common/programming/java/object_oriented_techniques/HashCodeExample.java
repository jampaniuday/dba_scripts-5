// -----------------------------------------------------------------------------
// HashCodeExample.java
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
 
import java.util.*;


/**
 * -----------------------------------------------------------------------------
 * 
 * The class java.lang.Object provides a hashCode() method. Its default behavior
 * is to output the relative location on the heap where the object is located. 
 * However, some objects may override hashCode(). The hashCode() method returns
 * an integer that is a "hashcode" for the object. A hashcode is like a
 * signature or checksum for an object. It's a random-looking identifying
 * number that is usually generated from the contents (attributes) of the
 * object. The hashcode should always be different for instances of the class
 * that contain different data, but should normally be the same for instances
 * that compare "equal" with the equals() method. This is the default
 * behavior. Hashcodes are used in the process of storing objects in a
 * Hashtable, or a similar kind of collection. The hashcode helps the Hashtable
 * optimize its storage of objects by serving as an identifier for distributing
 * them into storage evenly, and locating them quickly.
 * 
 * The default implementation of hashCode() in Object assigns each object
 * instance a unique number. If you don't override this method when you create 
 * a subclass, each instance of your class will have a unique hashcode. This is
 * sufficient for most objects. However, if you classes have a notion of 
 * equivalent objects (if you have overriden equals()) and you want equal 
 * objects to serve as equivalent keys in a Hashtable, then you should 
 * override hashCode() so that your equivalent objects generate the same
 * hashcode value.
 * 
 * -----------------------------------------------------------------------------
 * 
 * The hashCode() method gets inherited from the Object class, thus an instance 
 * of any object can make a call to hashCode(). The signature of hashCode is:
 * 
 *      public int hashCode();
 * 
 * The int value returned from hashCode() is of particular use with the hash 
 * based Collection classes:
 * 
 *      - HashTable
 *      - HashSet
 *      - HashSet
 *      
 * The nature of hash based collections is to store keys and values. The key is 
 * what you use to look up a value. Thus you could for instance use a HashMap 
 * to store employee id in the key value and the employee name in the value 
 * part. This type of collection provides for extremely fast lookups.
 * 
 * The default implementation of Sun's SDK hashCode() method, is a return value 
 * that will be the memory address of the object. 
 * 
 * Java allows any object to be used as a key in a hash table.
 * 
 * When storing objects in a hash, Java uses the hashCode() method which is a
 * method that returns a hash code value for the object. The hashCode() method 
 * is supposed to return an int that should uniquely identify different objects.
 * This method is supported for the benefit of hashtables such as those provided
 * by java.util.Hashtable.
 * 
 * If you override java.lang.Object.equals() method, you MUST override 
 * java.lang.Object.hashCode() to make sure that the objects which are equal
 * return the same hash code!!! It is very hard to override equals() and 
 * hashCode() methods in a mutable class (a class that allows instance variables
 * to be changed) and be sure that they will work correctly all the time.
 * 
 * Java's immutable classes such as String, Integer, Long, Double, Float, 
 * Boolean all return custom hash codes. Some of Java's mutable classes also 
 * return custom hash codes based on their data field values. (Look at the JDK 
 * documentation for java.util.Date.hashCode() for an example.)
 * 
 * Even if the default hashCode() method has been overridden in a class, you 
 * can get the integer value to be returned by the default hashCode() by using:
 * 
 *      System.identityHashCode(yourObject);
 * 
 * (An example of using the System.identityHashCode() method will be included
 * in this example class.)
 * 
 * If you are unsure how to implement hashCode(), just always return 0 in your 
 * implementations. So all of your custom objects will return the same hash 
 * code. Now, this is not a real solution as it turns hashtable of your objects
 * into one (possibly) long linked-list, but you have implemented hashCode() 
 * correctly!
 * 
 *      public int hashCode(){
 *          return 0;
 *      }
 * 
 * IMPORTANT:
 * ==========
 * Keep in mind that two equal objects must return the same integer. This is not
 * a problem if the same class constructs the two equal objects. Both objects 
 * will have the same hashCode() method and hence, return the same integer. 
 * You may have a problem if you are trying to be smarter and force two objects
 * from two different classes as being equal. Then, you must ensure that the 
 * hashCode() method of both classes returns the same integer.
 * 
 * In a more complex world, hash codes that you return are supposed to be 
 * well-distributed over the range of possible integers. This reduces collisions
 * and makes hash tables fast (by reducing chains / linked-lists). Remember that
 * hash codes do not have to be unique. (It is not possible to guarantee that
 * any way.) 
 * 
 * If you find the default hashCode() implementation based on the object 
 * identity too restrictive and returning a constant integer all the time too 
 * anti-performance, you can base a hashCode() based on the data field values of
 * the object. Beware though, that for mutable classes, a hashtable can lose 
 * track of keys if the data fields of the object used as a key are changed.
 * 
 * So, If you insist on implementing your own hashCode() based on the data field
 * values, can you make your class immutable? Just make all data fields private
 * which can only be initialized once through the class constructor. Then, don't
 * provide any setter methods or methods which change their values. Same thing 
 * in implementation of objects used as data fields of this class. If no one can
 * change the data fields, the hash code will always remain the same. 
 * 
 * If your class is immutable (the instance data cannot be modified once 
 * initialized), you can base the hash code on the data field values. You should
 * even calculate the hashCode() just once for an instance (after all, no data 
 * is going to change after the object has been instantiated - the class is 
 * immutable) and store it in a private instance variable. Next time onwards, 
 * the hashCode() method can just return the private variable's value, making 
 * the implementation very fast.
 * 
 * Immutable classes may not be a practical solution though, for many cases. 
 * Most custom classes have non-private data or setter methods and MUST alter
 * instance variables.
 * 
 * Any way, immutable or not, here are some of the ways to get a custom hash 
 * code based on the data field values (apart from returning 0 or a constant 
 * integer discussed earlier which is not based on data fields).
 * 
 * 
 * A properly written hashCode() method will follow these the following rules:
 * 
 *      1.) It is repeatable:  hashCode(x) must return the same int when called
 *                             again unless set methods have been called.
 *      2.) It is symmetric :  if x.equals(y), then x.hashCode()
 *                             must == y.hashCode(), i.e., either both return
 *                             true, or both return false.
 *      3.) If !x.equals(y) :  it is not required that
 *                             x.hashCode() != y.hashCode, but doing so may
 *                             improve performance of hash tables, i.e., hashes
 *                             may call hashCode() before equals().
 *                             
 * The default hashCode() implementation on Sun's SDK returns a machine
 * address, which comforms to Rule 1 (above).Conformance to Rules 2 and 3 
 * depends, in part, upon your equals() method.
 * 
 * A requirement of implementing hashCode() is that the hash value of an object
 * must be synchronized with equality, that is
 * 
 *      if (x.equals(y)) is true, then x.hashCode() == y.hashCode()
 * 
 * The reason for this involves collisions. If two objects have the same 
 * position in the hash table (they collide), you must allow the hashing 
 * algorithm to search for the object you want. The hashing algorithm will use 
 * the equals() method for the object.
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class HashCodeExample {


    public static void hashMapExample() {

        System.out.println("\nHashMap Example Method");
        System.out.println("======================\n");
        
        //construct a HashMap with default size and load factor
        HashMap table = new HashMap();

        String name;
        String key;

        // We want to map people with their social security numbers the 
        // key/value pair is ssn[i]/people[i]. Note that the value "Tom" appears
        // twice, but has different keys.
        String[] ssn = new String[5];
        ssn[0] = "00000";
        ssn[1] = "11111";
        ssn[2] = "22222";
        ssn[3] = "33333";
        ssn[4] = "44444";

        String[] people = new String[5];
        people[0] = "Tom";
        people[1] = "Jay";
        people[2] = "Pat";
        people[3] = "Meghan";
        people[4] = "Tom";


        // put() maps key/value pairs
        for (int i = 0; i < people.length; i++) {
            table.put(ssn[i],people[i]);
        }


        // Now return the name for social security number 11111 ...
        key = "11111";
        if (table.containsKey(key)) { 
            name = (String) table.get(key);
            System.out.println(key + " is mapped to " + name);
        } else {
            System.out.println(key + " not mapped.");
        }
    
        // What if the key didn't map in the table???
        key = "55555";
        if (table.containsKey(key)) { 
            name = (String) table.get(key);
            System.out.println(key + " is mapped to " + name);
        } else {
            System.out.println(key + " not mapped.");
        }

    }


    public static void testHashCodeOverride () {

        System.out.println("\nTesting Override hashCode() Method");
        System.out.println("==================================\n");

    
        HashPerson william = new HashPerson("Willy");
        HashPerson bill    = new HashPerson("Willy");


        // What hashCode() returns
        System.out.println("Hash code for william  = " + william.hashCode());
        System.out.println("Hash code for bill     = " + bill.hashCode());


        // map key william to "Silly"
        HashMap table = new HashMap();
        table.put(william,"Silly");

        if (table.containsKey(william )) {
            System.out.println(table.get(william));
        } else {
            System.out.println("Key " + william + " not found");
        }


        // Now look for the same key
        if (table.containsKey(bill)) {
            System.out.println(table.get(bill)); 
        } else {
            System.out.println("Key " + bill + " not found");
        }
    }


    public static void testHashCodeEqualsOverride() {

        System.out.println("\nRunning hashCode() and equals() Override");
        System.out.println("========================================\n");
        
        Team a = new Team("Eagles",6,2);
        Team b = new Team("Eagles",6,2);

        System.out.println(a.hashCode());
        System.out.println(b.hashCode());

        if (a.equals(b)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
        
    }




    public static void main(String[] args) {

        System.out.println("\nRunning several hashCode() implementations");
        System.out.println("==========================================\n");

        // Default hashCode() implementation
        Object someObject = new Object();
        System.out.println("Default hashCode() method        [Object]                 : " + someObject.hashCode());

        // Default hashCode() implementation
        HashCodeExample hce1 = new HashCodeExample();
        System.out.println("Default hashCode() method        [HashCodeExample]        : " + hce1.hashCode());

        // Default hashCode() implementation
        java.util.NoSuchElementException jay = new java.util.NoSuchElementException();
        System.out.println("Default hashCode() method        [NoSuchElementException] : " + jay.hashCode());

        // some objects choose to override hashCode()
        Integer one = new Integer(1);
        System.out.println("Classes that Override hashCode() [Integer]                : " + one.hashCode());

        // some overrides may even be negative
        java.awt.Color red = new java.awt.Color(0x44, 0x88, 0xcc);
        System.out.println("Negative Hash Codes              [Color]                  : " + red.hashCode());

        // Override the hashCode() method
        HashCodeExample2 hce2 = new HashCodeExample2();
        System.out.println("Override hashCode() method       [HashCodeExample2]       : " + hce2.hashCode());

        // Even if the default hashCode() method has been overridden in a class,
        // you can get the integer value to be returned by the default 
        // hashCode() by using:   System.identityHashCode(hce2)
        System.out.println("Call default hashCode() method   [HashCodeExample2]       : " + System.identityHashCode(hce2));

        // A HashMap Example
        hashMapExample();

        // Testing Overriding the hashCode() method
        testHashCodeOverride();

        // Testing Overriding the hashCode() and equals() method
        testHashCodeEqualsOverride();

        System.out.println();
        
    }

}


/**
 * This class overrides the hashCode() method.
 */
class HashCodeExample2 {

    public int hashCode() {
        return 100;
    }

}


/**
 * This class overrides the hashCode() method. It also overrides equals().
 */    
class HashPerson {

    private static final int HASH_PRIME = 1000003;

    public HashPerson(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    /**
     * This overrides equals() from java.lang.Object
     */
    public boolean equals(Object rhs) {
        // first determine if they are the same object reference
        if (this == rhs)
            return true;

        // make sure they are the same class
        if (rhs == null || rhs.getClass() != getClass())
            return false;

        // ok, they are the same class. Cast rhs to HashPerson
        HashPerson other = (HashPerson)rhs;

        // our test for equality simply checks the name field
        if (!name.equals(other.name)) {
            return false;
        }

        // if we get this far, they are equal
        return true;
    }


    /**
     * Simple hashCode() implementation
     */
    public int hashCode() {

        int result = 0;
        result = HASH_PRIME * result + name.hashCode();
        return result;

    }

    private String name;

}


class Team {

    private static final int HASH_PRIME = 1000003;
    private              String name;
    private              int wins;
    private              int losses;


    public Team(String name) {
        this.name = name;
    }

    public Team(String name, int wins, int losses) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
    }


    /**
     * this overrides equals() in java.lang.Object
     */
    public boolean equals(Object obj) {
        /**
         * return true if they are the same object
         */
        if (this == obj)
            return true;

        /**
         * the following two tests only need to be performed
         * if this class is directly derived from java.lang.Object
         */
        if (obj == null || obj.getClass() != getClass())
            return false;

        // we know obj is of type Team
        Team other = (Team)obj;

        // now test all pertinent fields ...
        if (wins != other.wins || losses!= other.losses) {
            return false;
        }
        
        if (!name.equals(other.name)) {
            return false;
        }

        // otherwise they are equal
        return true;
    }


    /**
     * This overrides hashCode() in java.lang.Object
     */
    public int hashCode() {
        int result = 0;

        result = HASH_PRIME * result + wins;
        result = HASH_PRIME * result + losses;
        result = HASH_PRIME * result + name.hashCode();

        return result;
    }

}
