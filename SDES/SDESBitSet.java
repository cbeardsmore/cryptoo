/***************************************************************************
*	FILE: SDESBitSet.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Container class for a BitSet in java
*   LAST MOD: 21/03/17
*   REQUIRES: NONE
***************************************************************************/

import java.util.Scanner;

public class SDES
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);
        String key = sc.nextLine();
        int keyDecimal = Integer.parseInt(key, 2);
        BitSet bs = BitSet.valueOf(new long[]{keyDecimal});

        System.out.println( bs.toString() );
    }
}
//---------------------------------------------------------------------------
