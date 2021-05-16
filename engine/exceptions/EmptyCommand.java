package engine.exceptions;

public class EmptyCommand extends MainException {
    private String message;

    public EmptyCommand() {
        this.message = "Command argument can't be empty!";
    }

    @Override
    public String toString() {
        return this.message;
    }
}

