// -----------------------------------------------------------------------------
// ParameterPassingExample.java
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
 * A question often asked by beginning Java programmers is:
 * 
 *      "Are parameters passed by Value or by Reference?”
 *
 * The purpose of this class is to demonstrate and explains how parameters are 
 * passed in Java by example.
 * 
 * Contrary to what many people think, “All Java parameters are passed by value,
 * even references to objects”. Many books on Java programming confuse the 
 * issue when they make statements like, “primitive types are passed by value, 
 * but objects are passed by reference”. Beginning Java programmers will read 
 * such a statement and end up with a completely wrong understanding of what 
 * actually happens.
 * 
 * To understand this, consider the following Java statement:
 * 
 *      Button b = new Button;
 *      
 * The variable b IS NOT an object; it is simply a reference to an object (hence
 * the term reference variable).
 * 
 *                         +-----------------+
 *      +-+                |                 |
 *      |b|  ------------> |  button object  |
 *      +-+                |                 |
 *                         +-----------------+
 *
 * So what happens when we call a method and "pass in an object" ? Well, let's 
 * be clear about one thing - we are not passing in an object, rather we are 
 * passing in a reference to an object, and “it is the reference to an object 
 * that gets passed by value”. Consider a method declared as:
 * 
 *      public void methodX(Button y);
 * 
 * If we call this method passing in a reference to a button object:
 * 
 *      Button b = new Button();
 *      methodX(b);
 *      
 * then the value of the variable b is passed by value, and the variable y 
 * within methodX receives a copy of this value. Variables b and y now have the 
 * same value. However, what does it mean to say that two reference variables 
 * have the same value ? It means that both variables refer to the same object. 
 * 
 *      +-+                +-----------------+
 *      |b|  ------------> |                 |
 *      +-+     ---------> |  button object  |
 *      +-+    |           |                 |
 *      |y|  --            +-----------------+
 *      +-+
 * 
 * As illustrated above, an object can have multiple references to it. In this 
 * example, we still have just the one object (button object), but it is being 
 * referenced by two different variables. So what is the consequence of this ? 
 * It means that within methodX you can update the button object via variable y,
 * 
 *      y.setLabel("new text");
 *
 * and the calling routine will see the changes (as variable b refers to the 
 * same object), but - and this is the important bit - if you change the value 
 * of the variable y within methodX so that it refers to a different object, 
 * then the value of variable b within the calling method remains unchanged, 
 * and variable b will still refer to the same button object that it always did.
 * 
 * For example, if in methodX we had y = new Button("another button"); we get 
 * the situation shown in below:
 * 
 *                         +-----------------+
 *      +-+                |                 |
 *      |b|  ------------> |  button object  |
 *      +-+                |                 |
 *                         +-----------------+
 *
 *                         +-------------------------+
 *      +-+                |                         |
 *      |y|  ------------> |  another button object  |
 *      +-+                |                         |
 *                         +-------------------------+
 *
 * With the two variables now referencing different objects, if methodX now 
 * updates the button which y now refers to:
 *
 *      y.setLabel("xxx");
 *
 * then the original button object to which b refers to is unaffected by any 
 * such changes.
 * 
 * The example code below demonstrates how Java parameter passing works.
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ParameterPassingExample {

    private String label = null;

    public ParameterPassingExample(String s) {
        this.label = s;
    }

    private String getLabel() {
        return this.label;
    }


    private void setLabel(String s) {
        this.label = s;
    }


    /**
     * A method that provides an example of how the reference to an object is
     * passed by value.
     */
    private void methodX(ParameterPassingExample y) {

        System.out.println();
        System.out.println("        In methodX");
        System.out.println("        ==========");
        System.out.println();
        System.out.println("        - The value of y's label is " + y.getLabel());

        System.out.println();
        System.out.println("        Update the object that both y and b refer to");
        y.setLabel("BBB");
        System.out.println("        - The value of y's label is " + y.getLabel());

        System.out.println();
        System.out.println("        Make y reference a different object - doesn't affect variable b");
        y = new ParameterPassingExample("CCC");
        System.out.println("        - The value of y's label is " + y.getLabel());

        System.out.println();
        System.out.println("        Updating object that y now references.");
        System.out.println("        Has no affect on button referenced by b");
        y.setLabel("DDD");
        System.out.println("        - The value of y's label is " + y.getLabel());

    }


    /**
     * A method that provides an example of how primitives are passed by
     * value as well.
     */
    public static void methodZ(int j) {

        System.out.println();
        System.out.println("        In methodZ");
        System.out.println("        ==========");
        System.out.println();
        System.out.println("        - The value of j is " + j);

        System.out.println();
        System.out.println("        Change value of j - doesn't affect variable i within main");
        j = 6;
        System.out.println("        - The value of j is " + j);

    }


    /**
     * A method that provides an example of how String objects are passed by
     * value as well.
     */
    public static void methodS(String k) {

        System.out.println();
        System.out.println("        In methodS");
        System.out.println("        ==========");
        System.out.println();
        System.out.println("        - The value of k is " + k);

        System.out.println();
        System.out.println("        Make k reference a different String object - doesn't affect variable s");
        k = "Alex M. Hunter";
        System.out.println("        - The value of k is " + k);

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

        //the reference to an object is passed by value
        ParameterPassingExample b = new ParameterPassingExample("AAA");
        System.out.println("    - The value of b's label is " + b.getLabel());

        b.methodX(b);

        System.out.println();
        System.out.println("Back in main");
        System.out.println("============");
        System.out.println();
        System.out.println("    - The value of b's label is " + b.getLabel());
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

        // Primitives are passed by value as well
        int i = 5;
        System.out.println("    - The value of i is " + i);
        methodZ(i);

        System.out.println();
        System.out.println("Back in main");
        System.out.println("============");
        System.out.println();
        System.out.println("    - The value of i is " + i);
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

        // In this case, pass in a String object
        String s = "Alex Hunter";
        System.out.println("    - The value of s is " + s);
        methodS(s);

        System.out.println();
        System.out.println("Back in main");
        System.out.println("============");
        System.out.println();
        System.out.println("    - The value of s is " + s);
        System.out.println();

        System.exit(0);

    }

}


