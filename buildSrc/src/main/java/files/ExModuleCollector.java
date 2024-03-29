package files;
import common.Logger;
import dto.InfoFileDTO;
import org.gradle.api.Project;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Objects;
import common.Parser;
import common.Loader;

public class ExModuleCollector {
    private static ExModuleCollector INSTANCE;
    private static List<InfoFileDTO> InfoData;
    private Project _project;
    private List<File> _modules;
    private String EXECUTIONS_PATH = "../executions";
    private String EXECUTION_MODULE_PREFIX = "ex_";

    private ExModuleCollector(Project project) {
        _project = project;
        collect();
    }

    public static ExModuleCollector getInstance(Project project) {
        if (INSTANCE == null) {
            INSTANCE = new ExModuleCollector(project);
        }

        return INSTANCE;
    }

    public List<InfoFileDTO> getInfoData() {
        if (Objects.isNull(InfoData)) {
            initInfoData();
        }

        return InfoData;
    }

    private void initInfoData() {
        InfoData = new ArrayList<>();

        for (File module : _modules) {
            ExModuleParser parser = new ExModuleParser(module);
            InfoData.add(parser.getParsedInfoFile());
        }
    }

    public class ExModuleParser {
        private File _moduleFile;
        private String INFO_FILE = "info.md";
        private String BUILD_GRADLE_FILE = "build.gradle";
        private final String DATE_PATTERN_REGEX = "(\\d+\\.){2}\\d+\\|(\\d+:){2}\\d+";
        private final String KEY_WORDS_PATTERN_REGEX =
            "(?<=# Key words[\\s\\S]*)(?<=\\*\\s).*(?=[\\s\\S]*\\n\\n[\\s\\S]*# Description)";
        private final String DESCRIPTION_PATTERN_REGEX = "(?<=# Description[\\s\\n]*)[\\s\\S]*";

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

        public InfoFileDTO getParsedInfoFile() {
            InfoFileDTO result = new InfoFileDTO();
            File infoFile  = getFile(_moduleFile, INFO_FILE);
            String content = Loader.loadFileContent(infoFile);

            result.date = Parser.getOriginDate(
                Parser.parseByRegex(DATE_PATTERN_REGEX, content).stream().findFirst().get()
            );

            result.keyWords = Parser.parseByRegex(KEY_WORDS_PATTERN_REGEX, content);
            result.description = Parser.parseByRegex(DESCRIPTION_PATTERN_REGEX, content).get(0);
            result.exNumber = Integer.parseInt(_moduleFile.getName().substring(EXECUTION_MODULE_PREFIX.length()));

            return result;
        }

        public File getBuildFile() {
            return getFile(_moduleFile, BUILD_GRADLE_FILE);
        }
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
