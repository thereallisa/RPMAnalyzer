RPMAnalyzer
==============

Quickly written Java utility that traverses through log files in a given directory and prints out the max and average requests received by minute and by second.

Usage
-----
In order for the program to run, you will need to specify the following parameter as a command line argument:
* Path to the directory containing your log files 

Example
-------
$ javac Analyze.java
$ java Analyze "/Users/thereallisa/Desktop/myLogFiles"
per minute  max = 81; average = 12
per second  max = 8; average = 1