package paleo.lib.interpreter;

import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;

import paleo.lib.token.IntegerOperandToken;
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
	 * Evaluates the {@link Queue} of {@link Yytoken} using two {@link Stack}.
	 *
	 * @note Used algorithm can be found at
	 * https:/}/algorithms.tutorialhorizon.com/evaluation-of-infix-expressions/
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
				OperationToken operationToken = (OperationToken) token;

				if (OperationToken.LPAREN == operationToken) {
					operationStack.push(operationToken);
				}
				else if (OperationToken.RPAREN == operationToken) {
					while (OperationToken.LPAREN != operationStack.peek()) {
						evaluateOperation();
					}
					operationStack.pop();
				}
				else {
					while (
						!operationStack.isEmpty()
						&& operationToken.getPriority() <= operationStack.peek().getPriority()
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
			throw new IllegalArgumentException("Empty stack");
		}
		return operandStack.peek();
	}

	private void evaluateOperation() throws IllegalArgumentException {
		if (2 > operandStack.size()) {
			throw new IllegalArgumentException("Not enough operands");
		}

		if (operationStack.isEmpty()) {
			throw new IllegalArgumentException("Not enough operations");
		}

		OperandToken op2 = operandStack.pop();
		OperandToken op1 = operandStack.pop();

		operandStack.push(
			OperationDictionary.getOperationEvaluator(
				operationStack.pop(),
				op1.getClass(),
				op2.getClass()
			).evaluateOperation(op1, op2)
		);
	}
}
