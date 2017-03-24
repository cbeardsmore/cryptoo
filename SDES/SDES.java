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
        int keyInput = Integer.parseInt( "0101011010", 2 );
        System.out.println( keyInput );
        SDESBits key = new SDESBits( keyInput, 10 );
        System.out.println( key.toString() );
        System.out.println( key.getBit(2) );

        key = key.permute( SDESConstants.P4 );

        System.out.println( key.toString() );
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

    //public static void keyGeneration() {}

//---------------------------------------------------------------------------

    //public static void feistalRound() {}

//---------------------------------------------------------------------------
}
