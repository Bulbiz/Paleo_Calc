package paleo.lib.historic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import paleo.lib.interpreter.Interpreter;
import paleo.lib.interpreter.OperationEvaluator;
import paleo.lib.parser.Parser;
import paleo.lib.token.operand.OperandToken;
import paleo.lib.token.operation.OperationToken;
import paleo.lib.token.Yytoken;

/**
 * Module providing historic features.
 *
 * There is a multiple way to design and to implement historic management :
 *
 *   * The first idea was to have the {@link HistoricManager} with
 *   getters/setters in order to be called from {@link OperationEvaluator}
 *   implemenation and to consider {@link HistoricToken} like an {@link
 *   OperationToken}.
 *   But, with this method we could provide only one historic queue.
 *
 *   * For getting multiple historics {@link HistoricManager} needs to be
 *   instanciates.  A possibility is to give to the {@link Interpreter} the
 *   current {@link HistoricManager} instance and modifies {@link
 *   OperationEvaluator} for getting this instance as argument.
 *   But, this method require to changes our previous implemenation.
 *
 *   * (Actual implemenation) We finally choosed to used an independant module
 *   plugable between the {@link Parser} and the {@link Interpreter}.
 */
public final class HistoricManager {

	private final ArrayList<OperandToken> historicArray; ///< Stores historic operands.

	/**
	 * {@link HistoricManager} constructor.
	 */
	public HistoricManager() {
		this.historicArray = new ArrayList<>();
	}

	/**
	 * Appends a new {@link OperandToken} to the historic.
	 *
	 * @param operandToken is the operand to append.
	 */
	public void add(final OperandToken operandToken) {
		this.historicArray.add(operandToken);
	}

	/**
	 * Replaces {@link HistoricToken} by the corresponding historic {@link OperandToken}.
	 *
	 * @param tokens a {@link Queue} of {@link Yytoken}.
	 * @return the new {@link Queue} with all occurrences of {@linkHistoricToken}
	 * replaced by the corresponding {@link Yytoken} or empty if an invalid
	 * {@link HistoricToken} is found.
	 */
	public Optional<Queue<Yytoken>> substitute(final Queue<Yytoken> tokens) {
		final Queue<Yytoken> substitutedTokens = new LinkedList<>();

		for (final Yytoken yytoken : tokens) {
			if (yytoken instanceof HistoricToken) {
				final HistoricToken hToken = (HistoricToken) yytoken;
				Optional<OperandToken> opOperand;

				if (0 == hToken.getArg()) {
					opOperand = this.getLast();
				} else {
					opOperand = this.get(hToken.getArg());
				}

				if (opOperand.isEmpty()) {
					return Optional.empty();
				}
				substitutedTokens.add(opOperand.get());
			} else {
				substitutedTokens.add(yytoken);
			}
		}
		return Optional.of(substitutedTokens);
	}

	/**
	 * Gets the corresponding {@link OperandToken} at the index pos.
	 *
	 * @param index the corresponding index of the operand in the historicArray.
	 * @return the stored operand packed in an {@link Optional} instance.
	 */
	public Optional<OperandToken> get(final int index) {
		if (0 >= index || this.historicArray.size() < index) {
			return Optional.empty();
		}
		return Optional.of(this.historicArray.get(index - 1));
	}

	/**
	 * Gets the last stored {@link OperandToken}.
	 *
	 * @return the last stored operand packed in an {@link Optional} instance.
	 */
	public Optional<OperandToken> getLast() {
		return get(this.historicArray.size());
	}

	/**
	 * Prints historic.
	 *
	 * TODO: Could be prettier.
	 */
	public void printHistoric() {
		final int max_vlen;
		final int max_klen;
		final String delimLine;

		if (this.historicArray.isEmpty()) {
			System.out.println("Empty historic...");
		} else {
			max_vlen = getMaxStringValueLength();
			max_klen = String.valueOf(this.historicArray.size()).length();
			delimLine = "+" + this.getNTimesChar(max_klen + max_vlen + 5, '-') + "+";

			System.out.println(delimLine);
			printEntry(max_klen, max_vlen, 0);
			System.out.println(delimLine);
			for (int i = historicArray.size(); i > 0; --i) {
				printEntry(max_klen, max_vlen, i);
			}
			System.out.println(delimLine);
		}
	}

	private int getMaxStringValueLength() {
		return this.historicArray.stream()
			.map(op -> op.toString())
			.max((s1, s2) -> s1.length() - s2.length())
			.get()
			.length();
	}

	private String getNTimesChar(final int n, final char c) {
		String res = "";

		for (int i = 0; i < n; ++i) res += c;

		return res;
	}

	private void printEntry(final int max_klen, final int max_vlen, final int i) {
		String currentLine = "";
		String currValue = 0 < i ? get(i).get().toString() : getLast().get().toString();

		currentLine = "| ";
		currentLine +=
			String.valueOf(i) + getNTimesChar(max_klen - String.valueOf(i).length(), ' ');
		currentLine +=
			" : " + currValue + getNTimesChar(max_vlen - currValue.length(), ' ') + " |";
		System.out.println(currentLine);
	}
}
