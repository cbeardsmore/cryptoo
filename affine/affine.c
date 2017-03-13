/***************************************************************************
*	FILE: affine.c
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Run Affine cipher given text and key, either encrypt or decrypt
*   LAST MOD: 11/03/17
*   REQUIRES: stdio.h, ctype.h, stdlib.h, string.h
***************************************************************************/

#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>

// PROTOTYPES
int keyEligible(int,int);
char encrypt(char,int,int);
char decrypt(char,int,int);
int gcdFunction(int,int);
int extendEuclid(int,int);

// CONSTANTS
#define ARGS 6
#define ALPHABET 26

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
            char cipher = encrypt( next, a, b );
            fputc( cipher, outF );
        }
    }

    // PERFORM DECRYPTION IF -d FLAG PROVIDED
    if ( !strncmp( flag, "-d", 2 ) )
    {
        while ( ( feof( inF ) == 0 ) && ( ferror( inF ) == 0) && (ferror( inF ) == 0) )
        {
            char next = fgetc( inF );
            char cipher = decrypt( next, a, b );
            fputc( cipher, outF );
        }
    }

    // CLOSE FILES
    fclose( inF );
    fclose( outF );

    return 0;
}

//------------------------------------------------------------------------------
// FUNCTION: keyEligible
// IMPORT: a (int), b (int)
// EXPORT: eligible (int)
// PURPOSE: Check that the two given keys are eligible via coprime check

int keyEligible( int a, int b)
{
    int eligible = 0;
    if ( gcdFunction( a, b ) == 1 )
        eligible = 1;
    return eligible;
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
// FUNCTION: eecrypt
// IMPORT: plain (char*), a (int), b (int)
// PURPOSE: ???

char decrypt( char cipher, int a, int b )
{
    return 'A';
}

//------------------------------------------------------------------------------
// FUNCTION: gcd
// IMPORT: a (int), b (int)
// PURPOSE: Find greatest common denominator of 2 numbers

int gcdFunction( int a, int b )
{
    int quotient, residue, temp, gcd = 1;

    if ( a < b )
    {
        int temp = a;
        a = b;
        b = temp;
    }

    // CHECK IF EITHER NUMBER IS 0
    if ( a == 0 )   return b;
    if ( b == 0 )   return a;

    // SATISFY THE EQUATION: A = B * quotient + residue
    quotient = a / b;
    residue = a - ( b * quotient );

    // RECURSIVELY CALL GCD
    gcd = gcdFunction( b, residue );

    return gcd;
}

//------------------------------------------------------------------------------
// FUNCTION: extendEuclid
// IMPORT: a (int), n (int)
// PURPOSE: Extended Euclidean algorithm to find inverse modular

int extendEuclid( int a, int n )
{
    int t = 0, newt = 1;
    int r = n, newr = a;
    int q = 0, temp = 0;

    if ( gcdFunction( a, n ) != 1 )
        return -1;

    while ( newr != 0 )
    {
        q = r / newr;
        temp = t;
        t = newt;
        newt = temp - ( q * newt );
        temp = r;
        r = newr;
        newr = temp - ( q * newr );
    }

    if ( t < 0 )
        t = t + n;

    return t;
}

//------------------------------------------------------------------------------
