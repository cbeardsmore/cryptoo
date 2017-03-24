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

    public SDESBits( SDESBits inBits )
    {
        bits = inBits.bits;
        size = inBits.size;
    }

//---------------------------------------------------------------------------

    public void switchHalves()
    {
        int oRight = bits & ( ( 1 << ( size >> 1 ) ) - 1 );
        bits >>>= ( size >> 1 );
        bits |= ( oRight << ( size >> 1 ) );
    }

//---------------------------------------------------------------------------

    public SDESBits permute( int[] permTable )
    {
        SDESBits permuted = new SDESBits( 0, permTable.length );
        for ( int ii = 0; ii < permTable.length; ii++ )
            permuted.setBit(getBit( permTable[ii] ), ii );
        return permuted;
    }

//---------------------------------------------------------------------------

    public void leftShift( int shifts)
    {
        int half = size >> 1;

        if ( half > shifts )
            shifts %= half;

        int left = bits >>> half;
        int right = ( bits & ( ( 1 << half ) - 1 ) ) >> half ;

        left = (left >>> shifts) | (shifts << ( half - shifts ) );
        right = (right >>> shifts) | (shifts << ( half - shifts ) );

        bits = ( left << half ) | right;
    }

//---------------------------------------------------------------------------

    public void setBit( boolean val, int index )
    {
        bits &= ~(1 << index);
        bits &= (1 << size)-1;
        bits |= ((val) ? 1 : 0 ) << index;
    }

//---------------------------------------------------------------------------

    public boolean getBit( int index )
    {
        return (bits & 1 << index) != 0;
    }

//---------------------------------------------------------------------------

    public String toString()
    {
        return Integer.toBinaryString( bits );
    }

//---------------------------------------------------------------------------
}
