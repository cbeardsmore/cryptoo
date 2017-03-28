/***************************************************************************
*	FILE: SDESBits.java
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: BitSet alternative using a int
*   LAST MOD: 24/03/17
*   REQUIRES: NONE
***************************************************************************/

public class SDESBits
{
    //CLASSFIELDS
    private int bits;
    private int size;
    private int half;
    // private only applies to different classes, so we can
    // import an SDESBits and retreive bits without a getter

//---------------------------------------------------------------------------
    //ALTERNATE CONSTRUCTOR

    public SDESBits( int inBits, int inSize )
    {
        bits = inBits;
        size = inSize;
        half = inSize >>> 1;
    }

//---------------------------------------------------------------------------
    //COPY CONSTRUCTOR

    public SDESBits( SDESBits inBits )
    {
        bits = inBits.bits;
        size = inBits.size;
        half = inBits.half;
    }

//---------------------------------------------------------------------------
    //FUNCTION: switchHalves()
    //PURPOSE: Switch the left half of bits with the right half

    public void switchHalves()
    {
        // Get the right half of the bits
        System.out.println(half);
        int oRight = bits & ( ( 1 << half ) - 1 );
        // Shift the left half of the bits down
        bits >>>= half;
        // Combine left half with right half shifted up
        bits |= ( oRight << half );
    }

//---------------------------------------------------------------------------
    //FUNCTION: permute()
    //IMPORT: permTable (int[])
    //EXPORT: permuted (SDESBits)
    //PURPOSE: Create a permutation of this objects bits in a new SDESBits

    public SDESBits permute( int[] permTable )
    {
        // Create temporary space the size of the permutation
        SDESBits permuted = new SDESBits( 0, permTable.length );
        // Iterate across the permutation, getting and setting bits
        for ( int ii = 0; ii < permTable.length; ii++ )
            permuted.setBit( getBit( permTable[ii] ), ii );
        return permuted;
    }

//---------------------------------------------------------------------------
    //FUNCTION: leftShift()
    //IMPORT: shifts (int)
    //PURPOSE: Perfrom a circular left shift on the bits of each half

    public void leftShift( int shifts )
    {
        // Temp variable for repeated 1's for a half
        int ones = ( 1 << half ) - 1;
        // Avoid shifting more than required
        if ( half > shifts )
            shifts %= half;

        // Get the left half and right half
        int left = bits >>> half;
        int right = bits & ones;

        // Loop for each shift individually
        for ( int ii = 0; ii < shifts; ii++ )
        {
            // Get the leftmost bit of the left sub-half
            int leftBit = ( left & ones );
            leftBit >>>= 4;
            // Get the rightmost bit of the right sub-half
            int rightBit = ( right & ones );
            rightBit >>>=4;

            // Perform the actual shifting of the bits
            left = ( left << 1 ) & ones;
            right = ( right << 1 ) & ones;

            // If the first bits of the halves were one, set final bit
            if ( leftBit == 1 )      left++;
            if ( rightBit == 1 )     right++;
        }

        // Recombine both halves back together
        bits = ( left << half ) | right;
    }

//---------------------------------------------------------------------------
    //FUNCTION: split()
    //EXPORT: halves (SDESBits[])
    //PURPOSE: Split the bits into two sub-halves and return as objects

    public SDESBits[] split()
    {
        // New container for the halves
        SDESBits[] halves = new SDESBits[2];
        // Get the left half and create object
        int leftInt = bits >>> half;
        halves[0] = new SDESBits( leftInt, half );
        // Get the right half and create object
        int rightInt = ( bits & ( ( 1 << half ) - 1 ) );
        halves[1] = new SDESBits( rightInt, half );

        return halves;
    }

//---------------------------------------------------------------------------
    //FUNCTION: xor()
    //IMPORT: inBits (SDESBits)
    //PURPOSE: XOR bits with the bits value of inBits

    public void xor( SDESBits inBits )
    {
        // Call simple exclusive-or on both bits
        bits ^= inBits.bits;
    }

//---------------------------------------------------------------------------
    //FUNCTION: setBit()
    //IMPORT: val (boolean), index (int)
    //PURPOSE: Set the value at the specifed index with the specified value

    public void setBit( boolean val, int index )
    {
        // Reset the given bit
        bits &= ~(1 << ( size - index - 1 ) );
        // Reset the bits greater than the size we want
        bits &= (1 << size)-1;
        // Set the required bit
        bits |= ((val) ? 1 : 0 ) << ( size - index - 1 );
    }

//---------------------------------------------------------------------------
    //FUNCTION: getBit()
    //IMPORT: index (int)
    //EXPORT: value (boolean)
    //PURPOSE: Get the value of the bit at the specified index

    public boolean getBit( int index )
    {
        // Bits are reverse ordered
        return (bits & 1 << ( size - index - 1 ) ) != 0;
    }

//---------------------------------------------------------------------------
    //FUNCTION: append()
    //IMPORT: newBits (SDESBits)
    //PURPOSE: Append new set of bits to the original set

    public void append( SDESBits newBits )
    {
        // Increment size
        size += newBits.size;
        // Shift original across and add new bits
        bits = ( bits << newBits.size ) | newBits.bits;
        // Update half value
        half = size >>> 1;
    }

//---------------------------------------------------------------------------
    //FUNCTION: sbox()
    //EXPORT: result (int)
    //PURPOSE: Find the sbox values for the bits in this object

    public int sbox()
    {
        // Split into halves
        SDESBits halves[] = this.split();

        // Get row and column of the first four bits
        int colS0 = ( halves[0].bits & 6 ) >>> 1;
        int rowS0 = ( ( halves[0].bits & 8 ) >>> 2 ) | ( halves[0].bits & 1 );
        // Get row and column of the second four bits
        int colS1 = ( halves[1].bits & 6 ) >>> 1;
        int rowS1 = ( ( halves[1].bits & 8 ) >>> 2 ) | ( halves[1].bits & 1 );

        // Get the appropriate sbox value
        int s0Val = SDESConstants.S0[rowS0][colS0];
        int s1Val = SDESConstants.S1[rowS1][colS1];

        // Combine the result
        int result = ( s0Val << 2 ) | s1Val;

        return result;
    }

//---------------------------------------------------------------------------
    //FUNCTION: toString()
    //EXPORT: state (String)
    //PURPOSE: Export bits in a readable binary format

    public String toString()
    {
        return Integer.toBinaryString( bits );
    }

//---------------------------------------------------------------------------
}
