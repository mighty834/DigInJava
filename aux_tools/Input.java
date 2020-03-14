package aux_tools;
import java.util.Scanner;
import java.util.ArrayList;

public class Input {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_CLEAR = "\033[H\033[2J";

	private String type;
	private String title;
	private int requestCount;
	private IValidation[] validationTypes;
	private String[] validationParams;
	private ArrayList<String[]> historyInput;
	private Scanner scanner;

	/**
	 * @param first define type of input value
	 * @param second define title name, in input line
	 * @param third define how many times input will be requested
	 */
	Input(String ... params) {
		this.type = "String";
		this.title = "Please, enter your value of '" + this.type + "' type:";
		this.requestCount = 1;
		this.historyInput = new ArrayList<String[]>();
		this.scanner = new Scanner(System.in);
		this.validationTypes = null;

		if (params.length >= 1) this.type = params[0];
		if (params.length >= 2) this.title = params[1];
		if (params.length >= 3) this.requestCount = Integer.parseInt(params[2]);
	}

	private String getUserInputLine() throws ValidationInputException {
		String inputLine;
		boolean isValidationPass;

		do {
			renderInvitation(this.title, "ordinary");
			inputLine = this.scanner.nextLine();
			isValidationPass = true;

			if (this.validationTypes != null) {
				try {
					for (IValidation validationPass: this.validationTypes) {
						if (!validationPass.validate(inputLine)) {
							throw new ValidationInputException("One of the input validations not passed!");
						}
					}
				}
				catch (ValidationInputException exception) {
					String[] temp = {inputLine, "error"};
					this.historyInput.add(temp);
					isValidationPass = false;
				}
			}
		} while (!isValidationPass);

		return inputLine;
	}

	private void setRenderType(String type) {
		switch (type) {
			case "error": System.out.print(ANSI_RED);
			break;
			case "ordinary": System.out.print(ANSI_WHITE);
			break;
			case "success": System.out.print(ANSI_GREEN);
			break;
			case "shadow": System.out.print(ANSI_BLACK);
			break;
			default: System.out.println(ANSI_RESET);
		}
	}
				

	private void renderPastInputs() {
		for (String[] input: this.historyInput) {
			this.setRenderType(input[1]);
			System.out.printf("<<< %s\n", input[0]);
		}

		this.setRenderType("");
	}

	public ArrayList<String[]> getHistoryInput() {
		return this.historyInput;
	}

	public ArrayList<Object> getUserData() throws ValidationInputException {
		ArrayList<Object> result = new ArrayList<Object>();

		for (int i = 0; i < this.requestCount; i++) {
			String[] temp = {this.getUserInputLine(), "success"};
			switch (this.type) {
				case "String": result.add(temp[0]);
				break;
				case "Integer": result.add(Integer.parseInt(temp[0]));
				break;
				case "Long": result.add(Long.parseLong(temp[0]));
				break;
				case "Float": result.add(Float.parseFloat(temp[0]));
				break;
				case "Double": result.add(Double.parseDouble(temp[0]));
				break;
				case "Boolean": result.add(Boolean.parseBoolean(temp[0]));
				break;
				default: result.add(temp[0]);
			}

			this.historyInput.add(temp);
		}

		this.refreshScreen();
		return result;
	}

	public void refreshScreen() {
		clear();
		this.renderPastInputs();
	}

	public void renderInvitation(String title, String type) {
		this.refreshScreen();
		this.setRenderType(type);
		System.out.printf("| %s |\n>>> ", title);
	}

	public void setValidationTypes(IValidation[] validationTypes) {
		this.validationTypes = validationTypes;
	}

	public void setValidationParams(String[] validationParams) {
		this.validationParams = validationParams;
	}

	public static void clear() {
		System.out.println(ANSI_CLEAR);
		System.out.print(ANSI_RESET);
		System.out.flush();
	}
}

