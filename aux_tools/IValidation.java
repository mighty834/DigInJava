package aux_tools;

@FunctionalInterface
interface IValidation {
	public boolean validate(String value, String params);
}

