// -----------------------------------------------------------------------------
// ParameterPassingExample2.java
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
 * 
 * This class provides an example of how parameter passing works in Java. When
 * Java passes arguments into methods, a copy of the argument is acutally
 * passed. There are three examples in this class that demonstrate just how 
 * parameter passing works.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ParameterPassingExample2 {

    private String label = null;

    public ParameterPassingExample2(String s) {
        this.label = s;
    }

    private String getLabel() {
        return this.label;
    }


    private void setLabel(String s) {
        this.label = s;
    }

    public String toString() {
        return this.label;
    }

    /**
     * A method that provides an example of how the reference to an object is
     * passed by value. The object reference being passed in is a copy of the
     * original object reference. We can override that object reference variable
     * with another object reference. The original object reference is still 
     * stored in a variable in the main method.
     */
    private static void methodX(ParameterPassingExample2 y) {

        System.out.println();
        System.out.println("        In methodX");
        System.out.println("        ==========");
        System.out.println();
        System.out.println("        - The value of y's label is: \"" + y + "\"");

        System.out.println();
        System.out.println("        Make y reference a different object");
        y = new ParameterPassingExample2("AAA");
        System.out.println("        - The value of y's label is: \"" + y.getLabel() + "\"");

    }


    /**
     * A method that provides an example of how the reference to an object is
     * passed by value. In this example, we pass in an object reference and use
     * it to change a value (label) in this object. Even though the object
     * reference being passed in is a reference, it points to the same object
     * that the original points to. Both object references can be used to make
     * changes to the object.
     */
    public static void methodY(ParameterPassingExample2 k) {

        System.out.println();
        System.out.println("        In methodY");
        System.out.println("        ==========");
        System.out.println();
        System.out.println("        - The value of k is " + k);

        System.out.println();
        System.out.println("        Use copied object reference to make changes to the object.");
        k.setLabel("Alex M. Hunter");
        System.out.println("        - The value of k is " + k);

    }


    /**
     * A method that takes in an integer value and alters it. This demonstrates
     * how Java passes parameters by copying the value. The copy of the value
     * will be changed, not the original value passed in.
     */
    private static void example1Method1(int intValueIn) {
    
        intValueIn += 15;

        System.out.println();
        System.out.println("        In example1Method1");
        System.out.println("        ==================");
        System.out.println();
        System.out.println("        - The value of intValueIn is " + intValueIn);

    }

    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        System.out.println("+-----------------------------------------------+");
        System.out.println("| EXAMPLE 1                                     |");
        System.out.println("+-----------------------------------------------+");

        System.out.println();
        System.out.println("In main");
        System.out.println("=======");
        System.out.println();

        // Pass integer argument. A copy of the integer will be passed. When
        // the value is passed in to method "exampleMethod1(), the value will
        // be changed, but the COPY of the value will be changed; not the
        // original value passed in.
        
        int intValue1 = 10;
        System.out.println("    - The value of intValue1's value is " + intValue1);
        
        example1Method1(intValue1);

        System.out.println();
        System.out.println("Back in main");
        System.out.println("============");
        System.out.println();
        System.out.println("    - The value of intValue1's value is " + intValue1);
        System.out.println();


        System.out.println();
        System.out.println("+-----------------------------------------------+");
        System.out.println("| EXAMPLE 2                                     |");
        System.out.println("+-----------------------------------------------+");
        System.out.println();

        System.out.println();
        System.out.println("In main");
        System.out.println("=======");
        System.out.println();

        // Create a variable that holds an object reference. Again, when the 
        // object reference is copied and passed in. The variable within the
        // methods is storing a copy of the object reference, and if that 
        // variable is changed, the variable holding the original object
        // reference is still stored in this main method.
        
        ParameterPassingExample2 i = new ParameterPassingExample2("Alex");
        System.out.println("    - The value of i is: \"" + i + "\"");
        methodX(i);

        System.out.println();
        System.out.println("Back in main");
        System.out.println("============");
        System.out.println();
        System.out.println("    - The value of i is: \"" + i + "\"");
        System.out.println();


        System.out.println();
        System.out.println("+-----------------------------------------------+");
        System.out.println("| EXAMPLE 3                                     |");
        System.out.println("+-----------------------------------------------+");
        System.out.println();

        System.out.println();
        System.out.println("In main");
        System.out.println("=======");
        System.out.println();

        // In this case, pass in an object reference. The object reference
        // is passed into the method as a copy of the object reference. We
        // pass in the copied object reference and use it to make changes to
        // the object. Even though the object reference being passed in is a 
        // reference, it points to the same object that the original points to.
        // Both object references can be used to make changes to the object.
        ParameterPassingExample2 j = new ParameterPassingExample2("Alex");
        System.out.println("    - The value of j is " + j);
        methodY(j);

        System.out.println();
        System.out.println("Back in main");
        System.out.println("============");
        System.out.println();
        System.out.println("    - The value of j is " + j);
        System.out.println();

        System.exit(0);

    }

}


