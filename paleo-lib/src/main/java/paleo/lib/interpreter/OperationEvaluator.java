package paleo.lib.interpreter;

import paleo.lib.token.OperandToken;

/**
 * {@link FunctionalInterface} used for implements {@link OperationToken} in
 * {@link OperationDictionary}.
 */
@FunctionalInterface
public interface OperationEvaluator {

	/**
	 * Evaluates an operation according two operands.
	 *
	 * @param op1 is the left operand.
	 * @param op2 is the right operand.
	 * @return the resulting operand.
	 */
	 public OperandToken evaluateOperation(OperandToken op1, OperandToken op2);
}
