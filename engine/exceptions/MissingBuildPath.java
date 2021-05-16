package engine.exceptions;

public class MissingBuildPath extends MainException {
    private String message;

    public MissingBuildPath() {
        this.message = "You must specify build path for assets biuld!";
    }

    @Override
    public String toString() {
        return this.message;
    }
}

