package paleo.lib.historic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Optional;

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
		assertEquals(op, historic.get(0));
	}

	@Test
	public void addIntegerOperandToken() {
		final IntegerOperandToken op = new IntegerOperandToken(3);
		HistoricManager historic = new HistoricManager();

		historic.add(op);
		assertEquals(op, historic.get(0));
	}
}
