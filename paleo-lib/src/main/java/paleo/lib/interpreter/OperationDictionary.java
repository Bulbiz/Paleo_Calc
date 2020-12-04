package paleo.lib.interpreter;

import java.util.HashMap;

import org.javatuples.Pair;

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
									Pair<Class <? extends OperandToken>,
										 OperationEvaluator>>>
							operationMap = new HashMap<>();

	public static <E1 extends OperandToken, E2 extends OperandToken> void addEntry(
			OperationToken operation,
			Class<E1> op1,
			Class<E2> op2,
			OperationEvaluator opEvaluator)
	{
		//TODO: need to verify existence before adding.

		if (null == operationMap.get(operation)) {
			operationMap.put(operation, new HashMap<>());
		}

		HashMap<Class<? extends OperandToken>,
			Pair<Class<? extends OperandToken>, OperationEvaluator>>
			endingMap = new HashMap<>();

		endingMap.put(op1, new Pair<>(op2, opEvaluator));
		operationMap.put(operation, endingMap);
	}

	public static OperationEvaluator getOperationEvaluator(
			OperationToken operation,
			Class<? extends OperandToken> op1,
			Class<? extends OperandToken> op2)
	{
		if (!operationMap.containsKey(operation)) {
			throw new IllegalArgumentException("Unsupported operation");
		}
		if (!operationMap.get(operation).containsKey(op1)) {
			throw new IllegalArgumentException(
					"Unsupported operation for the operand '" + op1.toString() + "'"
			);
		}
		if (!operationMap.get(operation).get(op1).contains(op2)) {
			throw new IllegalArgumentException(
					"Unsupported operation for the operand '"
					+ op1.toString()
					+ "' and '"
					+ op2.toString()
					+ "'"
			);
		}

		return operationMap.get(operation).get(op1).getValue1();
	}

}
