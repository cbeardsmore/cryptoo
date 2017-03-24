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
        int keyInput = Integer.parseInt( "1110101100", 2 );
        SBESBits keys = keyGeneration( keyInput );
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

    //public static void feistalRound() {}

//---------------------------------------------------------------------------
}
