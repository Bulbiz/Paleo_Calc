package paleo.lib.token.operand;

import java.util.List;
import paleo.lib.interpreter.OperationDictionary;
import paleo.lib.token.operation.*;

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
			new SumOperationToken(),
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
			new SubOperationToken(),
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
			new MultOperationToken(),
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
			new DivOperationToken(),
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

	private final int value;

	/**
	 * {@link IntegerOperandToken} constructor.
	 *
	 * @param value is the corresponding integer value.
	 */
	public IntegerOperandToken(final int value) {
		this.value = value;
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
}
