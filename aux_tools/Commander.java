package aux_tools;
import engine.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;
import exercises.*;
import java.lang.reflect.Method;

public class Commander {
	public static final String BUILD_ASSETS  = "-ba";
	public static final String CREATE_NEW_EX = "-cne";
	public static final String SHOW_TOPICS   = "-st";
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

			Loader loader = new Loader(Loader.PATH_TO_EXERCISES + Loader.EX_PREFIX + ex_num + "/");
			for (File file : loader.getAllFilesFromPath()) {
				if (file.getName().contains("Main")) {
					String name = "exercises."  + file.getName().substring(0, 7);
					Output.println(name);

					Class clazz = Class.forName(name);
					for (Method method : clazz.getMethods()) {
						Output.println(method, Output.YELLOW_COLOR);
						if (method.getName().contains("help")) {
							Object[] obj = new Object[arguments.length];
							for (int i = 0; i < arguments.length; i++) {
								obj[i] = arguments[i];
							}

							method.invoke(clazz);
						}
					}
					
					//MainNIO.main(arguments);
				}	
			}

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

				case SHOW_TOPICS : {
					Output.println("Here will be list with all topics...", Output.GREEN_COLOR);	
				}
				break;

				case "" : throw new EmptyCommand();
				default : throw new WrongCommand(command);
			}
		}
	}
}

