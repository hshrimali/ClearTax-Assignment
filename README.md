# Equation Simplifier

  This repository holds the code to extract the data from the input JSON file and then prints it in the form of an equation with 
  brackets. Then it pulls the variable on the LHS and rest operands and operators on the RHS of `equals`. Finally, it computes the
  value of `x`, the variable.
  
## Getting Started

  You need to clone this repository on your system to run the code.
  
#### Prerequisites

  You must have the IDE to run the `.java` file.Then, you should have the latest `jdk` and for the JSON Library, I have already added a folder named `org`.
  
#### Installation
  
  Click on the link to install the latest version of `jdk` :-
  [JDK Installation](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
  
  Click on the link to install the JSON library used :-
  [JSON Library](http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm)
  
## Running the tests

  After all the installations are done, you can test this code. Use below line to compile the `EquationSimplification.java` file
  ```
  javac EquationSimplification.java
  ```
  Then, it will create `.class` file for each class in the `EquationSimplification.java` file. You need to run the `.class' file 
  of the Main class which is again **EquationSimplification**. So, write the below code to run it.
  ```
  java EquationSimplification
  ```
  You can see there are 3 `.json` files. So out of them, currently I have used the `EquationData.json` file for testing purpose.
  So, the output will be the following :-
  ```
  ((x-4)+5)*3=21
  x=((21-3)/5)+4
  Value of x = 7
  ```
  If you want to test the code on the other two json files. You can change the `EquationData.json` to `EquationData2.json` or
  `EquationData3.json` in the following line in the code.
  ```
  JSONObject ob = (JSONObject)parser.parse(new FileReader("EquationData.json"));
  ```
  You can find this line on the 6th line of the `main` function.
  
  Output for `EquationData2.json` will be :-
  ```
  (x*5)+3=21
  x=(21-3)/5
  Value of x = 3
  ```
  
  Output for `EquationData3.json` will be :-
  ```
  x+3=21
  x=21-3
  Value of x = 18
  ```
