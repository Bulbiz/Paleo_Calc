package paleo.lib.token;

public enum OperationToken implements Yytoken {
    RPAREN          ("RPAREN_TOKEN", -1),
    LPAREN          ("LPAREN_TOKEN", -1),
    MULT            ("MULT_TOKEN", 2),
    DIV             ("DIV_TOKEN", 2),
    SUB             ("SUB_TOKEN", 1),
    SUM             ("SUM_TOKEN", 1);

    private final String name;
    private final int priority;

    private OperationToken(final String name, final int priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "[" + this.name + "]";
    }

    //TODO: Should have a better way.
    @Override
    public boolean isAnOperandToken() {
        return false;
    }

    /**
     * @return the operation priority.
     */
    public int getPriority() {
        return this.priority;
    }

}
