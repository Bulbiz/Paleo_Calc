package paleo.lib.calculator;

import java.util.Optional;
import paleo.lib.token.operand.OperandToken;

/**
 * Calculator
 */
public interface Calculator {
	public Optional<OperandToken> calculate(final String line);
}
