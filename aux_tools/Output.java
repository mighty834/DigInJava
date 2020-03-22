package aux_tools;
import java.util.*;

public class Output {
	public static final int MAX_LINE_SIZE = 200;
	public static final String DELIMITER = " | ";
	public static final String LINE_BEGIN = "< ";
	public static final String LINE_BREAK = "...\n";
	public static final String LINE_END = " >";

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

		return result;
	}

    public static void main(String[] args) {
		Integer[] temp = new Integer[10];

		for (int i = 1; i <= 10; i++) {
			temp[i - 1] = i * i * i;
		}

		String str = Output.printList(new ArrayList<Object>(Arrays.asList(temp)));
		println(str);
	}
}

