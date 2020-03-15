package aux_tools;

public class ValidationInputException extends Exception {
	private String message;

	ValidationInputException(String message) {
		this.message = "Validation problem - " + message;
	}

	@Override
	public String toString() {
		return this.message;
	}
}

