package paleo.lib.token;
import paleo.lib.interpreter.OperationDictionary;
import java.util.List;

public final class BooleanOperandToken implements OperandToken {
    private boolean value;
    public static final String key = "Boolean";
    /**
     * {@link DoubleOperandToken} constructor.
     *
     * @param value is the operand corresponding real value.
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

    public String getKey (){
        return key;
    }
}