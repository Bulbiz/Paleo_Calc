package paleo.lib.token;

import paleo.lib.interpreter.OperationDictionary;
import java.util.List;
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
            (operands) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)operands.pop()).getValue() + ((DoubleOperandToken)operands.pop()).getValue()
                    )
                );
			},
            List.of(DoubleOperandToken.key,DoubleOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.SUB,
            (operands) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)operands.pop()).getValue() - ((DoubleOperandToken)operands.pop()).getValue()
                    )
                );
			},
            List.of(DoubleOperandToken.key,DoubleOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.MULT,
            (operands) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)operands.pop()).getValue() * ((DoubleOperandToken)operands.pop()).getValue()
                    )
                );
			},
            List.of(DoubleOperandToken.key,DoubleOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.DIV,
            (operands) -> {
                OperandToken op1 = operands.pop();
                OperandToken op2 = operands.pop();
                if (0 == ((DoubleOperandToken)op2).getValue()) {
                    throw new IllegalArgumentException("Try to divide by zero");
                }
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() / ((DoubleOperandToken)op2).getValue()
                    )
                );
			},
            List.of(DoubleOperandToken.key,DoubleOperandToken.key)
        );

        /**
         * Double and Integer.
         */
        OperationDictionary.addEntry(
            OperationToken.SUM,
            (operands) -> {
				return (
                    new DoubleOperandToken(
                        ((IntegerOperandToken)operands.pop()).getValue() + ((DoubleOperandToken)operands.pop()).getValue()
                    )
                );
			},
            List.of(IntegerOperandToken.key,DoubleOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.SUM,
            (operands) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)operands.pop()).getValue() + ((IntegerOperandToken)operands.pop()).getValue()
                    )
                );
			},
            List.of(DoubleOperandToken.key,IntegerOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.SUB,
            (operands) -> {
				return (
                    new DoubleOperandToken(
                        ((IntegerOperandToken)operands.pop()).getValue() - ((DoubleOperandToken)operands.pop()).getValue()
                    )
                );
			},
            List.of(IntegerOperandToken.key,DoubleOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.SUB,
            (operands) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)operands.pop()).getValue() - ((IntegerOperandToken)operands.pop()).getValue()
                    )
                );
			},
            List.of(DoubleOperandToken.key,IntegerOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.MULT,
            (operands) -> {
				return (
                    new DoubleOperandToken(
                        ((IntegerOperandToken)operands.pop()).getValue() * ((DoubleOperandToken)operands.pop()).getValue()
                    )
                );
			},
            List.of(IntegerOperandToken.key,DoubleOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.MULT,
            (operands) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)operands.pop()).getValue() * ((IntegerOperandToken)operands.pop()).getValue()
                    )
                );
			},
            List.of(DoubleOperandToken.key,IntegerOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.DIV,
            (operands) -> {
                OperandToken op1 = operands.pop();
                OperandToken op2 = operands.pop();
                if (0 == ((DoubleOperandToken)op2).getValue()) {
                    throw new IllegalArgumentException("Try to divide by zero");
                }
				return (
                    new DoubleOperandToken(
                        ((IntegerOperandToken)op1).getValue() / ((DoubleOperandToken)op2).getValue()
                    )
                );
			},
            List.of(IntegerOperandToken.key,DoubleOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.DIV,
            (operands) -> {
                OperandToken op1 = operands.pop();
                OperandToken op2 = operands.pop();
                if (0 == ((IntegerOperandToken)op2).getValue()) {
                    throw new IllegalArgumentException("Try to divide by zero");
                }
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() * ((IntegerOperandToken)op2).getValue()
                    )
                );
			},
            List.of(DoubleOperandToken.key,IntegerOperandToken.key)
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

    // TODO: Needs to find a better design.
    @Override
    public boolean isAnOperandToken() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return (
            obj instanceof DoubleOperandToken
            && this.value == ((DoubleOperandToken) obj).getValue()
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

    public String getKey (){
        return key;
    }
}
