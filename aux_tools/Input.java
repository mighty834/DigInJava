package aux_tools;
import engine.exceptions.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Input {
	private String type;
	private String title;
	private int requestCount;
	private IValidation[] validationTypes;
	private ArrayList<String[]> historyInput;
	private Scanner scanner;

	/**
	 * @param first define type of input value
	 * @param second define title name, in input line
	 * @param third define how many times input will be requested
	 */
	public Input(String ... params) {
		this.type = "String";
		this.requestCount = 1;
		this.historyInput = new ArrayList<String[]>();
		this.scanner = new Scanner(System.in);
		this.validationTypes = null;

		if (params.length >= 1) this.type = params[0];
		this.title = "Please, enter your value of '" + this.type + "' type:";
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
					String[] temp = {inputLine, "red", exception.toString()};
					this.historyInput.add(temp);
					isValidationPass = false;
				}
				catch (NumberFormatException exception) {
					String[] temp = {inputLine, "red", "Your value must can be typecasted to '" + this.type + "'"};
					this.historyInput.add(temp);
					isValidationPass = false;
				}
			}
		} while (!isValidationPass);

		return inputLine;
	}

	private void renderPastInputs() {
		for (String[] input: this.historyInput) {
			Terminal.setColor(input[1]);
			if ((input.length > 2) && (input[2].length() > 0)) {
				System.out.printf("<<< %s | %s\n", input[0], input[2]);
			} else {
				System.out.printf("<<< %s\n", input[0]);
			}
		}

		Terminal.setColor("");
	}

	public ArrayList<String[]> getHistoryInput() {
		return this.historyInput;
	}

	public ArrayList<Object> getUserData() throws ValidationInputException {
		ArrayList<Object> result = new ArrayList<Object>();

		for (int i = 0; i < this.requestCount; i++) {
			String[] temp = {this.getUserInputLine(), "green", ""};

            try {
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
            }
            catch (NumberFormatException exception) {
                temp[1] = "red";
                temp[2] = "Your value must can be typecasted to '" + this.type + "'";
                i--;
            }

			this.historyInput.add(temp);
		}

		this.refreshScreen();
		return result;
	}

	public void refreshScreen() {
		Terminal.clear();
		this.renderPastInputs();
	}

	public void renderInvitation(String title, String type) {
		this.refreshScreen();
		Terminal.setColor(type);
		System.out.printf("| %s |\n>>> ", title);
	}

	public void setValidationTypes(IValidation[] validationTypes) {
		this.validationTypes = validationTypes;
	}
}

