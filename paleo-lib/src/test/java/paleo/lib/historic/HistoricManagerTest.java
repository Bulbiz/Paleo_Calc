package paleo.lib.historic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import paleo.lib.parser.Parser;
import paleo.lib.token.DoubleOperandToken;
import paleo.lib.token.IntegerOperandToken;

/**
 * Unit test for {@link HistoricManager}.
 */
public class HistoricManagerTest {

	@Test
	public void addIntegerOperandToken() {
		final IntegerOperandToken op = new IntegerOperandToken(3);
		HistoricManager historic = new HistoricManager();

		historic.add(op);

		assertTrue(historic.get(1).isPresent());
		assertEquals(op, historic.get(1).get());
	}

	@Test
	public void tryToAccessToAnInexistingHistoricValue() {
		assertTrue(new HistoricManager().get(1).isEmpty());
	}

	@Test
	public void tryToAccessToAnOutOfBoundIndex() {
		final IntegerOperandToken op = new IntegerOperandToken(3);
		HistoricManager historic = new HistoricManager();

		historic.add(op);

		assertTrue(new HistoricManager().get(2).isEmpty());
	}

	@Test
	public void getTheLastHistoricValue() {
		final IntegerOperandToken lastOp = new IntegerOperandToken(3);
		HistoricManager historic = new HistoricManager();

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
		HistoricManager historic = new HistoricManager();

		historic.add(new DoubleOperandToken(2));
		historic.add(new DoubleOperandToken(2));
		historic.add(thirdOp);
		historic.add(new DoubleOperandToken(2));

		assertTrue(historic.get(3).isPresent());
		assertEquals(thirdOp, historic.get(3).get());
	}

	@Test
	public void simpleSubstitution() {
		HistoricManager historic = new HistoricManager();

		historic.add(new IntegerOperandToken(2));

		assertEquals(
			new Parser("2 + 3").parse(),
			historic.substitute(new Parser("hist(1) + 3").parse().get())
		);
	}

	@Test
	public void multipleSubstitutions() {
		HistoricManager historic = new HistoricManager();

		historic.add(new IntegerOperandToken(2));
		historic.add(new DoubleOperandToken(3.3));

		assertEquals(
			new Parser("2 + 3 * (3.3 + 5)").parse(),
			historic.substitute(new Parser("hist(1) + 3 * (hist(2) + 5)").parse().get())
		);
	}

	@Test
	public void histCmdShouldReturnLastHistoricValue() {
		HistoricManager historic = new HistoricManager();

		historic.add(new IntegerOperandToken(2));
		historic.add(new DoubleOperandToken(3.3));

		assertEquals(
			new Parser("3.3").parse(),
			historic.substitute(new Parser("hist(0)").parse().get())
		);
	}

	@Test
	public void invalidHistCmdShouldReturnEmpty() {
		assertTrue(
			new HistoricManager().substitute(new Parser("hist(1)").parse().get()).isEmpty()
		);
	}
}
