package paleo.lib.token;

import java.util.List;
import paleo.lib.interpreter.OperationDictionary;

/**
 * Models an integer number.
 */
public final class IntegerOperandToken implements OperandToken {
	/**
	 * Adds corresponding {@link OperationEvaluator} implementations
	 * to the {@link OperationDictionary}.
	 */
	{
		OperationDictionary.addEntry(
			OperationToken.SUM,
			operands -> {
				return (
					new IntegerOperandToken(
						((IntegerOperandToken) operands.pop()).getValue() +
						((IntegerOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(IntegerOperandToken.class, IntegerOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.SUB,
			operands -> {
				return (
					new IntegerOperandToken(
						((IntegerOperandToken) operands.pop()).getValue() -
						((IntegerOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(IntegerOperandToken.class, IntegerOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.MULT,
			operands -> {
				return (
					new IntegerOperandToken(
						((IntegerOperandToken) operands.pop()).getValue() *
						((IntegerOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(IntegerOperandToken.class, IntegerOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.DIV,
			operands -> {
				OperandToken op1 = operands.pop();
				OperandToken op2 = operands.pop();
				if (0 == ((IntegerOperandToken) op2).getValue()) {
					throw new IllegalArgumentException("Try to divide by zero");
				}
				return (
					new IntegerOperandToken(
						((IntegerOperandToken) op1).getValue() /
						((IntegerOperandToken) op2).getValue()
					)
				);
			},
			List.of(IntegerOperandToken.class, IntegerOperandToken.class)
		);
	}

	private int value;

	/**
	 * {@link IntegerOperandToken} constructor.
	 *
	 * @param value is the corresponding integer value.
	 */
	public IntegerOperandToken(final int value) {
		this.value = value;
	}

	// TODO: Needs to find a better design.
	@Override
	public boolean isAnOperandToken() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		return (
			obj instanceof IntegerOperandToken &&
			value == ((IntegerOperandToken) obj).getValue()
		);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * @return the value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set.
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
