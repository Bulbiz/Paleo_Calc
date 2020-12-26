package paleo.lib.interpreter;

import java.util.Deque;
import paleo.lib.token.operand.OperandToken;

/**
 * {@link FunctionalInterface} used for implements {@link OperationToken} in
 * {@link OperationDictionary}.
 */
@FunctionalInterface
public interface OperationEvaluator {
	/**
	 * Evaluates an operation according a queue of operands.
	 *
	 * @note {@link Deque} is used in order to keep the right operand "position" by using
	 * a single `pop` call.
	 *
	 * @param operands is a {@link Deque} of {@link OperandToken} used for the evalutation.
	 * @return the resulting {@link OperandToken}.
	 */
	public OperandToken evaluateOperation(Deque<OperandToken> operands);
}
