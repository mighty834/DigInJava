package common;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Loader {
    public static final String SETTINGS_GRADLE = "settings.gradle";

    public static String loadSettingsGradle() {
        String result = "";
        try (
            Scanner reader = new Scanner(new File(SETTINGS_GRADLE))
        ) {
            while (reader.hasNextLine()) {
                result += reader.nextLine() + "\n";
            }

        } catch (FileNotFoundException exception) {
            System.out.println(
                String.format("No gradle settings file in project: %s", exception.getMessage())
            );
        } catch (Exception exception) {
            System.out.println(
                String.format("Something going wrong in gradle settings loader: %s", exception.getMessage())
            );
        }

        return result;
    }
}
