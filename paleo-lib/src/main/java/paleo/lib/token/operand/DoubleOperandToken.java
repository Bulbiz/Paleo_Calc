package paleo.lib.token.operand;

import java.util.List;
import paleo.lib.interpreter.OperationDictionary;
import paleo.lib.token.operation.*;

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
			new SumOperationToken(),
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
			new SubOperationToken(),
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
			new MultOperationToken(),
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
			new DivOperationToken(),
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
			new SumOperationToken(),
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
			new SumOperationToken(),
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
			new SubOperationToken(),
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
			new SubOperationToken(),
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
			new MultOperationToken(),
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
			new MultOperationToken(),
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
			new DivOperationToken(),
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
			new DivOperationToken(),
			operands -> {
				OperandToken op1 = operands.pop();
				OperandToken op2 = operands.pop();
				if (0 == ((IntegerOperandToken) op2).getValue()) {
					throw new IllegalArgumentException("Try to divide by zero");
				}
				return (
					new DoubleOperandToken(
						((DoubleOperandToken) op1).getValue() /
						((IntegerOperandToken) op2).getValue()
					)
				);
			},
			List.of(DoubleOperandToken.class, IntegerOperandToken.class)
		);
	}

	private final double value; ///< Is the real value.

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
}
