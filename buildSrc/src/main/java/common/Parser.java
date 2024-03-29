package common;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.gradle.api.Project;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Parser {
    private static Parser INSTANCE;
    private Project _project;
    public static final String SPLITERATOR = ",";
    public static final String EXECUTIONS_FIND_REG_EXP = "ex_[0-9]+";
    public static final String DATE_PATTERN = "dd.MM.yyyy|HH:mm:ss";

    private Parser(Project project) {
        _project = project;
    }

    public static Parser getInstance(Project project) {
        if (INSTANCE == null) {
            INSTANCE = new Parser(project);
        }

        return INSTANCE;
    }

    public <T> void parseCLIProps(List<T> target, String props, Function<String, T> func) {
        target.addAll(
            Arrays.stream(
                props.split(SPLITERATOR)
            ).map(prop -> {
                return func.apply(prop.trim());
            }).toList()
        );
    }

    public static List<String> parseByRegex(String regex, String content) {
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(content).results().map(result -> {
            return result.group();
        }).toList();
    }

    public List<Integer> parseExecutionNumbers() {
        List<Integer> result = new ArrayList<>();
        String data = Loader.getInstance(_project).loadSettingsGradle();
        parseByRegex(EXECUTIONS_FIND_REG_EXP, data).forEach((String exModuleName) -> {
            result.add(Integer.parseInt(exModuleName.substring(3)));
        });

        return result;
    }

    public String getDateValue(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        return formatter.format(date);
    }

    public static Date getOriginDate(String value) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        Date result = null;
        try {
            result = formatter.parse(value);
        } catch (ParseException exception) {
            Logger.logParseExceptionProblem(exception.getMessage());
        }

        return result;
    }
}
