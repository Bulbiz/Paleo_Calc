package paleo.lib.parser;

import fj.data.Either;
import java.util.Queue;
import paleo.lib.error.ErrorWithHeader;
import paleo.lib.token.Yytoken;

/**
 * {@link Parser} provides an interface that allows to tranform a string
 * expression into a {@link Queue} of {@link Yytoken}.
 *
 * @note A {@link Queue} is used to store tokens, because, in order to evaluate
 * the expression only one run of the tokens set is necessary and its more
 * handy that a {@link List}.
 */
@FunctionalInterface
public interface Parser {
	/**
	 * Parses a {@link String} expression.
	 *
	 * @param expr is the expression to parse.
	 * @return A queue of tokens or the catched {@link Error}/{@link Exception}.
	 */
	public Either<Throwable, Queue<Yytoken>> parse(final String expr);

	/**
	 * {@link Error} raised when trying to parse an unsupported symbol.
	 * @see ErrorWithHeader
	 */
	public static class UnknownSymbError extends ErrorWithHeader {

		private static final long serialVersionUID = -3695906031317795941L; ///< Generated serial version ID.
		private static final String header = "Unknown symbol '"; ///< Default header.

		public UnknownSymbError(final String msg) {
			super(header, msg + "'");
		}
	}
}
