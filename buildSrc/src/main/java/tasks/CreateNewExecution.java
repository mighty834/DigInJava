package tasks;
import enums.DependenciesType;
import enums.EnvTypes;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import common.Parser;
import common.Logger;
import files.Settings;
import files.exModules.*;

public class CreateNewExecution extends DefaultTask {
    private String _mainClassName;
    private List<String> _keyWords                     = new ArrayList<>();
    private AbstractExModule _type                     = new DefaultExModule();
    private List<enums.EnvTypes> _environment          = new ArrayList<>();
    private List<enums.DependenciesType> _dependencies = new ArrayList<>();
    private String _description;
    private String _executionModulePrefix = "executions:ex_";
    private String SUCCESSFUL_MESSAGE = "Execution module ex_%d successfully created!";

    private Integer getNumberForNewExecution() {
        List<Integer> exNums = Parser.getInstance(this.getProject()).parseExecutionNumbers();
        exNums.sort((a, b) -> a - b);

        if (exNums.size() == 0 || exNums.get(0) > 1) {
            return 1;
        }

        for (int i = 0; i < exNums.size() - 1; i++) {
            if (exNums.get(i) + 1 < exNums.get(i + 1)) {
                return exNums.get(i) + 1;
            }
        }

        return exNums.get(exNums.size() - 1) + 1;
    }

    private void addToSettingsGradle(int number) {
        Settings.getInstance(this.getProject()).addModule(String.format(
            _executionModulePrefix + "%d",
            number
        ));
    }

    @Option(option = "mcn", description = "Name of the execution main class")
    public void setMainClassName(String mainClassName) {
        _mainClassName = mainClassName;
    }

    @Input
    public String getMainClassName() {
        return _mainClassName;
    }

    @Option(option = "kw", description = "This is key topics included to execution")
    public void setKeyWords(String keyWords) {
        Parser.getInstance(this.getProject()).parseCLIProps(_keyWords, keyWords, prop -> prop);
    }

    @Optional
    @Input
    public String getKeyWords() {
        return _keyWords.toString();
    }

    @Option(option = "type", description = "Type of initial execution file structure")
    public void setType(String type) {
        switch (type.toUpperCase()) {
            case "DEFAULT": {
                _type = new DefaultExModule();
            }
            break;

            case "SPRING": {
                _type = new SpringExModule();
            }
            break;

            default : {}
        }
    }

    @Optional
    @Input
    public String getType() {
        return _type.toString();
    }

    @Option(option = "env", description = "Required environment for execution")
    public void setEnvironment(String environment) {
        Parser.getInstance(this.getProject()).parseCLIProps(_environment, environment, (String prop) -> {
            return EnvTypes.valueOf(prop.toUpperCase());
        });
    }

    @Optional
    @Input
    public String getEnvironment() {
        return _environment.toString();
    }

    @Option(option = "dep", description = "Required dependencies for execution")
    public void setDependencies(String dependencies) {
        Parser.getInstance(this.getProject()).parseCLIProps(_dependencies, dependencies, (String prop) -> {
            return DependenciesType.valueOf(prop.toUpperCase());
        });
    }

    @Optional
    @Input
    public String getDependencies() {
        return _dependencies.toString();
    }

    @Option(option = "desc", description = "Description of execution")
    public void setDescription(String description) {
        _description = description;
    }

    @Optional
    @Input
    public String getDescription() {
        return _description;
    }

    @TaskAction
    public void createNewEx() {
        int newExNumber = getNumberForNewExecution();

        addToSettingsGradle(newExNumber);
        _type.init(
            this.getProject(),
            _mainClassName,
            newExNumber,
            _keyWords,
            _description,
            new Date()
        ).create();

        Logger.logSuccessful(
            String.format(SUCCESSFUL_MESSAGE, newExNumber)
        );
    }
}
