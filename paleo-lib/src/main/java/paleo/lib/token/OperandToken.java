package paleo.lib.token;

/**
 * Models a generic operand type.
 */
public interface OperandToken extends Yytoken {
	/**
	 * Allows to distinguish the instances of {@link OperandToken}
	 * and {@link OperationToken}.
	 *
	 * @return true.
	 */
	default boolean isAnOperandToken() {
		return true;
	}
}
