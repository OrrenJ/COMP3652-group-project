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

TO DO
-----

* vectors and built in vector functions
* `if <expr> then <expr> [else <expr>]`
* `case { [p1:c1, ..., pn:cn] } <expr> : <expr>`
* `read`, `readint`

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

