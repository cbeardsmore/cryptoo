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
            permuted.setBit( getBit( permTable[ii] ), ii );
        return permuted;
    }

//---------------------------------------------------------------------------

    public void leftShift( int shifts)
    {
        int half = size >> 1;

        if ( half > shifts )
            shifts %= half;

        int left = bits >>> half;
        int right = ( bits & ( ( 1 << half ) - 1 ) );

        for ( int ii = 0; ii < shifts; ii++ )
        {
            int leftBit = ( left & ( 1 << ( half - 1 ) ) );
            leftBit >>= 4;
            int rightBit = ( right & ( 1 << ( half - 1 ) ) );
            rightBit >>=4;

            left = ( left << 1 ) & ( ( 1 << half ) - 1 );
            right = ( right << 1 ) & ( ( 1 << half ) - 1 );

            if ( leftBit == 1 )      left++;
            if ( rightBit == 1 )     right++;
        }

        bits = ( left << half ) | right;
    }

//---------------------------------------------------------------------------

    public SDESBits[] split()
    {
        int half = size >> 1;
        int leftInt = bits >>> half;
        int rightInt = ( bits & ( ( 1 << half ) - 1 ) );
        SDESBits[] halves = new SDESBits[2];
        halves[0] = new SDESBits( leftInt, half );
        halves[1] = new SDESBits( rightInt, half );
        return halves;
    }

//---------------------------------------------------------------------------

    public void xor( SDESBits subkey )
    {
        bits ^= subkey.bits;
    }

//---------------------------------------------------------------------------

    public void setBit( boolean val, int index )
    {
        bits &= ~(1 << ( size - index - 1) );
        bits &= (1 << size)-1;
        bits |= ((val) ? 1 : 0 ) << ( size - index - 1 );
    }

//---------------------------------------------------------------------------

    public boolean getBit( int index )
    {
        return (bits & 1 << ( size - index - 1 ) ) != 0;
    }

//---------------------------------------------------------------------------

    public void append( SDESBits newBits )
    {
        size += newBits.size;
        bits = ( bits << newBits.size ) | newBits.bits;
    }

//---------------------------------------------------------------------------

    public int sbox()
    {
        SDESBits halves[] = this.split();

        int colS0 = ( halves[0].bits & 6 ) >>> 1;
        int rowS0 = ( ( halves[0].bits & 8 ) >>> 2 ) | ( halves[0].bits & 1 );
        int colS1 = ( halves[1].bits & 6 ) >>> 1;
        int rowS1 = ( ( halves[1].bits & 8 ) >>> 2 ) | ( halves[1].bits & 1 );

        int s0Val = SDESConstants.S0[rowS0][colS0];
        int s1Val = SDESConstants.S1[rowS1][colS1];

        int result = ( s0Val << 2 ) | s1Val;

        return result;
    }

//---------------------------------------------------------------------------

    public String toString()
    {
        return Integer.toBinaryString( bits );
    }

//---------------------------------------------------------------------------
}
