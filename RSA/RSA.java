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
    //CONSTANTS
    public static final int NUM_ARGS = 4;

//---------------------------------------------------------------------------

    public static void main( String[] args )
    {
        // Check argument length and output usage
        if ( args.length != NUM_ARGS )
        {
            System.out.println("USAGE: RSA <mode> <input file> <output file>");
            System.out.println("modes = -e encryption, -d decryption");
            System.exit(1);
        }

        int p, q, e, n, d, totN;

        // Rename variables for simplicity
        String mode = args[0];
        String inFile = args[1];
        String outFile = args[2];
        int output = 0;

        //select two primes p/q using lehmanns and random, between 1000 and 10000
        p = NumberTheory.generatePrime();
        do
        {
            q = NumberTheory.generatePrime();
        } while ( p == q );

        //calculate n=pq
        n = p * q;
        totN = ( p - 1) * ( q - 1 );

        //use EEA to select e,n satisfying gcd(e, varphi(n)) == 1
        e = NumberTheory.generateE( totN );

        //use EEA to solve private key d
        d = NumberTheory.extendEuclid( e, totN );

        try
        {
            // Open file streams
            FileInputStream fis = new FileInputStream( new File( inFile ) );
            FileOutputStream fos = new FileOutputStream( new File( outFile ) );

            // Read bytes until end of file
            int next = fis.read();
            while ( next != -1 )
            {
                // Select function based on mode
                if ( mode.equals( "-e" ) )
                    output = encrypt( next, e, n );
                else if ( mode.equals( "-d") )
                    output = decrypt( next, d, n );
                else
                    throw new IllegalArgumentException("INVALID MODE");

                // Write converted output to file
                fos.write( output );
                next = fis.read();
            }
        }
        catch (Exception ex)
        {
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        }
    }

//---------------------------------------------------------------------------
//NAME: encrypt()
//IMPORT: input (int), e (int), n (int)
//EXPORT: output (int)
//PURPOSE: Encrypt given input using keys e and n, with RSA encryption

    public static int encrypt( int input, int e, int n )
    {
        int output = 0;
        //c = me mod n
        return output;
    }

//---------------------------------------------------------------------------
//NAME: decrypt()
//IMPORT: input (int), d (int), n (int)
//EXPORT: output (int)
//PURPOSE: Decrypt given input using keys d and n, with RSA encryption

    public static int decrypt( int input, int d, int n )
    {
        int output = 0;
        //m = cd mod n.
        return output;
    }

//---------------------------------------------------------------------------
}
