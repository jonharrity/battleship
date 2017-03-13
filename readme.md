#Battleship AI

For information about the gameplay and rules followed by this project, see the wikipedia page at:
https://en.wikipedia.org/wiki/Battleship_(game)

This project implements the classic Battleship game. An AI uses probability to calculate the likeliness of an enemy ship occupying a location on their grid, and every turn will shoot at the most likely spot. 

engine/ProbabilityStrategy.java contains the actual AI class. The script in engine/StrategyDriver.java runs the AI (by default) 1000 times. 

To quickly compile & run the code to test the AI, use these commands from the main folder after cloning: 

javac engine/*  \n
java engine/StrategyDriver


Running with 500000 games produced the output:

--Player evaluator--  \n
Games: 10000  \n
Min turns: 19  \n
Max turns: 71  \n
Average turns: 44.0981  \n
Time took: 6465 milliseconds 



This project was outlined by M. Gerb, Centennial High School.  \n
The code and AI was written by Jon Harrity.
