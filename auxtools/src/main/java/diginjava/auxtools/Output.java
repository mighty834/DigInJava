package diginjava.auxtools;
import java.util.*;

public class Output {
	public static final int MAX_LINE_SIZE 	   	  = 200;
	public static final String DELIMITER  	   	  = " | ";
	public static final String LINE_BEGIN 	   	  = "<";
	public static final String LINE_BREAK 	   	  = "...\n";
    public static final String STR_BREAK  	   	  = "...";
    public static final String EMPTY_CELL      	  = "EMPTY";
	public static final String LINE_END   	   	  = ">\n";
	public static final String TABLE_TITLE_ROW 	  = "=";
	public static final String V_LIST_LINE_PREFIX = "> ";
	public static final String V_LIST_LINE_END    = "\n";

	public static final String RESET_COLOR  = "";
	public static final String BLACK_COLOR  = "black";
	public static final String RED_COLOR    = "red";
	public static final String GREEN_COLOR  = "green";
	public static final String YELLOW_COLOR = "yellow";
	public static final String BLUE_COLOR   = "blue";
	public static final String WHITE_COLOR  = "white";
	public static final String PURPLE_COLOR = "purple";
	public static final String CYAN_COLOR   = "cyan";

	private static ArrayList<String> convertDataToStr(ArrayList<Object> data) {
		ArrayList<String> result = new ArrayList<String>();
		for (Object elem: data) {
			result.add(elem.toString());
		}

		return result;
	}

    @SuppressWarnings("unchecked")
	private static ArrayList<String> mergeLists(ArrayList<String> ... lists) {
        ArrayList<String> result = new ArrayList<String>();

        for (ArrayList<String> list: lists) {
            result.addAll(list);
        }
        return result;
    }

    private static String genTableTitle(String title, int size) {
		String result = "";
		int limiter = 0;

		limiter = (int)Math.floor(size / 2) - (int)Math.floor(title.length() / 2);
		for (int i = 0; i < limiter; i++) {
			result += TABLE_TITLE_ROW;
		}
		result += title;

		limiter = size - result.length();
		for (int i = 0; i < limiter; i++) {
			result += TABLE_TITLE_ROW;
		}
		result += "\n";
		
		return result;
	}

    private static String genTableTitle(String title) {
        return genTableTitle(title, MAX_LINE_SIZE);
    }

    private static String genCustomRow(String elem, int rowLength) {
        String result = "";
        for (int i = 0; i < rowLength; i++) {
            result += elem;
        }
        result += "\n";

        return result;
    }

	private static String genRowOfColumnTitles(
        ArrayList<String> titles,
        String mainTitle,
        int cellSize,
        int tableWidth
    ) {
		String result = "";
        
        for (String title: titles) {
            if (result.length() == 0) {
                result += genTableTitle(mainTitle, tableWidth);
                result += genCustomRow(TABLE_TITLE_ROW, tableWidth);
                result += LINE_BEGIN;
            } else {
                result += DELIMITER;
            }

            if (title.length() > cellSize) {
                result += title.substring(0, cellSize - STR_BREAK.length()) + STR_BREAK;
            } else {
                String temp = "";
                for (int i = title.length(); i < cellSize; i++) {
                    temp += " ";
                }

                result += temp + title;
            }
        }

        for (
            int i = result.substring(result.lastIndexOf("\n"), result.length()).length();
            i < tableWidth;
            i++    
        ) {
            result += " ";
        }
        result += LINE_END;
        result += genCustomRow(TABLE_TITLE_ROW, tableWidth);

		return result;
	}

	private static int getCellSize(List<String> list) {
		int cellSize = 0;
		for (String elem: list) {
			if (elem.length() > cellSize) {
				cellSize = elem.length();
			}
		}
		
		return cellSize;
	}

	public static String print(Object value) {
		System.out.print(value);
		return value.toString();
	}

	public static String println(Object value) {
		System.out.println(value);
		return value.toString();
	}

    public static String print(Object value, String color) {
        return print(
            Terminal.getColor(color) + value.toString() + Terminal.getColor("")
        );
    }

    public static String println(Object value, String color) {
        return println(
            Terminal.getColor(color) + value.toString() + Terminal.getColor("")
        );
    }

	public static String paint(Object value, String color) {
		return Terminal.getColor(color) + value.toString() + Terminal.getColor("");
	}

	public static String paintln(Object value, String color) {
		return Terminal.getColor(color) + value.toString() + Terminal.getColor("") + "\n";
	}

	public static ArrayList<Object> convertArrayToList(Object[] data) {
		return new ArrayList<Object>(Arrays.asList(data));
	}

	public static String printList(ArrayList<Object> list) {
		ArrayList<String> data = convertDataToStr(list);
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
		return printList(convertArrayToList(data));
	}

	public static String printList(HashSet<Object> data) {
		ArrayList<Object> list = new ArrayList<Object>();
		list.addAll(data);
		return printList(list);
	}

	public static String printVerticalList(ArrayList<String> list) {
		String result = "";

		for (String value : list) {
			result += V_LIST_LINE_PREFIX + value + V_LIST_LINE_END;
		}
		result += "\n";

		print(result);
		return result;
	}

	public static String printVerticalList(HashSet<String> list) {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.addAll(list);
		return printVerticalList(arrayList);
	}

