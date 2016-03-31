// -----------------------------------------------------------------------------
// GCD.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * The following class demonstrates how to find the greatest command divisor
 * (frequently associated with Euclid's algorithm) of two numbers that
 * appear in Euclid's Elements. The GCD algorithm states: Given two positive 
 * integers m and n, find their greatest common divisor, that is, the largest
 * positive integer that evenly divides both m and n.

 * @author Jeffrey Hunter
 * -----------------------------------------------------------------------------
 */

public class GCD {

    public static int gcd(int m, int n) {

        if (m < n) {
            int t = m;
            m = n;
            n = t;
        }

        int r = m % n;

        if (r == 0) {
            return n;
        } else {
            return gcd(n, r);
        }

    }

}
