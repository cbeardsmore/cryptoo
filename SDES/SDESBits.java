/***************************************************************************
*	FILE: SDESBits.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: BitSet Alternative with a short
*   LAST MOD: 24/03/17
*   REQUIRES: NONE
***************************************************************************/

public class SDESBits
{
    private int bits;
    private int size;

//---------------------------------------------------------------------------

    public SDESBits( int inBits, int inSize )
    {
        bits = inBits;
        size = inSize;
    }

//---------------------------------------------------------------------------

    public void switchHalves()
    {
        int oRight = bits & ( ( 1 << ( size >> 1 ) ) - 1 );
        bits >>>= ( size >> 1 );
        bits |= ( oRight << ( size >> 1 ) );
    }

//---------------------------------------------------------------------------

    public String toString()
    {
        return Integer.toBinaryString( bits );
    }

//---------------------------------------------------------------------------
}
