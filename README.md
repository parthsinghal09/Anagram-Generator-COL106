# Anagram-Generator

The program prints the anagrams (with a maximum of 2 space in between) for an input string which are present in the vocabulary. The problem statement is described below - 

_"You are given a vocabulary V of (lowercase) English words. It uses letters of English alphabet [a-z], digits [0-9], and the apostrophe symbol [']. No other characters are used in the vocabulary V. Your goal is to print out all valid anagrams of an input string."_

# Anagrams

Two strings are anagrams of each other if by rearranging the letters of one string we can obtain the other. For example, “a bit” is an anagram of “bait”, and “super” is an anagram of “purse”. Note that we can add spaces at will, i.e., we won’t count spaces when matching characters for checking anagrams. Anagrams with a maximum of 2 spaces in them (i.e., three words at most) are generated through this program.

> The O(1) search time complexity of the hash table has been utilized through the implementation of a _Multiset Hash Function_; which uses right bit shift and XOR operations to generate the hash keys, along with _Separate Chaining_ and _Linear Probing_ for collision resolution. The hash table contains the words present in the vocabulary. The program rather than going for a brute force methodology of generating all the anagrams for a string and then searching them in the vocabulary, searches for the list in the hash table which contains all the permutations of the given string present in the vocabulary. After the implementation of this step, the whole problem is just reduced to task of printing those permutations. Various anagrams can also be formed by dividing the string into 3 or less substrings and after that, combining the permutations of those substrings. Here we divide the string into a maximum of 3 substrings in a time complexity of O(3^n), where n is the size of the string. 

# Folders 

- Code 
  - Anagram.java => File with the main function

- Tester 
  - Contains files which test the code 

> Usage - 

> javac Anagram.java

> java Anagram <Vocabulary file (.txt)> <Input file (.txt)>
