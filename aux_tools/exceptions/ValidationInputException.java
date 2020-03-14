package aux_tools;

public class ValidationInputException extends Exception {
	private String message;

	ValidationInputException(String message) {
		this.message = "You have validation problem on input with this message:\n" +
						message;
	}

	@Override
	public String toString() {
		return this.message;
	}
}

