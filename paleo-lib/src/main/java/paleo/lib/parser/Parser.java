package paleo.lib.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import paleo.lib.token.Yytoken;

/**
 * Module allowing to tranform a string expression into a queue of tokens.
 * A {@link Lexer} instance is used for tokenizing the string.
 *
 * @note A {@link Queue} is used to store tokens, because, in order to evaluate the
 * expression only one run of the tokens set is necessary.
 *
 * @see Lexer
 */
public final class Parser {

	private String expr;

	/**
	 * Parser constructor.
	 *
	 * @param expr Is a string representation of the wanted parsed expression.
	 */
	public Parser(String expr) {
		this.expr = expr;
	}

	/**
	 * Parses `expr` with an {@link Lexer} instance.
	 *
	 * @return A queue of tokens or null if an {@link IOException} is catched.
	 */
	public Optional<Queue<Yytoken>> parse() {
		JFLexer lexer = new JFLexer(new StringReader(this.expr));
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
