package paleo.lib.parser;

import fj.data.Either;
import java.util.Queue;
import paleo.lib.error.ErrorWithHeader;
import paleo.lib.token.Yytoken;

/**
 * {@link Parser} provides an interface allowing to tranform a string
 * expression into a {@link Queue} of {@link Yytoken}.
 *
 * @note A {@link Queue} is used to store tokens, because, in order to evaluate the
 * expression only one run of the tokens set is necessary.
 */
@FunctionalInterface
public interface Parser {
	/**
	 * TODO:
	 * Parses a {@link String} exprssion.
	 *
	 * @param expr is the expression to parse.
	 * @return A queue of tokens or null if an {@link IOException} is catched.
	 */
	public Either<Throwable, Queue<Yytoken>> parse(final String expr);

	/**
	 * {@link Error} raised when trying to parse an unsupported symbol.
	 * @see ErrorWithHeader
	 */
	public static class UnknownSymbError extends ErrorWithHeader {

		private static final long serialVersionUID = -3695906031317795941L; ///< Generated serial version ID.
		private static final String header = "[ERROR_PARSER] - unknown symbol '"; ///< Default header.

		public UnknownSymbError(final String msg) {
			super(header, msg + "'");
		}
	}
}
