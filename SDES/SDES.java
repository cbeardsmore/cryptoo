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
        int num = Integer.parseInt( "11110000", 2 );
        System.out.println( num );
        SDESBits key = new SDESBits( num, 8 );
        System.out.println( key.toString() );
        key.switchHalves();
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
