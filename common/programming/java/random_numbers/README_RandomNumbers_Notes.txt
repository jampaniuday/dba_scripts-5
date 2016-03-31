# +----------------------------------------------------------------------------+
# | FILE      : README_RandomNumbers_Notes.txt                                 |
# | AUTHOR    : Jeffrey Hunter                                                 |
# | WEB       : http://www.iDevelopment.info                                   |
# | CATEGORY  : Java Programming                                               |
# | SUBJECT   : Random Numbers                                                 |
# +----------------------------------------------------------------------------+

I. INTRODUCTION

    The following note provides an overview of how to utilize Random Numbers
    using Java's APIs.

    Java has support for random numbers in two classes:

        java.lang.Math
        java.util.Random


II. USING java.util.Random

    Java provides support for random numbers in the library 
    class java.util.Random.

    Its basic use is:

        import java.util.*;

        Random rn = new Random();

        ...

        int r = rn.nextInt();           // 32-bit random number

        double d = rn.nextDouble();     // random value in range 0.0 - 1.0

    The random number generation algorithm used for this class is based on the 
    linear congruential method (see Chapter 3 in Volume 2 of Knuth's 
    "Art of Computer Programming"). This method uses a starting value, known
    as a "seed", and each value of the sequence is generated from the last.

    Is this truly random? That question is beyond the scope of this note. For
    a further and deeper discuss in streams of random numbers, consult 
    Knuth's book.

    When using the default Random() constructor the random number generator is 
    seeded from the current time of day. It is also possible to provide your
    own seed value as in:

        Random rn = new Random(1234);


    Method        Range
    ------------  -------------------------------------------
    nextInt()     -2147483648 to 2147483647 
    nextLong()    -9223372036854775808 to 9223372036854775807 
    nextFloat()   -1.0 to 1.0 
    nextDouble()  -1.0 to 1.0 


III. USING Math.random()

    Math.random() will produce a random number between [0,1). The square braket
    means "includes" whereas the parenthesis means "doesn't include". This means
    values between 0.0 and 1.0, including 0.0 and not including 1.0. There are 
    about 2^62 different double fractions between 0 and 1.
    
    - Total number of numbers in a floating-point number system is:
    
            2(M-m+1)b^(p-1) + 1
            where "b" is the base (usually 2).
            "p" is the precision (digits in the mantissa)
            "M" is the largest exponent, and "m" is the smallest exponent.

            IEEE 754 uses:
            M=1023, m=-1022, p=53, and b=2
            so the total number of numbers is:
            2(1023+1022-1)2^52
            = 2((2^10-1)+(2^10-1))2^52
            = (2^10-1)2^54
            = 2^64 - 2^54
            
    Half of these numbers (corresponding to exponents in the range [-1022,0])
    are less than 1 in magnitude (both positive and negative), so 1/4 of that 
    expression, of 2^62 - 2^52 + 1 (approximately 2^62) is in the range [0,1).


    Returns a double value with a positive sign, greater than or equal to 0.0 
    and less than 1.0. Returned values are chosen pseudorandomly with 
    (approximately) uniform distribution from that range. When this method is 
    first called, it creates a single new pseudorandom-number generator, 
    exactly as if by the expression 

        new java.util.Random()

    This new pseudorandom-number generator is used thereafter for all calls to 
    this method and is used nowhere else. 
    
    This method is properly synchronized to allow correct use by more than one 
    thread. However, if many threads need to generate pseudorandom numbers at a 
    great rate, it may reduce contention for each thread to have its own 
    pseudorandom-number generator. 


III. GENERATING RANDOM CHARACTERS

    Since Math.random() generates a value between 0 and 1, you need only
    multiply it by the upper bound of the range of numbers you want to product 
    (26 for the letters in the alphabet) and add an offset to establish the 
    lower bound. Although it appears you're switching on a character here, the 
    switch statement is actually using the integral value of the character. The 
    singly-quoted characters in the case statements also produce integral values
    that are used to comparison.

    Math.random() produces a double, so the value 26 is converted to a double to 
    perform the multiplication, which also produces a double. This means that 
    'a' must be converted to a double to perform the addition. The double result
    is turned back into a "char" with the cast.
   
    If for example, you have the value 29.7 and you cast it to a char, the
    resulting value is 29. Always remember, that casting from a float or double 
    to an integral value always truncates.
