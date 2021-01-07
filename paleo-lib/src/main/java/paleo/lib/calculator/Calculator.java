package paleo.lib.calculator;

import fj.data.Either;
import java.util.Optional;
import paleo.lib.token.operand.OperandToken;

/**
 * {@link Calculator} provides an interface in order to "calculate a line", means :
 *
 *   1. Parse the line.
 *   2. Evaluate the parsed line.
 *   3. Return the resulting value if the line corresponds to a valid
 *   mathematical expression.
 */
@FunctionalInterface
public interface Calculator {
	public Optional<Either<Throwable, OperandToken>> calculate(final String line);
}
