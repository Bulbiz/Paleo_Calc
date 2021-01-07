package paleo.lib.parser;

import fj.data.Either;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Queue;
import paleo.lib.token.Yytoken;

/**
 * Implements {@link Parser} by using a {@link JFLexer} instance to tokenize the string.
 */
public final class JFLexParser implements Parser {

	/**
	 * Parses `expr` with an {@link JFLexer} instance.
	 *
	 * @return A queue of tokens or null if an {@link IOException} is catched.
	 */
	public Either<Throwable, Queue<Yytoken>> parse(final String expr) {
		JFLexer lexer = new JFLexer(new StringReader(expr));
		Queue<Yytoken> tokens = new LinkedList<>();
		Yytoken token;

		try {
			while (null != (token = lexer.yylex())) {
				tokens.add(token);
			}
		} catch (final Error | IOException e) {
			return Either.left(e);
		}

		return Either.right(tokens);
	}
}
