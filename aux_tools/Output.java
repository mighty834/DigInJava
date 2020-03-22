package aux_tools;
import java.util.*;

public class Output {
	public static final int MAX_LINE_SIZE = 200;
	public static final String DELIMITER = " | ";
	public static final String LINE_BEGIN = "<";
	public static final String LINE_BREAK = "...\n";
	public static final String LINE_END = ">\n";

	private static ArrayList<String> convertData(ArrayList<Object> data) {
		ArrayList<String> result = new ArrayList<String>();
		for (Object elem: data) {
			result.add(elem.toString());
		}

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

	public static String printTable(ArrayList<Object> data, String title) {
		// Here will be code
		return "";
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

		for (int i = 1; i <= 70; i++) {
			data.add(i * i * i);
		}

		printList(data);
	}
}

