package files.exModules;
import org.gradle.api.Project;
import java.io.IOException;
import common.Logger;
import java.util.Scanner;
import java.util.List;
import common.Parser;
import java.util.Date;

/**
 * This class is for interaction with execution modules
 */
public abstract class AbstractExModule {
    protected Project  _project;
    protected String   _mainClassName;
    protected Integer  _exModuleNumber;
    protected List<String> _keyWords;
    protected String _description;
    protected Date _creationDate;

    protected String[] EX_MODULE_DIRS = {
        "../executions/ex_%d",
        "../executions/ex_%d/src",
        "../executions/ex_%d/src/main",
        "../executions/ex_%d/src/main/java",
        "../executions/ex_%d/src/main/resources",
        "../executions/ex_%d/src/test",
        "../executions/ex_%d/src/test/java",
        "../executions/ex_%d/src/test/resources"
    };

    protected String EX_MODULE_BUILD_GRALDE = "../executions/ex_%d/build.gradle";
    protected String EX_MODULE_MAIN_CLASS   = "../executions/ex_%d/src/main/java/%s.java";
    protected String EX_MODULE_INFO_FILE    = "../executions/ex_%d/src/main/java/info.md";
    protected String EX_MODULE_BUILD_GRADLE_TEMP = "../buildSrc/src/main/resources/templates/build.gradle.template";
    protected String EX_MODULE_MAIN_CLASS_TEMP   = "../buildSrc/src/main/resources/templates/main.class.template";
    protected String EX_MODULE_INFO_PART_TEMP    = "# %s  \n%s";
    protected String EX_MODULE_INFO_PART_ELEM    = "* %s";

    protected String EX_MODULE_CREATION_DATE = "Creation date";
    protected String EX_MODULE_KEY_WORDS     = "Key words";
    protected String EX_MODULE_DESCRIPTION   = "Description";

    public AbstractExModule init(
        Project project,
        String mainClassName,
        Integer exNumber,
        List<String> keyWords,
        String description,
        Date creationDate
    ) {
        _project = project;
        _mainClassName = mainClassName;
        _exModuleNumber = exNumber;
        _keyWords = keyWords;
        _description = description;
        _creationDate = creationDate;

        return this;
    }

    protected String getTemplate(String tempPath) {
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

    protected String getListInfo(List<String> list) {
        String result = "";
        for (String info : list) {
            result += String.format(EX_MODULE_INFO_PART_ELEM, info + "\n");
        }

        return result;
    }

    protected String getInfoFileContent() {
        String result = "";
        result += String.format(
            EX_MODULE_INFO_PART_TEMP,
            EX_MODULE_CREATION_DATE,
            Parser.getInstance(_project).getDateValue(_creationDate) + "\n"
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

    abstract public void create();
}
