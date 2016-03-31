// -----------------------------------------------------------------------------
// ObjectEquality.java
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
 * When writing object-oriented programs (i.e. Java), the need is almost always
 * there to compare instances of the same class. And once instances are 
 * "comparable", they can then be sorted.
 * 
 * The key to this example is that you should always use the equals() method
 * when comparing objects. The relational operator == tests for pointer equality
 * (i.e., do the objects point to the same thing). The equals method tests to 
 * see whether the two objects are equal (and may vary in definition from one 
 * object to the next). It is up to the developer to decide what makes two 
 * objects "equal".
 * 
 * Every class by default has a method called equals() which has the following 
 * signature: 
 *
 *      public boolean equals(Object obj) 
 * 
 * This method accepts an object as its argument, performs a comparison and 
 * returns true if the two object instances are equal and false otherwise. 
 * What some developers fail to realize is that the equals() method, in its 
 * default state, only compares the memory addresses of two objects, not their 
 * contents. (This is the behavior of what we see when using the relational
 * operator == to compare two objects.) To compare the contents of two objects
 * you must override the equals method.
 * 
 * For most of the Java library classes, the equals() method is already 
 * overridden for you. (i.e. Integer)
 * 
 * Note that both the equals() and hostCode() methods are used by hashes
 * (Hashtable and HashMap).
 * 
 * Here are the rules for an equals() method:
 *      1.) It if reflexive   :        x.equals(x)  must be true.
 *      2.) It is symmetric   :        x.equals(y)  must be true if and only if 
 *                                     y.equals(x)  is also true.
 *      3.) It is transitive  :  if    x.equals(y)  is true
 *                               and   y.equals(z)  is true;
 *                               then  x.equals(z)  must also be true.
 *      4.) It it consistent  :  Multiple calls on   x.equals(y)  return the 
 *                               same value (unless state values used in the
 *                               comparison are changed, as by calling a set
 *                               method).
 *      5.) It is cautious    :        x.equals(null)   must return false,
 *                               rather than accidentally throwing a
 *                               NullPointerException.
 *                               
 *      NOTE: Even with the testing that has been provided in the "Employee"
 *            class, what could go wrong? Well, there are situations that still
 *            need care. What if the object is a subclass of Employee? We cast
 *            it and .... compare only our fields. Like in our example, you 
 *            should test explicitly with getClass() if subclassing is likely.
 *            And subclasses should call super.equals() to test all superclass
 *            fields.
 *            
 *            Other things that can go wrong; what if either emp1, emp2, etc
 *            is null? In cases like this, you would be handed a
 *            NullPointerException. So you also need to test for any possible
 *            null values.
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ObjectEquality {


    /*
     * Relational operators generate a boolean result. They evaluate the
     * relationship between the values of the operands. A relational expression
     * produces a "true" if the relationship is true, and "false" if the
     * relationship is untrue. The relational operators are:
     * 
     *      <   - less than
     *      >   - greater than
     *      <=  - less than or equal to
     *      >=  - greater than or equal to
     *      ==  - equivalent
     *      !=  - not equivalent
     *      
     * Equivalence and nonequivalence works with all built-in types, but the other
     * comparisons won't work with the type "boolean".
     * 
     * The relational operators "==" and "!=" also work with all objects, but
     * their meaning often confuses new comers to Java.
     */
    public static void RelationalOperators() {

        System.out.println();
        System.out.println("RELATIONAL OPERATORS");
        System.out.println("====================");
        System.out.println();

        /*
         * The following code segment creates and compares two built-in int
         * types using the relational operator ==. The output of this code
         * segment is what you would expect. The relational operators == and !=
         * compare the contents of the int type.
         */
        int m1 = 42;
        int m2 = 42;
        System.out.println("  Compare two built-in types  :  m1 == m2  = " + (m1 == m2)); // true
        System.out.println("  Compare two built-in types  :  m1 != m2  = " + (m1 != m2)); // false
        System.out.println();

        /*
         * The following code segment creates and compares two Integer objects 
         * using the relational operator ==. At first, you would think the
         * comparison would return "true" since both Integer objects are the 
         * same. But while the "contents" of the objects are the same, the 
         * references are not the same and the operators == and != compare 
         * object references.
         */
        Integer n1 = new Integer(42);
        Integer n2 = new Integer(42);
        System.out.println("  Compare two Integer objects :  n1 == n2  = " + (n1 == n2)); // false
        System.out.println("  Compare two Integer objects :  n1 != n2  = " + (n1 != n2)); // true
        System.out.println();

    }


    /*
     * If you want to compare the actual contents of two objects for
     * equivalence, you will need to use the special method equals() that 
     * exists for all objects (not primitives, which work fine with ==
     * and !=).
     * 
     * Most of the Java library classes implement the equals() method so
     * that it compares the contents of objects instead of the default, which
     * is to compare their object references. The following method uses the 
     * Integer object as an example of one of Java's library classes that does
     * override the equals() method
     */
    public static void JavaClassLibraryObjects() {

        System.out.println();
        System.out.println("JAVA CLASS LIBRARY OBJECTS");
        System.out.println("==========================");
        System.out.println();

        Integer n1 = new Integer(42);
        Integer n2 = new Integer(42);

        System.out.println("  Compare two Integer objects using equals() method : " + n1.equals(n2)); // true
        System.out.println();

    }


    /*
     * The following method demonstrates comparing two objects that DO NOT
     * override the equals() method. From this example, you can see that the
     * objects do have an equals() method (inherited from Object). But the
     * default equals() method for class Objects implements the most 
     * discriminating possible equivalence relation on objects; that is, for 
     * any reference values x and y, this method returns true if and only if 
     * x and y refer to the same object (x==y has the value true).
     * 
     * From this example, you can see that you should always override the
     * equals() method to compare the contents of the object rather than the
     * default behavior which is to compare object references.
     */
    public static void NotOverrrideEqualsMethod() {

        System.out.println();
        System.out.println("DO NOT OVERRIDE THE EQUALS METHOD");
        System.out.println("=================================");
        System.out.println();

        EmpValue v1 = new EmpValue(120);
        EmpValue v2 = new EmpValue(120);

        System.out.println("  Compare two EmpValue objects using default equals() method : " + v1.equals(v2)); // false
        System.out.println();

    }


    /*
     * 
     */
    public static void OverrideEqualsMethod() {

        System.out.println();
        System.out.println("OVERRIDE THE EQUALS METHOD");
        System.out.println("=================================");
        System.out.println();
        
        Employee      emp1  = new Employee();
        Employee      emp1c = (Employee)emp1;
        Employee      emp2  = new Employee("John", "Doe");
        EmployeeFake  emp2f = new EmployeeFake("John", "Doe");
        Employee      emp3  = new Employee("Ron", "Miller");
        Employee      emp4  = new Employee("Ron", "Miller");
        Employee      emp5  = new Employee("Jane", "Doe");



        System.out.println("  Print out all objects");
        System.out.println("  ---------------------");
        System.out.println("    Employee      (emp1)   = " + emp1);
        System.out.println("    Employee      (emp1c)  = " + emp1c);
        System.out.println("    Employee      (emp2)   = " + emp2);
        System.out.println("    EmployeeFake  (emp2f)  = " + emp2f);
        System.out.println("    Employee      (emp3)   = " + emp3);
        System.out.println("    Employee      (emp4)   = " + emp4);
        System.out.println("    Employee      (emp5)   = " + emp5);
        System.out.println();
        

        System.out.println("  Comparison results");
        System.out.println("  ------------------");
        System.out.println("    Compare (emp1) to (emp1c)  : " + emp1.equals(emp1c));  // true
        System.out.println("    Compare (emp1) to (emp2)   : " + emp1.equals(emp2));   // true
        System.out.println("    Compare (emp2) to (emp1c)  : " + emp2.equals(emp1c));  // true
        System.out.println("    Compare (emp2) to (emp2f)  : " + emp2.equals(emp2f));  // false
        System.out.println("    Compare (emp3) to (emp4)   : " + emp3.equals(emp4));   // true
        System.out.println("    Compare (emp4) to (emp5)   : " + emp4.equals(emp5));   // false

    }
    

    public static void main(String[] args) {

        RelationalOperators();
        JavaClassLibraryObjects();
        NotOverrrideEqualsMethod();
        OverrideEqualsMethod();

    }

}


class EmpValue {

    int i;

    public EmpValue() {
        this.i = 0;
    }

    public EmpValue(int x) {
        this.i = x;
    }

}


class EmployeeFake {

    private String firstName   = null;
    private String lastName    = null;

    public EmployeeFake() {
        this.firstName = "John";
        this.lastName  = "Doe";
    }

    public EmployeeFake(String fname, String lname) {
        this.firstName = fname;
        this.lastName  = lname;
    }

    public String toString() {
        return (lastName + ", " + firstName);
    }
}


class Employee {

    private String firstName   = null;
    private String lastName    = null;

    public Employee() {
        this.firstName = "John";
        this.lastName  = "Doe";
    }

    public Employee(String fname, String lname) {
        this.firstName = fname;
        this.lastName  = lname;
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
            Employee tmp = (Employee)obj;
            if ( (tmp.firstName.equalsIgnoreCase(this.firstName))
                 &&
                 (tmp.lastName.equalsIgnoreCase(this.lastName)) ) {
                return true;
            } else {
                return false;
            }
        }
    }

    public String toString() {
        return (lastName + ", " + firstName);
    }

}