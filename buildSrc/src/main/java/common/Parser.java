package common;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.gradle.api.Project;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Parser {
    private static Parser INSTANCE;
    private final String SPLITERATOR = ",";
    private final String EXECUTIONS_FIND_REG_EXP = "ex_[0-9]+";
    private final String DATE_PATTERN = "dd.MM.yyyy|HH:mm:ss";
    private Project _project;

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

    private List<String> getAllSubstringsByRegExp(String data, String regExp) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            result.add(matcher.group(0));
        }

        return result;
    }

    public List<Integer> parseExecutionNumbers() {
        List<Integer> result = new ArrayList<>();
        String data = Loader.getInstance(_project).loadSettingsGradle();
        getAllSubstringsByRegExp(data, EXECUTIONS_FIND_REG_EXP).forEach((String exModuleName) -> {
            result.add(Integer.parseInt(exModuleName.substring(3)));
        });

        return result;
    }

    public String getDateValue(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        return formatter.format(date);
    }

    public Date getOriginDate(String value) {
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
