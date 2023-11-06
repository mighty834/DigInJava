package common;
import files.Settings;
import org.gradle.api.Project;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Loader {
    private static Loader INSTANCE;
    private Project _project;

    private Loader(Project project) {
        _project = project;
    }

    public static Loader getInstance(Project project) {
        if (INSTANCE == null) {
            INSTANCE = new Loader(project);
        }

        return INSTANCE;
    }

    public String loadSettingsGradle() {
        return Settings.getInstance(_project).getContent();
    }

    public static String loadFileContent(File file) {
        String result = "";

        try (
            Scanner reader = new Scanner(file)
        ) {
            while (reader.hasNextLine()) {
                result += reader.nextLine() + "\n";
            }
        } catch (FileNotFoundException exception) {
            Logger.logFileNotFoundProblem(exception.getMessage());
        }

        return result;
    }
}
