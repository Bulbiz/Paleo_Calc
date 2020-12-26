package paleo.lib.token.operation;

/**
 * Model the mathematical parentheses.
 */
public enum ParenOperationToken implements OperationToken {
	LEFT,
	RIGHT;

	public static final Priority priority = Priority.REALLYLOW; ///< Is the operation priority for evaluation.
	public static final int arity = 0; ///< Is the operation arity.

	@Override
	public String toString() {
		return getClass().toString();
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
