package engine.exceptions;

public class ValidationInputException extends MainException {
	private String message;

	public ValidationInputException(String message) {
		this.message = "Validation problem - " + message;
	}

	@Override
	public String toString() {
		return this.message;
	}
}

