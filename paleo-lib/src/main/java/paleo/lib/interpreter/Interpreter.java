package paleo.lib.interpreter;

import java.util.Queue;
import paleo.lib.token.Yytoken;
import paleo.lib.token.operand.OperandToken;

/**
 * {@link Interpreter} provides an interface to evaluate a {@link Queue} of {@link Yytoken}.
 */
@FunctionalInterface
public interface Interpreter {
	/**
	 * Evaluates the {@link Queue} of {@link Yytoken}.
	 *
	 * @return the {@link OperandToken} corresponding to the result of the
	 * {@link Yytoken} {@link Queue}.
	 * @throws IllegalArgumentException if the expression is not valid.
	 */
	public OperandToken evaluate() throws IllegalArgumentException;

	/**
	 * {@link Interpreter.Factory} provides a supplier to instanciate the wanted
	 * {@link Interpreter} implementation.
	 */
	@FunctionalInterface
	public interface Factory {
		public Interpreter create(final Queue<Yytoken> tokens);
	}
}
