package paleo.lib.token;

/**
 * IntegerOperandToken a representation for integer numbers.
 */
public final class IntegerOperandToken implements OperandToken {

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
