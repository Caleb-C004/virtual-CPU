# virtual-CPU
Virtual java program that emulates a basic memory traversal and cpu runtime 

This program runs on 3 files CPU, Memory, and Stack with an input file input.txt

The CPU files aims to emulate the basic funtions of a cpu through calculating and numbers and sending for memory locations

Memory serves as the holding place for the code from input.txt as well as hosting the main function
The memory has a size of 4096 totoal, with 1024 locations for stack and 3072 for the program
Memory locations 940, 941, and 942(Hexidecimal) are used to store values to be added/substracted etc.

Stack stores the CPU's register states in a stack whenever a subroutine is called for the CPU
The stack memory locations are from 0 to 3FF (1024 locations)

When the code is executed, it will promp for an input file, in this case, input.txt. The code will then execute and output to a textfile output.txt
