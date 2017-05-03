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
    public static final int NUM_ARGS = 3;

//---------------------------------------------------------------------------

    public static void main( String[] args )
    {
        // Check argument length and output usage
        if ( args.length < NUM_ARGS )
        {
            System.out.println("USAGE: RSA <input file> <output file> <mode> <keys>");
            System.out.println("modes = -e encryption, -d decryption");
            System.out.println("if mode = -d, keys = d n");
            System.exit(1);
        }

        int p, q, totN;
        int e = 0, d = 0, n = 0;

        // Rename variables for simplicity
        String inFile = args[0];
        String outFile = args[1];
        String mode = args[2];
        char output = ' ';

        //generate new keys for encryption
        if ( mode.equals("-e") )
        {
            System.out.println("Generating Keys: ");
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

            outputKeys( p, q, n, totN, e, d );
        }
        else if ( mode.equals("-d") )
        {
            try
            {
                d = Integer.parseInt( args[3] );
                n = Integer.parseInt( args[4] );
            }
            catch ( NumberFormatException ex)
            {
                    System.out.println("KEY VALUES INVALID");
                    System.exit(1);
            }
        }

        try
        {
            // Open file streams
            FileInputStream fis = new FileInputStream( new File( inFile ) );
            FileOutputStream fos = new FileOutputStream( new File( outFile ) );
            DataOutputStream dos = new DataOutputStream( fos );

            // Read bytes until end of file
            int next = fis.read();
            while ( next != -1 )
            {
                // Select function based on mode
                if ( mode.equals( "-e" ) )
                    output = (char)encrypt( next, e, n );
                else if ( mode.equals( "-d") )
                    output = (char)decrypt( next, d, n );
                else
                    throw new IllegalArgumentException("INVALID MODE");

                // Write converted output to file
                System.out.println("\t" + (int)output);
                dos.writeByte( output );
                next = fis.read();
            }

            //close and flush buffers
            fis.close();
            fos.close();
        }
        catch (Exception ex)
        {
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
            System.exit(1);
        }
    }

//---------------------------------------------------------------------------
//NAME: encrypt()
//IMPORT: input (int), e (int), n (int)
//EXPORT: output (int)
//PURPOSE: Encrypt given input using keys e and n, with RSA encryption

    public static int encrypt( int input, int e, int n )
    {
        int output = NumberTheory.modularExpo( input, e, n );
        //System.out.println( "input " + input + " to output " + output );
        return output;
    }

//---------------------------------------------------------------------------
//NAME: decrypt()
//IMPORT: input (int), d (int), n (int)
//EXPORT: output (int)
//PURPOSE: Decrypt given input using keys d and n, with RSA encryption

    public static int decrypt( int input, int d, int n )
    {
        return NumberTheory.modularExpo( input, d, n );
    }

//---------------------------------------------------------------------------
//NAME: outputKeys()
//IMPORT: p, q, n, totN, e, d (ints)
//PURPOSE: Output all generated keys for display

    public static void outputKeys( int p, int q, int n, int totN, int e, int d )
    {
        System.out.println("\tp    = " + p);
        System.out.println("\tq    = " + q);
        System.out.println("\tn    = " + n);
        System.out.println("\ttotN = " + totN);
        System.out.println("\te    = " + e);
        System.out.println("\td    = " + d);
    }

//---------------------------------------------------------------------------
}
