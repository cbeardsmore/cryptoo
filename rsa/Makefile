# Makefile For RSA Implementation
# FCC200 Assignment
# Last Modified: 02/05/17
# Connor Beardsmore - 15504319

# MAKE VARIABLES
CC=gcc
CFLAGS=-std=c99 -Wall -pedantic -g
EXEC=rsa
OBJ=main.o numberTheory.o

# RULES + DEPENDENCIES
$(EXEC): $(OBJ)
	$(CC) -o $(EXEC) $(OBJ)

main.o: main.c main.h
	$(CC) $(CFLAGS) -c -o main.o main.c

numberTheory.o: numberTheory.c numberTheory.h
	$(CC) $(CFLAGS) -c -o numberTheory.o numberTheory.c

clean:
	rm -r $(OBJ) $(EXEC)
