package tasks;
import enums.DependenciesType;
import enums.EnvTypes;
import enums.ExecutionTypes;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CreateNewExecution extends DefaultTask {
    private String _mainClassName;
    private List<String> _keyWords                     = new ArrayList<>();
    private enums.ExecutionTypes _type                 = enums.ExecutionTypes.DEFAULT;
    private List<enums.EnvTypes> _environment          = new ArrayList<>();
    private List<enums.DependenciesType> _dependencies = new ArrayList<>();
    private String _description;

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
        _keyWords.addAll(
            Arrays.stream(
                keyWords.split(",")
            ).map(word -> word.trim()).toList()
        );
    }

    @Optional
    @Input
    public String getKeyWords() {
        return _keyWords.toString();
    }

    @Option(option = "type", description = "Type of initial execution file structure")
    public void setType(String type) {
        _type = ExecutionTypes.valueOf(type.toUpperCase());
    }

    @Optional
    @Input
    public String getType() {
        return _type.toString();
    }

    @Option(option = "env", description = "Required environment for execution")
    public void setEnvironment(String environment) {
        _environment.addAll(
          Arrays.stream(
              environment.split(",")
          ).map(word -> {
              return EnvTypes.valueOf(
                  word.trim().toUpperCase()
              );
          }).toList()
        );
    }

    @Optional
    @Input
    public String getEnvironment() {
        return _environment.toString();
    }

    @Option(option = "dep", description = "Required dependencies for execution")
    public void setDependencies(String dependencies) {
        _dependencies.addAll(
            Arrays.stream(
                dependencies.split(",")
            ).map(word -> {
                return DependenciesType.valueOf(
                    word.trim().toUpperCase()
                );
            }).toList()
        );
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
        System.out.println(
            String.format("Name of dependencies type for new execution: %s", _dependencies)
        );
    }
}
