/***************************************************************************
*	FILE: numberTheory.h
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Header file for number theory functionality
*   LAST MOD: 02/05/17
*   REQUIRES: stdio.h, stdlib.h, stdint.h, limits.h, math.h
***************************************************************************/

#include <stdlib.h>
#include <stdint.h>
#include <stdio.h>
#include <limits.h>
#include <math.h>

//CONSTANTS
#define PRIME_TESTS 25
#define LOWER_PRIME 1000
#define UPPER_PRIME 10000
#define LIMIT 10000000000

//BOOLEANS
#define TRUE 1
#define FALSE 0

//PROTOTYPES
int primalityTest(int64_t,int);
int64_t generatePrime(int,int);
int64_t modularExpo(int64_t, int64_t, int64_t);
int64_t extendedEuclid(int64_t,int64_t);
int64_t findGCD(int64_t, int64_t);

//------------------------------------------------------------------------------
