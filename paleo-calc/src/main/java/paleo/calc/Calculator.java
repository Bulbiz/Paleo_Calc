package paleo.calc;

import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;

import paleo.calc.utils.Color;
import paleo.lib.historic.HistoricManager;
import paleo.lib.interpreter.Interpreter;
import paleo.lib.parser.Parser;
import paleo.lib.token.OperandToken;
import paleo.lib.token.Yytoken;

/**
 * Application entry point.
 */
public final class Calculator {

	private static final Scanner inScanner = new Scanner(System.in);

	public static void main(String[] args) {
		final HistoricManager historicManager = new HistoricManager();
		Optional<OperandToken> optionalOp;
		int historicCpt = 1;
		String line = "";

		printHeader();

		while (!line.trim().equalsIgnoreCase("exit")) {
			printPrompt();
			line = readLine();

			optionalOp = evaluate(line, historicManager);
			if (optionalOp.isPresent()) {
				historicManager.add(optionalOp.get());
				printRes(optionalOp.get().toString(), historicCpt++);
			}
		}
	}

	private static String readLine() {
		return inScanner.nextLine();
	}

	private static void printHeader() {
		Color.printlnWith("               ______           ", Color.BLUE);
		Color.printlnWith("______________ ___  /__________", Color.LIGHT_BLUE);
		Color.printlnWith("___  __ \\  __ `/_  /_  _ \\  __ \\", Color.CYAN);
		Color.printlnWith("__  /_/ / /_/ /_  / /  __/ /_/ /", Color.LIGHT_CYAN);
		Color.printlnWith("_  .___/\\__,_/ /_/  \\___/\\____/", Color.GREEN);
		Color.printlnWith(
			"/_/ " + Color.LIGHT_GREEN + "                      v1.2\n" + Color.NORMAL,
			Color.LIGHT_GREEN
		);
	}

	private static void printPrompt() {
		Color.printWith("> ", Color.LIGHT_BLUE);
	}

	private static Optional<OperandToken> evaluate(final String expr, final HistoricManager historicManager) {
		Interpreter interpreter;
		Optional<Queue<Yytoken>> tokenExpression = new Parser(expr).parse();

		if (tokenExpression.isPresent()) {
			try {
				interpreter = new Interpreter(
					historicManager.substitute(tokenExpression.get()).get()
				);
				return Optional.of(interpreter.evaluate());
			}
			catch (Exception e) {
				Color.printlnWith("[ERR] : " + e.getMessage(), Color.LIGHT_RED);
			}
		}
		return Optional.empty();
	}

	private static void printRes(final String res, final int historicCpt) {
		Color.printWith("(" + historicCpt + ") : ", Color.LIGHT_CYAN);
		Color.printlnWith(res, Color.LIGHT_GREEN);
	}
}
