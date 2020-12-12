package paleo.lib.token;

import paleo.lib.interpreter.OperationDictionary;
import java.util.List;
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
            (op1, op2) -> {
				return (
                    new IntegerOperandToken(
                        ((IntegerOperandToken)op1).getValue() + ((IntegerOperandToken)op2).getValue()
                    )
                );
			},
            List.of(IntegerOperandToken.key,IntegerOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.SUB,
            (op1, op2) -> {
				return (
                    new IntegerOperandToken(
                        ((IntegerOperandToken)op1).getValue() - ((IntegerOperandToken)op2).getValue()
                    )
                );
			},
            List.of(IntegerOperandToken.key,IntegerOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.MULT,
            (op1, op2) -> {
				return (
                    new IntegerOperandToken(
                        ((IntegerOperandToken)op1).getValue() * ((IntegerOperandToken)op2).getValue()
                    )
                );
			},
            List.of(IntegerOperandToken.key,IntegerOperandToken.key)
        );
        OperationDictionary.addEntry(
            OperationToken.DIV,
            (op1, op2) -> {
                if (0 == ((IntegerOperandToken)op2).getValue()) {
                    throw new IllegalArgumentException("Try to divide by zero");
                }
				return (
                    new IntegerOperandToken(
                        ((IntegerOperandToken)op1).getValue() / ((IntegerOperandToken)op2).getValue()
                    )
                );
			},
            List.of(IntegerOperandToken.key,IntegerOperandToken.key)
        );
    }

    private int value;
    public static final String key = "Integer";
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
            obj instanceof IntegerOperandToken
            && value == ((IntegerOperandToken) obj).getValue()
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

    public String getKey (){
        return key;
    }
}
