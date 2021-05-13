package aux_tools;
import java.util.Arrays;

public class Commander {
	public static final String BUILD_ASSETS_KEY = "-ba";
	public static final String CREATE_NEW_EX    = "-cne";

	public static void main(String[] args) throws Exception {
		String command = "";
		String[] arguments = {""};
		if (args.length > 0) command   = args[0];
		if (args.length > 1) arguments = Arrays.copyOfRange(args, 1, args.length); 

		switch (command) {
			case CREATE_NEW_EX : {
				if (arguments == null) throw new WrongArguments(CREATE_NEW_EX);
				Helpers.createNewEx(arguments[0]);
			}
			break;
		}
	}
}

