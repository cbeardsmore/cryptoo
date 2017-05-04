/***************************************************************************
*	FILE: main.c
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Main RSA implementation
*   LAST MOD: 03/05/17
*   REQUIRES: main.h
***************************************************************************/

#include "main.h"

//------------------------------------------------------------------------------

int main( int argc, char **argv )
{
    if ( ( argc != 4 ) && ( argc != 6 ) )
    {
        printf( "USAGE: ./rsa <infile> <outfile> <mode> <keys>\n" );
        printf( "\tMODE: -e = encryption, -d = decryption\n" );
        printf( "\tKEYS: if mode = -d, supply values for d and n\n" );
        exit(1);
    }

    FuncPtr modeFunc = NULL;
    FILE* input = NULL;
    FILE* output = NULL;

	//seed random
    srand( time(NULL) );

    //generate keys on default
    generateKeys();

    //read command line arguments, ignoring the first
	modeFunc = readArgs( argc, argv );

    //open files
    printf("OPENING FILES...\n");
	input = fopen(inFile, "rb");
	if( input == NULL )
	{
		printf("CANNOT OPEN %s FOR FILE READING\n\n", inFile);
        exit(1);
	}
	output = fopen(outFile, "wb");
	if( output == NULL )
	{
		printf("Problem opening %s for writing\n\n", outFile);
        exit(1);
	}

    printf("PERFORMING RSA ENCRYPTION/DECRYPTION...\n");
    //perform actual encryption or decryption
	while( (*modeFunc)(input,output) != EOF ) {}
    printf("\tCOMPLETE\n");

	return 0;
}

//------------------------------------------------------------------------------
//NAME: encrypt()
//IMPORT: input (FILE*), output (FILE*)
//EXPORT: retVal (int)
//PURPOSE: Reads in two bytes, encrypts, and write back out 4 bytes

int encrypt(FILE* input, FILE* output)
{
	int c;
    int retVal = 0;
	int64_t plaintext = 0;
	int64_t ciphertext;

    //read in two characters
	for( int ii = 0; ii < PLAIN_BYTES; ii++ )
	{
		c = fgetc(input);
        //EOF reached
		if(c == EOF)
        {
            retVal = EOF;
            break;
        }
		else
			plaintext += c << ( ( 1 - ii ) << 3 );
	}

    //skip over if there nothing read in
	if(plaintext != 0)
	{
        //calculate the actual ciphertext
		ciphertext = modularExpo(plaintext, e, n);

        //write back out 4 characters
		for( int ii = 0; ii < CIPHER_BYTES; ii++)
		{
			c = ciphertext >> ( ( 3 - ii ) << 3 );
			fputc(c, output);
		}
	}

	//close files when done
	if(retVal == EOF)
	{
		fclose(input);
		fclose(output);
	}

	return retVal;
}

//------------------------------------------------------------------------------
//NAME: decrypt()
//IMPORT: input (FILE*), output (FILE*)
//EXPORT: retVal (int)
//PURPOSE: Reads in 4 bytes, decrypts, and write back out 2 bytes

int decrypt(FILE* input, FILE* output)
{
	int c;
	int64_t plaintext;
	int64_t ciphertext = 0;
	int retVal = 0;

	for(int ii = 0; ii < CIPHER_BYTES; ii++ )
	{
		c = fgetc(input);
        //EOF reached
	    if( c == EOF )
		{
			retVal = EOF;
			break;
		}
		else
			ciphertext += c << (( 3 - ii ) << 3);
	}

    //skip over if nothing read in
	if(ciphertext != 0)
	{
        //calculate the actual plaintext
		plaintext = modularExpo(ciphertext, d, n);

        //write back out 2 bytes
		for( int ii = 0; ii < PLAIN_BYTES; ii++)
		{
			c = plaintext >> ( ( 1 - ii ) << 3 );
			if( c != 0 )
				fputc( c, output );
		}
	}

	//close files when done
	if(retVal == EOF)
	{
		fclose(input);
		fclose(output);
	}

	return retVal;

}

//------------------------------------------------------------------------------
//NAME: readArgs()
//IMPORT: argc (int), argv (char**)
//PURPOSE: Read the command line arguments into global variables

FuncPtr readArgs( int argc, char **argv )
{
    FuncPtr modeFunc = NULL;
    //rename for readability
    inFile = argv[1];
    outFile = argv[2];
    char* mode = argv[3];

    //only if decryption is set
    if ( argc > 4 )
    {
        d = atoi( argv[4] );
        n = atoi( argv[5] );

        //check validity of keys
        if ( ( d == 0 ) || ( n == 0 ) )
        {
            printf("INVALID KEYS FOR DECRYPTION");
            exit(1);
        }
    }

    //set the correct mode
    if ( strcmp( mode, "-e" ) == 0 )
        modeFunc = &encrypt;
    else if ( strcmp( mode, "-d"  ) == 0 )
        modeFunc = &decrypt;
    else
    {
        printf( "INVALID MODE ARGUMENT\n" );
        exit(1);
    }

    return modeFunc;
}

//------------------------------------------------------------------------------
//NAME: generateKeys()
//PURPOSE: Generate key values for RSA, p, q, n, totN, e and d

void generateKeys(void)
{
    //generate two different prime numbers
	p = generatePrime( LOWER_PRIME, UPPER_PRIME );
	do
	{
		q = generatePrime( LOWER_PRIME, UPPER_PRIME );
	}
	while( p == q );

    //calculate n and totN
    n = p * q;
    totN = ( p - 1 ) * ( q - 1);

    //choose suitable e value
	e = generateE( totN );
    //determine modular inverse of e and totN, the d value
	d = extendedEuclid( e, totN );

    printKeys();
}

//------------------------------------------------------------------------------
//NAME: generateE()
//IMPORT: totN (int64_t)
//EXPORT: e (int64_t)
//PURPOSE: Determine suitable e value so that e and totN are coprime

int64_t generateE( int64_t totN )
{
	int64_t e;

    //repeat until the values of coprime
	do
	{
		e = rand() % totN;
	}
	while( findGCD( e, totN ) != 1 );

	return e;
}

//------------------------------------------------------------------------------
//NAME: printKeys()
//PURPOSE: Print all keys and variables required in RSA

void printKeys(void)
{
    printf("GENERATING NEW KEYSET:\n");
    printf("\tp = %lld\n\tq = %lld\n", p, q );
    printf("\tn = %lld\n\ttotN = %lld\n", n, totN );
    printf("\te = %lld\n\td = %lld\n", e, d );
}
//------------------------------------------------------------------------------
