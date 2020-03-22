package aux_tools;

public class Terminal {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
 	public static final String ANSI_RED = "\u001B[31m";
 	public static final String ANSI_GREEN = "\u001B[32m";
 	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_CLEAR = "\033[H\033[2J";
		
	public static void setColor(String type) {
        switch (type) {
            case "red": System.out.print(ANSI_RED);
            break;
            case "white": System.out.print(ANSI_WHITE);
            break;
            case "green": System.out.print(ANSI_GREEN);
            break;
            case "black": System.out.print(ANSI_BLACK);
            break;
            default: System.out.println(ANSI_RESET);
        }
    }

	public static void clear() {
		System.out.println(ANSI_CLEAR);
		System.out.print(ANSI_RESET);
		System.out.flush();
	}
}

