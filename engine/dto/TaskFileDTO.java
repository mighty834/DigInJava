package engine.dto;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskFileDTO {
	private String _description;
	private ArrayList<String> _topics;
	private boolean _isDone;
	private int _exNum;

	public TaskFileDTO() {
		_topics 	 = new ArrayList<String>();
		_description = "";
		_isDone 	 = false;
		_exNum		 = 0;
	}

	public TaskFileDTO(String description,
					   ArrayList<String> topics,
					   boolean status,
					   int exNum) {
		_description = description;
		_topics      = topics;
		_isDone 	 = status;
		_exNum		 = exNum;
	}

	public String getDescription() {
		return this._description;
	}

	public void setDescription(String value) {
		this._description = value;
	}

	public ArrayList<String> getTopics() {
		return _topics;
	}

	public void setTopics(ArrayList<String> value) {
		_topics = value;
	}

	public void addTopic(String topic) {
		_topics.add(topic);
	}

	public boolean getDoneStatus() {
		return _isDone;
	}

	public void setDoneStatus(boolean value) {
		_isDone = value;
	}

	public int getExNum() {
		return _exNum;
	}

	public void setExNum(int exNum) {
		_exNum = exNum;
	}
}

