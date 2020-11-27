package paleo.lib.token;

public enum OperandToken implements Yytoken {
    INTEGER_VALUE   ("INTEGER_VALUE"),
    REAL_VALUE      ("REAL_VALUE");

    private String name;
    private float value;

    private OperandToken(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[" + this.name + ":"
                + (this == INTEGER_VALUE ? Math.round(this.value) : this.value)
                + "]";
    }

    public boolean isOpereationToken() {
        return false;
    }

    public void setValue(String strValue) {
        this.value = Float.parseFloat(strValue);
    }
}
