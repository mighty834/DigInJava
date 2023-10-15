package diginjava.auxtools;

public class Terminal {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
 	public static final String ANSI_RED = "\u001B[31m";
 	public static final String ANSI_GREEN = "\u001B[32m";
 	public static final String ANSI_YELLOW = "\u001B[33m";
 	public static final String ANSI_BLUE = "\u001B[34m";
 	public static final String ANSI_WHITE = "\u001B[37m";
   	public static final String ANSI_PURPLE = "\u001B[35m";
    	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_CLEAR = "\033[H\033[2J";
		
	public static void setColor(String type) {
        switch (type) {
            case "red": System.out.print(ANSI_RED);
            break;
            case "white": System.out.print(ANSI_WHITE);
            break;
            case "green": System.out.print(ANSI_GREEN);
            break;
            case "yellow": System.out.print(ANSI_YELLOW);
            break;
            case "blue": System.out.println(ANSI_BLUE);
            break;
            case "black": System.out.print(ANSI_BLACK);
            break;
            case "purple": System.out.print(ANSI_PURPLE);
            break;
            case "cyan": System.out.print(ANSI_CYAN);
            break;
            default: System.out.println(ANSI_RESET);
        }
    }

    public static String getColor(String type) {
        switch (type) {
            case    "red": return ANSI_RED;
            case  "white": return ANSI_WHITE;
            case  "green": return ANSI_GREEN;
            case "yellow": return ANSI_YELLOW;
            case   "blue": return ANSI_BLUE;
            case  "black": return ANSI_BLACK;
            case "purple": return ANSI_PURPLE;
            case   "cyan": return ANSI_CYAN;
            default      : return ANSI_RESET;
        }
    }

	public static void clear() {
		System.out.println(ANSI_CLEAR);
		System.out.print(ANSI_RESET);
		System.out.flush();
	}
}

