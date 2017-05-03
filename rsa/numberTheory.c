/***************************************************************************
*	FILE: numberTheory.c
*	AUTHOR: Connor Beardsmore - 15504319
*	UNIT: FCC200
*	PURPOSE: Functionality for basic number theory techniques
*   LAST MOD: 02/05/17
*   REQUIRES: numberTheory.h
***************************************************************************/

#include "numberTheory.h"

//------------------------------------------------------------------------------
//NAME: primalityTest
//IMPORT: prime (int64_t), tests (int)
//EXPORT: isPrime (int)
//PURPOSE: Check if a number is prime or not to some confidence level

int primalityTest( int64_t prime, int tests )
{
	int64_t a;
	int64_t r;
    int64_t exponent;
	int isPrime = TRUE;

	for( int ii = 0; ii < tests; ii++ )
	{
        //calculate r result
		a = ( rand() % prime ) + 1;
		exponent = ( prime - 1 ) >> 1;
		r = modularExpo( a, exponent, prime );

        // if r not 1 or -1 it is 100% not prime
		if( ! ( (r == 1) || (r == (prime-1))) )
			return FALSE;

	}
	return isPrime;
}

//------------------------------------------------------------------------------
//NAME: generatePrime()
//EXPORT: newPrime (int64_t)
//PURPOSE: Generate a random prime number between the two bounds given

int64_t generatePrime( int lower, int upper)
{
	int64_t newPrime;

	do
	{
		newPrime = ( rand() % ( upper - lower) ) + lower;
	}
	while( !primalityTest( newPrime, PRIME_TESTS ) );

	return newPrime;
}

//------------------------------------------------------------------------------
//NAME: modularExpo()
//IMPORT: base (int64_t), exponent (int64_t), modulus (int64_t)
//EXPORT: result (int64_t)
//PURPOSE: Calculae the value base^exponent mod modulus efficiently

int64_t modularExpo(int64_t base, int64_t exponent, int64_t modulus)
{
    int64_t result = 1;
    base = base % modulus;

    //check upper limit
    if ( ( base > LIMIT ) || ( exponent > LIMIT ) || ( modulus > LIMIT ) )
        return -1;

    //anything mod 1 results in 0
    if ( modulus == 1 )
        return 0;

    //loop until all exponents reviewed
    while( exponent > 0 )
    {
    	//check least significant bit
    	if( exponent & 1 )
    		result = ( result * base ) % modulus;

    	//shift exponent to consider next bit
    	exponent >>= 1;
    	base = ( base * base ) % modulus;
    }

    return result;
}

//------------------------------------------------------------------------------
//NAME: extendedEuclid()
//IMPORT: a (int64_t), n (int64_t)
//EXPORT: t (int64_t)
//PURPOSE: Find the inverse modular a number via the extended euclidean algorithm

int64_t extendedEuclid( int64_t a, int64_t n )
{
    int64_t t = 0, newt = 1;
    int64_t r = n, newr = a;
	int64_t q = 0, temp = 0;

    //only applicable if gcd is 1
    if ( findGCD( a, n ) != 1 )
        return -1;

    //perform the actual eea
	while( newr != 0 )
	{
		q =  r / newr;
		temp = t;
		t = newt;
		newt = temp - ( q * newt );
		temp = r;
		r = newr;
		newr = temp - ( q * newr );
	}

    //ensure t is positive
	if( t < 0 )
		t += n;

	return t;
}

//------------------------------------------------------------------------------
//NAME: findGCD()
//IMPORT: a (int64_t), b (int64_t)
//EXPORT: gcd (int64_t)
//PURPOSE: Find greatest common denominator of 2 numbers

int64_t findGCD( int64_t a, int64_t b )
{
	int64_t gcd, quotient, residue;

    //check if either number is 0
    if ( a == 0 )   return b;
    if ( b == 0 )   return a;

    //satisfy the equation A = B * quotient + residue
    quotient = a / b;
    residue = a - ( b * quotient );

    //recursively call gcd
    gcd = findGCD( b, residue );

	return gcd;
}

//------------------------------------------------------------------------------
