package paleo.lib.token.operand;

import java.util.List;
import paleo.lib.interpreter.OperationDictionary;
import paleo.lib.token.operation.*;

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
			new AndOperationToken(),
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
			new OrOperationToken(),
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
			new NotOperationToken(),
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

	private final boolean value;

	/**
	 * {@link BooleanOperandToken} constructor.
	 *
	 * @param value is the boolean operand corresponding value.
	 */
	public BooleanOperandToken(final boolean value) {
		this.value = value;
	}

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
}
