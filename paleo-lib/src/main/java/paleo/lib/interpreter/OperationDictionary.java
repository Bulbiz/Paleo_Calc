package paleo.lib.interpreter;

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import paleo.lib.token.operand.OperandToken;
import paleo.lib.token.operation.OperationToken;

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
	private static HashMap<String, OperationEvaluator> operationMap = new HashMap<>();

	/**
	 * Adds an {@link OperationEvaluator} to the corresponding {@link HashMap}
	 * in operationMap.
	 *
	 * @param operation is the {@link OperationToken} implemented by the opEvaluator.
	 * @param opEvaluator is the {@link OperationEvaluator} implementation of the operation.
	 * @param signature is the list of {@link Operand} classes supported by the opereation.
	 */
	public static void addEntry(
		final OperationToken operation,
		final OperationEvaluator opEvaluator,
		final List<Class<? extends OperandToken>> signature
	) {
		final String key = generateKeyFrom(operation, signature);

		if (!operationMap.containsKey(key)) {
			operationMap.put(key, opEvaluator);
		}
	}

	/**
	 * Get the implementation of {@link OperationEvaluator} corresponding to the given
	 * operation and operands types.
	 *
	 * @param operation is the wanted {@link OperationToken}.
	 * @param signature is the list of {@link Operand} classes of the argument.
	 * @return the corresponding implementation of the operation if its
	 * provided, otherwise, throw an {@link IllegalArgumentException}.
	 */
	public static OperationEvaluator getOperationEvaluator(
		final OperationToken operation,
		final Deque<OperandToken> signature
	) {
		final List<Class<? extends OperandToken>> signatureKey = signature
			.parallelStream()
			.map(op -> op.getClass())
			.collect(Collectors.toList());
		final String key = generateKeyFrom(operation, signatureKey);

		if (!operationMap.containsKey(key)) {
			throw new IllegalArgumentException(
				"Unsupported operation '" +
				operation.toString() +
				"' for the corresponding signature '" +
				parallelCollectToString(signature) +
				"'"
			);
		}
		return operationMap.get(key);
	}

	private static String generateKeyFrom(
		final OperationToken operation,
		final List<Class<? extends OperandToken>> signature
	) {
		return operation.getClass().toString() + parallelCollectToString(signature);
	}

	private static String parallelCollectToString(final Collection<?> collection) {
		return collection
			.parallelStream()
			.map(e -> e.toString())
			.collect(Collectors.joining(" "));
	}
}
