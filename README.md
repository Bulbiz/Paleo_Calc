# Paleo

An evolved calculator written in Java.

## Dependencies

You need to have :

* `maven` >= 4.0
* `make`
 
## Build

To compile the project, just run

```zsh
$ make
```

In order to generate documentation, run

```zsh
$ make doc
```

For cleaning, run

```zsh
$ make clean
```

## Run

For launching the calculator, run

```zsh
$ make run
```

or

```zsh
$ java -jar paleo-calculator.jar
```

## Basic use of the calculator

The calculator use the infix syntax and not the RPN syntax.
For example : 
` 5 + 5 ` is correct but
` 5 5 + ` doesn't work (it will show an error) 

Some other example that work :
` (5 + 10) - 10 * 54 `
` 5 `
` -5 + 10 `

The basic procedure of the calculator is :
	- The application wait a input
	- A parser parse the input into a list of Token 
	- A Interpreter calculate the output from the list of Token given by the parser
	- The application print the output

### Fonctionality/Extension 
## Extension 1 : Calculator Multi-Type

The calculator can be used with multiple type. 
For now, the type that are implemented are :

# Real Number 

The application recognize a real number if it is only composed of number
Example : `5` ; `78` ; `0` ; ...
The operation that can be used are : `+` | `-` | `/` | `*`
Example : ` 5 + 5` ; `(-5 + 8) * 6 ` ; ...

# Decimal Number 

The application recognize a decimal number if it start with number and has one and only '.'
Exemple : `5.` ; `78.64` ; `0.0` ; ...
The operation are the same as the Real Number (`+`| `-` | `/` | `*`)
We can mix Decimal Number and Real Number in a expression
Example : ` 5 + 5.4 ` ; ` (4.0 + 8.0) * 6 ` ; ...


# Boolean 

The application recognize a boolean if it's either "true" or "false"
The operation that can be used are : `and` | `or` | `not`
Example : ` true and not (true or false) ` ; ` true ` ; ` false ` ; ...

# Set

The application recognize a set if it start with '{' then somes values 
(can be real, decimal or boolean) separated with ' ' or ';' or ',' and end with '}'.
Exemple : ` { 1 ; 2.0 ; 3 ; true } ` ; `{}` ; `{ false }` ; ...
The operation that can be used are : `union` | `inter` | `diff`
Example : `{1} union {5;6}` ; `{false ; 1.0} inter {true ; false}` ; `{1;2;3;4;5;6} diff ({1} union {3;5})`

# How does it work ?

The calculator use a map of <signature;evaluator>. The interpretor first calculate the key from the operation and 
the operands (which can be viewed as the signature) and search if there is a evaluator with the signature to evaluate it.
Each new type that are implemented need to add their evaluator with the corresponding signature. 
This way, the operator can be differents types and the clients can add as much types they want easily.

## Extension 2 : Infix Syntax 

As it has been stated in the "Basic use of the calculator" section, the calculator use a infix syntax instead of
the RPN syntax.

# How does it work ?

The Interpretor just use the algorithm for the evaluation of infix expression. 
You can find the documentation here : https://algorithms.tutorialhorizon.com/evaluation-of-infix-expressions/ 
With the actual architecture of the program, it should be possible to create an RPNInterpretor instead of the 
InfixInterpretor to transform the infix calculator into a RPN calculator.

## Extension 3 : Historic

The calculator have an historic which can be used to recall old value and reuse it for other expression.

Every time a output is calculated, the historic will retains the value in the index that is writted before the output.
Example : `(2) : {5; 6; 7; 2; 1}` means that `{5; 6; 7; 2; 1}` is stored in `hist(2)`

To recall an old value, use `hist(` index_wanted `)`
Example : `hist(2)` will recall the value stored in hist(2)

It's possible to use the recalled value to calculate a new expression :
Example : `hist(2) + 5.0` work (if the value in hist(2) is either a real or a decimal number of course) 
 
To list all the value that have been stored in the historic, use the command `ls`
To print the documentation of the historic, use the command `help`

# How does it work ?

The historic manager use an Arraylist. Each time an ouput have been calculated in the application, it will also be added
in the ArrayList of the historic manager. When recalled, it will create a special historic token that will substitute itself with
the value stored in the historic manager.

### Package 
paleo-lib : This package contains all the implementation of the calculator. It have the implementation of the multi-type,
the interpreter, parser , ect ... His basic rôle is to calculate an output from an input (String) 

paleo-demo : This package is the entry point of the program. This is were the input is entered, and where the output given by
the calculator is printed.

We have separated these two differents module so that for example if a client would want to build a graphics calculator based 
from our calculator, we can easily remove the paleo-demo module that give a text interface with a graphic one. 







