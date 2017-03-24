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
        FileInputStream fs = null;
        String filename = "affine2.txt";

		try
		{
			fs = new FileInputStream(filename);
            byte[] hello = new byte[1];
            char next;
            while ( fs.read(hello) != -1 )
            {
                next = (char)hello[0];
                System.out.print( next );

            }


			fs.close();
		}
		catch (IOException e)
		{

		}
    }

//---------------------------------------------------------------------------

    //public static void switchFunction() {}

//---------------------------------------------------------------------------

    //public static void keyGeneration() {}

//---------------------------------------------------------------------------

    //public static void feistalRound() {}

//---------------------------------------------------------------------------
}
