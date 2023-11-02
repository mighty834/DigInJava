package files;
import org.gradle.api.Project;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * This class is for interaction with settings.gradle file
 */
public class Settings {
    private static Settings INSTANCE;
    private String SETTINGS_GRADLE = "../settings.gradle";
    private String INCLUDES_END    = ")";
    private String INCLUDES_SEPARATOR = ",";
    private String BEFORE_MODULE_NAME = "    '";
    private String AFTER_MODULE_NAME  = "'";
    private Project _project;

    private Settings(Project project) {
        _project = project;
    }

    private String buildContent(List<String> strs) {
        String result = "";
        for (String str : strs) {
            result += str + "\n";
        }

        return result;
    }

    private void logFileNotFoundProblem(String errorMessage) {
        System.out.println(
            String.format("No gradle settings file in project: %s", errorMessage)
        );
    }

    private void logIOExceptionProblem(String errorMessage) {
        System.out.println(
            String.format("Con't write to this file: %s", errorMessage)
        );
    }

    public static Settings getInstance(Project project) {
        if (INSTANCE == null) {
            INSTANCE = new Settings(project);
        }

        return INSTANCE;
    }

    public String getContent() {
        String result = "";
        try (
            Scanner reader = new Scanner(_project.file(SETTINGS_GRADLE));
        ) {
            while (reader.hasNextLine()) {
                result += reader.nextLine() + "\n";
            }
        } catch (FileNotFoundException exception) {
            logFileNotFoundProblem(exception.getMessage());
        }

        return result;
    }

    private void rewriteContent(String content) {
        File settings = _project.file(SETTINGS_GRADLE);
        try (
            FileWriter fileWriter = new FileWriter(settings);
            BufferedWriter writer = new BufferedWriter(fileWriter)
        ) {
            writer.write(content);
        } catch (IOException exception) {
            logIOExceptionProblem(exception.getMessage());
        }
    }

    public List<String> getContentAsStrings() {
        ArrayList<String> result = new ArrayList<>();
        try (
            Scanner reader = new Scanner(_project.file(SETTINGS_GRADLE));
        ) {
            while (reader.hasNextLine()) {
                result.add(reader.nextLine());
            }
        } catch (FileNotFoundException exception) {
            logFileNotFoundProblem(exception.getMessage());
        }

        return result;
    }

    public void addModule(String moduleName) {
        List<String> contentList = getContentAsStrings();
        for (int i = 0; i < contentList.size(); i++) {
            if (contentList.get(i).equals(INCLUDES_END)) {
                contentList.set(i - 1,
                    String.format(
                        contentList.get(i - 1) + "%s",
                        INCLUDES_SEPARATOR
                    )
                );
                contentList.add(i,
                    String.format(
                        "%s" + moduleName + "%s",
                        BEFORE_MODULE_NAME,
                        AFTER_MODULE_NAME
                    )
                );

                break;
            }
        }

        rewriteContent(
            buildContent(contentList)
        );
    }
}
