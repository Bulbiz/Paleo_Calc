package paleo.lib.token;

/**
 * {@link FunctionalInterface} neeeded to tokenize expressions with JFlex.
 * @see JFLexer
 */
@FunctionalInterface
public interface Yytoken {
    boolean isAnOperandToken();
}
