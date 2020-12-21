package paleo.lib.token;

import java.util.List;
import paleo.lib.interpreter.OperationDictionary;

/**
 * Models a boolean operand.
 */
public final class BooleanOperandToken implements OperandToken {
	/**
	 * Adds corresponding {@link OperationEvaluator} implementations
	 * to the {@link OperationDictionary}.
	 */
	{
		OperationDictionary.addEntry(
			OperationToken.AND,
			operands -> {
				return (
					new BooleanOperandToken(
						((BooleanOperandToken) operands.pop()).getValue() &&
						((BooleanOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(BooleanOperandToken.class, BooleanOperandToken.class)
		);

		OperationDictionary.addEntry(
			OperationToken.OR,
			operands -> {
				return (
					new BooleanOperandToken(
						((BooleanOperandToken) operands.pop()).getValue() ||
						((BooleanOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(BooleanOperandToken.class, BooleanOperandToken.class)
		);

		OperationDictionary.addEntry(
			OperationToken.NOT,
			operands -> {
				return (
					new BooleanOperandToken(
						!((BooleanOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(BooleanOperandToken.class)
		);
	}

	private boolean value;

	/**
	 * {@link BooleanOperandToken} constructor.
	 * @param value is the boolean operand corresponding value.
	 */
	public BooleanOperandToken(final boolean value) {
		this.value = value;
	}

	/**
	 * Verify if this is equals to obj
	 * @param obj is the object we compare to.
	 */
	@Override
	public boolean equals(Object obj) {
		return (
			obj instanceof BooleanOperandToken &&
			this.value == ((BooleanOperandToken) obj).getValue()
		);
	}

	@Override
	public String toString() {
		return value ? "true" : "false";
	}

	/**
	 * @return the value.
	 */
	public boolean getValue() {
		return value;
	}

	/**
	 * @param value the value to set.
	 */
	public void setValue(final Boolean value) {
		this.value = value;
	}
}
