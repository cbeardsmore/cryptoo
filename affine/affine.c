/***************************************************************************
*	FILE: affine.c
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Run Affine cipher given text and key, either encrypt or decrypt
*   LAST MOD: 11/03/17
*   REQUIRES: affine.h
***************************************************************************/

#include "affine.h"

//------------------------------------------------------------------------------
// FUNCTION: main

int main( int argc, char* argv[] )
{
    if ( argc != ARGS )
    {
        printf("\nUSAGE: <FLAG> <INPUT FILE> <OUTPUT FILE> <KEY A> <KEY B>\n");
        printf("FLAGS ARE: -e for encryption, -d for decryption\n\n");
        return 1;
    }

    // CONVERT ARGC NAMES
    char* flag = argv[1];
    char* inFile = argv[2];
    char* outFile = argv[3];
    int a = atoi( argv[4] );
    int b = atoi( argv[5] );

    // CHECK THAT THE KEYS ARE ELIGIBLE
    int validity = keyEligible( a, b );
    if ( validity != 1 )
    {
        printf("KEYS %d AND %d ARE NOT VALID.", a, b );
        return 2;
    }

    // OPEN INPUT AND OUTPUT FILES
    FILE* inF = fopen( inFile, "r" );
    FILE* outF = fopen( outFile, "w" );

    // CHECK OPEN FOR ERRORS
    if ( ( inF == NULL ) || ( outF == NULL ) )
    {
        perror("Error Opening input or output file");
        return 3;
    }

    // PERFORM ENCRYPTION IF -e FLAG PROVIDED
    if ( !strncmp( flag, "-e", 2 ) )
    {
        while ( ( feof( inF ) == 0 ) && ( ferror( inF ) == 0) && (ferror( inF ) == 0) )
        {
            char next = fgetc( inF );
            if ( feof( inF ) == 0 )
            {
                char cipher = encrypt( next, a, b );
                fputc( cipher, outF );
            }
        }
    }

    // PERFORM DECRYPTION IF -d FLAG PROVIDED
    if ( !strncmp( flag, "-d", 2 ) )
    {
        while ( ( feof( inF ) == 0 ) && ( ferror( inF ) == 0) && (ferror( inF ) == 0) )
        {
            char next = fgetc( inF );
            if ( feof( inF ) == 0 )
            {
            char cipher = decrypt( next, a, b );
            fputc( cipher, outF );
            }
        }
    }

    // CLOSE FILES
    fclose( inF );
    fclose( outF );

    return 0;
}

//------------------------------------------------------------------------------
// FUNCTION: encrypt
// IMPORT: plain (char), a (int), b (int)
// PURPOSE: Convert a plaintext char into the encryped character

char encrypt( char plain, int a, int b)
{
    char output = plain;
    if ( isupper(plain) )
        output = ( ( ( plain - 'A' ) * a + b ) % 26 ) + 'A';
    else if ( islower(plain) )
        output = ( ( ( plain - 'a' ) * a + b ) % 26 ) + 'a';

    return output;
}

//------------------------------------------------------------------------------
// FUNCTION: decrypt
// IMPORT: plain (char*), a (int), b (int)
// PURPOSE: ???

char decrypt( char cipher, int a, int b )
{
    int inverse = extendEuclid( a, ALPHABET );
    char output = cipher;
    if ( isupper(cipher) )
        output = ( ( inverse * ( cipher - 'A' - b + 26 ) ) % 26 ) + 'A';
    else if ( islower(cipher) )
        output = ( ( inverse * ( cipher - 'a' - b + 26 ) ) % 26 ) + 'a';
    return output;
}

//------------------------------------------------------------------------------
