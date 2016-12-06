SMPL Interpreter
================

Example of compile command from src folder on a Linux system
`javac -cp .:/usr/share/java/cup.jar **/*.java`

Example of run command from src folder on a Linux system
`java -cp .:/usr/share/java/cup.jar smpl.sys.Repl test.smpl`


Completed
---------

* take name of file to interpret as argument
* binary operators require white space on both sides (more to be implemnted)
* + and - are allowed in variable names (perhaps other bitwise opertors should be allowed too?)
* integers (positive, negative, hexadecimal (#x), binary(#b))
* double precision floating point numbers (positive, negative, whole.fraction, whole., .fraction)
* strings, escape sequences ('\n', '\t', '\\\\', '\"'), string concatenation (+)
* character literals (eg, #ca or #c0061)
* boolean constants #t and #f
* pairs
* built in pair, car, cdr and pair? functions
* assignment operator :=
* arithmetic operators +, -, *, /, %, ^
* relational operators =, >, <, <=, >=, !=
* logical operators and, or, not
* negative expressions
* { ... }
* comments

TO DO
-----

* lists and built in list functions
* vectors and built in vector functions
* eqv? and equal?
* substr
* bitwise operators &, |, ~
* list concatenation @
* order of precedence ~ -> *, /, % -> +, - -> &, | -> =, >, <, <=, >=, != -> not -> and -> or
	* currently ^ -> *, /, % -> +, - -> =, >, <, <=, >=, != -> not -> and -> or
* proc, call, lazy, let, def
* n-valued result
* assign expression to multiple variables
* if `expr` then `expr` [else `expr`]
* case { [p1:c1, ..., pn:cn] } `expr` : `expr`
* multi-valued expression
* print, println
* read, readint

Optional (with suggested implementation)
----------------------------------------

* lazy evaluation
* dynamic scoping
* reference parameters (as opposed to call by value)
* graphics

Optional (without suggested implementation)
-------------------------------------------

* arbitrary precision integer arithmetic
* user defined records (like structs)
* additional control structures
* exceptions
* macros
* call-by-name
* threads

