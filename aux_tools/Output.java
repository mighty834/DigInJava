package aux_tools;
import java.util.*;

public class Output {
	public static final int MAX_LINE_SIZE = 200;
	public static final String DELIMITER = " | ";
	public static final String LINE_BEGIN = "<";
	public static final String LINE_BREAK = "...\n";
	public static final String LINE_END = ">\n";
	public static final String TABLE_TITLE_ROW = "=";

	private static ArrayList<String> convertData(ArrayList<Object> data) {
		ArrayList<String> result = new ArrayList<String>();
		for (Object elem: data) {
			result.add(elem.toString());
		}

		return result;
	}

	private static String genTableTitle(String title) {
		String result = "";
		int limiter = 0;

		limiter = (int)Math.floor(MAX_LINE_SIZE / 2) - (int)Math.floor(title.length() / 2);
		for (int i = 0; i < limiter; i++) {
			result += TABLE_TITLE_ROW;
		}
		result += title;

		limiter = MAX_LINE_SIZE - result.length();
		for (int i = 0; i < limiter; i++) {
			result += TABLE_TITLE_ROW;
		}
		result += "\n";
		
		return result;
	}

	public static String print(Object value) {
		System.out.print(value);
		return value.toString();
	}

	public static String println(Object value) {
		System.out.println(value);
		return value.toString();
	}

	public static ArrayList<Object> convertArray(Object[] data) {
		return new ArrayList<Object>(Arrays.asList(data));
	}

	public static String printList(ArrayList<Object> list) {
		ArrayList<String> data = convertData(list);
		String result = "";

		String line = "";
		for (String elem: data) {
			if (line.length() + elem.length() > MAX_LINE_SIZE) {
				if (result.length() == 0) result += LINE_BEGIN + "\n";
				result += line + LINE_BREAK;
				line = elem;
			} else {
				if (line.length() == 0) {
					line += elem;
				} else {
					line += DELIMITER + elem;
				}
			}
		}

		if (result.length() == 0) {
			result += LINE_BEGIN + line + LINE_END;
		} else {
			result += line + "\n" + LINE_END;
		}

		print(result);
		return result;
	}

	public static String printList(Object[] data) {
		return printList(convertArray(data));
	}

	public static String printTable(ArrayList<Object> list, String title) {
		ArrayList<String> data = convertData(list);
		String result = "";

		int cellSize = 0;
		for (String elem: data) {
			if (cellSize < elem.length()) {
				cellSize = elem.length();
			}
		}

		int cellsLineQuantity = (int)Math.ceil(MAX_LINE_SIZE / (cellSize + DELIMITER.length()));

		result += genTableTitle(title);
	
		int lineCounter = 0;
		int specialQuantity = -1;
		for (String elem: data) {
			if (lineCounter < cellsLineQuantity) {
				if (lineCounter == 0) {
					 result += LINE_BEGIN;
				}

				for (int i = elem.length(); i < cellSize; i++) {
					result += " ";
				}

				if (lineCounter == cellsLineQuantity - 1) {
					result += elem;
				} else {
					result += elem + DELIMITER;
				}

				lineCounter++;
			} else {
				if (lineCounter == cellsLineQuantity) {
					if (specialQuantity == -1) {
						specialQuantity = MAX_LINE_SIZE - result.length() + MAX_LINE_SIZE;
					}

					for (int i = 0; i < specialQuantity; i++) {
						result += " ";
					}
					result += LINE_END;
				}
				lineCounter = 0;
			}
		}

		specialQuantity = MAX_LINE_SIZE - (result.length() - result.lastIndexOf("<") - 1 + LINE_END.length());
		for (int i = 0; i < specialQuantity; i++) {
			result += " ";
		}

		result += LINE_END;
		print(result);
		return result;
	}

	public static String printColumnsTable(
		ArrayList<String> columnsTitles,
		ArrayList<Object> data,
		String title
	) {
		// Here will be code
		return "";	
	}

	public static String printRowsTable(
		ArrayList<String> rowsTitles,
		ArrayList<Object> data,
		String title
	) {
		// Here will be code
		return "";
	}

	public static String printBothTable(
		ArrayList<String> columnsTitles,
		ArrayList<String> rowsTitles,
		ArrayList<Object> data,
		String title
	) {
		// Here will be code
		return "";
	}

    public static void main(String[] args) {
		ArrayList<Object> data = new ArrayList<Object>();

		for (int i = 1; i <= 100; i++) {
			data.add(i * i * i);
		}

		printTable(data, "Look to my special table!");
	}
}

