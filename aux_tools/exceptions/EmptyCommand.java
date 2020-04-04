package aux_tools;

public class EmptyCommand extends MainException {
    private String message;

    EmptyCommand() {
        this.message = "Command argument can't be empty!";
    }

    @Override
    public String toString() {
        return this.message;
    }
}

