package aux_tools;
import aux_tools.*;
import engine.dto.TaskFileDTO;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Parser {
	public static final String TASK_FILE_DESCRIPTION_TITLE = "# Description";
	public static final String TASK_FILE_TOPICS_TITLE	   = "# Topics";
	public static final String TOPIC_PREFIX				   = "* ";
	public static final String TOPIC_DONE_POSTFIX		   = "**done**";

	private static String getDescriptionFromTaskContent(String content) {
		int beginIndex = TASK_FILE_DESCRIPTION_TITLE.length();
		int endIndex   = content.indexOf(TASK_FILE_TOPICS_TITLE);

		return content.substring(beginIndex, endIndex).trim();
	}

	private static ArrayList<String> getTopicsFromTaskContent(String content) {
		ArrayList<String> result = new ArrayList<String>();
		
		String topicsPart = content.substring(
			content.indexOf(TASK_FILE_TOPICS_TITLE) + TASK_FILE_TOPICS_TITLE.length()
		).trim();

		for (String row : topicsPart.split("\n")) {
			if (row.startsWith(TOPIC_PREFIX)) {
				result.add(row.substring(TOPIC_PREFIX.length()).trim());
			} else {
				break;
			}
		}

		return result;
	}

	private static boolean getStatusFromTaskContent(String content) {
		return content.contains(TOPIC_DONE_POSTFIX);
	}
	
	public static TaskFileDTO parseTaskFile(File taskMd) throws FileNotFoundException, IOException {
		TaskFileDTO result = new TaskFileDTO();
		String content = Loader.getFileContent(taskMd);

		result.setDescription(getDescriptionFromTaskContent(content));		
		result.setTopics(getTopicsFromTaskContent(content));		
		result.setDoneStatus(getStatusFromTaskContent(content));

		return result;
	}

	public static ArrayList<TaskFileDTO> parseAllTaskFiles(ArrayList<File> taskMds)
	throws FileNotFoundException, IOException {
		ArrayList<TaskFileDTO> result = new ArrayList<TaskFileDTO>();
		for (File taskMd : taskMds) {
			result.add(parseTaskFile(taskMd));
		}

		return result;
	}
}

