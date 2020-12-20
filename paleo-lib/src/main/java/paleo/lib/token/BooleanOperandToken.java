package paleo.lib.token;
import paleo.lib.interpreter.OperationDictionary;
import java.util.List;

public final class BooleanOperandToken implements OperandToken {
/**
     * Adds corresponding {@link OperationEvaluator} implementations
     * to the {@link OperationDictionary}.
     */
    {
        OperationDictionary.addEntry(
            OperationToken.AND,
            (operands) -> {
				return (
                    new BooleanOperandToken(
                        ((BooleanOperandToken)operands.pop()).getValue() && ((BooleanOperandToken)operands.pop()).getValue()
                    )
                );
			},
            List.of(BooleanOperandToken.class,BooleanOperandToken.class)
        );
        
        OperationDictionary.addEntry(
            OperationToken.OR,
            (operands) -> {
				return (
                    new BooleanOperandToken(
                        ((BooleanOperandToken)operands.pop()).getValue() || ((BooleanOperandToken)operands.pop()).getValue()
                    )
                );
			},
            List.of(BooleanOperandToken.class,BooleanOperandToken.class)
        );

        OperationDictionary.addEntry(
            OperationToken.NOT,
            (operands) -> {
				return (
                    new BooleanOperandToken(
                        !((BooleanOperandToken)operands.pop()).getValue()
                    )
                );
			},
            List.of(BooleanOperandToken.class)
        );
    }

    private boolean value;
    /**
     * {@link BooleanOperandToken} constructor.
     *
     * @param value is the boolean operand corresponding the value.
     */
    public BooleanOperandToken(final boolean value) {
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
            obj instanceof BooleanOperandToken
            && this.value == ((BooleanOperandToken) obj).getValue()
        );
    }

    @Override
    public String toString() {
        return value?"true" : "false";
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