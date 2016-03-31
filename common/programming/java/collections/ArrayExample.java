// -----------------------------------------------------------------------------
// ArrayExample.java
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
 
import java.util.Arrays;
import java.util.Random;
import java.lang.reflect.Array;

/**
 * -----------------------------------------------------------------------------
 * Used to provide many of examples of how to work with Arrays in Java.
 * Some of the examples include Array declaration, resizing Arrays, returning
 * Arrays from methods and finally examples of using the Arrays class.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ArrayExample {


    /**
     * Provides an example of declaring an Array using different methodologies.
     */
    public static void declareExample() {


        System.out.println();
        System.out.println("+===============================================+");
        System.out.println("|  ***                                     ***  |");
        System.out.println("|  ***        ARRAY DECLARATION            ***  |");
        System.out.println("|  ***                                     ***  |");
        System.out.println("+===============================================+");
        System.out.println();


        // Example 1
        // ---------
        // First declare a reference and then construct it.
        int[] ExampleArray1;
        ExampleArray1 = new int[24];


        // Example 2
        // ---------
        // This can be considered the short form for declaring and construction.
        int[] ExampleArray2 = new int[24];


        // Example 3
        // ---------
        // Construct and assign an array using a single command.
        String[] ExampleArray3 = {
              "Sun Solaris"
            , "HP-UX"
            , "Linux"
            , "MS Windows"
            , "Macintosh"
        };


        // Example 4
        // ---------
        // Construct and assign an array of Objects using a single command.
        Integer[] ExampleArray4 = {
            new Integer(100),
            new Integer(200),
            new Integer(300),
            new Integer(400)
        };


        System.out.println("+----------------------------+");
        System.out.println("| Declare / Print Examples   |");
        System.out.println("| Built-in Types             |");
        System.out.println("| - print ExampleArray3[]    |");
        System.out.println("+----------------------------+");
        for (int i = 0; i < ExampleArray3.length; i++) {
            System.out.println("  - " + ExampleArray3[i]);
        }
        System.out.println("\n");

        System.out.println("+----------------------------+");
        System.out.println("| Declare / Print Examples   |");
        System.out.println("| Object Types               |");
        System.out.println("| - print ExampleArray4[]    |");
        System.out.println("+----------------------------+");
        for (int i = 0; i < ExampleArray4.length; i++) {
            System.out.println("  - " + ExampleArray4[i]);
        }
        System.out.println("\n");

    }


    /**
     * Provides an example of declaring an Array of Objects.
     */
    public static void objectArray() {

        System.out.println();
        System.out.println("+===============================================+");
        System.out.println("|  ***                                     ***  |");
        System.out.println("|  ***           OBJECT ARRAY              ***  |");
        System.out.println("|  ***                                     ***  |");
        System.out.println("+===============================================+");
        System.out.println();

        final int MAX = 5;
        String[] platforms = new String[MAX];

        platforms[0] = new String("Sun Solaris");
        platforms[1] = new String("HP-UX");
        platforms[2] = new String("RedHat Linux");
        platforms[3] = new String("MS Windows");
        platforms[4] = new String("Macintosh");

        System.out.println("+----------------------------+");
        System.out.println("| Declare / Print Examples   |");
        System.out.println("| Object Types               |");
        System.out.println("| - print platforms[]        |");
        System.out.println("+----------------------------+");
        for (int i = 0; i < MAX; i++) {
            System.out.println("  - " + platforms[i]);
        }
        System.out.println("\n");

    }


    /**
     * Provides an example of how to resize an array by checking its length 
     * before inserting into the Array.
     */
    public static void arrayResize() {

        System.out.println();
        System.out.println("+===============================================+");
        System.out.println("|  ***                                     ***  |");
        System.out.println("|  ***            RESIZE ARRAY             ***  |");
        System.out.println("|  ***                                     ***  |");
        System.out.println("+===============================================+");
        System.out.println();

        final int STARTMAX = 5;
        int nPlatforms = 10;

        String[] platforms = new String[STARTMAX];

        System.out.println("+----------------------------+");
        System.out.println("| Resize Array               |");
        System.out.println("+----------------------------+");

        // Load the array
        for (int i = 0; i < nPlatforms; i++) {
        
            // Check and resize Array if required
            if (i >= platforms.length) {
                String[] tmp = new String[platforms.length + STARTMAX];
                System.arraycopy(platforms, 0, tmp, 0, platforms.length);
                platforms = tmp;
                // tmp array will be garbage collected...
            }
            platforms[i] = "Sun Solaris - " + i;
        }

        for (int i = 0; i < platforms.length; i++) {
            System.out.println("Platform " + i + " = " + platforms[i]);
        }
        System.out.println("\n");

    }


    /**
     * Helper class that returns a random number.
     */
    private static int getRandom(int mod) {
        Random rand = new Random();
        return Math.abs(rand.nextInt()) % mod + 1;
    }


    /**
     * Provides an example of how to create an Array without knowing the size
     * at compile time. In this case, simply use the "new" to create the 
     * elements of the Array. It should be clear that the creation of the Array
     * is happening at runtime.
     */
    public static void randomSizeArray() {

        System.out.println();
        System.out.println("+===============================================+");
        System.out.println("|  ***                                     ***  |");
        System.out.println("|  ***          RANDOM SIZE ARRAY          ***  |");
        System.out.println("|  ***                                     ***  |");
        System.out.println("+===============================================+");
        System.out.println();

        System.out.println("+--------------------------------+");
        System.out.println("| Random Size Array Declaration  |");
        System.out.println("+--------------------------------+");

        int[] a1 = new int[getRandom(50)];
        System.out.println("Length of Random Size Array (int)    : " + a1.length);
    
        String[] a2 = new String[getRandom(50)];
        System.out.println("Length of Random Size Array (String) : " + a2.length);
        System.out.println();

    }


    /**
     * Provides an example of how to return an array from a method.
     * @param n Size fo the array to create
     * @return <code>String[]</code> of the String array.
     */
    private static String[] returnArray(int n) {

        // Force number to be positive
        n = Math.abs(n);

        String[] results = new String[n];

        for (int i = 0; i < n; i++) {
            results[i] = "Platform #" + i;
        }
        return results;

    }


    /**
     * Provides an example of how to return an Array from a method. Returning an
     * Array is no different than returning any other object - you are returning 
     * a reference to an Array.
     */
    public static void returnArray() {

        System.out.println();
        System.out.println("+===============================================+");
        System.out.println("|  ***                                     ***  |");
        System.out.println("|  ***           RETURN AN ARRAY           ***  |");
        System.out.println("|  ***                                     ***  |");
        System.out.println("+===============================================+");
        System.out.println();

        System.out.println("+--------------------------------+");
        System.out.println("| Return an Array                |");
        System.out.println("+--------------------------------+");

        String[] a1 = returnArray(5);

        for (int i = 0; i < a1.length; i++) {
            System.out.println("  - " + a1[i]);
        }
        System.out.println();

    }


    /**
     * Provides an example of how to work with Multidimensional Arrays.
     */
    public static void multidimensionalArray() {

        System.out.println();
        System.out.println("+===============================================+");
        System.out.println("|  ***                                     ***  |");
        System.out.println("|  ***     MULTIDIMENSIONAL ARRAYS         ***  |");
        System.out.println("|  ***                                     ***  |");
        System.out.println("+===============================================+");
        System.out.println();
        
        System.out.println("  -------------");
        System.out.println("  2x3 int Array");
        System.out.println("  -------------");
        int[][] a1 = {
            {1, 2, 3},
            {10, 20, 30}
        };
        
        for (int i = 0; i < a1.length; i++) {
            for (int j = 0; j < a1[i].length; j++) {
                System.out.println("  a1[" + i + "][" + j + "] = " + a1[i][j]);
            }
        }
        System.out.println();


        System.out.println("  ---------------------------------");
        System.out.println("  2-D Array of nonprimitive objects");
        System.out.println("  ---------------------------------");
        Integer[][] a2 = {
            {new Integer(1)   , new Integer(2)   },
            {new Integer(10)  , new Integer(20)  },
            {new Integer(100) , new Integer(200) }
        };
        for (int i = 0; i < a2.length; i++) {
            for (int j = 0; j < a2[i].length; j++) {
                System.out.println("  a2[" + i + "][" + j + "] = " + a2[i][j]);
            }
        }
        System.out.println();

        System.out.println("  ---------------------------");
        System.out.println("  3-D Array with fixed length");
        System.out.println("  ---------------------------");
        String[][][] a3 = new String[2][3][3];

        for (int i = 0; i < a3.length; i++) {
            for (int j = 0; j < a3[i].length; j++) {
                for (int k = 0; k < a3[i][j].length; k++) {
                    a3[i][j][k] =
                        new String("String [" + i + "][" + j + "][" + k + "]");
                }
            }
        }

        for (int i = 0; i < a3.length; i++) {
            for (int j = 0; j < a3[i].length; j++) {
                for (int k = 0; k < a3[i][j].length; k++) {
                    System.out.println(
                        "  a3[" + i + "][" + j + "][" + k + "] = " + 
                        a3[i][j][k]);
                }
            }
        }
        System.out.println();

    }


    /**
     * Provides an example of how to Copy, Clone and Compare Arrays.
     * 
     * Differences between Copying and Cloning an Array
     * ------------------------------------------------
     *   - When "copying" an Array, you are required to create and size the 
     *     target array. You also have the ability of copying only a subset of
     *     the array.
     *     
     *   - Since Arrays implement the Cloneable interface, besides copying an
     *     Array, you can "clone" them. Cloning will create a new array of
     *     the same size and type and copying all the old elements into the
     *     new array. In the case of primitive elements, the new array has
     *     copies of the old elements, so changes to the elements of one are
     *     not reflected in the copy. However, in the case of objecte 
     *     references, only the reference is copied. Thus, both copies of the
     *     array would point to the same object. Changes to that object would
     *     be reflected in both arrays. This is known as a shallow copy
     *     of shallow clone.
     *   
     * Checking for Equality
     * ---------------------
     *   - When checking using the "==" operator, you are checking for two 
     *     array variables pointing to the same place in memory and thus
     *     pointing to the same array. When copying or cloning, the two 
     *     array variables will NOT be pointing to the same place in
     *     memory.
     *   - Even when checking two arrays with the  "equals()" method of Object,
     *     since the arrays have the same elements but exist in different
     *     memory space, they are different.
     *   - In order to have a clone (or copy) of an array be equal to the
     *     original, you must use the equals() method of the:
     *     java.util.Arrays class.
     *   
     */
    public static void copyCloneArrays() {

        System.out.println();
        System.out.println("+===============================================+");
        System.out.println("|  ***                                     ***  |");
        System.out.println("|  ***      COPYING / CLONING ARRAYS       ***  |");
        System.out.println("|  ***                                     ***  |");
        System.out.println("|  ***    (Also checking for EQUALITY)     ***  |");
        System.out.println("|  ***                                     ***  |");
        System.out.println("+===============================================+");
        System.out.println();

        String[] originalArray = {
              "Sun Solaris"
            , "HP-UX"
            , "Linux"
            , "MS Windows"
            , "Macintosh"
        };

        String[] copiedArray = new String[5];
        System.arraycopy(originalArray, 0, copiedArray, 0, originalArray.length);

        System.out.println("  Original Array");
        System.out.println("  --------------");
        for (int i=0; i < originalArray.length; i++) {
            System.out.println("  - " + originalArray[i]);
        }

        System.out.println();
        System.out.println("  Copied Array");
        System.out.println("  ------------");
        for (int i=0; i < copiedArray.length; i++) {
            System.out.println("  - " + copiedArray[i]);
        }

        System.out.println();
        System.out.println("  Check for Equality");
        System.out.println("  ------------------");
        System.out.println();
        System.out.println("    - originalArray == copiedArray              : " + (originalArray == copiedArray));
        System.out.println();
        System.out.println("    - originalArray.equals(copiedArray)         : " + (originalArray.equals(copiedArray)));
        System.out.println();
        System.out.println("    - Arrays.equals(originalArray, copiedArray) : " + (Arrays.equals(originalArray, copiedArray)));
        System.out.println();


        String[] clonedArray = (String[])originalArray.clone();

        System.out.println();
        System.out.println("  Original Array");
        System.out.println("  --------------");
        for (int i=0; i < originalArray.length; i++) {
            System.out.println("  - " + originalArray[i]);
        }

        System.out.println();
        System.out.println("  Cloned Array");
        System.out.println("  ------------");
        for (int i=0; i < clonedArray.length; i++) {
            System.out.println("  - " + clonedArray[i]);
        }

        System.out.println();
        System.out.println("  Check for Equality");
        System.out.println("  ------------------");
        System.out.println();
        System.out.println("    - originalArray == clonedArray              : " + (originalArray == clonedArray));
        System.out.println();
        System.out.println("    - originalArray.equals(clonedArray)         : " + (originalArray.equals(clonedArray)));
        System.out.println();
        System.out.println("    - Arrays.equals(originalArray, clonedArray) : " + (Arrays.equals(originalArray, clonedArray)));
        System.out.println();

    }


    /**
     * Provides an example of how to work with the Java Arrays Class.
     * The Arrays Class is found within the java.util package and contains four 
     * static methods used to perform utility functions on Arrays.
     */
    public static void theArraysClass() {

        System.out.println();
        System.out.println("+===============================================+");
        System.out.println("|  ***                                     ***  |");
        System.out.println("|  ***        USING THE ARRAYS CLASS       ***  |");
        System.out.println("|  ***                                     ***  |");
        System.out.println("+===============================================+");
        System.out.println();


        String[] a1 = new String[5];
        String[] a2 = new String[5];

        System.out.println("  --------------------");
        System.out.println("  Arrays.fill() Method");
        System.out.println("  --------------------");
        System.out.println();

        Arrays.fill(a1, "*");
        Arrays.fill(a2, "-");

        System.out.println("  ----------------------");
        System.out.println("  Arrays.equals() Method");
        System.out.println("  ----------------------");
        System.out.println("  Array a1 " +
                (Arrays.equals(a1, a2) ? "MATCHES" : "DOES NOT MATCH") + " a2");
        System.out.println();

        System.out.println("  --------------------");
        System.out.println("  Arrays.sort() Method");
        System.out.println("  --------------------");
        int[] a3 = new int[10];
        for (int i = 0; i < a3.length; i++) {
            a3[i] = getRandom(50 + i);
        }

        System.out.println("  Original Array");
        for (int i = 0; i < a3.length; i++) {
            System.out.println("    - a3[" + i + "] = " + a3[i]);
        }
        System.out.println();

        System.out.println("  Sorted Array");
        Arrays.sort(a3);
        for (int i = 0; i < a3.length; i++) {
            System.out.println("    - a3[" + i + "] = " + a3[i]);
        }
        System.out.println();

        System.out.println("  ----------------------------");
        System.out.println("  Arrays.binarySearch() Method");
        System.out.println("  ----------------------------");
        int location = Arrays.binarySearch(a3, 25);
        if (location >= 0) {
            System.out.println("    - Found the number 25 at location: " + location);
        } else {
            System.out.println("    - Could not find the number 25.");
        }
        System.out.println();

    }


    /**
     * While developing an application, it is reasonable that the developer 
     * needs to determine if an argument or object is an array. To perform this
     * in Java, you must first retrieve the object’s Class object and simply 
     * ask it. The isArray() method of the Class class will tell you.
     * 
     * Once you have determined you have an array, you can ask the 
     * getComponentType() method of Class what type of array you actually 
     * have. The getComponentType() method returns null if the isArray() method 
     * returned false. Otherwise, the Class type of the element is returned. 
     * You can recursively call isArray() if the array is multidimensional.
     * 
     * You can use the getLength() method of the Array class found in 
     * java.lang.reflect package to discover the length of the array.
     * 
     */
    public static void arrayReflection() {

        System.out.println();
        System.out.println("+===============================================+");
        System.out.println("|  ***                                     ***  |");
        System.out.println("|  ***           ARRAY REFLECTION          ***  |");
        System.out.println("|  ***                                     ***  |");
        System.out.println("+===============================================+");
        System.out.println();


        System.out.println();
        System.out.println("  ----------------------------------------------");
        System.out.println("  Check a String Object to see if it is an Array");
        System.out.println("  ----------------------------------------------");
        System.out.println();

        String stringVariable = "Alex Hunter";
        Class type0 = stringVariable.getClass();

        if (type0.isArray()) {
            Class elementType = type0.getComponentType();
            System.out.println("    - Array of  : " + elementType);
            System.out.println("    - Length of : " + Array.getLength(stringVariable));
        } else {
            System.out.println("    - " + type0.getName() + " is not an Array");
        }


        System.out.println();
        System.out.println("  -----------------------");
        System.out.println("  String Array Reflection");
        System.out.println("  -----------------------");
        System.out.println();

        String[] stringArray = {
            "Sun Solaris", "HP-UX", "Linux", "MS Windows", "Macintosh"
        };

        Class type1 = stringArray.getClass();

        if (type1.isArray()) {
            Class elementType = type1.getComponentType();
            System.out.println("    - Array of  : " + elementType);
            System.out.println("    - Length of : " + Array.getLength(stringArray));
        } else {
            System.out.print("    - " + type1.getName() + " is not an Array");
        }


        System.out.println();
        System.out.println("  --------------------------------");
        System.out.println("  int (primitive) Array Reflection");
        System.out.println("  --------------------------------");
        System.out.println();

        int[] intArray = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        Class type2 = intArray.getClass();

        if (type2.isArray()) {
            Class elementType = type2.getComponentType();
            System.out.println("    - Array of  : " + elementType);
            System.out.println("    - Length of : " + Array.getLength(intArray));
        } else {
            System.out.print("    - " + type2.getName() + " is not an Array");
        }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        declareExample();
        objectArray();
        arrayResize();
        randomSizeArray();
        returnArray();
        multidimensionalArray();
        copyCloneArrays();
        theArraysClass();
        arrayReflection();

    }

}
