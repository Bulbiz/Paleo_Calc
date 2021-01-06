package paleo.lib.parser;

import java.util.Optional;
import java.util.Queue;
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
	 * Parses a {@link String} exprssion.
	 *
	 * @param expr is the expression to parse.
	 * @return A queue of tokens or null if an {@link IOException} is catched.
	 */
	public Optional<Queue<Yytoken>> parse(final String expr);
}
