package engine.exceptions;

public class ColumnsTableException extends MainException {
    private String message;

    public ColumnsTableException(String cause) {
        this.message = "Your problem is: " + cause;
    }

    @Override
    public String toString() {
        return this.message;
    }
}

