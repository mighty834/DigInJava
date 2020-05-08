package exercises;
import aux_tools.*;

class MainAWT {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws MainException {
        if (args.length > 0) {
            for (String arg: args) {
                Output.println("ARG>>> " + arg);
            }
        } else {
            Output.println("No any args here...");
        }
    }
}

