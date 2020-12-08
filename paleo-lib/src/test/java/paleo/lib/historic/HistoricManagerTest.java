package paleo.lib.historic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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

		assertTrue(historic.get(0).isPresent());
		assertEquals(op, historic.get(0).get());
	}

	@Test
	public void tryToAccessToAnInexistingHistoricValue() {
		assertTrue(new HistoricManager().get(0).isEmpty());
	}
}
