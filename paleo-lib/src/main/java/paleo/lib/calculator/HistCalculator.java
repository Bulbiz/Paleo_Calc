package paleo.lib.calculator;

import fj.data.Either;
import java.util.Optional;
import java.util.Queue;
import paleo.lib.historic.HistoricManager;
import paleo.lib.interpreter.Interpreter;
import paleo.lib.parser.Parser;
import paleo.lib.token.Yytoken;
import paleo.lib.token.operand.OperandToken;

/**
 * {@link HistCalculator} implements {@link Calculator} by adding an historic
 * management through the {@link HistoricManager} instance.
 */
public class HistCalculator implements Calculator {

	private Interpreter.Factory interpreterFactory; ///< Allows to creates an {@link Interpreter} instance from an expression.
	private Parser parser; ///< {@link Parser} instance used to parse the line.
	private HistoricManager historicManager; ///< Is the historic manager.

	/**
	 *  {@link HistCalculator} constructor.
	 */
	public HistCalculator(
		final Interpreter.Factory interpreterFactory,
		final Parser parser,
		final HistoricManager historicManager
	) {
		this.interpreterFactory = interpreterFactory;
		this.parser = parser;
		this.historicManager = historicManager;
	}

	/**
	 * Calculates the given line (@see Calculator).
	 *
	 * @param line could be a mathematical expression or an internal command
	 * such as 'ls', in this case empty is returned.
	 * @return an {@link Optional}, if the line could be evaluate like a valid
	 * mathematical expression return the corresponding value, otherwise, an
	 * empty optional.
	 */
	public Optional<Either<Throwable, OperandToken>> calculate(final String line) {
		Either<Throwable, OperandToken> optionalOp;

		if (line.trim().equalsIgnoreCase("ls")) {
			historicManager.printHistoric();
		} else {
			optionalOp = evaluate(line);
			if (optionalOp.isRight()) {
				historicManager.add(optionalOp.right().value());
				return Optional.of(optionalOp);
			} else {
				return Optional.of(optionalOp);
			}
		}
		return Optional.empty();
	}

	private Either<Throwable, OperandToken> evaluate(final String expr) {
		final Either<Throwable, Queue<Yytoken>> tokenExpression = this.parser.parse(expr);

		if (tokenExpression.isRight()) {
			try {
				final Interpreter interpreter =
					this.interpreterFactory.create(
							historicManager
								.substitute(tokenExpression.right().value())
								.get()
						);
				return Either.right(interpreter.evaluate());
			} catch (final Exception e) {
				return Either.left(e);
			}
		}
		return Either.left(tokenExpression.left().value());
	}

	public Interpreter.Factory getInterpreterFactory() {
		return interpreterFactory;
	}

	public void setInterpreterFactory(final Interpreter.Factory interpreterFactory) {
		this.interpreterFactory = interpreterFactory;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(final Parser parser) {
		this.parser = parser;
	}

	public HistoricManager getHistoricManager() {
		return historicManager;
	}

	public void setHistoricManager(final HistoricManager historicManager) {
		this.historicManager = historicManager;
	}
}
