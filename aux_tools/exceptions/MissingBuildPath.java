package aux_tools;

public class MissingBuildPath extends MainException {
    private String message;

    MissingBuildPath() {
        this.message = "You must specify build path for assets biuld!";
    }

    @Override
    public String toString() {
        return this.message;
    }
}

