/***************************************************************************
*	FILE: NumberTheory.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Basic helper functions for performing RSA encryption
*   LAST MOD: 01/05/17
*   REQUIRES: NONE
***************************************************************************/

import java.util.Random;

public class NumberTheory
{
    //CONSTANTS
    public static final long LIMIT = 10000000000L;
    public static final int LOWER_PRIME = 1000;
    public static final int UPPER_PRIME = 10000;
    public static final int CONFIDENCE = 25;

//---------------------------------------------------------------------------
    //NAME: modularExpo()
    //IMPORT: base (int), exponent (int), modulus (int)
    //EXPORT: result (int)
    //PURPOSE: Calculate the value base^exponent mod modulus efficiently

    public static int modularExpo( int base, int exponent, int modulus )
    {
        int result = 1;

        //check upper limit
        if ( ( base > LIMIT ) || ( exponent > LIMIT ) || ( modulus > LIMIT) )
            throw new IllegalArgumentException("INVALID MODULAR EXPO NUMBER");

        //anything mod 1 results in 0
        if ( modulus == 1 )
            return 0;

        //reduce base to the lowest form
        base = base % modulus;

        //loop until all exponents reviewed
        while ( exponent > 0 )
        {
            //if the bit is set (from lowest to highest order bit)
            if ( ( exponent & 1 ) == 1 )
                //increase result by the base
                result = ( result * base ) % modulus;
            //shift exponent to consider the next higher order bit
            exponent = exponent >> 1;
            //increase the base
            base = ( base * base ) % modulus;
        }

        return result;
    }

//------------------------------------------------------------------------------
//FUNCTION: generatePrime()
//EXPORT: newPrime (int)
//PURPOSE: Generate a prime number between LOWER_PRIME and UPPER_PRIME

     public static int generatePrime()
     {
         Random rand = new Random();

         //loop until the random generated number is a prime, based on Lehmanns
         //number generated will be between 1000 and 10000
         int newPrime = rand.nextInt( UPPER_PRIME - LOWER_PRIME ) + LOWER_PRIME;
         while ( !primalityTest( newPrime, CONFIDENCE ) )
            newPrime = rand.nextInt( UPPER_PRIME - LOWER_PRIME ) + LOWER_PRIME;

         return newPrime;
     }

//------------------------------------------------------------------------------
// FUNCTION: primalityTest
// IMPORT: p (int), tests (int)
// EXPORT: isPrime (boolean)
// PURPOSE: Determine if a number is prime or not to some confidence level

    private static boolean primalityTest( int prime, int tests )
    {
        long a, r;
        long expo;
        Random rand = new Random();

        //perform multiple tests
        for ( int ii = 0; ii < tests; ii++ )
        {
            //calculate r result
            a = rand.nextInt() % ( prime - 1 ) + 1;
            expo = ( prime - 1 ) >> 1;
            r = (long)Math.pow( a, expo ) % prime;

            //if r not 1 or -1 it is 100% not prime
            if ( ( r != 1 ) && ( r != -1 ) )
                if ( ( r != ( prime - 1 ) ) && ( r != ( prime - 1 ) ) )
                return false;
        }

        return true;
    }

//------------------------------------------------------------------------------
// FUNCTION: gcd
// IMPORT: a (int), b (int)
// EXPORT: gcd (int)
// PURPOSE: Find greatest common denominator of 2 numbers

    public static int gcdFunction( int a, int b )
    {
        int gcd = 1;
        int quotient;
        int residue;

        //swap the elements to ensure b is smaller
        if ( a < b )
        {
            int temp = a;
            a = b;
            b = temp;
        }

        //check if either number is 0
        if ( a == 0 )   return b;
        if ( b == 0 )   return a;

        //satisfy the equation A = B * quotient + residue
        quotient = a / b;
        residue = a - ( b * quotient );

        //recursively call gcd
        gcd = gcdFunction( b, residue );

        return gcd;
    }

//------------------------------------------------------------------------------
// FUNCTION: extendEuclid
// IMPORT: a (int), n (int)
// EXPORT: t (int)
// PURPOSE: Extended Euclidean algorithm to find inverse modular

    public static int extendEuclid( int a, int n )
    {
        int t = 0, newt = 1;
        int r = n, newr = a;
        int q = 0, temp = 0;

        //only applicable if the gcd is 1
        if ( gcdFunction( a, n ) != 1 )
            return -1;

        //perform the eea
        while ( newr != 0 )
        {
            q = r / newr;
            temp = t;
            t = newt;
            newt = temp - ( q * newt );
            temp = r;
            r = newr;
            newr = temp - ( q * newr );
        }

        //ensure t is positive
        if ( t < 0 )
            t = t + n;

        return t;
    }

//---------------------------------------------------------------------------
}
