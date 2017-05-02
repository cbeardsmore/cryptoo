/***************************************************************************
*	FILE: RSA.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Performs RSA public-key encryption or decryption on a given file
*   LAST MOD: 01/05/17
*   REQUIRES: NONE
***************************************************************************/

import java.util.*;
import java.io.*;

public class RSA
{
    public static final long LIMIT = 10000000000L;
//---------------------------------------------------------------------------

    public static void main( String[] args )
    {
        int base = 236;
        int exponent = 239721;
        int modulus = 2491;

        System.out.println( "BASE:     " + base );
        System.out.println( "EXPONENT: " + exponent );
        System.out.println( "MODULUS:  " + modulus );

        int result = modularExpo( base, exponent, modulus );
        System.out.println( "RESULT:   " + result );
    }

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

//---------------------------------------------------------------------------
}