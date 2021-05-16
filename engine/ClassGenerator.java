package engine;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;

class ClassGenerator {
	public static final String PATH_TO_AUTO_GEN    = "./engine/auto_gen/";
	public static final String INVOKER_CLASS_NAME  = "Invoker.java";
	public static final String PATH_TO_EX_DIR_ROOT = "./exercises/";
	public static final String EX_DIR_PREFIX       = "ex_";

	// Fields for invoker code generate
	public static String caseBlocks                = "";
	public static String defaultBlock			   = "";
	public static String codeForInvoker			   = "";
	
	public static String INVOKER_BASE_TEMPLATE =
	"package engine.auto_gen;\n" +
	"import java.util.Arrays;\n" +
	"import aux_tools.*;\n" +
	"import exercises.*;\n\n"+
	"public class Invoker {\n" +
	"\tpublic static void main(String[] args) throws Exception {\n" +
	"\t\tif (args.length < 1) throw new Exception(\"Bad arguments in Invoker\");\n\n" +
	"\t\tint ex_num = Integer.parseInt(args[0]);\n\n" + 
	"\t\tswitch (ex_num) {\n" +
	"%s" +
	"\t\t}\n" +
	"\t}\n" +
	"}\n\n";							 

	public static String INVOKER_CASE_TEMPLATE =
	"\t\t\tcase %s : {\n" +
	"\t\t\t\texercises.%s.main(Arrays.copyOfRange(args, 1, args.length));\n" +
	"\t\t\t}\n" +
	"\t\t\tbreak;\n\n";

	public static String INVOKER_DEFAULT_BLOCK_TEMPLATE =
	"\t\t\tdefault : {\n" +
	"\t\t\t\tOutput.println(\"No ex for your number: \" + ex_num, Output.RED_COLOR);\n" +
	"\t\t\t}\n";

	private static void preparePackage() throws IOException {
		File autoGen = new File(PATH_TO_AUTO_GEN);
		
		if (autoGen.exists()) {
			File[] allAutoGenFiles = autoGen.listFiles();
			for (File file : allAutoGenFiles) {
				file.delete();
			}
			autoGen.delete();
		}

		autoGen.mkdir();
	}

	private static void createInvoker(String code) throws IOException {
		File invoker = new File(PATH_TO_AUTO_GEN + INVOKER_CLASS_NAME);
		invoker.createNewFile();

		Path path = Paths.get(invoker.getPath());
		Set<StandardOpenOption> options = new HashSet<>();
		options.add(StandardOpenOption.CREATE);
		options.add(StandardOpenOption.WRITE);
		FileChannel channel = FileChannel.open(path, options);

		channel.write(ByteBuffer.wrap(code.getBytes()));
	}

	private static String getInvokerCode() throws Exception {
		caseBlocks 	   = "";
		defaultBlock   = "";
		codeForInvoker = "";

		getReflectData().forEach((Integer exNum, String mainClass) -> {
			caseBlocks += String.format(INVOKER_CASE_TEMPLATE, exNum, mainClass);
		});

		defaultBlock = String.format(INVOKER_DEFAULT_BLOCK_TEMPLATE);

		codeForInvoker = String.format(INVOKER_BASE_TEMPLATE, caseBlocks + defaultBlock);

		return codeForInvoker;	
	}

	private static HashMap<Integer, String> getReflectData() throws Exception {
		HashMap<Integer, String> reflectData = new HashMap<Integer, String>();
		File exDirRoot = new File(PATH_TO_EX_DIR_ROOT);
		File[] exDirs  = exDirRoot.listFiles();

		for (File exDir : exDirs) {
			int exNum;
			try {
				exNum = Integer.parseInt(exDir.getName().substring(EX_DIR_PREFIX.length()));
			} catch (NumberFormatException exception) {
				throw new NumberFormatException("Problem in ClassGenerator: " + exception.getMessage());
			}

			reflectData.put(exNum, getBaseFileName(getMainFile(exDir)));
		}

		return reflectData;
	}

	private static File getMainFile(File exDir) throws Exception {
		File[] allFiles = exDir.listFiles();
		
		for (File file : allFiles) {
			if (file.getName().contains("Main")) {
				return file;
			}
		}
		throw new Exception("No Main file in exDir: " + exDir.getName());
	}

	private static String getBaseFileName(File mainFile) {
		ArrayList<String> formats = new ArrayList<String>();
		formats.add(".java");

		for (String format : formats) {
			if (mainFile.getName().contains(format)) {
				return mainFile.getName().substring(0, mainFile.getName().length() - format.length());
			}
		}
		return mainFile.getName();
	}

	public static void main(String[] args) throws Exception {
		preparePackage();
		createInvoker(getInvokerCode());
	}
}

