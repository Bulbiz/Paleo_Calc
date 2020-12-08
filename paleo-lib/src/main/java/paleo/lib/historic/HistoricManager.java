package paleo.lib.historic;

import java.util.ArrayList;

import paleo.lib.historic.exception.HistoricDisabledException;
import paleo.lib.token.OperandToken;

/**
 * Module providing historic features.
 *
 * @note Needed to be static in order to be access from
 * {@link paleo.lib.interpreter.OperationEvaluator} implementations used for
 * multitype feature.
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
	 * @throws HistoricDisabled if historic is not enabled.
	 */
	public void add(final OperandToken operandToken) {
		this.historicArray.add(operandToken);
	}

	/**
	 * Gets the corresponding {@link OperandToken} at the index pos.
	 *
	 * @param index the corresponding index of the operand in the historicArray.
	 * @return the stored operand.
	 * @throws HistoricDisabledException if historic is not enabled.
	 */
	public OperandToken get(final int index) {
		//TODO: managed invalid index.
		return this.historicArray.get(index);
	}
}
