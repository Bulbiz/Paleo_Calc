package paleo.lib.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.Test;
import paleo.lib.historic.HistoricToken;
import paleo.lib.token.Yytoken;
import paleo.lib.token.operand.*;
import paleo.lib.token.operation.*;

/**
 * Unit test for {@link JFLexParser}.
 */
public class JFLexParserTest {

	/**
	 * Compares two {@link Queue} of {@link Yytoken}.
	 *
	 * @param expectedTokens is the expected queue.
	 * @param actualTokens is the actual queue.
	 * @return true if both queues have the same tokens in the same order.
	 */
	private boolean areTokenQueuesEqual(
		final Queue<Yytoken> expectedTokens,
		final Queue<Yytoken> actualTokens
	) {
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
	private Queue<Yytoken> createTokenQueue(final Yytoken... tokens) {
		final Queue<Yytoken> tokenQueue = new LinkedList<>();

		for (final Yytoken token : tokens) {
			tokenQueue.add(token);
		}

		return tokenQueue;
	}

	@Test
	public void shouldReturnEmptyListWithEmptyString() {
		assertEquals(0, new JFLexParser().parse("").right().value().size());
	}

	@Test
	public void simpleIntegerToken() {
		final Queue<Yytoken> actualTokens = new JFLexParser().parse("3").right().value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			new IntegerOperandToken(3)
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void simpleSumExpression() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("3 + 5")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			new IntegerOperandToken(3),
			new SumOperationToken(),
			new IntegerOperandToken(5)
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void simpleMultExpression() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("3 * 5")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			new IntegerOperandToken(3),
			new MultOperationToken(),
			new IntegerOperandToken(5)
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void simpleParenExpression() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("(3 * 5)")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			ParenOperationToken.LEFT,
			new IntegerOperandToken(3),
			new MultOperationToken(),
			new IntegerOperandToken(5),
			ParenOperationToken.RIGHT
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void simpleDoubleExpression() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("(3.4 * 5.6)")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			ParenOperationToken.LEFT,
			new DoubleOperandToken(3.4),
			new MultOperationToken(),
			new DoubleOperandToken(5.6),
			ParenOperationToken.RIGHT
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void intAndDoubleExpression() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("(3.4 * 5.6) / 3")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			ParenOperationToken.LEFT,
			new DoubleOperandToken(3.4),
			new MultOperationToken(),
			new DoubleOperandToken(5.6),
			ParenOperationToken.RIGHT,
			new DivOperationToken(),
			new IntegerOperandToken(3)
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void expressionWithANegativeDouble() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("3.4 * -5.6")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			new DoubleOperandToken(3.4),
			new MultOperationToken(),
			new DoubleOperandToken(-5.6)
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void expressionWithANegativeInteger() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("3.4 * -5")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			new DoubleOperandToken(3.4),
			new MultOperationToken(),
			new IntegerOperandToken(-5)
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void notFormattedExpression() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("1/3.4* -5 ))")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			new IntegerOperandToken(1),
			new DivOperationToken(),
			new DoubleOperandToken(3.4),
			new MultOperationToken(),
			new IntegerOperandToken(-5),
			ParenOperationToken.RIGHT,
			ParenOperationToken.RIGHT
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void expressionWithMultipleParenDepth() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("(2 - 3 * 4 + (2 + 4 - 6 * 5)) + 1")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			ParenOperationToken.LEFT,
			new IntegerOperandToken(2),
			new SubOperationToken(),
			new IntegerOperandToken(3),
			new MultOperationToken(),
			new IntegerOperandToken(4),
			new SumOperationToken(),
			ParenOperationToken.LEFT,
			new IntegerOperandToken(2),
			new SumOperationToken(),
			new IntegerOperandToken(4),
			new SubOperationToken(),
			new IntegerOperandToken(6),
			new MultOperationToken(),
			new IntegerOperandToken(5),
			ParenOperationToken.RIGHT,
			ParenOperationToken.RIGHT,
			new SumOperationToken(),
			new IntegerOperandToken(1)
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void simpleBooleanToken() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("true")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			new BooleanOperandToken(true)
		);
		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void simpleBooleanExpression() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("true  and not false")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			new BooleanOperandToken(true),
			new AndOperationToken(),
			new NotOperationToken(),
			new BooleanOperandToken(false)
		);
		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void simpleParenBooleanExpression() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("true  and (not false and true)")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			new BooleanOperandToken(true),
			new AndOperationToken(),
			ParenOperationToken.LEFT,
			new NotOperationToken(),
			new BooleanOperandToken(false),
			new AndOperationToken(),
			new BooleanOperandToken(true),
			ParenOperationToken.RIGHT
		);
		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void expressionWithSimpleHistCall() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("hist(1) + 3")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			new HistoricToken(1),
			new SumOperationToken(),
			new IntegerOperandToken(3)
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void expressionWithMultipleHistCall() {
		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("hist(1) + hist(1) - (hist(3)))")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			new HistoricToken(1),
			new SumOperationToken(),
			new HistoricToken(1),
			new SubOperationToken(),
			ParenOperationToken.LEFT,
			new HistoricToken(3),
			ParenOperationToken.RIGHT,
			ParenOperationToken.RIGHT
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}

	@Test
	public void histCmdWihoutArgShouldReturnEmpty() {
		assertTrue(new JFLexParser().parse("hist() + hist(1)(hist(3)))").isLeft());
	}

	@Test
	public void histCmdWhithMissingParenShouldReturnEmpty() {
		assertTrue(new JFLexParser().parse("hist1) + hist(1)(hist(3)))").isLeft());
	}

	@Test
	public void histCmdWhithNotValidArgShouldReturnEmpty() {
		assertTrue(new JFLexParser().parse("hist1) + hist(1(hist(3)))").isLeft());
	}

	@Test
	public void expressionWithSimpleSet() {
		final SetOperandToken set = new SetOperandToken();
		set.addAll(List.of(new IntegerOperandToken(1)));

		final Queue<Yytoken> actualTokens = new JFLexParser()
			.parse("{1} union 3")
			.right()
			.value();
		final Queue<Yytoken> expectedTokens = createTokenQueue(
			set,
			new UnionOperationToken(),
			new IntegerOperandToken(3)
		);

		assertTrue(areTokenQueuesEqual(expectedTokens, actualTokens));
	}
}
