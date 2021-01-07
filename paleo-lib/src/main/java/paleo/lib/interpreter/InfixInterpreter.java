package paleo.lib.interpreter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;
import paleo.lib.token.Yytoken;
import paleo.lib.token.operand.OperandToken;
import paleo.lib.token.operation.OperationToken;
import paleo.lib.token.operation.ParenOperationToken;

/**
 * {@link InfixInterpreter} is an {@link Interpreter} implemenation allowing to
 * evaluate a {@link Queue} of {@link Yytoken} in an infix form (which can be
 * provided by an instance of {@link paleo.lib.parser.JFLexParser}).
 */
public final class InfixInterpreter implements Interpreter {

	private final Queue<Yytoken> tokens; ///< Is the tokens to evaluate.
	private final Stack<OperandToken> operandStack; ///< Stores {@link OperandToken} during the evaluation.
	private final Stack<OperationToken> operationStack; ///< Stores {@link OperationToken} during the evaluation.

	/**
	 * {@link InfixInterpreter} constructor.
	 *
	 * @param tokens Is the token representation of an infix expression.
	 */
	public InfixInterpreter(final Queue<Yytoken> tokens) {
		this.tokens = tokens;
		this.operandStack = new Stack<>();
		this.operationStack = new Stack<>();
	}

	/**
	 * Evaluates the {@link Queue} of {@link Yytoken} using two {@link Stack}.
	 *
	 * @note used algorithm can be found at
	 * https://algorithms.tutorialhorizon.com/evaluation-of-infix-expressions/
	 *
	 * @return the last {@link OperandToken} of the operandStack which
	 * corresponds to the result of the evaluation.
	 * @throws IllegalArgumentException if the expression is not valid.
	 */
	public OperandToken evaluate() throws IllegalArgumentException {
		Yytoken token;
		OperationToken operationToken;

		while (!tokens.isEmpty()) {
			token = tokens.poll();

			if (token.isAnOperandToken()) {
				operandStack.push((OperandToken) token);
			} else {
				operationToken = (OperationToken) token;
				if (operationToken.equals(ParenOperationToken.LEFT)) {
					operationStack.push(operationToken);
				} else if (operationToken.equals(ParenOperationToken.RIGHT)) {
					while (!operationStack.peek().equals(ParenOperationToken.LEFT)) {
						evaluateOperation();
					}
					operationStack.pop();
				} else {
					while (
						!operationStack.isEmpty() &&
						operationToken.getPriority() <=
						operationStack.peek().getPriority()
					) {
						evaluateOperation();
					}
					operationStack.push(operationToken);
				}
			}
		}

		while (!operationStack.isEmpty()) {
			evaluateOperation();
		}

		if (operandStack.isEmpty()) {
			throw new IllegalArgumentException("Missing operands");
		}

		return operandStack.peek();
	}

	private void evaluateOperation() throws IllegalArgumentException {
		OperationToken operation;
		Deque<OperandToken> operandsDeque;

		if (operationStack.isEmpty()) {
			throw new IllegalArgumentException("Not enough operations");
		}

		operation = operationStack.pop();
		if (operation.getArity() > operandStack.size()) {
			throw new IllegalArgumentException("Not enough operands");
		}

		operandsDeque = new ArrayDeque<OperandToken>();
		for (int i = 0; i < operation.getArity(); i++) {
			operandsDeque.push(operandStack.pop());
		}

		operandStack.push(
			OperationDictionary
				.getOperationEvaluator(operation, operandsDeque)
				.evaluateOperation(operandsDeque)
		);
	}

	/**
	 * {@link InfixInterpreter.Factory} implements the {@link Interpreter.Factory} interface
	 * in order to be used in composition in a {@link Calculator} instance.
	 */
	public static class Factory implements Interpreter.Factory {

		public InfixInterpreter create(final Queue<Yytoken> tokens) {
			return new InfixInterpreter(tokens);
		}
	}
}
