package paleo.lib.historic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import paleo.lib.token.Yytoken;
import paleo.lib.token.operand.OperandToken;

/**
 * {@link TabHistoricManager} is an {@link HistoricManager} implemetation which
 * prints the current historic state inside a table (@see {@link printHistoric}).
 */
public final class TabHistoricManager implements HistoricManager {

	private final ArrayList<OperandToken> historicArray; ///< Stores historic operands.

	/**
	 * {@link TabHistoricManager} constructor.
	 */
	public TabHistoricManager() {
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
		final Queue<Yytoken> substitutedTokens = tokens
			.parallelStream()
			.map(
				yytoken -> {
					if (yytoken instanceof HistoricToken) {
						final HistoricToken hToken = (HistoricToken) yytoken;
						Optional<OperandToken> opOperand;

						if (0 == hToken.getArg()) {
							opOperand = this.getLast();
						} else {
							opOperand = this.get(hToken.getArg());
						}
						return opOperand;
					}
					return Optional.of(yytoken);
				}
			)
			.filter(yytokenOp -> yytokenOp.isPresent())
			.collect(
				LinkedList<Yytoken>::new,
				(ll, tokOp) -> ll.add((Yytoken) tokOp.get()),
				(ll1, ll2) -> ll1.addAll(ll2)
			);

		return substitutedTokens.isEmpty()
			? Optional.empty()
			: Optional.of(substitutedTokens);
	}

	/**
	 * Gets the corresponding {@link OperandToken} at the index pos.
	 *
	 * @param index the corresponding index of the operand in the historicArray.
	 * @return the stored operand packed in an {@link Optional} instance or empty
	 * if the index is out of bounds.
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
	 * Prints the current historic state inside a table following the format :
	 *
	 * <p>
	 *   +-------------------------+<br>
	 *   | n : last operand value  |<br>
	 *   |           ...           |<br>
	 *   | 1 : first operand value |<br>
	 *   +-------------------------+<br>
	 *   | 0 : last operand value  |<br>
	 *   +-------------------------+<br>
	 *  </p>
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
			for (int i = historicArray.size(); i > 0; --i) {
				printEntry(max_klen, max_vlen, i);
			}
			System.out.println(delimLine);
			printEntry(max_klen, max_vlen, 0);
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
		final String currValue = 0 < i
			? get(i).get().toString()
			: getLast().get().toString();

		currentLine = "| ";
		currentLine +=
			String.valueOf(i) + getNTimesChar(max_klen - String.valueOf(i).length(), ' ');
		currentLine +=
			" : " + currValue + getNTimesChar(max_vlen - currValue.length(), ' ') + " |";
		System.out.println(currentLine);
	}
}
