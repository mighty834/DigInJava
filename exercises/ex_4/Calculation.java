package exercises;
import aux_tools.*;
import java.util.ArrayList;

public class Calculation {
	public static final String POSSIBLE_OPERATOR_SIGNS = "+-*/";

	public static void getNumResult(String expression) {
		String usedOperators = "";
		ArrayList<Double> usedOperands = new ArrayList<Double>();

		int i = 0;
		for (String exp : split(expression, i)) {
			try {

			} catch (NumberFormatException exception) {

			}
		}
	}

	private static String[] split(String expression, int index) {
		if (index < POSSIBLE_OPERATOR_SIGNS.length()) {
			return expression.split("\\" + String.valueOf(POSSIBLE_OPERATOR_SIGNS.charAt(index)));
		} else {
			String[] result = {expression};
			return result;
		}
	}
}

