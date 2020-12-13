package paleo.lib.historic;

import paleo.lib.token.Yytoken;

/**
 * Models a historic {@link Yytoken} used by {@link HistoricManager}.
 */
public final class HistoricToken implements Yytoken {

	private int arg; ///< Is the historic argument.

	/**
	 * {@link HistoricToken} constructor.
	 *
	 * @param arg is the historic command argument.
	 */
	public HistoricToken(final int arg) {
		this.arg = arg;
	}

	/**
	 * @return the argument.
	 */
	public int getArg() {
		return arg;
	}

	@Override
	public boolean isAnOperandToken() {
		return false;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof HistoricToken && this.arg == ((HistoricToken)o).getArg();
	}
}
