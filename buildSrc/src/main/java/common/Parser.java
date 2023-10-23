package common;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static final String SPLITERATOR = ",";
    public static final String EXECUTIONS_FIND_REG_EXP = "ex_[0-9]+";

    public static <T> void parseCLIProps(List<T> target, String props, Function<String, T> func) {
        target.addAll(
            Arrays.stream(
                props.split(SPLITERATOR)
            ).map(prop -> {
                return func.apply(prop.trim());
            }).toList()
        );
    }

    private static List<String> getAllSubstringsByRegExp(String data, String regExp) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            result.add(matcher.group(0));
        }

        return result;
    }

    public static List<Integer> parseExecutionNumbers() {
        List<Integer> result = new ArrayList<>();
        String data = Loader.loadSettingsGradle();
        getAllSubstringsByRegExp(data, EXECUTIONS_FIND_REG_EXP).forEach((String exModuleName) -> {
            result.add(Integer.parseInt(exModuleName.substring(3)));
        });

        return result;
    }
}
