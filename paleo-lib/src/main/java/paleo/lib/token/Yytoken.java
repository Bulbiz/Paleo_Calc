package paleo.lib.token;

/**
 * {@link FunctionalInterface} neeeded to tokenize expressions with JFlex.
 * @see JFLexer
 */
@FunctionalInterface
public interface Yytoken {
	/**
	 * Allows to distinguish the instances of {@link OperandToken}
	 * and {@link OperationToken}.
	 *
	 * @return true.
	 */
	boolean isAnOperandToken();
}
