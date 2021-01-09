package paleo.lib.token.operation;

/**
 * Models the mathematical substraction operation.
 */
public final class SubOperationToken implements OperationToken {

	public static final String symbol = "-"; ///< Is the symbol corresponding to the operation
	public static final Priority priority = Priority.LOW; ///< Is the operation priority for evaluation.
	public static final int arity = 2; ///< Is the operation arity.

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
		return obj instanceof SubOperationToken;
	}
}
