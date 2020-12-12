package paleo.lib.interpreter;

import java.util.Queue;
import java.util.Stack;
import java.util.List;

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

	/**
	 * Interpreter constructor.
	 *
	 * @param tokens Is the token representation of an infix expression.
	 */
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
		OperationToken operationToken;

		while (!tokens.isEmpty()) {
			token = tokens.poll();

			if (token.isAnOperandToken()) {
				operandStack.push((OperandToken) token);
			}
			else {
				operationToken = (OperationToken) token;
				switch (operationToken){
					case LPAREN : operationStack.push(operationToken); break;
					case RPAREN : 
						while (OperationToken.LPAREN != operationStack.peek()) 
							evaluateOperation();
						operationStack.pop();
						break;
					default :
						while (!operationStack.isEmpty() && operationToken.getPriority() <= operationStack.peek().getPriority())
							evaluateOperation();
						operationStack.push(operationToken);
						break;
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
		OperandToken op1;
		OperandToken op2;

		if (2 > operandStack.size()) {
			throw new IllegalArgumentException("Not enough operands");
		}

		if (operationStack.isEmpty()) {
			throw new IllegalArgumentException("Not enough operations");
		}

		op2 = operandStack.pop();
		op1 = operandStack.pop();

		operandStack.push(
			OperationDictionary.getOperationEvaluator(
				operationStack.pop(), List.of(op1.getKey(),op2.getKey())
			).evaluateOperation(op1, op2)
		);
	}
}
