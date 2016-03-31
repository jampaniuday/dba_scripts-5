/*
 *  FILE   : gcd.c
 *  AUTHOR : Jeffrey Hunter
 *  WEB    : http://www.iDevelopment.info
 *  NOTES  : The following program demonstrates how to find 
 *           the greatest command divisor (frequently 
 *           associated with Euclid's algorithm) of two numbers
 *           that appear in Euclid's Elements. The GCD 
 *           algorithm states: Given two positive  integers m and n,
 *           find their greatest common divisor, that is, the 
 *           largest positive integer that evenly divides 
 *           both m and n.
 */

#include <stdlib.h>

int gcd(int m, int n) {

  int t, r;

  if (m < n) {
    t = m;
    m = n;
    n = t;
  }

  r = m % n;

  if (r == 0) {
    return n;
  } else {
    return gcd(n, r);
  }

}

main() {

  printf("Greatest Common Divisor\n");
  printf("=======================\n");

  printf("  GCD(123, 31)    = %d\n", gcd(123, 31));
  printf("  GCD(5, 4)       = %d\n", gcd(5, 4));
  printf("  GCD(119, 544)   = %d\n", gcd(119, 544));
  printf("  GCD(544, 119)   = %d\n", gcd(544, 119));
  printf("  GCD(100, 100)   = %d\n", gcd(100, 100));
  printf("  GCD(100, 50)    = %d\n", gcd(100, 50));
  printf("  GCD(50, 100)    = %d\n", gcd(50, 100));

  return 0;

}
