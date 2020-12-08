package paleo.lib.interpreter;

import java.util.HashMap;

import paleo.lib.token.OperandToken;
import paleo.lib.token.OperationToken;

/**
 * Static module providing a dictionary for getting an {@link OperationEvaluator}
 * implementation corresponding to the given {@link OperationToken}
 * and {@link OperandToken}.
 *
 * @note Any new types will have to add in the dictionary the different
 * implementations corresponding to the operations wanted to be supported.
 */
public final class OperationDictionary {

	/**
	 * Stores all {@link OperationEvaluator}.
	 */
	private static HashMap<OperationToken,
							HashMap<Class <? extends OperandToken>,
									HashMap<Class <? extends OperandToken>,
										 OperationEvaluator>>>
							operationMap = new HashMap<>();

	/**
	 * Adds an {@link OperationEvaluator} to the corresponding {@link HashMap}
	 * in operationMap.
	 *
	 * @param operation is the {@link OperationToken} implemented by the opEvaluator.
	 * @param op1 is the {@link Class} of the left operand.
	 * @param op2 is the {@link Class} of the right operand.
	 * @param opEvaluator is the implementation of the operation.
	 */
	public static void addEntry(
			OperationToken operation,
			OperationEvaluator opEvaluator,
			Class<? extends OperandToken> ... signature)
	{
		if (null == operationMap.get(operation)) {
			operationMap.put(operation, new HashMap<>());
		}
		if (null == operationMap.get(operation).get(signature[0])) {
			operationMap.get(operation).put(signature[0], new HashMap<>());
		}

		operationMap.get(operation).get(signature[0]).put(signature[1], opEvaluator);
	}

	/**
	 * Get the implementation of {@link OperationEvaluator} corresponding to the given
	 * operation and operands types.
	 *
	 * @param operation is the wanted {@link OperationToken}.
	 * @param op1 is the {@link Class} of the left operand.
	 * @param op2 is the {@link Class} of the right operand.
	 * @return the corresponding implementation of the operation if its
	 * provided, otherwise, throw an {@link IllegalArgumentException}.
	 */
	public static OperationEvaluator getOperationEvaluator(
			OperationToken operation,
			Class<? extends OperandToken> op1,
			Class<? extends OperandToken> op2)
	{
		if (!operationMap.containsKey(operation)) {
			throw new IllegalArgumentException(
					operation.toString() + " unsupported operation"
			);
		}
		if (!operationMap.get(operation).containsKey(op1)) {
			throw new IllegalArgumentException(
					operation.toString()
					+ " unsupported operation for the operand '"
					+ op1.toString()
					+ "'"
			);
		}
		if (!operationMap.get(operation).get(op1).containsKey(op2)) {
			throw new IllegalArgumentException(
					operation.toString() + " "
					+ "unsupported operation for the operand '"
					+ op1.toString()
					+ "' and '"
					+ op2.toString()
					+ "'"
			);
		}
		return operationMap.get(operation).get(op1).get(op2);
	}
}
