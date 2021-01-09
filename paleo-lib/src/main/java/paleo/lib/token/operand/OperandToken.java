package paleo.lib.token.operand;

import paleo.lib.token.Yytoken;

/**
 * Models a generic operand type.
 */
public interface OperandToken extends Yytoken {
	/**
	 * Default implementation of the {@link Yytoken} function.
	 *
	 * @return true.
	 */
	@Override
	default boolean isAnOperandToken() {
		return true;
	}
}
