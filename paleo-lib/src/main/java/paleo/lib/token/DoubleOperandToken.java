package paleo.lib.token;

import java.util.List;
import paleo.lib.interpreter.OperationDictionary;

/**
 * Models a real number operand.
 */
public final class DoubleOperandToken implements OperandToken {
	/**
	 * Adds corresponding {@link OperationEvaluator} implementations
	 * to the {@link OperationDictionary}.
	 */
	{
		OperationDictionary.addEntry(
			OperationToken.SUM,
			operands -> {
				return (
					new DoubleOperandToken(
						((DoubleOperandToken) operands.pop()).getValue() +
						((DoubleOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(DoubleOperandToken.class, DoubleOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.SUB,
			operands -> {
				return (
					new DoubleOperandToken(
						((DoubleOperandToken) operands.pop()).getValue() -
						((DoubleOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(DoubleOperandToken.class, DoubleOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.MULT,
			operands -> {
				return (
					new DoubleOperandToken(
						((DoubleOperandToken) operands.pop()).getValue() *
						((DoubleOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(DoubleOperandToken.class, DoubleOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.DIV,
			operands -> {
				OperandToken op1 = operands.pop();
				OperandToken op2 = operands.pop();
				if (0 == ((DoubleOperandToken) op2).getValue()) {
					throw new IllegalArgumentException("Try to divide by zero");
				}
				return (
					new DoubleOperandToken(
						((DoubleOperandToken) op1).getValue() /
						((DoubleOperandToken) op2).getValue()
					)
				);
			},
			List.of(DoubleOperandToken.class, DoubleOperandToken.class)
		);

		/**
		 * Double and Integer.
		 */
		OperationDictionary.addEntry(
			OperationToken.SUM,
			operands -> {
				return (
					new DoubleOperandToken(
						((IntegerOperandToken) operands.pop()).getValue() +
						((DoubleOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(IntegerOperandToken.class, DoubleOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.SUM,
			operands -> {
				return (
					new DoubleOperandToken(
						((DoubleOperandToken) operands.pop()).getValue() +
						((IntegerOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(DoubleOperandToken.class, IntegerOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.SUB,
			operands -> {
				return (
					new DoubleOperandToken(
						((IntegerOperandToken) operands.pop()).getValue() -
						((DoubleOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(IntegerOperandToken.class, DoubleOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.SUB,
			operands -> {
				return (
					new DoubleOperandToken(
						((DoubleOperandToken) operands.pop()).getValue() -
						((IntegerOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(DoubleOperandToken.class, IntegerOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.MULT,
			operands -> {
				return (
					new DoubleOperandToken(
						((IntegerOperandToken) operands.pop()).getValue() *
						((DoubleOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(IntegerOperandToken.class, DoubleOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.MULT,
			operands -> {
				return (
					new DoubleOperandToken(
						((DoubleOperandToken) operands.pop()).getValue() *
						((IntegerOperandToken) operands.pop()).getValue()
					)
				);
			},
			List.of(DoubleOperandToken.class, IntegerOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.DIV,
			operands -> {
				OperandToken op1 = operands.pop();
				OperandToken op2 = operands.pop();
				if (0 == ((DoubleOperandToken) op2).getValue()) {
					throw new IllegalArgumentException("Try to divide by zero");
				}
				return (
					new DoubleOperandToken(
						((IntegerOperandToken) op1).getValue() /
						((DoubleOperandToken) op2).getValue()
					)
				);
			},
			List.of(IntegerOperandToken.class, DoubleOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.DIV,
			operands -> {
				OperandToken op1 = operands.pop();
				OperandToken op2 = operands.pop();
				if (0 == ((IntegerOperandToken) op1).getValue()) {
					throw new IllegalArgumentException("Try to divide by zero");
				}
				return (
					new DoubleOperandToken(
						((DoubleOperandToken) op2).getValue() /
						((IntegerOperandToken) op1).getValue()
					)
				);
			},
			List.of(DoubleOperandToken.class, IntegerOperandToken.class)
		);
	}

	private double value; ///< Is the real value.
	public static final String key = "Double";

	/**
	 * {@link DoubleOperandToken} constructor.
	 *
	 * @param value is the operand corresponding real value.
	 */
	public DoubleOperandToken(final double value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		return (
			obj instanceof DoubleOperandToken &&
			this.value == ((DoubleOperandToken) obj).getValue()
		);
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

	/**
	 * @return the value.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set.
	 */
	public void setValue(final double value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}
}
