package aux_tools;

public class WrongArguments extends Exception {
    private String message;

    WrongArguments(String commandName) {
        this.message = "You pass wrong set of arguments to command: " + commandName;
    }

    @Override
    public String toString() {
        return this.message;
    }
}

