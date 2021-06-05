package engine;
import aux_tools.*;
import engine.exceptions.*;
import engine.dto.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Informer {
	private ArrayList<TaskFileDTO> _allTaskFilesData;

	public Informer() throws FileNotFoundException, IOException {
		_allTaskFilesData = Parser.parseAllTaskFiles(
			Loader.getAllTaskFiles()
		);
	}

	public HashSet<String> getAllDefinedTopics() {
		HashSet<String> result = new HashSet<String>();
		
		for (TaskFileDTO data : _allTaskFilesData) {
			for (String value : data.getTopics()) {
				result.add(value);
			}
		}

		return result;
	}

	public HashSet<String> getAllExNumsByTopic(String topic) {
		HashSet<String> result = new HashSet<String>();

		for (TaskFileDTO data : _allTaskFilesData) {
			if (data.getTopics().contains(topic)) {
				result.add(data.getExNum() + "");
			}
		}

		return result;	
	}

	public HashSet<String> getAllExNumsByStatus(boolean neededStatus) {
		HashSet<String> result = new HashSet<String>();

		for (TaskFileDTO data : _allTaskFilesData) {
			if (data.getDoneStatus() == neededStatus) {
				result.add(data.getExNum() + "");
			}
		}

		return result;
	}

	public HashMap<String, HashSet<String>> getAllDefinedTopicsDetail() {
		HashMap<String, HashSet<String>> result = new HashMap<String, HashSet<String>>();

		for (String topic : getAllDefinedTopics()) {
			result.put(topic, getAllExNumsByTopic(topic));
		}

		return result;
	}
}

