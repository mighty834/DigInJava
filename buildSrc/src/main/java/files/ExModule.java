package files;
import org.gradle.api.Project;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import common.Logger;
import java.util.Scanner;
import java.util.List;
import enums.ExecutionTypes;
import common.Parser;
import java.util.Date;

/**
 * This class is for interaction with execution modules
 */
public class ExModule {
    private Project  _project;
    private String   _mainClassName;
    private Integer  _exModuleNumber;
    private List<String> _keyWords;
    private String _description;
    private ExecutionTypes _type;

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
    private String EX_MODULE_INFO_FILE    = "../executions/ex_%d/src/main/java/info.md";
    private String EX_MODULE_BUILD_GRADLE_TEMP = "../buildSrc/src/main/resources/templates/build.gradle.template";
    private String EX_MODULE_MAIN_CLASS_TEMP   = "../buildSrc/src/main/resources/templates/main.class.template";
    private String EX_MODULE_INFO_PART_TEMP    = "# %s  \n%s";
    private String EX_MODULE_INFO_PART_ELEM    = "* %s";

    private String EX_MODULE_CREATION_DATE = "Creation date";
    private String EX_MODULE_KEY_WORDS     = "Key words";
    private String EX_MODULE_DESCRIPTION   = "Description";

    public ExModule(Project project, String mainClassName, Integer number) {
        _project = project;
        _mainClassName = mainClassName;
        _exModuleNumber = number;
    }

    public ExModule(
        Project project,
        String mainClassName,
        Integer number,
        List<String> keyWords,
        ExecutionTypes type,
        String description
    ) {
        this(project, mainClassName, number);
        _keyWords = keyWords;
        _type = type;
        _description = description;
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

    private String getListInfo(List<String> list) {
        String result = "";
        for (String info : list) {
            result += String.format(EX_MODULE_INFO_PART_ELEM, info + "\n");
        }

        return result;
    }

    private String getInfoFileContent() {
        String result = "";
        result += String.format(
            EX_MODULE_INFO_PART_TEMP,
            EX_MODULE_CREATION_DATE,
            Parser.getInstance(_project).getDateValue(new Date()) + "\n"
        );

        if (_keyWords != null && _keyWords.size() > 0) {
            result += "\n" + String.format(
                EX_MODULE_INFO_PART_TEMP,
                EX_MODULE_KEY_WORDS,
                getListInfo(_keyWords)
            );
        }

        if (_description != null) {
            result += "\n" + String.format(
                EX_MODULE_INFO_PART_TEMP,
                EX_MODULE_DESCRIPTION,
                _description
            );
        }

        return result;
    }

    public void create() {
        switch (_type) {
            case DEFAULT -> createDefaultExModule();
        }
    }

    private void createDefaultExModule() {
        for (String dir : EX_MODULE_DIRS) {
            _project.file(String.format(
                dir, _exModuleNumber
            )).mkdir();
        }

        try (
            BufferedWriter buildGradleWriter = new BufferedWriter(
                new FileWriter(_project.file(
                    String.format(EX_MODULE_BUILD_GRALDE, _exModuleNumber)
                ))
            );
            BufferedWriter mainClassWriter = new BufferedWriter(
                new FileWriter(_project.file(
                    String.format(EX_MODULE_MAIN_CLASS, _exModuleNumber, _mainClassName)
                ))
            );
            BufferedWriter infoFileWriter = new BufferedWriter(
                new FileWriter(_project.file(
                    String.format(EX_MODULE_INFO_FILE, _exModuleNumber)
                ))
            )
        ) {

            buildGradleWriter.write(String.format(
                getTemplate(EX_MODULE_BUILD_GRADLE_TEMP),
                _exModuleNumber,
                _mainClassName,
                _exModuleNumber
            ));

            mainClassWriter.write(String.format(
                getTemplate(EX_MODULE_MAIN_CLASS_TEMP),
                _exModuleNumber,
                _mainClassName
            ));

            infoFileWriter.write(getInfoFileContent());

        } catch (IOException exception) {
            Logger.logIOExceptionProblem(exception.getMessage());
        }
    }
}
