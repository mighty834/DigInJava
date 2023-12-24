package common;
import dto.InfoFileDTO;
import files.ExModuleCollector;
import org.gradle.api.Project;
import java.util.List;
import java.util.Objects;

public class Finder {
    private static Finder INSTANCE;
    private Project _project;

    private Finder(Project project) {
        _project = project;
    }

    public static Finder getInstance(Project project) {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new Finder(project);
        }

        return INSTANCE;
    }

    public Integer findNumberOfLastEx() {
        List<InfoFileDTO> infoData = ExModuleCollector.getInstance(_project).getInfoData();
        infoData.sort((a, b) -> b.date.compareTo(a.date));

        return infoData.get(0).exNumber;
    }
}
