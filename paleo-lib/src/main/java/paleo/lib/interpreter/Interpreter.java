package paleo.lib.interpreter;

import java.util.Queue;
import java.util.Stack;

import paleo.lib.token.OperandToken;
import paleo.lib.token.OperationToken;
import paleo.lib.token.Yytoken;

/**
 * Module allowing to evaluate a {@link Queue} of {@link Yytoken} in an infix form
 * (which can be provided by an instance of {@link paleo.lib.parser.Parser}).
 */
public final class Interpreter {

	private Queue<Yytoken> tokens;
	private Stack<OperandToken> operandStack;
	private Stack<OperationToken> operationStack;

	public Interpreter(Queue<Yytoken> tokens) {
		this.tokens = tokens;
		this.operandStack = new Stack<>();
		this.operationStack = new Stack<>();
	}

	/**
	 * Evaluates the {@link Queue} of {@link Yytoken}.
	 *
	 * @return the last {@link OperandToken} of the operandStack.
	 * @throws IllegalArgumentException if the expression is not valid.
	 */
	public OperandToken evaluate() throws IllegalArgumentException {
		Yytoken token;

		while (!tokens.isEmpty()) {
			token = tokens.poll();
			if (token.isAnOperandToken()) {
				operandStack.push((OperandToken) token);
			}
			else {
				operationStack.push((OperationToken) token);
			}
		}

		if (operandStack.isEmpty()) {
			throw new IllegalArgumentException("Empty stack");
		}

		return operandStack.peek();
	}

}
