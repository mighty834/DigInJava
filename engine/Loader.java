package engine;
import aux_tools.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

public class Loader {
	public static final String PATH_TO_BUILD	 = "./classes/";
	public static final String PATH_TO_EXERCISES = "./exercises/";
	public static final String EX_PREFIX         = "ex_";
	
	private String _path;

	public Loader(String path) {
		_path = path;
	}

	public ArrayList<File> getAllFilesFromPath() {
		File currentDir = new File(_path);
		File[] files    = currentDir.listFiles();

		return new ArrayList<File>(Arrays.asList(files)); 
	}

	public void getAllTopicsFiles() {
		
	}

	public static void main(String[] args) {
		Output.println("Hello Loader!");
	}
}

