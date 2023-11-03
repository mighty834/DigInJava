package files;
import org.gradle.api.Project;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import common.Logger;
import java.util.Scanner;

/**
 * This class is for interaction with execution modules
 */
public class ExModule {
    private static ExModule INSTACNE;
    private Project _project;
    private String[] EX_MODULE_DIRS = {
        "../executions/ex_%d",
        "../executions/ex_%d/src",
        "../executions/ex_%d/src/main",
        "../executions/ex_%d/src/main/java",
        "../executions/ex_%d/src/main/resources",
        "../executions/ex_%d/src/test",
        "../executions/ex_%d/src/test/java",
        "../executions/ex_%d/src/test/resources"
    };

    private String EX_MODULE_BUILD_GRALDE = "../executions/ex_%d/build.gradle";
    private String EX_MODULE_MAIN_CLASS   = "../executions/ex_%d/src/main/java/%s.java";
    private String EX_MODULE_BUILD_GRADLE_TEMP = "../buildSrc/src/main/resources/templates/build.gradle.template";
    private String EX_MODULE_MAIN_CLASS_TEMP   = "../buildSrc/src/main/resources/templates/main.class.template";

    private ExModule(Project project) {
        _project = project;
    }

    private String getTemplate(String tempPath) {
        String result = "";
        try (
            Scanner scanner = new Scanner(_project.file(tempPath))
        ) {

            while (scanner.hasNextLine()) {
                result += scanner.nextLine() + "\n";
            }

        } catch (IOException exception) {
            Logger.logIOExceptionProblem(exception.getMessage());
        }

        return result;
    }

    public static ExModule getInstance(Project project) {
        if (INSTACNE == null) {
            INSTACNE = new ExModule(project);
        }

        return INSTACNE;
    }

    public void create(String mainClassName, Integer number) {
        for (String dir : EX_MODULE_DIRS) {
            _project.file(String.format(
                dir, number
            )).mkdir();
        }

        try (
            BufferedWriter buildGradleWriter = new BufferedWriter(
                new FileWriter(_project.file(
                    String.format(EX_MODULE_BUILD_GRALDE, number)
                ))
            );
            BufferedWriter mainClassWriter = new BufferedWriter(
                new FileWriter(_project.file(
                    String.format(EX_MODULE_MAIN_CLASS, number, mainClassName)
                ))
            );
        ) {

            buildGradleWriter.write(String.format(
                getTemplate(EX_MODULE_BUILD_GRADLE_TEMP),
                number,
                mainClassName,
                number
            ));

            mainClassWriter.write(String.format(
                getTemplate(EX_MODULE_MAIN_CLASS_TEMP),
                number,
                mainClassName
            ));

        } catch (IOException exception) {
            Logger.logIOExceptionProblem(exception.getMessage());
        }
    }
}
