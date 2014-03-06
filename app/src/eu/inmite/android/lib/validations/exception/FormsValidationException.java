package eu.inmite.android.lib.validations.exception;

/**
 * @author Tomas Vondracek
 */
public class FormsValidationException extends RuntimeException {

	public FormsValidationException() {
	}

	public FormsValidationException(String detailMessage) {
		super(detailMessage);
	}

	public FormsValidationException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public FormsValidationException(Throwable throwable) {
		super(throwable);
	}
}
