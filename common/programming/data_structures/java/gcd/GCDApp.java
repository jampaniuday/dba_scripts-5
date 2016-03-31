// -----------------------------------------------------------------------------
// GCDApp.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * The following class demonstrates how to find the greatest command divisor
 * (frequently associated with Euclid's algorithm) of two numbers that
 * appear in Euclid's Elements. The GCD algorithm states: Given two positive 
 * integers m and n, find their greatest common divisor, that is, the largest
 * positive integer that evenly divides both m and n.
 *
 * @author Jeffrey Hunter
 * -----------------------------------------------------------------------------
 */

public class GCDApp {

    public static void main(String[] args) {

        System.out.println("Greatest Common Divisor");
        System.out.println("=======================\n");

        System.out.println("  GCD(123, 31)    = " + GCD.gcd(123, 31));
        System.out.println("  GCD(5, 4)       = " + GCD.gcd(5, 4));
        System.out.println("  GCD(119, 544)   = " + GCD.gcd(119, 544));
        System.out.println("  GCD(544, 119)   = " + GCD.gcd(544, 119));
        System.out.println("  GCD(100, 100)   = " + GCD.gcd(100, 100));
        System.out.println("  GCD(100, 50)    = " + GCD.gcd(100, 50));
        System.out.println("  GCD(50, 100)    = " + GCD.gcd(50, 100));
    }
}
