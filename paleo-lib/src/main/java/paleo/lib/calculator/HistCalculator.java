package paleo.lib.calculator;

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

	private final Interpreter.Factory interpreterFactory; ///< Allows to creates an {@link Interpreter} instance from an expression.
	private final Parser parser; ///< {@link Parser} instance used to parse the line.
	private final HistoricManager historicManager; ///< Is the historic manager.

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
	 * @line is the line to be calculate.
	 * @return an {@link Optional}, if the line could be evaluate like a valid
	 * mathematical expression return the corresponding value, otherwise, an
	 * empty optional.
	 */
	public Optional<OperandToken> calculate(final String line) {
		Optional<OperandToken> optionalOp;

		if (line.trim().equalsIgnoreCase("ls")) {
			historicManager.printHistoric();
		} else {
			optionalOp = evaluate(line);
			if (optionalOp.isPresent()) {
				historicManager.add(optionalOp.get());
				return optionalOp;
			}
		}
		return Optional.empty();
	}

	private Optional<OperandToken> evaluate(final String expr) {
		Interpreter interpreter;
		final Optional<Queue<Yytoken>> tokenExpression = this.parser.parse(expr);

		if (tokenExpression.isPresent()) {
			try {
				interpreter =
					this.interpreterFactory.create(
							historicManager.substitute(tokenExpression.get()).get()
						);
				return Optional.of(interpreter.evaluate());
			} catch (final Exception e) {
				//TODO: handle exception
				System.err.println(e.getMessage());
			}
		}
		return Optional.empty();
	}
}
