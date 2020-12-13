package paleo.lib.historic.exception;

/**
 * {@link Error} raised when trying to parse an invalid historic token command.
 */
public class InvalidHistoricTokenException extends Error {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Invalid 'hist' command";
	}
}
