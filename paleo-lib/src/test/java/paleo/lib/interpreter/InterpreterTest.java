package paleo.lib.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import paleo.lib.parser.Parser;
import paleo.lib.token.IntegerOperandToken;

/**
 * Unit test for {@link Interpreter}.
 */
public class InterpreterTest {

    @Test
    public void withOnlyOneIntegerOperand() {
        assertEquals(
            new IntegerOperandToken(3),
            new Interpreter(new Parser("3").parse()).evaluate()
        );
    }

    @Test
    public void simpleIntegerSum() {
        assertEquals(
            new IntegerOperandToken(8),
            new Interpreter(new Parser("3 + 5").parse()).evaluate()
        );
    }

    @Test
    public void simpleIntegerSub() {
        assertEquals(
            new IntegerOperandToken(-2),
            new Interpreter(new Parser("3 - 5").parse()).evaluate()
        );
    }

    @Test
    public void simpleIntegerDiv() {
        assertEquals(
            new IntegerOperandToken(0),
            new Interpreter(new Parser("3 / 5").parse()).evaluate()
        );
    }

    @Test
    public void simpleIntegerMult() {
        assertEquals(
            new IntegerOperandToken(15),
            new Interpreter(new Parser("3 * 5").parse()).evaluate()
        );
    }

    @Test
    public void parenIntegerExpression() {
        assertEquals(
            new IntegerOperandToken(16),
            new Interpreter(new Parser("2 * (3 + 5)").parse()).evaluate()
        );
    }

}
