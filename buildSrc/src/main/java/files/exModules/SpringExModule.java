package files.exModules;
import common.Logger;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class SpringExModule extends AbstractExModule {
    protected String EX_MODULE_APP_PROPERTIES    = "../executions/ex_%d/src/main/resources/application.properties";
    protected String EX_MODULE_CUSTOM_PROPERTIES = "../executions/ex_%d/src/main/resources/custom.properties";
    protected String EX_MODULE_APP_CONFIG_FILE   = "../executions/ex_%d/src/main/java/AppConfig.java";
    protected String EX_MODULE_BUILD_GRADLE_TEMP = "../buildSrc/src/main/resources/templates/build.gradle.spring.template";
    protected String EX_MODULE_MAIN_CLASS_TEMP   = "../buildSrc/src/main/resources/templates/main.class.spring.template";
    protected String EX_MODULE_APP_CONFIG_TEMP   = "../buildSrc/src/main/resources/templates/app.config.spring.template";

    public void create() {
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
            );
            BufferedWriter appPropertiesWriter = new BufferedWriter(
                new FileWriter(_project.file(
                    String.format(EX_MODULE_APP_PROPERTIES, _exModuleNumber)
                ))
            );
            BufferedWriter customPropertiesWriter = new BufferedWriter(
                new FileWriter(_project.file(
                    String.format(EX_MODULE_CUSTOM_PROPERTIES, _exModuleNumber)
                ))
            );
            BufferedWriter appConfigWriter = new BufferedWriter(
                new FileWriter(_project.file(
                    String.format(EX_MODULE_APP_CONFIG_FILE, _exModuleNumber)
                ))
            )
        ) {

            buildGradleWriter.write(String.format(
                getTemplate(EX_MODULE_BUILD_GRADLE_TEMP),
                _exModuleNumber,
                _mainClassName,
                _exModuleNumber,
                _exModuleNumber
            ));

            mainClassWriter.write(String.format(
                getTemplate(EX_MODULE_MAIN_CLASS_TEMP),
                _exModuleNumber,
                _exModuleNumber,
                _mainClassName
            ));

            infoFileWriter.write(getInfoFileContent());

            appPropertiesWriter.write("server.port=8081");
            customPropertiesWriter.write("");
            appConfigWriter.write(String.format(
                getTemplate(EX_MODULE_APP_CONFIG_TEMP),
                _exModuleNumber,
                _exModuleNumber
            ));

        } catch (IOException exception) {
            Logger.logIOExceptionProblem(exception.getMessage());
        }
    }
}
