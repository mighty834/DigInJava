import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;

public class TasksCheckerTest {
    private File projectDir = new File("../executions");
    private String[][] commands = {
        {"cne", "--mcn=TestClassName", "--kw=Test keywords", "--desc=Test description"},
    };

    @Test
    public void testAllCommands() {
        for (String[] command : commands) {
            BuildResult result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withArguments(Arrays.asList(command))
                .build();

            assertEquals(SUCCESS, result.task(
                String.format(":executions:%s", command[0])
            ).getOutcome());
        }
    }
}
