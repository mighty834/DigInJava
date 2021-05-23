package engine;
import aux_tools.*;
import engine.auto_gen.*;
import engine.exceptions.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.File;
import exercises.*;
import java.lang.reflect.Method;

public class Commander {
	public static final String BUILD_ASSETS  = "-ba";
	public static final String CREATE_NEW_EX = "-cne";
	public static final String SHOW_DEFINED_TOPICS   = "-sdt";
	public static final String EX_KEY    	 = "--";

	public static void main(String[] args) throws Exception {
		String command = "";
		String[] arguments = {""};
		if (args.length > 0) command   = args[0];
		if (args.length > 1) arguments = Arrays.copyOfRange(args, 1, args.length); 

		if (command.contains(EX_KEY)) {
			int ex_num;
			try {
				ex_num = Integer.parseInt(command.substring(2));
			} catch (NumberFormatException exception) {
				ArrayList<File> tempFiles = new Loader(Loader.PATH_TO_EXERCISES).getAllFilesFromPath();

				if (tempFiles.size() > 0) {
					File lastFile = tempFiles.get(tempFiles.size() - 1);
					ex_num 		  = Integer.parseInt(lastFile.getName().substring(Loader.EX_PREFIX.length()));
				} else {
					throw new Exception("No exercises in our app yet...");
				}
			}

			String[] invokeArguments = new String[arguments.length + 1];
			invokeArguments[0] = ex_num + "";
			for (int i = 1; i < invokeArguments.length; i++) {
				invokeArguments[i] = arguments[i - 1];
			}

			engine.auto_gen.Invoker.main(invokeArguments);

		} else {
			switch (command) {
				case CREATE_NEW_EX : {
					if (arguments == null) throw new WrongArguments(CREATE_NEW_EX);
					Helpers.createNewEx(arguments[0]);
				}
				break;

				case BUILD_ASSETS : {
					if (arguments[0] == null) throw new MissingBuildPath();
					Helpers.buildAssets(arguments[0]);
				}
				break;

				case SHOW_DEFINED_TOPICS : {
					HashSet<String> allDefinedTopics = new Informer().getAllDefinedTopics();
					Output.printVerticalList(allDefinedTopics);
				}
				break;

				case "" : throw new EmptyCommand();
				default : throw new WrongCommand(command);
			}
		}
	}
}

