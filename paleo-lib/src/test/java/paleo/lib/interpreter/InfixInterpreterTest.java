package paleo.lib.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Optional;
import org.junit.Ignore;
import org.junit.Test;
import paleo.lib.parser.JFLexParser;
import paleo.lib.token.operand.*;

/**
 * Unit test for {@link InfixInterpreter}.
 */
public class InfixInterpreterTest {

	@Test
	public void withOnlyOneIntegerOperand() {
		assertEquals(
			new IntegerOperandToken(3),
			new InfixInterpreter(new JFLexParser().parse("3").right().value()).evaluate()
		);
	}

	@Test
	public void simpleIntegerSum() {
		assertEquals(
			new IntegerOperandToken(8),
			new InfixInterpreter(new JFLexParser().parse("3 + 5").right().value())
				.evaluate()
		);
	}

	@Test
	public void simpleIntegerSub() {
		assertEquals(
			new IntegerOperandToken(-2),
			new InfixInterpreter(new JFLexParser().parse("3 - 5").right().value())
				.evaluate()
		);
	}

	@Test
	public void simpleIntegerDiv() {
		assertEquals(
			new IntegerOperandToken(0),
			new InfixInterpreter(new JFLexParser().parse("3 / 5").right().value())
				.evaluate()
		);
	}

	@Test
	public void simpleIntegerMult() {
		assertEquals(
			new IntegerOperandToken(15),
			new InfixInterpreter(new JFLexParser().parse("3 * 5").right().value())
				.evaluate()
		);
	}

	@Test
	public void simpleParenIntegerExpression() {
		assertEquals(
			new IntegerOperandToken(16),
			new InfixInterpreter(new JFLexParser().parse("2 * (3 + 5)").right().value())
				.evaluate()
		);
	}

	@Test
	public void multipleParenIntegerExpression() {
		assertEquals(
			new IntegerOperandToken(35),
			new InfixInterpreter(
				new JFLexParser().parse("7 * ((8 + 3) / 2)").right().value()
			)
				.evaluate()
		);
	}

	@Test
	public void testOperationPriority() {
		assertEquals(
			new IntegerOperandToken(-24),
			new InfixInterpreter(new JFLexParser().parse("2 + 4 - 6 * 5").right().value())
				.evaluate()
		);
	}

	@Test
	public void multipleParenIntegerExpressionWithOperationPriority() {
		assertEquals(
			new IntegerOperandToken(-33),
			new InfixInterpreter(
				new JFLexParser()
					.parse("(2 - 3 * 4 + (2 + 4 - 6 * 5)) + 1")
					.right()
					.value()
			)
				.evaluate()
		);
	}

	@Test
	public void simpleDoubleSum() {
		assertEquals(
			new DoubleOperandToken(8.8),
			new InfixInterpreter(new JFLexParser().parse("3.4 + 5.4").right().value())
				.evaluate()
		);
	}

	@Test
	public void simpleParenDoubleExpression() {
		assertEquals(
			new DoubleOperandToken(17.6),
			new InfixInterpreter(
				new JFLexParser().parse("2.0 * (3.4 + 5.4)").right().value()
			)
				.evaluate()
		);
	}

	@Test
	public void integerTimesDouble() {
		assertEquals(
			new DoubleOperandToken(9.0),
			new InfixInterpreter(new JFLexParser().parse("2 * 4.5").right().value())
				.evaluate()
		);
	}

	@Test
	public void multipleParenIntegerDoubleExpression() {
		assertEquals(
			new DoubleOperandToken(5.0),
			new InfixInterpreter(
				new JFLexParser().parse("(2 - 4.5) * (4 - 6)").right().value()
			)
				.evaluate()
		);
	}

	@Test
	public void divideByZeroShouldThrowAnException() {
		try {
			new InfixInterpreter(new JFLexParser().parse("3 / 0").right().value())
				.evaluate();
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void simpleBooleanToken() {
		assertEquals(
			new BooleanOperandToken(true),
			new InfixInterpreter(new JFLexParser().parse("true").right().value())
				.evaluate()
		);
	}

	@Test
	public void simpleOrBooleanExpression() {
		assertEquals(
			new BooleanOperandToken(true),
			new InfixInterpreter(new JFLexParser().parse("true or false").right().value())
				.evaluate()
		);
	}

	@Test
	public void simpleAndBooleanExpression() {
		assertEquals(
			new BooleanOperandToken(false),
			new InfixInterpreter(
				new JFLexParser().parse("true and false").right().value()
			)
				.evaluate()
		);
	}

	@Test
	public void simpleNotBooleanExpression() {
		assertEquals(
			new BooleanOperandToken(false),
			new InfixInterpreter(new JFLexParser().parse("not true").right().value())
				.evaluate()
		);
	}

	@Test
	public void parenBooleanExpression() {
		assertEquals(
			new BooleanOperandToken(false),
			new InfixInterpreter(
				new JFLexParser().parse("not (true or (true and false))").right().value()
			)
				.evaluate()
		);
	}

	@Test
	public void simpleEmptySet() {
		SetOperandToken set = new SetOperandToken();
		set.addAll(List.of());
		assertEquals(
			set,
			new InfixInterpreter(new JFLexParser().parse("{ }").right().value())
				.evaluate()
		);
	}

	@Test
	public void simpleIntegerSet() {
		SetOperandToken set = new SetOperandToken();
		set.addAll(List.of(new IntegerOperandToken(3)));
		assertEquals(
			set,
			new InfixInterpreter(new JFLexParser().parse("{ 3 }").right().value())
				.evaluate()
		);
	}

	@Test
	public void simpleDoubleSet() {
		SetOperandToken set = new SetOperandToken();
		set.addAll(List.of(new DoubleOperandToken(-3.5)));
		assertEquals(
			set,
			new InfixInterpreter(new JFLexParser().parse("{ -3.5 }").right().value())
				.evaluate()
		);
	}

	@Test
	public void simpleBooleanSet() {
		SetOperandToken set = new SetOperandToken();
		set.addAll(List.of(new BooleanOperandToken(true)));
		assertEquals(
			set,
			new InfixInterpreter(new JFLexParser().parse("{ true }").right().value())
				.evaluate()
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
			new InfixInterpreter(
				new JFLexParser().parse("{ true ; 1.0 ; false ; 5 }").right().value()
			)
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
			new InfixInterpreter(
				new JFLexParser()
					.parse("{ true } union {false ; true ; false ; 1}")
					.right()
					.value()
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
			new InfixInterpreter(
				new JFLexParser()
					.parse("{ true } inter {false ; true ; false}")
					.right()
					.value()
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
			new InfixInterpreter(
				new JFLexParser()
					.parse("{ true ; 1.0 } diff {false ; true ; false}")
					.right()
					.value()
			)
				.evaluate()
		);
	}
}
