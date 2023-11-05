package files.exModules;
import common.Logger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DefaultExModule extends AbstractExModule {
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
