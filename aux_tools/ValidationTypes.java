package aux_tools;
import java.util.function.Function;

public class ValidationTypes {
	public static boolean lettersCaseValidation(String value, String params) {
		boolean result  = true;

		switch (params) {
			case "upper": if (!value.equals(value.toUpperCase())) result = false;
			break;
			case "lower": if (!value.equals(value.toLowerCase())) result = false;
			break;
			case "mixed":
				if ((!value.equals(value.toLowerCase())) && (!value.equals(value.toUpperCase()))) {
					result = true;
				} else {
					result = false;
				}
			break;
			default: result = true;
		}

		return result;
	}

	public static boolean minStrLengthValidation(String value, String params) {
		boolean result = true;
		int min = Integer.parseInt(params);

		if (value.length() < min) {
			result = false;
		}

		return result;
	}

	public static boolean maxStrLengthValidation(String value, String params) {
		boolean result = true;
		int max = Integer.parseInt(params);

		if (value.length() > max) {
			result = false;
		}

		return result;
	}

	public static boolean greaterThanValidation(String value, String params) {
		boolean result = true;
		int number = Integer.parseInt(params);
		int numericValue = Integer.parseInt(value);		

		if (numericValue <= number) {
			result = false;
		}

		return result;
	}

	public static boolean lowerThanValidation(String value, String params) {
		boolean result = true;
		int number = Integer.parseInt(params);
		int numericValue = Integer.parseInt(value);

		if (numericValue >= number) {
			result = false;
		}

		return result;
	}
}

