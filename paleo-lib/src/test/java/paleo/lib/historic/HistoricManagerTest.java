package paleo.lib.historic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
}
