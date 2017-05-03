/***************************************************************************
*	FILE: main.h
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Header file for rsa
*   LAST MOD: 02/05/17
*   REQUIRES: numberTheory.h
***************************************************************************/

#include <time.h>
#include <string.h>
#include "numberTheory.h"

// FUNCTION POINTER
typedef int(*FuncPtr)(void);

//PROTOTYPES
int64_t generateE(int64_t);
void generateKeys(void);
void printvals(void);
FuncPtr readArgs(int,char**);
char* readLine(FILE*);
int encrypt(void);
int decrypt(void);
void printKeys(void);

//GLOBALS
int64_t p, q, n, totN, e, d;
char *inFile, *outFile;
FILE* input, *output;

//------------------------------------------------------------------------------
