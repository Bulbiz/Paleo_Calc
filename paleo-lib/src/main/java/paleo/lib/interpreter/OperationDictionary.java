package paleo.lib.interpreter;

import java.util.HashMap;

import paleo.lib.token.OperandToken;
import paleo.lib.token.OperationToken;

/**
 * Static module providing a dictionary for getting a {@link OperationEvaluator}
 * implementation corresponding to the given {@link OperationToken}
 * and {@link OperandToken}.
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

	public static void addEntry(
			OperationToken operation,
			Class<? extends OperandToken> op1,
			Class<? extends OperandToken> op2,
			OperationEvaluator opEvaluator)
	{
		//TODO: need to verify existence before adding.

		if (null == operationMap.get(operation)) {
			operationMap.put(operation, new HashMap<>());
		}
		if (null == operationMap.get(operation).get(op1)) {
			operationMap.get(operation).put(op1, new HashMap<>());
		}

		operationMap.get(operation).get(op1).put(op2, opEvaluator);
	}

	public static OperationEvaluator getOperationEvaluator(
			OperationToken operation,
			Class<? extends OperandToken> op1,
			Class<? extends OperandToken> op2)
	{
		if (!operationMap.containsKey(operation)) {
			throw new IllegalArgumentException(operation.toString() + " unsupported operation");
		}
		if (!operationMap.get(operation).containsKey(op1)) {
			throw new IllegalArgumentException(
					operation.toString() + " unsupported operation for the operand '" + op1.toString() + "'"
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
