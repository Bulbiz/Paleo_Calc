package paleo.lib.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	private static HashMap<String,OperationEvaluator> operationMap = new HashMap<String,OperationEvaluator>();


	private static String generateKeyFrom (OperationToken operation ,List<String> signature){
		Stream <String> stream = signature.stream();
		String key = operation.getKey() + stream.collect(Collectors.joining("|"));
		return key;
	}

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
			List<String> signature)
	{
		String key = generateKeyFrom(operation,signature);
		if (!operationMap.containsKey(key)) {
			operationMap.put(key,opEvaluator);
		}
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
			List<String> signature)
	{
		String key = generateKeyFrom(operation,signature);
		if (!operationMap.containsKey(key))
			throw new IllegalArgumentException( operation.toString() + " unsupported operation" );
		else
			return operationMap.get(key);
	}
}
/*if (!operationMap.containsKey(operation)) {
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
		if (!operationMap.get(operation).next(op1).containsKey(op2)) {
			throw new IllegalArgumentException(
					operation.toString() + " "
					+ "unsupported operation for the operand '"
					+ op1.toString()
					+ "' and '"
					+ op2.toString()
					+ "'"
			);
		}
		return operationMap.get(operation).next(op1).next(op2).getEvaluator().get();*/