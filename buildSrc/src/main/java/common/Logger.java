package common;

public class Logger {
    public static void logFileNotFoundProblem(String errorMessage) {
        System.out.println(
            String.format("No gradle settings file in project: %s", errorMessage)
        );
    }

    public static void logIOExceptionProblem(String errorMessage) {
        System.out.println(
            String.format("Can't get access to this file: %s", errorMessage)
        );
    }

    public static void logSuccessful(String message) {
        System.out.println(message);
    }
}
