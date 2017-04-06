/***************************************************************************
*	FILE: frequency.c
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Count appearance frequency of letters in file
*   LAST MOD: 02/03/17
*   REQUIRES: stdio.h, ctype.h
***************************************************************************/

#include <stdio.h>
#include <ctype.h>

#define ALPHABET 26

// PROTOTYPES
void countFreq(char*, int[]);
void outputFreq(int[]);

//------------------------------------------------------------------------------
// FUNCTION: main

int main(void)
{
    char filename[100];
    int freq[25];

    // USER INPUT
    printf("Input filename: ");
    scanf("%s", filename);

    // INIT FREQUENCY ARRAY
    for ( int ii = 0; ii < ALPHABET; ii++ )
        freq[ii] = 0;

    // COUNT FREQUENCIES
    countFreq( filename, freq );

    // OUTPUT
    outputFreq( freq );
}

//------------------------------------------------------------------------------
// FUNCTION: countFreq
// IMPORT: filename (char*), freq (int[])
// PURPOSE: Count frequencies of characters in the alphabet

void countFreq(char* filename, int freq[])
{
    FILE* file = fopen( filename, "r" );

    char next = getc(file);
    while ( next != EOF )
    {
        if ( isupper(next) )
            freq[ next - 'A' ]++;
        else if ( islower(next) )
            freq[ next - 'a' ]++;
        next = getc(file);
    }

    fclose(file);
}

//------------------------------------------------------------------------------
// FUNCTION: outputFreq
// IMPORT: freq (int[])
// PURPOSE: Output frequencies in a bar chart format

void outputFreq(int freq[])
{
    for ( int ii = 0; ii < ALPHABET; ii++ )
    {
        printf( "%c: ", ii + 'A' );
        for ( int jj = 0; jj < freq[ii]; jj++ )
            printf("x");
        printf("\n");
    }
}

//------------------------------------------------------------------------------
