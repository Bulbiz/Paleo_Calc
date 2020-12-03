package paleo.lib.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Ignore;
import org.junit.Test;

import paleo.lib.token.DoubleOperandToken;
import paleo.lib.token.IntegerOperandToken;
import paleo.lib.token.OperationToken;
import paleo.lib.token.Yytoken;

/**
 * Unit test for {@link Parser}.
 */
public class ParserTest  {

    /**
     * Compares two {@link Queue} of {@link Yytoken}.
     *
     * @param expectedTokens is the expected queue.
     * @param actualTokens is the actual queue.
     * @return true if both queues have the same tokens in the same order.
     */
    private boolean areTokenQueuesEqual(
            final Queue<Yytoken> expectedTokens,
            final Queue<Yytoken> actualTokens)
    {
        final int expectedSize = expectedTokens.size();

        if (expectedSize != actualTokens.size()) {
            return false;
        }

        for (int i = 0; i < expectedSize; ++i) {
            if (!expectedTokens.remove().equals(actualTokens.remove())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Creates a {@link Queue} of {@link Yytoken} from a varargs.
     *
     * @param tokens is a sequence of token.
     * @return a queue of token.
     */
    private Queue<Yytoken> createTokenQueue(final Yytoken ... tokens) {
        Queue<Yytoken> tokenQueue = new LinkedList<>();

        for (Yytoken token : tokens) {
            tokenQueue.add(token);
        }

        return tokenQueue;
    }

    @Test
    public void shouldReturnEmptyListWithEmptyString() {
        assertEquals(0, new Parser("").parse().size());
    }

    @Test
    public void simpleIntegerToken() {
        final Queue<Yytoken> actualTokens = new Parser("3").parse();
        final Queue<Yytoken> expectedTokens = createTokenQueue(new IntegerOperandToken(3));

        assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
    }

    @Test
    public void simpleSumExpression() {
        final Queue<Yytoken> actualTokens = new Parser("3 + 5").parse();
        final Queue<Yytoken> expectedTokens =
            createTokenQueue(
                    new IntegerOperandToken(3),
                    OperationToken.SUM,
                    new IntegerOperandToken(5));

        assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
    }

    @Test
    public void simpleMultExpression() {
        final Queue<Yytoken> actualTokens = new Parser("3 * 5").parse();
        final Queue<Yytoken> expectedTokens =
            createTokenQueue(
                    new IntegerOperandToken(3),
                    OperationToken.MULT,
                    new IntegerOperandToken(5));

        assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
    }

    @Test
    public void simpleParenExpression() {
        final Queue<Yytoken> actualTokens = new Parser("(3 * 5)").parse();
        final Queue<Yytoken> expectedTokens =
            createTokenQueue(
                    OperationToken.LPAREN,
                    new IntegerOperandToken(3),
                    OperationToken.MULT,
                    new IntegerOperandToken(5),
                    OperationToken.RPAREN);

        assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
    }

    @Test
    public void simpleDoubleExpression() {
        final Queue<Yytoken> actualTokens = new Parser("(3.4 * 5.6)").parse();
        final Queue<Yytoken> expectedTokens =
            createTokenQueue(
                    OperationToken.LPAREN,
                    new DoubleOperandToken(3.4),
                    OperationToken.MULT,
                    new DoubleOperandToken(5.6),
                    OperationToken.RPAREN);

        assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
    }
}
