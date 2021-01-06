package paleo.lib.calculator;

import java.util.Optional;
import java.util.Queue;
import paleo.lib.historic.HistoricManager;
import paleo.lib.interpreter.Interpreter;
import paleo.lib.parser.Parser;
import paleo.lib.token.Yytoken;
import paleo.lib.token.operand.OperandToken;

public class HistCalculator implements Calculator {

	private final Interpreter.Factory interpreterFactory;
	private final Parser parser;
	private final HistoricManager historicManager;

	public HistCalculator(
		Interpreter.Factory interpreterFactory,
		Parser parser,
		HistoricManager historicManager
	) {
		this.interpreterFactory = interpreterFactory;
		this.parser = parser;
		this.historicManager = historicManager;
	}

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
		Optional<Queue<Yytoken>> tokenExpression = this.parser.parse(expr);

		if (tokenExpression.isPresent()) {
			try {
				interpreter =
					this.interpreterFactory.create(
							historicManager.substitute(tokenExpression.get()).get()
						);
				return Optional.of(interpreter.evaluate());
			} catch (Exception e) {
				//TODO: handle exception
				System.err.println(e.getMessage());
			}
		}
		return Optional.empty();
	}
}
