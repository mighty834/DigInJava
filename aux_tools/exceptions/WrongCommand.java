package aux_tools;

public class WrongCommand extends MainException {
    private String message;

    WrongCommand(String commandName) {
        this.message = "Command '" + commandName + "' is not exist!";
    }

    @Override
    public String toString() {
        return this.message;
    }
}

