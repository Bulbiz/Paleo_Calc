package paleo.lib.token;

/**
 * DoubleOperandToken
 */
public class DoubleOperandToken implements OperandToken {

    private double value;

    // TODO: Needs to find a better design.
    @Override
    public boolean isAnOperandToken() {
        return true;
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
