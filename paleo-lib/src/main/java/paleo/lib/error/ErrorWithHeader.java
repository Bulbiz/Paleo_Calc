package paleo.lib.error;

/**
 * Provides an default implemetation for {@link Error} with header message.
 *
 * @see Parser.UnknownSymbError
 * @see HistoricToken.InvalidHistoricTokenError
 */
public class ErrorWithHeader extends Error {

	private static final long serialVersionUID = -2412935355283804661L; ///< Generated serial version ID.

	private final String message; ///< Is the error message.
	private final String header; ///< Is the header message.

	public ErrorWithHeader(final String header, final String message) {
		this.header = header;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.header + this.message;
	}
}
