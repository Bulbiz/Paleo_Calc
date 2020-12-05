package paleo.lib.token;

public enum OperationToken implements Yytoken {
    DIV             ("DIV_TOKEN"),
    RPAREN          ("RPAREN_TOKEN"),
    LPAREN          ("LPAREN_TOKEN"),
    MULT            ("MULT_TOKEN"),
    SUB             ("SUB_TOKEN"),
    SUM             ("SUM_TOKEN");

    private String name;

    private OperationToken(final String name) {
        this.name = name;
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
}
