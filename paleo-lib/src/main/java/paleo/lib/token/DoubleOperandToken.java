package paleo.lib.token;

/**
 * DoubleOperandToken
 */
public final class DoubleOperandToken implements OperandToken {

    private double value;

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

    public DoubleOperandToken(final double value) {
        this.value = value;
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
