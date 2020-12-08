package paleo.lib.token;

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
            (op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() + ((DoubleOperandToken)op2).getValue()
                    )
                );
			},
            DoubleOperandToken.class,
            DoubleOperandToken.class
        );
        OperationDictionary.addEntry(
            OperationToken.SUB,
            (op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() - ((DoubleOperandToken)op2).getValue()
                    )
                );
			},
            DoubleOperandToken.class,
            DoubleOperandToken.class
        );
        OperationDictionary.addEntry(
            OperationToken.MULT,
            (op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() * ((DoubleOperandToken)op2).getValue()
                    )
                );
			},
            DoubleOperandToken.class,
            DoubleOperandToken.class
        );
        OperationDictionary.addEntry(
            OperationToken.DIV,
            (op1, op2) -> {
                if (0 == ((DoubleOperandToken)op2).getValue()) {
                    throw new IllegalArgumentException("Try to divide by zero");
                }
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() / ((DoubleOperandToken)op2).getValue()
                    )
                );
			},
            DoubleOperandToken.class,
            DoubleOperandToken.class
        );

        /**
         * Double and Integer.
         */
        OperationDictionary.addEntry(
            OperationToken.SUM,
            (op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((IntegerOperandToken)op1).getValue() + ((DoubleOperandToken)op2).getValue()
                    )
                );
			},
            IntegerOperandToken.class,
            DoubleOperandToken.class
        );
        OperationDictionary.addEntry(
            OperationToken.SUM,
            (op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() + ((IntegerOperandToken)op2).getValue()
                    )
                );
			},
            DoubleOperandToken.class,
            IntegerOperandToken.class
        );
        OperationDictionary.addEntry(
            OperationToken.SUB,
            (op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((IntegerOperandToken)op1).getValue() - ((DoubleOperandToken)op2).getValue()
                    )
                );
			},
            IntegerOperandToken.class,
            DoubleOperandToken.class
        );
        OperationDictionary.addEntry(
            OperationToken.SUB,
            (op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() - ((IntegerOperandToken)op2).getValue()
                    )
                );
			},
            DoubleOperandToken.class,
            IntegerOperandToken.class
        );
        OperationDictionary.addEntry(
            OperationToken.MULT,
            (op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((IntegerOperandToken)op1).getValue() * ((DoubleOperandToken)op2).getValue()
                    )
                );
			},
            IntegerOperandToken.class,
            DoubleOperandToken.class
        );
        OperationDictionary.addEntry(
            OperationToken.MULT,
            (op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() * ((IntegerOperandToken)op2).getValue()
                    )
                );
			},
            DoubleOperandToken.class,
            IntegerOperandToken.class
        );
        OperationDictionary.addEntry(
            OperationToken.DIV,
            (op1, op2) -> {
                if (0 == ((DoubleOperandToken)op2).getValue()) {
                    throw new IllegalArgumentException("Try to divide by zero");
                }
				return (
                    new DoubleOperandToken(
                        ((IntegerOperandToken)op1).getValue() / ((DoubleOperandToken)op2).getValue()
                    )
                );
			},
            IntegerOperandToken.class,
            DoubleOperandToken.class
        );
        OperationDictionary.addEntry(
            OperationToken.DIV,
            (op1, op2) -> {
                if (0 == ((IntegerOperandToken)op2).getValue()) {
                    throw new IllegalArgumentException("Try to divide by zero");
                }
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() * ((IntegerOperandToken)op2).getValue()
                    )
                );
			},
            DoubleOperandToken.class,
            IntegerOperandToken.class
        );
    }

    private double value; ///< Is the real value.

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
}
