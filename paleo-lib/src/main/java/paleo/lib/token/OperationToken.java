package paleo.lib.token;

/**
 * Models an expression operation.
 */
public enum OperationToken implements Yytoken {
	RPAREN("RPAREN_TOKEN", -1, -1),
	LPAREN("LPAREN_TOKEN", -1, -1),
	MULT("MULT_TOKEN", 2, 2),
	DIV("DIV_TOKEN", 2, 2),
	SUB("SUB_TOKEN", 1, 2),
	SUM("SUM_TOKEN", 1, 2),
    AND("AND", 1, 2),
    OR("OR", 1, 2),
	NOT("NOT", 2, 1),
	INTER("INTER", 1, 2),
	UNION("UNION", 1, 2),
	DIFF("DIFF", 1, 2);
    
	private final String name; ///< Is the operation name used for generate {@link OperationDictionary} entry key.
	private final int priority; ///< Is the operation priority for evaluation.
	private final int arity; ///< Is the operation arity.

	private OperationToken(final String name, final int priority, final int arity) {
		this.name = name;
		this.priority = priority;
		this.arity = arity;
	}

	@Override
	public String toString() {
		return this.name;
	}

	//TODO: Should have a better way.
	@Override
	public boolean isAnOperandToken() {
		return false;
	}

	/**
	 * @return the operation priority.
	 */
	public int getPriority() {
		return this.priority;
	}

	/**
	 * @return the operation arity.
	 */
	public int getArity() {
		return this.arity;
	}
}
