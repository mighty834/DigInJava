package aux_tools;
import engine.exceptions.*;
import java.io.*;
import java.util.Arrays;

public class Helpers {
    private static String command = "";
    private static String[] arguments;
    private static String rootBuildPath;
    private static final String templateOfTaskFile = "" +
        "# Description\n\n\n\n" +
        "# Topics\n\n" +
        "* \n";
    private static final String templateOfMainFile = "" +
        "package exercises;\n" +
        "import aux_tools.*;\n" +
		"import engine.exceptions.*;\n\n" +
        "public class Main${anchor_1} {\n" +
        "\t@SuppressWarnings(\"unchecked\")\n" +
        "\tpublic static void main(String[] args) throws MainException {\n\n" +
        "\t}\n" +
        "}\n\n";
    
    /**
     * This method unifies work with templates in Helpers class
     */ 
    private static String getTemplate(String template, String...anchors) {
        int anchorNum = 1;
        for (String anchor: anchors) {
            template = template.replaceAll("\\$\\{anchor_" + anchorNum++ + "\\}", anchor);
        }

        return template;
    }

    /**
     * This method help collect all assets in every exercise,
     * and put them into build directory
     */
    private static void buildAssets(String assetsPath, boolean isAssets) {
        File currentDir = new File(assetsPath);
        File[] list = currentDir.listFiles();

        for (File file: list) {
            if (file.getAbsolutePath().indexOf(rootBuildPath) == -1) {
                if (isAssets) {
                    if (file.isDirectory()) {
                        buildAssets(file.getPath(), true);
                    } else {
                        try (
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                BufferedWriter writer =
                                new BufferedWriter(
                                    new FileWriter(
                                        new File(rootBuildPath + "/assets/" + file.getName())
                                    )    
                                )
                            )
                        {
                            int character;
                            while ((character = reader.read()) != -1) {
                                writer.write(character);
                            }

                            System.out.println("Asset '" + file.getName() + "' is successfully loaded!");
                        }
                        catch (IOException exception) {
                            System.out.println("Can't build assets! Exception is: " + exception);
                        }
                    }
                } else {
                    if (file.isDirectory()) {
                        if (file.getName().equals("assets")) buildAssets(file.getPath(), true);
                        else buildAssets(file.getPath(), false);
                    }
                }
            }
        }
    }

    /**
     * This overload form of method for first call
     */
    public static void buildAssets(String buildPath) {
        rootBuildPath = buildPath;                                                                                                                                 
        File buildDir = new File(rootBuildPath + "/assets");                                                                                                        
        if (!buildDir.exists()) buildDir.mkdir();

        buildAssets("./", false);
    }

    /**
     * This method help us create template for new excercise,
     * and then we can start practice some java topics
     */
    public static void createNewEx(String name) throws IOException, DoubleMainClassException {
		String className = Loader.EX_ENTRY_POINT_PREFIX + name;
		if (Loader.getAllMainClassesNames().contains(className)) {
			throw new DoubleMainClassException(className);
		}

        int exNumber = 1;
        String path = "exercises/ex_";
        File exDir, mainClassFile, taskFile;
        BufferedWriter stream;

        try {
            do {
                exDir = new File(path + exNumber++);
            } while (exDir.exists());
            exDir.mkdir();

            mainClassFile = new File(exDir.getPath() + "/" + className + ".java");
            taskFile = new File(exDir.getPath() + "/task.md");

            stream = new BufferedWriter(new FileWriter(mainClassFile));
            stream.write(getTemplate(templateOfMainFile, name));
            stream.close();

            stream = new BufferedWriter(new FileWriter(taskFile));
            stream.write(getTemplate(templateOfTaskFile));
            stream.close();

            mainClassFile.createNewFile();
            taskFile.createNewFile();

            System.out.println(
                "New '" + exDir.getName() +  "' exercise " +
                "with name: '" + name + "' successfully created!"
            );
        }
        catch (IOException exception) {
            System.out.println("Can't create new exercise file!\n Exception is: " + exception);
        }
    }

    public static void main(String[] args) throws MainException, IOException {
        if (args.length > 0) command = args[0];
        if (args.length > 1) arguments = Arrays.copyOfRange(args, 1, args.length);

        switch (command) {
            case "createNewEx": {
                if (arguments == null) throw new WrongArguments("createNewEx");
                createNewEx(arguments[0]);
            }
            break;
            case "buildAssets": {
                if (arguments == null) throw new MissingBuildPath();
                buildAssets(arguments[0]);
            }
            break;
            case "": throw new EmptyCommand();
            default: throw new WrongCommand(command);
        }
    }
}

