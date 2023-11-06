package files;
import common.Logger;
import org.gradle.api.Project;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class ExModuleCollector {
    private static ExModuleCollector INSTANCE;
    private Project _project;
    private List<File> _modules;
    private String EXECUTIONS_PATH = "../executions";
    private String EXECUTION_MODULE_PREFIX = "ex_";

    private ExModuleCollector(Project project) {
        _project = project;
        collect();
    }

    class ExModuleParser {
        private File _moduleFile;
        private String INFO_FILE = "info.md";
        private String BUILD_GRADLE_FILE = "build.gradle";

        public ExModuleParser(File moduleFile) {
            _moduleFile = moduleFile;
        }

        private File getFile(File dir, String fileName) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    File tempResult = getFile(file, fileName);
                    if (tempResult != null) {
                        return tempResult;
                    }
                } else {

                    if (file.getName().contains(fileName)) {
                        return file;
                    }
                }
            }

            return null;
        }

        public File getInfoFile() {
            return getFile(_moduleFile, INFO_FILE);
        }

        public File getBuildFile() {
            return getFile(_moduleFile, BUILD_GRADLE_FILE);
        }
    }

    public static ExModuleCollector getInstance(Project project) {
        if (INSTANCE == null) {
            INSTANCE = new ExModuleCollector(project);
        }

        return INSTANCE;
    }

    public ExModuleCollector collect() {
        File executions = _project.file(EXECUTIONS_PATH);
        _modules = new ArrayList<>();
        for (File file : executions.listFiles()) {
            if (file.getName().contains(EXECUTION_MODULE_PREFIX)) {
                _modules.add(file);
            }
        }

        return this;
    }

    public List<File> getModules() {
        return _modules;
    }

    public File getModule(int moduleNum) {
        for (File module : _modules) {
            if (module.getName().contains(String.valueOf(moduleNum))) {
                return module;
            }
        }

        Logger.logModuleFindExceptionProblem(moduleNum);
        return null;
    }
}