/***************************************************************************
*	FILE: SDESConstants
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Structures to represent the constants in the SDES algorithm
*   LAST MOD: 21/03/17
*   REQUIRES: NONE
***************************************************************************/

public class SDESConstants
{
    // P10 PERMUTATION FOR THE 10-BIT KEY
    public static final int[] P10 = { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6 };

//---------------------------------------------------------------------------

    // P8 PERMUTATION FOR THE 10-BIT KEY
    public static final int[] P8 = { 6, 3, 7, 4, 8, 5, 10, 9 };

//---------------------------------------------------------------------------

    // INITIAL PERMUTATION FOR THE 8-BIT PLAINTEXT
    public static final int[] IP = { 2, 6, 3, 1, 4, 8, 5, 7 };

//---------------------------------------------------------------------------

    // INVERSE PERMUTATION FOR THE 8-BIT PLAINTEXT
    public static final int[] IPI = { 4, 1, 3, 5, 7, 2, 8, 6 };

//---------------------------------------------------------------------------

    // EXPANSION PERMUTATION FOR 4-BITS IN Fk
    public static final int[] EP = { 4, 1, 2, 3, 2, 3, 4, 1 };

//---------------------------------------------------------------------------

    // P4 PERMUTATION AFTER THE S-BOX SELECTION
    public static final int[] P4 = { 2, 4, 3, 1 };

//---------------------------------------------------------------------------

    // SBOX ONE
    public static final int[][] S0 = {  { 1, 0, 3, 2 },
                                        { 3, 2, 1, 0 },
                                        { 0, 2, 1, 3 },
                                        { 3, 1, 3, 2 }  };

//---------------------------------------------------------------------------

    // SBOX TWO
    public static final int[][] S1 = {  { 0, 1, 2, 3 },
                                        { 2, 0, 1, 3 },
                                        { 3, 0, 1, 0},
                                        { 2, 1, 0, 3 }  };

//---------------------------------------------------------------------------
}
