package engine;
import aux_tools.*;
import engine.auto_gen.*;
import engine.exceptions.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import exercises.*;
import java.lang.reflect.Method;

public class Commander {
	
	/**
	Possible commands for Commander
	*/
	public enum Commands {
		BUILD_ASSETS,
		CREATE_NEW_EX,
		SHOW_DEFINED_TOPICS,
		SHOW_DEFINED_TOPICS_DETAIL,
		FIND_EXERCISES_BY_TOPIC,
		EXERCISE_RUN
	}

	private static void exerciseRun(String[] args) throws Exception {
		String mainKey = args[0];
		int ex_num;
		try {
			ex_num = Integer.parseInt(mainKey.substring(2));
		} catch (NumberFormatException exception) {
			ArrayList<File> tempFiles = new Loader(Loader.PATH_TO_EXERCISES).getAllFilesFromPath();

			if (tempFiles.size() > 0) {
				File lastFile = tempFiles.get(tempFiles.size() - 1);
				ex_num 		  = Integer.parseInt(lastFile.getName().substring(Loader.EX_PREFIX.length()));
			} else {
				throw new Exception("No exercises in our app yet...");
			}
		}

		String[] invokeArguments = new String[args.length];
		invokeArguments[0] = ex_num + "";
		for (int i = 1; i < invokeArguments.length; i++) {
			invokeArguments[i] = args[i];
		}

		engine.auto_gen.Invoker.main(invokeArguments);
	}
	
	private static void showDefinedTopics() throws FileNotFoundException, IOException {
		HashSet<String> allDefinedTopics = new Informer().getAllDefinedTopics();
		Output.printVerticalList(allDefinedTopics);
	}

	private static void showDefinedTopicsDetail() throws FileNotFoundException, IOException {
		HashMap<String, HashSet<String>> allDefinedTopicsDetail = new Informer().getAllDefinedTopicsDetail();
 	    Output.printVerticalList(allDefinedTopicsDetail);
	}

	private static void findExercisesByTopic(String topic)
	throws FileNotFoundException, IOException {
		HashSet<String> allFoundExNums = new Informer().getAllExNumsByTopic(topic);
		Output.printVerticalList(allFoundExNums);
	}

	public static void main(String[] args) throws Exception {
		Commands command = KeyKeeper.getCommand(args);

		switch (command) {
			case EXERCISE_RUN : exerciseRun(args);
			break;

			case CREATE_NEW_EX : Helpers.createNewEx(args[1]);
			break;

			case BUILD_ASSETS :	Helpers.buildAssets(args[1]);
			break;

			case SHOW_DEFINED_TOPICS : showDefinedTopics();
			break;

			case SHOW_DEFINED_TOPICS_DETAIL : showDefinedTopicsDetail();
			break;

			case FIND_EXERCISES_BY_TOPIC : findExercisesByTopic(args[1]);
			break;

			default : throw new WrongCommand(command.toString());
		}
	}
}

