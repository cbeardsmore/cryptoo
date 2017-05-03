/***************************************************************************
*	FILE: main.h
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Header file for rsa
*   LAST MOD: 02/03/17
*   REQUIRES: numberTheory.h
***************************************************************************/

#include "numberTheory.h"
#include <time.h>
#include <string.h>

//PROTOTYPES
int64_t generateE(int64_t);
void generateKeys(void);
void printvals(void);
void readArgs(int, char**);
void readFilepath(char**, char*);
char* readLine(FILE*);
void populate();
void readKeysFromFile(char*);
void help(void);
int encrypt(void);
int decrypt(void);
void printKeys(void);

//GLOBALS
int64_t p, q, n, totN, e, d;
int (*modeFunc)(void);
char *inFile, *outFile;
FILE* input, *output;

//------------------------------------------------------------------------------
