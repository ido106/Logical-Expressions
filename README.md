# Logical Expressions

OOP course assignment in Java

implementation of a system that can represent nested logical expressions that include variables, evaluate their truth values for specific variable assignments, convert them, and simplify the results by removing "redundant" parts.

In doing so we will work in a recursive framework, see some more examples of polymorphism, and practice the use of inheritance and class hierarchies for sharing common code.

Our goal is to represent logical expressions such as: ~((T & (x | y))^x)

Where T is a value of "True", the ~,|,&,^, symbols denotes the "not","or","and" and "xor" operators respectively, and x and y are variables.
<p align="center">
  <img 
    src="https://user-images.githubusercontent.com/92651125/155814934-29d234ba-ec83-4e84-995f-2b4fe20c42e5.svg"
  >
</p>

Class Hierarchy:

<p align="center">
  <img 
    src="https://user-images.githubusercontent.com/92651125/155814791-ea39188f-e40c-4ad2-8cfe-70fead9c46af.svg"
  >
</p>

Example:  
Making an expression
```
Expression e2 = new Xor(new And(new Var("x"), new Var("y")), new Val(true));

String s = e2.toString();
System.out.println(s);

Should print ((x & y) ^ T)
```

Evaluate its value for a given variable assignment to values:
```
Map<String, Boolean> assignment = new TreeMap<>();
assignment.put("x", true);
assignment.put("y", false);

Boolean value = e2.evaluate(assignment);
System.out.println("The result is: " + value);

Should print: The result is: true
```
