SMPL INTERPRETER
================

Completed
---------

* binary operators require white space on both sides (more to be implemnted)
* + and - are allowed in variable names (perhaps other bitwise opertors should be allowed too?)
* integers (positive, negative, hexadecimal (#x), binary(#b))
* double precision floating point numbers (positive, negative, whole.fraction, whole., .fraction)
* strings, escape sequences ('\n', '\t', '\\\\', '\"'), string concatenation (+)
* character literals (#c{char} or #c{4-digit hex unicode})
* boolean constants #t and #f
* pairs
* built in pair, car, cdr and pair? functions
* assignment operator :=
* arithmetic operators +, -, *, /, %, ^
* negative expressions
* { ... }

TO DO
-----

* lists and built in list functions
* vectors and built in vector functions
* eqv? and equal?
* substr
* bitwise operators &, |, ~
* relational operators =, >, <, <=, >=, !=
* logical operators and, or, not
* list concatenation @
* order of precedence ~ -> *, /, % -> +, - -> &, | -> =, >, <, <=, >=, != -> not -> and -> or
	* currently ^ -> *, /, % -> +, -
* proc, call, lazy, let, def
* n-valued result
* assign expression to multiple variables
* if <expr> then <expr> [else <expr>]
* case { [p1:c1, ..., pn:cn] } <expr> : <expr>
* multi-valued expression
* print, println
* read, readint
* comments

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
* call by name
* threads

