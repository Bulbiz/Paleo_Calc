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

}
