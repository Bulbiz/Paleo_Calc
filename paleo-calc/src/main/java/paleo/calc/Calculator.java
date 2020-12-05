package paleo.calc;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Queue;
import java.util.Optional;

import paleo.lib.interpreter.Interpreter;
import paleo.lib.parser.Parser;

import paleo.lib.token.Yytoken;
import paleo.lib.token.DoubleOperandToken;
import paleo.lib.token.IntegerOperandToken;
import paleo.lib.token.OperandToken;

/**
* Calculator
*/
public final class Calculator {
	private static final Scanner sc = new Scanner (System.in);

	public static void evaluate (){
		String inputExpression = sc.nextLine();
		Optional<Queue<Yytoken>> tokenExpression = new Parser (inputExpression).parse();

		if (tokenExpression.isPresent()){
			Interpreter interpreteur = new Interpreter(tokenExpression.get());
			System.out.println(interpreteur.evaluate());
		}
	}

	public static void main (String[] args) {
		while (true){
			Calculator.evaluate();
		}
	}

}
