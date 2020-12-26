package paleo.lib.token.operation;

import paleo.lib.token.Yytoken;

/**
 * Models an expression operation.
 */
public interface OperationToken extends Yytoken {
	/**
	 * Models the priority of an operation.
	 */
	public static enum Priority {
		REALLYLOW, ///< Parens priority.
		LOW, ///< Addition/substraction priority.
		MEDIUM, ///< Multiplication/division priority.
		HIGH; ///< Exponents/square priority.

		public int getPriority() {
			return this.ordinal();
		}
	}

	public String toString();

	@Override
	default boolean isAnOperandToken() {
		return false;
	}

	/**
	 * @return the operation priority.
	 */
	public int getPriority();

	/**
	 * @return the operation arity.
	 */
	public int getArity();

	public boolean equals(Object obj);
}
