package paleo.lib.historic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import paleo.lib.token.OperandToken;
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
 *   But, with this method we could provide only one historic.
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

	private ArrayList<OperandToken> historicArray; ///< Stores historic operands.

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
		Queue<Yytoken> substitutedTokens = new LinkedList<>();

		for (Yytoken yytoken : tokens) {
			if (yytoken instanceof HistoricToken) {
				HistoricToken hToken = (HistoricToken) yytoken;
				Optional<OperandToken> opOperand;

				if (0 == hToken.getArg()) {
					opOperand = this.getLast();
				}
				else {
					opOperand = this.get(hToken.getArg());
				}

				if (opOperand.isEmpty()) {
					return Optional.empty();
				}
				substitutedTokens.add(opOperand.get());
			}
			else {
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
		return Optional.of(this.historicArray.get(index-1));
	}

	/**
	 * Gets the last stored {@link OperandToken}.
	 *
	 * @return the last stored operand packed in an {@link Optional} instance.
	 */
	public Optional<OperandToken> getLast() {
		return get(this.historicArray.size());
	}
}
