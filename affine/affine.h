/***************************************************************************
*	FILE: affine.h
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Header file for affine cipher
*   LAST MOD: 11/03/17
*   REQUIRES: stdio.h, ctype.h, stdlib.h, string.h, keyEligible.h
***************************************************************************/

#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include "keyEligible.h"

// PROTOTYPES
char encrypt(char,int,int);
char decrypt(char,int,int);

// CONSTANTS
#define ARGS 6
#define ALPHABET 26

//------------------------------------------------------------------------------
