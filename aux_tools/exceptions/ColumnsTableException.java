package aux_tools;

public class ColumnsTableException extends MainException {
    private String message;

    ColumnsTableException(String cause) {
        this.message = "Your problem is: " + cause;
    }

    @Override
    public String toString() {
        return this.message;
    }
}

