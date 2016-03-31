# +---------------------------------------------------+
# | FILE   : Makefile                                 |
# | AUTHOR : Jeffrey Hunter                           |
# | WEB    : http://www.iDevelopment.info             |
# | DATE   : 27-AUG-2002                              |
# | NOTE   : Makefile for Linked List data structure. |
# +---------------------------------------------------+

CC=gcc

testLinkedList: testLinkedList.o list.o list.h
	$(CC) testLinkedList.o list.o -o $@

clean:
	rm -f a.out core *.o testLinkedList

