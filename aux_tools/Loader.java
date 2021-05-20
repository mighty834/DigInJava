package aux_tools;
import aux_tools.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

public class Loader {
	public static final String PATH_TO_BUILD	 	 = "./classes/";
	public static final String PATH_TO_EXERCISES 	 = "./exercises/";
	public static final String EX_PREFIX         	 = "ex_";
	public static final String EX_ENTRY_POINT_PREFIX = "Main"; 
	public static final String JAVA_FILES_SUFFIX     = ".java";
	
	private String _path;

	public Loader(String path) {
		_path = path;
	}

	public static ArrayList<File> getAllFilesFromPath(String path) {
		File currentDir = new File(path);
		File[] files    = currentDir.listFiles();

		return new ArrayList<File>(Arrays.asList(files));
	}

	public ArrayList<File> getAllFilesFromPath() {
		return getAllFilesFromPath(_path);
	}

	public static ArrayList<File> getAllMainFiles() {
		ArrayList<File> result = new ArrayList<File>();
		ArrayList<File> allExercises = getAllFilesFromPath(PATH_TO_EXERCISES);

		for (File exerciseDir : allExercises) {
			File[] exFiles = exerciseDir.listFiles();
			for (File file : exFiles) {
				if (file.getName().contains(EX_ENTRY_POINT_PREFIX)) {
					result.add(file);
				}
			}
		}

		return result;
	}

	public static ArrayList<String> getAllMainClassesNames() {
		ArrayList<String> result = new ArrayList<String>();
		for (File mainFile : getAllMainFiles()) {
			String fileName = mainFile.getName();
			result.add(fileName.substring(0, fileName.length() - JAVA_FILES_SUFFIX.length()));
		}

		return result;
	}

	public static ArrayList<File> getAllTopicsFiles() {
		ArrayList<File> result = new ArrayList<File>();
		return result;
	}

	public static void main(String[] args) {
		Output.println("This is Loader!");
	}
}

