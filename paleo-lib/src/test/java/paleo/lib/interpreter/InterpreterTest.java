package paleo.lib.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Optional;
import org.junit.Ignore;
import org.junit.Test;
import paleo.lib.parser.Parser;
import paleo.lib.token.BooleanOperandToken;
import paleo.lib.token.BooleanOperandToken;
import paleo.lib.token.DoubleOperandToken;
import paleo.lib.token.IntegerOperandToken;
import paleo.lib.token.SetOperandToken;

/**
 * Unit test for {@link Interpreter}.
 */
public class InterpreterTest {

	@Test
	public void withOnlyOneIntegerOperand() {
		assertEquals(
			new IntegerOperandToken(3),
			new Interpreter(new Parser("3").parse().get()).evaluate()
		);
	}

	@Test
	public void simpleIntegerSum() {
		assertEquals(
			new IntegerOperandToken(8),
			new Interpreter(new Parser("3 + 5").parse().get()).evaluate()
		);
	}

	@Test
	public void simpleIntegerSub() {
		assertEquals(
			new IntegerOperandToken(-2),
			new Interpreter(new Parser("3 - 5").parse().get()).evaluate()
		);
	}

	@Test
	public void simpleIntegerDiv() {
		assertEquals(
			new IntegerOperandToken(0),
			new Interpreter(new Parser("3 / 5").parse().get()).evaluate()
		);
	}

	@Test
	public void simpleIntegerMult() {
		assertEquals(
			new IntegerOperandToken(15),
			new Interpreter(new Parser("3 * 5").parse().get()).evaluate()
		);
	}

	@Test
	public void simpleParenIntegerExpression() {
		assertEquals(
			new IntegerOperandToken(16),
			new Interpreter(new Parser("2 * (3 + 5)").parse().get()).evaluate()
		);
	}

	@Test
	public void multipleParenIntegerExpression() {
		assertEquals(
			new IntegerOperandToken(35),
			new Interpreter(new Parser("7 * ((8 + 3) / 2)").parse().get()).evaluate()
		);
	}

	@Test
	public void testOperationPriority() {
		assertEquals(
			new IntegerOperandToken(-24),
			new Interpreter(new Parser("2 + 4 - 6 * 5").parse().get()).evaluate()
		);
	}

	@Test
	public void multipleParenIntegerExpressionWithOperationPriority() {
		assertEquals(
			new IntegerOperandToken(-33),
			new Interpreter(new Parser("(2 - 3 * 4 + (2 + 4 - 6 * 5)) + 1").parse().get())
				.evaluate()
		);
	}

	@Test
	public void simpleDoubleSum() {
		assertEquals(
			new DoubleOperandToken(8.8),
			new Interpreter(new Parser("3.4 + 5.4").parse().get()).evaluate()
		);
	}

	@Test
	public void simpleParenDoubleExpression() {
		assertEquals(
			new DoubleOperandToken(17.6),
			new Interpreter(new Parser("2.0 * (3.4 + 5.4)").parse().get()).evaluate()
		);
	}

	@Test
	public void integerTimesDouble() {
		assertEquals(
			new DoubleOperandToken(9.0),
			new Interpreter(new Parser("2 * 4.5").parse().get()).evaluate()
		);
	}

	@Test
	public void multipleParenIntegerDoubleExpression() {
		assertEquals(
			new DoubleOperandToken(5.0),
			new Interpreter(new Parser("(2 - 4.5) * (4 - 6)").parse().get()).evaluate()
		);
	}

	@Test
	public void divideByZeroShouldThrowAnException() {
		try {
			new Interpreter(new Parser("3 / 0").parse().get()).evaluate();
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void simpleBooleanToken() {
		assertEquals(
			new BooleanOperandToken(true),
			new Interpreter(new Parser("true").parse().get()).evaluate()
		);
	}

	@Test
	public void simpleOrBooleanExpression() {
		assertEquals(
			new BooleanOperandToken(true),
			new Interpreter(new Parser("true or false").parse().get()).evaluate()
		);
	}

	@Test
	public void simpleAndBooleanExpression() {
		assertEquals(
			new BooleanOperandToken(false),
			new Interpreter(new Parser("true and false").parse().get()).evaluate()
		);
	}

	@Test
	public void simpleNotBooleanExpression() {
		assertEquals(
			new BooleanOperandToken(false),
			new Interpreter(new Parser("not true").parse().get()).evaluate()
		);
	}

	@Test
	public void parenBooleanExpression() {
		assertEquals(
			new BooleanOperandToken(false),
			new Interpreter(new Parser("not (true or (true and false))").parse().get())
				.evaluate()
		);
	}

	@Test
	public void simpleEmptySet() {
		SetOperandToken set = new SetOperandToken();
		set.addAll(List.of());
		assertEquals(
			set,
			new Interpreter(new Parser("{ }").parse().get()).evaluate()
		);
	}

	@Test
	public void simpleIntegerSet() {
		SetOperandToken set = new SetOperandToken();
		set.addAll(List.of(new IntegerOperandToken(3)));
		assertEquals(
			set,
			new Interpreter(new Parser("{ 3 }").parse().get()).evaluate()
		);
	}

	@Test
	public void simpleDoubleSet() {
		SetOperandToken set = new SetOperandToken();
		set.addAll(List.of(new DoubleOperandToken(-3.5)));
		assertEquals(
			set,
			new Interpreter(new Parser("{ -3.5 }").parse().get()).evaluate()
		);
	}

	@Test
	public void simpleBooleanSet() {
		SetOperandToken set = new SetOperandToken();
		set.addAll(List.of(new BooleanOperandToken(true)));
		assertEquals(
			set,
			new Interpreter(new Parser("{ true }").parse().get()).evaluate()
		);
	}

	@Test
	public void multitypedSet() {
		SetOperandToken set = new SetOperandToken();
		set.addAll(
			List.of(
				new BooleanOperandToken(true),
				new DoubleOperandToken(1.0),
				new BooleanOperandToken(false),
				new IntegerOperandToken(5)
			)
		);
		assertEquals(
			set,
			new Interpreter(new Parser("{ true ; 1.0 ; false ; 5 }").parse().get())
				.evaluate()
		);
	}

	@Test
	public void unionMultitypedSetExpression() {
		SetOperandToken set = new SetOperandToken();
		set.addAll(
			List.of(
				new BooleanOperandToken(true),
				new BooleanOperandToken(false),
				new IntegerOperandToken(1)
			)
		);
		assertEquals(
			set,
			new Interpreter(
				new Parser("{ true } union {false ; true ; false ; 1}").parse().get()
			)
				.evaluate()
		);
	}

	@Test
	public void interBooleanSetExpression() {
		SetOperandToken set = new SetOperandToken();
		set.addAll(List.of(new BooleanOperandToken(true)));
		assertEquals(
			set,
			new Interpreter(
				new Parser("{ true } inter {false ; true ; false}").parse().get()
			)
				.evaluate()
		);
	}

	@Test
	public void diffMultitypedSetExpression() {
		SetOperandToken set = new SetOperandToken();
		set.addAll(List.of(new DoubleOperandToken(1.0)));
		assertEquals(
			set,
			new Interpreter(
				new Parser("{ true ; 1.0 } diff {false ; true ; false}").parse().get()
			)
				.evaluate()
		);
	}
}
