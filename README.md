Assignment 1 contains two parts: Typoglycemia and MarkVShaney.

The purpose of Typoglycemia was to build a tool that jumbles the words in a text file. By running Typoglycemia.java you will be asked if you have a text file you want scrambled, if not press enter and the default will be used. The output will be printed to output.txt unless you provide the name of a file you wish to use.
In an older version of the assignment I jumbled the text file by taking each line in at a time and splitting the component words into a String ArrayList and ran the jumbling on each word.
In the later version I tried to make the algorithm more efficient by taking each line as a char array and doing the jumbling of words in place on the array. This removed overhead of creating Strings for each word.
