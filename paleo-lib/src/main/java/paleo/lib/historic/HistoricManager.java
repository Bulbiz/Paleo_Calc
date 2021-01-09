package paleo.lib.historic;

import java.util.Optional;
import java.util.Queue;
import paleo.lib.interpreter.Interpreter;
import paleo.lib.interpreter.OperationEvaluator;
import paleo.lib.token.Yytoken;
import paleo.lib.token.operand.OperandToken;
import paleo.lib.token.operation.OperationToken;

/**
 * Interface providing historic feature.
 *
 * There is a multiple way to design and to implement historic management :
 *
 *   * The first idea was to have the {@link HistoricManager} with
 *   getters/setters in order to be called from {@link OperationEvaluator}
 *   implemenation and to consider {@link HistoricToken} like an {@link
 *   OperationToken}.
 *   But, with this method we could provide only one historic queue.
 *
 *   * For getting multiple historics, {@link HistoricManager} needs to be
 *   instanciates.  A possibility is to give to the {@link Interpreter} the
 *   current {@link HistoricManager} instance and modifies {@link
 *   OperationEvaluator} for getting this instance as argument.
 *   But, this method require to changes our previous implemenation.
 *
 *   * (Actual implemenation) We finally choosed to used an independant module
 *   plugable inside a {@link Calculator} instance.
 */
public interface HistoricManager {
	/**
	 * Replaces {@link HistoricToken} by the corresponding historic {@link OperandToken}.
	 *
	 * @param tokens a {@link Queue} of {@link Yytoken}.
	 * @return the new {@link Queue} with all occurrences of {@linkHistoricToken}
	 * replaced by the corresponding {@link Yytoken} or empty if an invalid
	 * {@link HistoricToken} is found.
	 */
	public Optional<Queue<Yytoken>> substitute(final Queue<Yytoken> tokens);

	/**
	 * Appends a new {@link OperandToken} to the historic.
	 *
	 * @param operandToken is the operand to append.
	 */
	public void add(final OperandToken operandToken);

	/**
	 * Gets the corresponding {@link OperandToken} at the index pos.
	 *
	 * @param index the corresponding index of the operand in the historicArray.
	 * @return the stored operand packed in an {@link Optional} instance.
	 */
	public Optional<OperandToken> get(final int index);

	/**
	 * Gets the last stored {@link OperandToken}.
	 *
	 * @return the last stored operand packed in an {@link Optional} instance
	 * or empty if the historic is empty.
	 */
	public Optional<OperandToken> getLast();

	/**
	 * Prints the current historic state.
	 */
	public void printHistoric();
}
