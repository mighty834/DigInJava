package common;
import files.Settings;
import org.gradle.api.Project;

public class Loader {
    private static Loader INSTANCE;
    private Project _project;

    private Loader(Project project) {
        _project = project;
    }

    public static Loader getInstance(Project project) {
        if (INSTANCE == null) {
            INSTANCE = new Loader(project);
        }

        return INSTANCE;
    }

    public String loadSettingsGradle() {
        return Settings.getInstance(_project).getContent();
    }
}
