package paleo.calc;

import java.util.HashMap;

import paleo.lib.token.DoubleOperandToken;
import paleo.lib.token.IntegerOperandToken;
import paleo.lib.token.OperandToken;

/**
* Calculator
*/
public class Calculator {

	public static void main (String[] args) {
		HashMap<Class<? extends OperandToken>, String> map = new HashMap<>();

		map.put(IntegerOperandToken.class, "IntegerOperandToken");
		map.put(DoubleOperandToken.class, "DoubleOperandToken");
		OperandToken op1 = new IntegerOperandToken(4);
		OperandToken op2 = new DoubleOperandToken(4);

		System.out.println("op1 = " + map.get(op1.getClass()));
		System.out.println("op2 = " + map.get(op2.getClass()));
	}
}
