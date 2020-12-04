package paleo.lib.token;

import paleo.lib.interpreter.OperationDictionary;

/**
 * DoubleOperandToken a representation for real numbers.
 */
public final class DoubleOperandToken implements OperandToken {

    /**
     * Adds corresponding {@link OperationEvaluator} implementations
     * to the {@link OperationDictionary}.
     */
    {
        OperationDictionary.addEntry(
            OperationToken.SUM,
            DoubleOperandToken.class,
            DoubleOperandToken.class,
			(op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() + ((DoubleOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.SUB,
            DoubleOperandToken.class,
            DoubleOperandToken.class,
			(op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() - ((DoubleOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.MULT,
            DoubleOperandToken.class,
            DoubleOperandToken.class,
			(op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() * ((DoubleOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.DIV,
            DoubleOperandToken.class,
            DoubleOperandToken.class,
			(op1, op2) -> {
                if (0 == ((DoubleOperandToken)op2).getValue()) {
                    throw new IllegalArgumentException("Try to divide by zero");
                }
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() / ((DoubleOperandToken)op2).getValue()
                    )
                );
			}
        );

        /**
         * Double and Integer.
         */
        OperationDictionary.addEntry(
            OperationToken.SUM,
            IntegerOperandToken.class,
            DoubleOperandToken.class,
			(op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((IntegerOperandToken)op1).getValue() + ((DoubleOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.SUM,
            DoubleOperandToken.class,
            IntegerOperandToken.class,
			(op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() + ((IntegerOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.SUB,
            IntegerOperandToken.class,
            DoubleOperandToken.class,
			(op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((IntegerOperandToken)op1).getValue() - ((DoubleOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.SUB,
            DoubleOperandToken.class,
            IntegerOperandToken.class,
			(op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() - ((IntegerOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.MULT,
            IntegerOperandToken.class,
            DoubleOperandToken.class,
			(op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((IntegerOperandToken)op1).getValue() * ((DoubleOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.MULT,
            DoubleOperandToken.class,
            IntegerOperandToken.class,
			(op1, op2) -> {
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() * ((IntegerOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.DIV,
            IntegerOperandToken.class,
            DoubleOperandToken.class,
			(op1, op2) -> {
                if (0 == ((DoubleOperandToken)op2).getValue()) {
                    throw new IllegalArgumentException("Try to divide by zero");
                }
				return (
                    new DoubleOperandToken(
                        ((IntegerOperandToken)op1).getValue() / ((DoubleOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.DIV,
            DoubleOperandToken.class,
            IntegerOperandToken.class,
			(op1, op2) -> {
                if (0 == ((IntegerOperandToken)op2).getValue()) {
                    throw new IllegalArgumentException("Try to divide by zero");
                }
				return (
                    new DoubleOperandToken(
                        ((DoubleOperandToken)op1).getValue() * ((IntegerOperandToken)op2).getValue()
                    )
                );
			}
        );
    }

    private double value;

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
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

}
