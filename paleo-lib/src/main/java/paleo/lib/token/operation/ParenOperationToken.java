package paleo.lib.token.operation;

/**
 * Model the mathematical parentheses.
 */
public enum ParenOperationToken implements OperationToken {
	LEFT("("),
	RIGHT(")");

	public static final Priority priority = Priority.REALLYLOW; ///< Is the operation priority for evaluation.
	public static final int arity = 0; ///< Is the operation arity.

	public final String symbol; ///< Is the symbol corresponding to the operation

	private ParenOperationToken(final String symbol) {
		this.symbol = symbol;
	}

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
}
