package aux_tools;
import java.util.function.Function;

public class ValidationTypes {
	public static boolean upperCaseValidation(String value) {
		boolean result = true;
		
		if (!value.equals(value.toUpperCase())) {
			result = false;
		}

		return result;
	}

	public static boolean lowerCaseValidation(String value) {
		boolean result = true;
		
		if (!value.equals(value.toLowerCase())) {
			result = false;
		}
		
		return result;
	}
}

