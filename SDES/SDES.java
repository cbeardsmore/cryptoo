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
    public static void main( String[] args )
    {
        // Check argument length and output usage
        if ( args.length != 4 )
        {
            System.out.println("USAGE: SDES <flag> <key> <input file> <output file>");
            System.out.println("flags = -e encryption, -d decryption");
            System.out.println("keys = int between 0 and 255");
            return;
        }

        // Rename variables for simplicity
        String flag = args[0];
        String key = args[1];
        String inFile = args[2];
        String outFile = args[3];

        // Parse key and generate subkeys
        int intKey = Integer.parseInt( key );
        SDESBits subkeys[] = keyGeneration( intKey );

        try
        {
            FileInputStream fis = new FileInputStream( new File( inFile ) );
            FileOutputStream fos = new FileOutputStream( new File( outFile ) );
            int next = fis.read();
            while ( next != -1 )
            {
                SDESBits message = new SDESBits( next, 8 );
                SDESBits output = decrypt( message, subkeys  );
                int outputInt = output.getBits();
                fos.write( outputInt );
                next = fis.read();
            }
        }
        catch (Exception e) {}

    }

//---------------------------------------------------------------------------
    //FUNCTION: encrypt()
    //IMPORT: message (SDESBits), subkeys (SDESBits[])
    //EXPORT: message (SDESBits)
    //PURPOSE:

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
    // switchFunction()
    // IMPORT: input (SDESBitSet)
    // PURPOSE: Import 8-bit binary and swap the first and last 4 bits

    public static void switchFunction( SDESBits input )
    {
        input.switchHalves();
    }

//---------------------------------------------------------------------------

    public static SDESBits[] keyGeneration( int keyDec )
    {
        SDESBits key = new SDESBits( keyDec, 10 );
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

    public static SDESBits feistalRound( SDESBits message, SDESBits subkey )
    {
        SDESBits halves[] = message.split();
        SDESBits fMap = fMapping( halves[1], subkey );
        halves[0].xor( fMap );
        halves[0].append(halves[1]);
        return halves[0];
    }

//---------------------------------------------------------------------------

    public static SDESBits fMapping( SDESBits message, SDESBits subkey )
    {
        message = message.permute( SDESConstants.EP );
        message.xor( subkey );
        message = new SDESBits( message.sbox(), 4 );
        message = message.permute( SDESConstants.P4 );
        return message;
    }

//---------------------------------------------------------------------------
}
