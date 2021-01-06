package paleo.lib.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Optional;
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
	public Optional<Queue<Yytoken>> parse(final String expr) {
		JFLexer lexer = new JFLexer(new StringReader(expr));
		Queue<Yytoken> tokens = new LinkedList<>();
		Yytoken token;

		try {
			while (null != (token = lexer.yylex())) {
				tokens.add(token);
			}
			//TODO: Must find a way to get error messages.
		} catch (Exception e) {
			return Optional.empty();
		} catch (Error e) {
			return Optional.empty();
		}

		return Optional.of(tokens);
	}
}