	public static String printVerticalList(HashMap<String, HashSet<String>> data) {
		ArrayList<String> list = new ArrayList<String>();
		data.forEach((key, values) -> {
			ArrayList<String> listValues = new ArrayList<String>();
			listValues.addAll(values);

			String row = key + DELIMITER;
			for (int i = 0; i < listValues.size(); i++) {
				if (i == listValues.size() - 1) {
					row += listValues.get(i) + " ";
				} else {
					row += listValues.get(i) + ", ";
				}
			}
			
			list.add(row);
		});

		return printVerticalList(list);
	}

	public static String genTable(ArrayList<Object> list, String title, int width, int cellSize) {
		ArrayList<String> data = convertDataToStr(list);
		String result = "";
		int cellsLineQuantity = (int)Math.ceil(width / (cellSize + DELIMITER.length()));

        if (title.length() > 0) {
		    result += genTableTitle(title, width);
        }
	
		int lineCounter = 0;
		int specialQuantity = -1;
		for (int elemIndex = 0; elemIndex < data.size(); elemIndex++) {
            String elem = data.get(elemIndex);

			if (lineCounter < cellsLineQuantity) {
				if (lineCounter == 0) {
					 result += LINE_BEGIN;
				}

                if (elem.length() > cellSize) {
                    result += elem.substring(0, cellSize - STR_BREAK.length()) + STR_BREAK;
                } else {
                    for (int i = elem.length(); i < cellSize; i++) {
                        result += " ";
                    }
                   
                    result += elem;
                }

                if (lineCounter != cellsLineQuantity - 1) {
                    result += DELIMITER;
                }

				lineCounter++;
			} else {
				if (lineCounter == cellsLineQuantity) {
					if (specialQuantity == -1) {
                        if (title.length() == 0) {
					        specialQuantity = width - result.length();
                        } else {
                            specialQuantity = 2 * width - result.length();
                        }
					}

					for (int i = 0; i < specialQuantity; i++) {
						result += " ";
					}
					result += LINE_END;
				}
				lineCounter = 0;
                elemIndex--;
			}
		}

		specialQuantity = width - (result.length() - result.lastIndexOf(LINE_BEGIN) - 1 + LINE_END.length());
        if (title.length() == 0) specialQuantity++;

		for (int i = 0; i < specialQuantity; i++) {
			result += " ";
		}

        if (result.charAt(result.length() - 1) != '\n') {
            result += LINE_END;
        }

		return result;
	}

    public static String genTable(ArrayList<Object> list, String title) {
        return genTable(list, title, MAX_LINE_SIZE, getCellSize(convertDataToStr(list)));
    }

    public static String printTable(ArrayList<Object> list, String title, int width, int cellSize) {
        return print(genTable(list, title, width, cellSize));
    }

    public static String printTable(ArrayList<Object> list, String title) {
        return print(genTable(list, title));
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<Object> aggregateData(ArrayList<Object> ... lists) {
        ArrayList<Object> result = new ArrayList<Object>();
        int maxListSize = 0;
        for (ArrayList<Object> list: lists) {
            if (list.size() > maxListSize) {
                maxListSize = list.size();
            }
        }

        for (int i = 0; i < maxListSize; i++) {
            for (ArrayList<Object> list: lists) {
                if (list.size() > i) {
                    result.add(list.get(i));
                } else {
                    result.add(EMPTY_CELL);
                }
            }
        }

        return result;
    }

	public static String printColumnsTable(
		ArrayList<String> columnsTitles,
		ArrayList<Object> list,
		String title,
        String color,
        int customCellSize
	) {
		ArrayList<String> data = convertDataToStr(list);
        int cellSize;
        if (customCellSize > 0) {
            cellSize = customCellSize + STR_BREAK.length();
        } else {
            @SuppressWarnings("unchecked")
            ArrayList<String> tempList = mergeLists(data, columnsTitles);
            cellSize = getCellSize(tempList);
        }

        int tableWidth = cellSize * columnsTitles.size() + DELIMITER.length() * (columnsTitles.size() + 1);
        
		String result = Terminal.getColor(color);
		result += genRowOfColumnTitles(columnsTitles, title, cellSize, tableWidth);
        result += Terminal.getColor("");
        result += genTable(list, "", tableWidth - 1, cellSize);

	    print(result);

		return "";	
	}

    @SuppressWarnings("unchecked")
    public static String printColumnsTable(
        ArrayList<String> columnsTitles,
        String title,
        String color,
        int customCellSize,
        ArrayList<Object> ... columns
    ) throws Exception {
        if (columnsTitles.size() != columns.length) {
            throw new Exception("titles quantity must be equal to columns");
        }

        @SuppressWarnings("unchecked")
        ArrayList<Object> allColumns = aggregateData(columns);
        
        return printColumnsTable(
            columnsTitles,
            allColumns,
            title,
            color,
            customCellSize
        );
    }

    public static String printColumnsTable(
        ArrayList<String> columnsTitles,
        ArrayList<Object> list,
        String title
    ) {
        return printColumnsTable(columnsTitles, list, title, "cyan", 0);
    }

    @SuppressWarnings("unchecked")
    public static String printColumnsTable(
        ArrayList<String> columnsTitles,
        String title,
        ArrayList<Object> ... columns
    ) throws Exception {
        if (columnsTitles.size() != columns.length) {
            throw new Exception("titles quantity must be equal to columns");
        }

        @SuppressWarnings("unchecked")
        ArrayList<Object> allColumns = aggregateData(columns);

        return printColumnsTable(columnsTitles, allColumns, title);
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
}

