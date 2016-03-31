// -----------------------------------------------------------------------------
// ReflectClass.java
// -----------------------------------------------------------------------------

import java.lang.reflect.*;
import java.util.*;

/**
 * -----------------------------------------------------------------------------
 * ReflectClass.java -  Dump a class using Reflection.
 *
 * Copyright (c) 1997 Chuck McManis, All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies.
 *
 * CHUCK MCMANIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. CHUCK MCMANIS
 * SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT
 * OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 * -----------------------------------------------------------------------------
 */


public class ReflectClass {

    private String firstName = null;
    private String lastName  = null;
    private int    idNumber  = 0;

    public ReflectClass() {
        super();
    }

    public static void main(String args[]) {
        Constructor   cn[];
        Class         cc[];
        Method        mm[];
        Field         ff[];
        Class         c = null;
        Class         supClass;
        String      x, y, s1, s2, s3;
        Hashtable classRef = new Hashtable();

        if (args.length == 0) {
            System.out.println("Please specify a class name on the command line.");
            System.exit(1);
        }

        try {
            c = Class.forName(args[0]);
        } catch (ClassNotFoundException ee) {
            System.out.println("Couldn't find class '"+args[0]+"'");
            System.exit(1);
        }

        /*
         * Step 0: If our name contains dots we're in a package so put
         * that out first.
         */
        x = c.getName();
        if (x.lastIndexOf(".") != -1) {
            y = x.substring(0, x.lastIndexOf("."));
            System.out.println("package "+y+";\n\r");
        }

        /*
         * Let's use the Reflection API to sift through what is
         * inside this class.
         *
         * Step 1: Collect referenced classes
         * This step is used so that I can regenerate the import statements.
         * It isn't strictly required of course, Java works just fine with
         * fully qualified object class names, but it looks better when you
         * use 'String' rather than 'java.lang.String' as the return type.
         */

        ff = c.getDeclaredFields();
        for (int i = 0; i < ff.length; i++) {
            x = tName(ff[i].getType().getName(), classRef);
        }

        cn = c.getDeclaredConstructors();
        for (int i = 0; i < cn.length; i++) {
            Class cx[] = cn[i].getParameterTypes();
            if (cx.length > 0) {
                for (int j = 0; j < cx.length; j++) {
                    x = tName(cx[j].getName(), classRef);
                }
            }
        }

        mm = c.getDeclaredMethods();
        for (int i = 0; i < mm.length; i++) {
            x = tName(mm[i].getReturnType().getName(), classRef);
            Class cx[] = mm[i].getParameterTypes();
            if (cx.length > 0) {
                for (int j = 0; j < cx.length; j++) {
                    x = tName(cx[j].getName(), classRef);
                }
            }
        }

        // Don't import ourselves ...
        classRef.remove(c.getName());

        /*
         * Step 2: Start class description generation, start by printing
         *  out the import statements.
         *
         * This is the line that goes 'public SomeClass extends Foo {'
         */
        for (Enumeration e = classRef.keys(); e.hasMoreElements(); ) {
            System.out.println("import "+e.nextElement()+";");
        }
        System.out.println();

        /*
         * Step 3: Print the class or interface introducer. We use
         * a convienience method in Modifer to print the whole string.
         */
        int mod = c.getModifiers();
        System.out.print(Modifier.toString(mod));

        if (Modifier.isInterface(mod)) {
            System.out.print(" interface ");
        } else {
            System.out.print(" class ");
        }
        System.out.print(tName(c.getName(), null));

        supClass = c.getSuperclass();
        if (supClass != null) {
            System.out.print(" extends "+tName(supClass.getName(), classRef));
        }
        System.out.println(" {");

        /*
         * Step 4: Print out the fields (internal class members) that are declared
         * by this class.
         *
         * Fields are of the form [Modifiers] [Type] [Name] ;
         */

        System.out.println("\n\r/*\n\r * Field Definitions.\r\n */");
        for (int i = 0; i < ff.length; i++) {
            Class ctmp = ff[i].getType();
            int md     = ff[i].getModifiers();

            System.out.println("    "+Modifier.toString(md)+" "+
                    tName(ff[i].getType().getName(), null) +" "+
                    ff[i].getName()+";");
        }


        /*
         * Step 5: Print out the constructor declarations.
         *
         * We note the name of the class which is the 'name' for all
         * constructors. Also there is no type, so the definition is
         * simplye [Modifiers] ClassName ( [ Parameters ] ) { }
         *
         */
        System.out.println("\n\r/*\n\r * Declared Constructors. \n\r */");
        x = tName(c.getName(), null);
        for (int i = 0; i < cn.length; i++) {
            int md = cn[i].getModifiers();
            System.out.print("    " + Modifier.toString(md) + " " + x);

            Class cx[] = cn[i].getParameterTypes();
            System.out.print("( ");
            if (cx.length > 0) {
                for (int j = 0; j < cx.length; j++) {
                    System.out.print(tName(cx[j].getName(), null));
                    if (j < (cx.length - 1)) System.out.print(", ");
                }
            }
            System.out.print(") ");
            System.out.println("{ ... }");
        }

        /*
         * Step 6: Print out the method declarations.
         *
         * Now methods have a name, a return type, and an optional
         * set of parameters so they are :
         *  [modifiers] [type] [name] ( [optional parameters] ) { }
         */
        System.out.println("\n\r/*\n\r * Declared Methods.\n\r */");
        for (int i = 0; i < mm.length; i++) {
            int md = mm[i].getModifiers();
            System.out.print("    "+Modifier.toString(md)+" "+
                    tName(mm[i].getReturnType().getName(), null)+" "+
                    mm[i].getName());

            Class cx[] = mm[i].getParameterTypes();
            System.out.print("( ");
            if (cx.length > 0) {
                for (int j = 0; j < cx.length; j++) {
                    System.out.print(tName(cx[j].getName(), classRef));
                    if (j < (cx.length - 1)) System.out.print(", ");
                }
            }
            System.out.print(") ");
            System.out.println("{ ... }");
        }

        /*
         * Step 7: Print out the closing brace and we're done!
         */
        System.out.println("}");
    }
    static String tName(String nm, Hashtable ht) {
        String yy;
        String arr;

        if (nm.charAt(0) != '[') {
            int i = nm.lastIndexOf(".");
            if (i == -1)
                return nm; // It's a primitive type, ignore it.
            else {
                yy = nm.substring(i+1);
                if (ht != null)
                    ht.put(nm, yy); // note class types in the hashtable.
                return yy;
            }
        }
        arr = "[]";
        if (nm.charAt(1) == '[')
            yy = tName(nm.substring(1), ht);
        else {
            switch (nm.charAt(1)) {
                case 'L' :
                    yy = tName(nm.substring(nm.indexOf("L")+1, nm.indexOf(";")), ht);
                    break;
                case 'I':
                    yy = "int";
                    break;
                case 'V':
                    yy = "void";
                    break;
                case 'C':
                    yy = "char";
                    break;
                case 'D':
                    yy = "double";
                    break;
                case 'F':
                    yy = "float";
                    break;
                case 'J':
                    yy = "long";
                    break;
                case 'S':
                    yy = "short";
                    break;
                case 'Z':
                    yy = "boolean";
                    break;
                case 'B':
                    yy = "byte";
                    break;
                default:
                    yy = "BOGUS:"+nm;
                    break;

            }
        }
        return yy+arr;
    }


}
