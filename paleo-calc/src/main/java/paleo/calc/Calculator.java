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
	private final Scanner sc;
	private static Calculator singleton;

	private Calculator () {
		this.sc = new Scanner (System.in);
	}

	public static Calculator instance (){
		if (singleton == null)
			singleton = new Calculator ();
		return singleton;
	}


	public void evaluate (){
		String inputExpression = sc.nextLine();
		Optional<Queue<Yytoken>> tokenExpression = new Parser (inputExpression).parse();

		if (tokenExpression.isPresent()){
			Interpreter interpreteur = new Interpreter(tokenExpression.get());
			System.out.println(interpreteur.evaluate());
		}
	}

	public static void main (String[] args) {
		while (true){
			instance().evaluate();
		}
	}

}
