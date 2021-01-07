package paleo.lib.historic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import paleo.lib.parser.JFLexParser;
import paleo.lib.token.operand.*;

/**
 * Unit test for {@link TabHistoricManager}.
 */
public class TabHistoricManagerTest {

	@Test
	public void addIntegerOperandToken() {
		final IntegerOperandToken op = new IntegerOperandToken(3);
		TabHistoricManager historic = new TabHistoricManager();

		historic.add(op);

		assertTrue(historic.get(1).isPresent());
		assertEquals(op, historic.get(1).get());
	}

	@Test
	public void tryToAccessToAnInexistingHistoricValue() {
		assertTrue(new TabHistoricManager().get(1).isEmpty());
	}

	@Test
	public void tryToAccessToAnOutOfBoundIndex() {
		final IntegerOperandToken op = new IntegerOperandToken(3);
		TabHistoricManager historic = new TabHistoricManager();

		historic.add(op);

		assertTrue(new TabHistoricManager().get(2).isEmpty());
	}

	@Test
	public void getTheLastHistoricValue() {
		final IntegerOperandToken lastOp = new IntegerOperandToken(3);
		TabHistoricManager historic = new TabHistoricManager();

		historic.add(new DoubleOperandToken(2));
		historic.add(new DoubleOperandToken(2));
		historic.add(new DoubleOperandToken(2));
		historic.add(lastOp);

		assertTrue(historic.getLast().isPresent());
		assertEquals(lastOp, historic.getLast().get());
	}

	@Test
	public void getASpecifiedIndex() {
		final IntegerOperandToken thirdOp = new IntegerOperandToken(3);
		TabHistoricManager historic = new TabHistoricManager();

		historic.add(new DoubleOperandToken(2));
		historic.add(new DoubleOperandToken(2));
		historic.add(thirdOp);
		historic.add(new DoubleOperandToken(2));

		assertTrue(historic.get(3).isPresent());
		assertEquals(thirdOp, historic.get(3).get());
	}

	@Test
	public void simpleSubstitution() {
		TabHistoricManager historic = new TabHistoricManager();

		historic.add(new IntegerOperandToken(2));

		assertEquals(
			new JFLexParser().parse("2 + 3").right().value(),
			historic
				.substitute(new JFLexParser().parse("hist(1) + 3").right().value())
				.get()
		);
	}

	@Test
	public void multipleSubstitutions() {
		TabHistoricManager historic = new TabHistoricManager();

		historic.add(new IntegerOperandToken(2));
		historic.add(new DoubleOperandToken(3.3));

		assertEquals(
			new JFLexParser().parse("2 + 3 * (3.3 + 5)").right().value(),
			historic
				.substitute(
					new JFLexParser().parse("hist(1) + 3 * (hist(2) + 5)").right().value()
				)
				.get()
		);
	}

	@Test
	public void histCmdShouldReturnLastHistoricValue() {
		TabHistoricManager historic = new TabHistoricManager();

		historic.add(new IntegerOperandToken(2));
		historic.add(new DoubleOperandToken(3.3));

		assertEquals(
			new JFLexParser().parse("3.3").right().value(),
			historic.substitute(new JFLexParser().parse("hist(0)").right().value()).get()
		);
	}

	@Test
	public void invalidHistCmdShouldReturnEmpty() {
		assertTrue(
			new TabHistoricManager()
				.substitute(new JFLexParser().parse("hist(1)").right().value())
				.isEmpty()
		);
	}
}
