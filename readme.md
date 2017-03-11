SMPL Interpreter
================

Example of compile command from src folder on a Linux system
```
javac -cp .:/usr/share/java/cup.jar **/*.java
```

Example of run command from src folder on a Linux system
```
java -cp .:/usr/share/java/cup.jar smpl.sys.Repl test.smpl
```


Completed
---------

* take name of file to interpret as argument
* binary operators require white space on both sides (more to be implemnted)
* `+` and `-` are allowed in variable names (perhaps other bitwise opertors should be allowed too?)
* integers (positive, negative, hexadecimal `#x`, binary `#b`)
* double precision floating point numbers (`positive`, `negative`, `whole.fraction`, `whole.`, `.fraction`)
* strings, escape sequences (`\n`, `\t`, `\\`, `\"`), string concatenation (`+`)
* character literals (eg, `#ca` or `#c0061`)
* boolean constants `#t` and `#f`
* pairs
* built in `pair`, `car`, `cdr` and `pair?` functions
* assignment operator `:=`
* arithmetic operators `+`, `-`, `*`, `/`, `%`, `^`
* bitwise operators `&`, `|`, `~`
* relational operators `=`, `>`, `<`, `<=`, `>=`, `!=`
* logical operators `and`, `or`, `not`
* order of precedence `^` -> `~` -> `*`, `/`, `%` -> `+`, `-` -> `&`, `|` -> `=`, `>`, `<`, `<=`, `>=`, `!=` -> `not` -> `and` -> `or`
* negative expressions
* { ... }
* comments
* `print`, `println`
* lists
* `list` function and square bracket notation
* `let`, `proc`
* n-valued result
* assign expression to multiple variables
* `substr`
* `eqv?` and `equal?`
* `call`, `lazy`, `def`
* list concatenation `@`
* `read`, `readint`
* `if <expr> then <expr> [else <expr>]`
* vectors and built in vector functions

* `case { [p1:c1, ..., pn:cn] } <expr> : <expr>`


TO DO
-----

* trig functions sine, cosine, tan, sec, cosec, cot evaluations
* fraction evaluation (data type)
* quadratics
* polynomials
* Differentiation
* Limits
* Integration
* exponent (e^x)
* logarithm



