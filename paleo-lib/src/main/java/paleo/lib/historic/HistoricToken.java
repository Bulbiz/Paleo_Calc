package paleo.lib.historic;

import paleo.lib.error.ErrorWithHeader;
import paleo.lib.token.Yytoken;

/**
 * Models a historic {@link Yytoken} used by an {@link HistoricManager} implementation
 * such as {@link TabHistoricManager}.
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
		return o instanceof HistoricToken && this.arg == ((HistoricToken) o).getArg();
	}

	/**
	 * {@link Error} raised when trying to parse an invalid historic token command.
	 * @see ErrorWithHeader
	 */
	public static class InvalidHistoricTokenError extends ErrorWithHeader {

		private static final long serialVersionUID = 6532253696971122665L; ///< Generated serial version ID.
		private static final String header = "Invalid historic command"; ///< Default header.

		public InvalidHistoricTokenError() {
			super(header, "");
		}
	}
}
