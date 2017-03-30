# Makefile for Affine Cipher
# FCC200 Assignment
# Last Modified: 24/03/17
# Connor Beardsmore - 15504319

# MAKE VARIABLES
EXEC1 = affine
OBJ1 = affine.o keyEligible.o
CFLAGS = -Wall -Wextra -std=c99
CC = gcc


# RULES + DEPENDENCIES
$(EXEC1) : $(OBJ1)
	$(CC) $(OBJ1) -o $(EXEC1) $(CFLAGS)

affine.o : affine.c affine.h keyEligible.h
	$(CC) -c affine.c $(CFLAGS)

keyEligible.o : keyEligible.h
	$(CC) -c keyEligible.c $(CFLAGS)

clean:
	rm -f $(EXEC1) $(OBJ1) *.gch
