package engine.exceptions;
import aux_tools.Loader;

public class DoubleMainClassException extends MainException {
	private String message;

	public DoubleMainClassException(String className) {
		this.message = "Class with name: " + className + " already exist.\n" +
					   "List of occupied class names: " + Loader.getAllMainClassesNames();
	}

	@Override
	public String toString() {
		return this.message;
	}
}

