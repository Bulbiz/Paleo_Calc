package paleo.lib.token;

import paleo.lib.interpreter.OperationDictionary;
import paleo.lib.interpreter.OperationEvaluator;

/**
 * IntegerOperandToken a model for integer numbers.
 */
public final class IntegerOperandToken implements OperandToken {

    /**
     * Adds corresponding {@link OperationEvaluator} implementations
     * to the {@link OperationDictionary}.
     */
    {
        OperationDictionary.addEntry(
            OperationToken.SUM,
            IntegerOperandToken.class,
            IntegerOperandToken.class,
			(op1, op2) -> {
				return (
                    new IntegerOperandToken(
                        ((IntegerOperandToken)op1).getValue() + ((IntegerOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.SUB,
            IntegerOperandToken.class,
            IntegerOperandToken.class,
			(op1, op2) -> {
				return (
                    new IntegerOperandToken(
                        ((IntegerOperandToken)op1).getValue() - ((IntegerOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.MULT,
            IntegerOperandToken.class,
            IntegerOperandToken.class,
			(op1, op2) -> {
				return (
                    new IntegerOperandToken(
                        ((IntegerOperandToken)op1).getValue() * ((IntegerOperandToken)op2).getValue()
                    )
                );
			}
        );
        OperationDictionary.addEntry(
            OperationToken.DIV,
            IntegerOperandToken.class,
            IntegerOperandToken.class,
			(op1, op2) -> {
                if (0 == ((IntegerOperandToken)op2).getValue()) {
                    throw new IllegalArgumentException("Try to divide by zero");
                }
				return (
                    new IntegerOperandToken(
                        ((IntegerOperandToken)op1).getValue() / ((IntegerOperandToken)op2).getValue()
                    )
                );
			}
        );
    }

    private int value;

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
            obj instanceof IntegerOperandToken
            && value == ((IntegerOperandToken) obj).getValue()
        );
    }

    @Override
    public String toString() {
        return "{value: " + value + "}";
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

}
