// -----------------------------------------------------------
// RandomNumbers.java
// -----------------------------------------------------------

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
 
package info.idevelopment.math;

/**
 * Used to document and create random numbers.
 * 
 * This class also implements many of the conventions used in
 * the "Java Coding and Documenting Conventions" guide within
 * the DBA Documentation Library.
 * <p>
 * For more information on the different features of Javadoc, visit
 * <a target="_blank" href="http://java.sun.com/j2se/javadoc">Javadoc Tool Home Page</a>
 * @author Jeffrey Hunter
 * @author <a href="mailto:jhunter@iDevelopment.info">jhunter@iDevelopment.info</a>
 * @author <a target="_blank" href="http://www.iDevelopment.info">www.iDevelopment.info</a>
 * @version 1.0,  &nbsp; 07-SEP-2002
 * @since SDK1.4
 */

public class RandomNumbers {

  /**
   * Prints a brief overview of how random numbers are gnerated from
   * the Java Math class.
   */
  public static void printRandomOverview() {
    System.out.print("\n");
    System.out.println("----------------------------------------------------------------------");
    System.out.println("GENERATING RAW RANDOM NUMBERS");
    System.out.println("----------------------------------------------------------------------");
    System.out.println("Math.random() will produce a random number between [0,1).");
    System.out.println("The square braket means \"includes\" whereas the parenthesis means \"doesn't include\".");
    System.out.println("This means values between 0.0 and 1.0, including 0.0 and not including 1.0.");
    System.out.println("There are about 2^62 different double fractions between 0 and 1.");
    System.out.println("  - Total number of numbers in a floating-point number system is:");
    System.out.println("    2(M-m+1)b^(p-1) + 1");
    System.out.println("    where \"b\" is the base (usually 2).");
    System.out.println("    \"p\" is the precision (digits in the mantissa)");
    System.out.println("    \"M\" is the largest exponent, and \"m\" is the smallest exponent.");
    System.out.println("    IEEE 754 uses:");
    System.out.println("    M=1023, m=-1022, p=53, and b=2");
    System.out.println("    so the total number of numbers is:");
    System.out.println("    2(1023+1022-1)2^52");
    System.out.println("    = 2((2^10-1)+(2^10-1))2^52");
    System.out.println("    = (2^10-1)2^54");
    System.out.println("    = 2^64 - 2^54");
    System.out.println("Half of these numbers (corresponding to exponents in the range [-1022,0]) are less than 1");
    System.out.println("in magnitude (both positive and negative), so 1/4 of that expression, of 2^62 - 2^52 + 1");
    System.out.println("(approximately 2^62) is in the range [0,1).");
  }

  /**
   * Get a random number from the Java Math class.
   * The random number will be in the range [0,1).
   * @return A random number in the range of [0,1).
   */
  public static double getRandomNumber() {
    return Math.random();
  }

}
