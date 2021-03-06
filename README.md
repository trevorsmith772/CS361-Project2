# Project 2: Nondeterministic Finite Automata

* Author: Trevor Smith, Brandon Mattaini, Berto Cisneros
* Class: CS 361, Section 1 (Trevor & Berto), Section 2 (Brandon)
* Semester: Spring 2021


## Overview

This program is an implementation of a Nondeterministic finite automaton. The program takes in a file and reads the states, alphabet, and transitions, and determines whether specific input strings are valid or not.
Additionally, this program computes an equivalent DFA to a NFA’s instance.

## Compiling and Using

In order to compile this program, make sure you are in the parent directory containing this README file. Once you are at this directory, run the command shown below to compile the whole package:

javac fa/nfa/NFADriver.java

Then to run the program, run the following command, where \<input file> is the file (and filepath) that the program will run.

java fa.nfa.NFADriver \<input file>


## Discussion

This project overall was not too bad. I would say that implementing the search algorithms is where the most difficulty came from. Most of the project went very smoothly without any problems, but we had few issues that took more time to solve which are outlined below:

As expected, the getDFA() method presented the most issues. Having familiarity with DFS and BFS searches from CS 221 helped a lot when implementing this method and the search() method for eclosure(). Most of the trouble with these two methods came from design, where we struggled to figure out how to even structure these methods. To figure out how these methods would work, we basically referred to previous homework assignments and outlined the steps on paper to convert an NFA to DFA and the process for finding eclosure. Writing this out on paper helped as we could then kind of convert those steps into code. Upon completing the two searches, we still had some bugs that took us a while to solve as we were thinking they existed within our search algorithm, but they were actually in much simpler methods that required simple fixes. Because of this, I wish we had developed another driver class for unit testing to ensure our smaller methods were working correctly. This would have made developing the search methods much easier as well, since we wouldn't have been trying to fix bugs that we thought were in a different location. 

Overall though, we thought this project wasn't as bad as we were expecting, but it still presented challenges. As stated before, having familiarity with searches from the Circuit Tracer project in CS 221 helped a lot, as that program included similar concepts.

## Testing

For testing this project, we compiled and ran the NFADriver class with the 4 given test files to be able to compare our results. We didn't add anymore tests this time, as we felt fairly confident that the given tests covered most cases. If we were to change one thing in our development process, it would have been to do more testing on smaller methods to ensure they were working correctly.