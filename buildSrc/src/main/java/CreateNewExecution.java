import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import java.util.Arrays;
import java.util.List;

public class CreateNewExecution extends DefaultTask {

    public enum ExecutionTypes {
        DEFAULT
    }

    private String _mainClassName;
    private List<String> _keyWords;
    private ExecutionTypes _type = ExecutionTypes.DEFAULT;
    private String _environment;
    private String _dependencies;
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
        _keyWords = Arrays.stream(keyWords.split(",")).toList();
        _keyWords.forEach((keyWord) -> {
            keyWord = keyWord.trim();
        });
    }

    @Optional
    @Input
    public String getKeyWords() {
        return (_keyWords != null) ? _keyWords.toString() : null;
    }

    @Option(option = "env", description = "Required environment for execution")
    public void setEnvironment(String environment) {
        _environment = environment;
    }

    @Optional
    @Input
    public String getEnvironment() {
        return _environment;
    }

    @Option(option = "dep", description = "Required dependencies for execution")
    public void setDependencies(String dependencies) {
        _dependencies = dependencies;
    }

    @Optional
    @Input
    public String getDependencies() {
        return _dependencies;
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
            String.format("Name of main class for new execution: %s", _mainClassName)
        );
    }
}
