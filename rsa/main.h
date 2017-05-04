/***************************************************************************
*	FILE: main.h
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Header file for main RSA implementation
*   LAST MOD: 02/05/17
*   REQUIRES: time.h, string.h numberTheory.h
***************************************************************************/

#include <time.h>
#include <string.h>
#include "numberTheory.h"

//FUNCTION POINTER FOR MODE (ENCRYPT/DECRYPT)
typedef int(*FuncPtr)(FILE*,FILE*);

//CONSTANTS
#define PLAIN_BYTES 2
#define CIPHER_BYTES 4

//PROTOTYPES
int64_t generateE(int64_t);
void generateKeys(void);
void printvals(void);
FuncPtr readArgs(int,char**);
char* readLine(FILE*);
int encrypt(FILE*,FILE*);
int decrypt(FILE*,FILE*);
void printKeys(void);

//GLOBALS
int64_t p, q, n, totN, e, d;
char *inFile, *outFile;

//------------------------------------------------------------------------------
