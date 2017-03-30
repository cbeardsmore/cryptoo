/***************************************************************************
*	FILE: SDES.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Performs SDES encryption or decryption on a given file
*   LAST MOD: 21/03/17
*   REQUIRES: NONE
***************************************************************************/

import java.util.*;
import java.io.*;

public class SDES
{

    public static final int NUM_ARGS = 4;
    public static final int MAX_KEY = 1023;
    public static final int KEY_SIZE = 10;
    public static final int MESSAGE_SIZE = 8;

//---------------------------------------------------------------------------

    public static void main( String[] args )
    {
        // Check argument length and output usage
        if ( args.length != NUM_ARGS )
        {
            System.out.println("USAGE: SDES <mode> <key> <input file> <output file>");
            System.out.println("modes = -e encryption, -d decryption");
            System.out.println("keys = int between 0 and 255");
            System.exit(1);
        }

        // Rename variables for simplicity
        String mode = args[0];
        String key = args[1];
        String inFile = args[2];
        String outFile = args[3];
        SDESBits message, output;
        int intKey = Integer.parseInt( key );

        try
        {
            // Generate subkeys
            SDESBits subkeys[] = keyGeneration( intKey );

            // Open file streams
            FileInputStream fis = new FileInputStream( new File( inFile ) );
            FileOutputStream fos = new FileOutputStream( new File( outFile ) );

            // Read bytes until end of file
            int next = fis.read();
            while ( next != -1 )
            {
                message = new SDESBits( next, MESSAGE_SIZE );

                // Select function based on mode
                if ( mode.equals( "-e" ) )
                    output = encrypt( message, subkeys  );
                else if ( mode.equals( "-d") )
                    output = decrypt( message, subkeys  );
                else
                    throw new IllegalArgumentException("INVALID MODE");

                // Write converted output to file
                int outputInt = output.getBits();
                fos.write( outputInt );
                next = fis.read();
            }
        }
        catch (Exception e)
        {
            System.out.println( e.getMessage() );
        }

    }

//---------------------------------------------------------------------------
    //FUNCTION: encrypt()
    //IMPORT: message (SDESBits), subkeys (SDESBits[])
    //EXPORT: message (SDESBits)
    //PURPOSE: Encrypt given message with given subkeys

    public static SDESBits encrypt( SDESBits message, SDESBits[] subkeys )
    {
        // Initial Permutation
        message = message.permute( SDESConstants.IP );
        // First feistal key round with subkey 1
        message = feistalRound( message, subkeys[0] );
        // Switch left and right subhalves
        switchFunction( message );
        // Second feistal key round with subkey 2
        message = feistalRound( message, subkeys[1] );
        // Inverse of Initial Permutation
        message = message.permute( SDESConstants.IPI );
        return message;
    }

//---------------------------------------------------------------------------
    //FUNCTION: decrypt()
    //IMPORT: message (SDESBits), subkeys (SDESBits[])
    //EXPORT: message (SDESBits)
    //PURPOSE: Decrypt given message with given subkeys

    public static SDESBits decrypt( SDESBits message, SDESBits[] subkeys )
    {
        // Initial Permutation
        message = message.permute( SDESConstants.IP );
        // First feistal key round with subkey 2
        message = feistalRound( message, subkeys[1] );
        // Switch left and right subhalves
        switchFunction( message );
        // First feistal key round with subkey 2
        message = feistalRound( message, subkeys[0] );
        // Inverse of Initial Permutation
        message = message.permute( SDESConstants.IPI );
        return message;
    }

//---------------------------------------------------------------------------
    //FUNCTION: switchFunction()
    //IMPORT: input (SDESBitSet)
    //PURPOSE: Import 8-bit binary and swap the first and last 4 bits

    public static void switchFunction( SDESBits input )
    {
        input.switchHalves();
    }

//---------------------------------------------------------------------------
    //FUNCTION: keyGeneration()
    //IMPORT: keyDec (int)
    //EXPORT: subkeys (SDESBits[])
    //PURPOSE: Generate subkeys given the full key

    public static SDESBits[] keyGeneration( int keyDec )
    {
        // Check key validity
        if ( ( keyDec < 0 ) || ( keyDec > MAX_KEY ) )
            throw new IllegalArgumentException("INVALID KEY");

        // Convert int key into an SDESBits object and create subkey array
        SDESBits key = new SDESBits( keyDec, KEY_SIZE );
        SDESBits[] subkeys = new SDESBits[2];

        // P10 permutation, left shift and P8 permutation to form subkey 1
        key = key.permute( SDESConstants.P10 );
        key.leftShift(1);
        subkeys[0] = key.permute( SDESConstants.P8 );
        // P8 permutation and double left shift to form subkey 2
        key.leftShift(2);
        subkeys[1] = key.permute( SDESConstants.P8 );

        return subkeys;
    }

//---------------------------------------------------------------------------
    //FUNCTION: feistalRound()
    //IMPORT: message (SDESBits), subkey (SDESBits)
    //EXPORT: halves (SDESBits)
    //PURPOSE: Perform feistal key round on message given a subkey

    public static SDESBits feistalRound( SDESBits message, SDESBits subkey )
    {
        // Split message in half
        SDESBits halves[] = message.split();
        // Perform fMapping function
        SDESBits fMap = fMapping( halves[1], subkey );
        // XOR the halves and append
        halves[0].xor( fMap );
        halves[0].append(halves[1]);
        return halves[0];
    }

//---------------------------------------------------------------------------
    //FUNCTION: fMapping()
    //IMPORT: message (SDESBits), subkey (SDESBits)
    //EXPORT: message (SDESBits)
    //PURPOSE: Perform fMapping function on given message with subkey

    public static SDESBits fMapping( SDESBits message, SDESBits subkey )
    {
        // Expansion permutation and XOR with subkey
        message = message.permute( SDESConstants.EP );
        message.xor( subkey );
        // Calculate SBOX values and P4 permutation
        message = new SDESBits( message.sbox(), MESSAGE_SIZE/2 );
        message = message.permute( SDESConstants.P4 );
        return message;
    }

//---------------------------------------------------------------------------
}
