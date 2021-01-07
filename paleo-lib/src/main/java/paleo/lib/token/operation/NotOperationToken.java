package paleo.lib.token.operation;

/**
 * Models the boolean NOT operation.
 */
public final class NotOperationToken implements OperationToken {

	public static final String symbol = "not"; ///< Is the symbol corresponding to the operation
	public static final Priority priority = Priority.HIGH; ///< Is the operation priority for evaluation.
	public static final int arity = 1; ///< Is the operation arity.

	@Override
	public String toString() {
		return symbol;
	}

	/**
	 * @return the operation priority.
	 */
	public int getPriority() {
		return priority.getPriority();
	}

	/**
	 * @return the operation arity.
	 */
	public int getArity() {
		return arity;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof NotOperationToken;
	}
}
