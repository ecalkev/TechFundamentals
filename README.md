Assignment 1 contains two parts: Typoglycemia and MarkVShaney.

The purpose of Typoglycemia was to build a tool that jumbles the words in a text file. By running Typoglycemia.java you will be asked if you have a text file you want scrambled, if not press enter and the default will be used. The output will be printed to output.txt unless you provide the name of a file you wish to use.
In an older version of the assignment I jumbled the text file by taking each line in at a time and splitting the component words into a String ArrayList and ran the jumbling on each word.
In the later version I tried to make the algorithm more efficient by taking each line as a char array and doing the jumbling of words in place on the array. This removed overhead of creating Strings for each word.

The purpose of MarkVShaney was to build a tool that can read a text file and based on a prefix length (number of preceding words) could obtain the follower words for all prefixes of the length. This prefix to follower map is used to generate randomly generated text based on randomly selecting followers for a randomly selected prefix.
By running MarkVShaney.java you will be asked for a text file you wish to gather all the prefix-follower data, the file you wish for the randomly generated sentences to be printed to, the length of the prefix you wish the programme to use (the smaller the number, the less likely the sentence will make sense), and the number of words you wished to be printed to the output file.

Assignment 2 contains two parts: DirectMappedCache and FullyAssociativeCache

The purpose of this asssignment was to create the two caches for Hexadecimal addresses. Both caches will generate a cache with randomly generated values for the cache entries. The user can provide a text file to test the caches on. This must consist of each address on separate lines. There is a default provided that contains several addresses.
When the DirectMappedCache is ran the results are printed as hit/miss for the addresses in the file: "DirectCacheHits.txt"
When the FullyAssociativeCache is ran the results are printed as hit/miss for the addresses in the file: "AssociativeCacheHits.txt"
