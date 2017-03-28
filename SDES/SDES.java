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
        char selection = 'd';
        String rawKey = "1110101100";
        String rawMes = "01011001";

        int intKey = Integer.parseInt( rawKey, 2 );
        int intMes = Integer.parseInt( rawMes, 2 );

        SDESBits subkeys[] = keyGeneration( intKey );
        SDESBits message = new SDESBits( intMes, 8 );

        if ( selection == 'e' )
            message = encrypt( message, subkeys );
        else if ( selection == 'd' )
            message = decrypt( message, subkeys);
        else
            throw new IllegalArgumentException("INVALID SELECTION");

    }

//---------------------------------------------------------------------------

    public static SDESBits encrypt( SDESBits message, SDESBits[] subkeys )
    {
        message = message.permute( SDESConstants.IP );
        message = feistalRound( message, subkeys[0] );
        switchFunction( message );
        message = feistalRound( message, subkeys[1] );
        message = message.permute( SDESConstants.IPI );
        return message;
    }

//---------------------------------------------------------------------------

    public static SDESBits decrypt( SDESBits message, SDESBits[] subkeys )
    {
        message = message.permute( SDESConstants.IP );
        message = feistalRound( message, subkeys[1] );
        switchFunction( message );
        message = feistalRound( message, subkeys[0] );
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

        key = key.permute( SDESConstants.P10 );
        key.leftShift(1);
        subkeys[0] = key.permute( SDESConstants.P8 );

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
