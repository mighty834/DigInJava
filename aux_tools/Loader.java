package aux_tools;
import aux_tools.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;

public class Loader {
	public static final String PATH_TO_BUILD	 	 = "./classes/";
	public static final String PATH_TO_EXERCISES 	 = "./exercises/";
	public static final String EX_PREFIX         	 = "ex_";
	public static final String EX_ENTRY_POINT_PREFIX = "Main"; 
	public static final String JAVA_FILES_SUFFIX     = ".java";
	public static final String TASK_FILE_NAME        = "task.md";	

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

	public static ArrayList<File> getAllTaskFiles() {
		ArrayList<File> result 		 = new ArrayList<File>();
		ArrayList<File> exercisesDir = getAllFilesFromPath(PATH_TO_EXERCISES);
		
		for (File exDir : exercisesDir) {
			File[] innerFiles = exDir.listFiles();
			for (File innerFile : innerFiles) {
				if (innerFile.getName().equals(TASK_FILE_NAME)) {
					result.add(innerFile);
				}	
			}
		}

		return result;
	}

	public static String getFileContent(File file) throws FileNotFoundException, IOException {
		String result 		   = "";
		RandomAccessFile rFile = new RandomAccessFile(file, "r");
		FileChannel channel    = rFile.getChannel();
		ByteBuffer buffer      = ByteBuffer.allocate(512);

		while (channel.read(buffer) > 0) {
			buffer.flip();

			while (buffer.hasRemaining()) {
				result += (char)buffer.get();
			}
		}

		return result;
	}
}

