// -----------------------------------------------------------------------------
// ReflectionDemo.java
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
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of using the Java Reflection API framework.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ReflectionDemo {


    public static void main(String[] args) {

        /*
         * Here we use the .class notation to get a reference to the Class of
         * Calendar. The reflection methods don't belong to a particular 
         * instance of Calendar itself; they belong to the 
         * java.lang.Class object that describes the Calendar class.
         */
        System.out.println();
        System.out.println("====================");
        System.out.println("Calendar Methods (1)");
        System.out.println("====================");
        Method[] methods = Calendar.class.getMethods();
        for (int i=0; i < methods.length; i++) {
            System.out.println("  " + methods[i]);
        }


        /*
         * If we wanted to start from an instance of Calendar (or any 
         * unknown object), we could have used the getClass() method of the
         * object instead.
         */
        System.out.println();
        System.out.println("====================");
        System.out.println("MyClass Methods (2)");
        System.out.println("====================");
        MyClass myObject = new MyClass();
        Method[] methods2 = myObject.getClass().getMethods();
        for (int i=0; i < methods2.length; i++) {
            System.out.println("  " +  methods2[i]);
        }


        /*
         * The class java.lang.reflect.Method represents a static or instance
         * method. A Method object's invoke() method can be used to call the
         * underlying object's method with special arguments.
         * 
         * Here is an example that invokes the methods of a given dynamic 
         * object.
         */
        System.out.println();
        System.out.println("====================");
        System.out.println("Calendar Methods (3)");
        System.out.println("====================");
        try {

            String className = "java.lang.System";
            String methodName = "currentTimeMillis";
            
            Class  c = Class.forName(className);
            Method m = c.getMethod(methodName, new Class[] {} );
            Object ret = m.invoke(null, null);
            System.out.println("  Invoke static method : " + methodName);
            System.out.println("  of class             : " + className);
            System.out.println("  Results              : " + ret);

        } catch (ClassNotFoundException e) {
            System.err.println("Class.forName() can't find the class");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.err.println("Method does not exist");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("No permission to invoke method");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.err.println("Method threw an: " + e.getTargetException());
            e.printStackTrace();
        }


        /*
         * The class java.lang.reflect.Field is used to represent static
         * variables and instance variables. This class has a complete set
         * of accessor methods for all of the base types (i.e. getInt()
         * and setInt(). Also included are get() and set() methods for
         * accessing members that are object references.
         * 
         * In the following example, we are assuming that we know the structure
         * of the MyClass object. The real power of using the reflection API
         * is in examining objects that we've never seen before.
         * 
         * Keep in mind that in the following example that if either the
         * idNumber or fistName field is declared private, we would still be
         * able to look up the Field object that describes it, but we would not
         * be able to read or write its value.
         * 
         * In the example below, we first look up the specified Class by name.
         * To do so, we call the forName() method with the name of the desired
         * class. We then ask for the specified method by its name:
         * getMethod(). This method has two arguments: the first the method
         * name and the second is an array of Class objects that specifies
         * the method's signature. Since our simple example calls only methods
         * with no arguments, we create an anonymous empty array of Class
         * objects.
         */
        System.out.println();
        System.out.println("====================");
        System.out.println("MyClass Fields (4)");
        System.out.println("====================");
        MyClass myObject2 = new MyClass();
        
        try {

            Field idNumberField = MyClass.class.getField("idNumber");

            // Read field
            int myIdNumber = idNumberField.getInt(myObject2);
            System.out.println("  Read idNumber          : " + myIdNumber);

            // Change field
            idNumberField.setInt(myObject2, 42);
            System.out.println("  Display new idNumber   : " + myObject2.idNumber + "\n");
            
        } catch (NoSuchFieldException e) {
            System.err.println("Could not find \"idNumber\" field.");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("No permission to access \"idNumber\" field.");
            e.printStackTrace();
        }

        try {

            Field firstNameField = MyClass.class.getField("firstName");

            // Read field
            String myFirstName = (String) firstNameField.get(myObject2);
            System.out.println("  Read firstName         : " + myFirstName);

            // Change field
            firstNameField.set(myObject2, "ALEX");
            System.out.println("  Display new firstName  : " + myObject2.firstName + "\n");
            
        } catch (NoSuchFieldException e) {
            System.err.println("Could not find \"firstName\" field.");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("No permission to access \"firstName\" field.");
            e.printStackTrace();
        }


        /*
         * The class java.lang.reflect.Constructor represents an object
         * constructor that accepts arguments. You can use it to create a 
         * new instance of an object.
         * 
         * NOTE: You can also create instances of a class with 
         *       Class.newInstance(), but you are not able to specify 
         *       arguments with this method.
         *       
         * The following example will create an instance of 
         * java.util.Date, passing a string argument to the constructor.
         */
        System.out.println();
        System.out.println("=======================");
        System.out.println("Constructor Example (5)");
        System.out.println("=======================");
        try {
            
            Constructor c =
                Date.class.getConstructor(new Class[] { String.class } );
            
            Object o = c.newInstance(new Object[] { "Jun 18, 2000" } );
            Date d = (Date)o;
            
            System.out.println("  Date : " + d);

        } catch (NoSuchMethodException e) {
            System.err.println("getConstructor() couldn't find the constructor.");
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.err.println("The class is abstract");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("No permission to invoke method");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.err.println("Method threw an: " + e.getTargetException());
            e.printStackTrace();
        }

        System.out.println();
    }

}


class MyClass {

    public String firstName = null;
    public String lastName  = null;
    public int    idNumber  = 0;

    public MyClass(String fname, String lname, int id) {
        this.firstName = fname;
        this.lastName  = lname;
        this.idNumber  = id;
    }

    public MyClass() {
        this("Alex", "Hunter", 12345);
    }

    public String toString() {
        return (lastName + ", " + firstName + " [" + idNumber + "]");
    }
}
