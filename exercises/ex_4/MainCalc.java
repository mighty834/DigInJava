package exercises;
import aux_tools.*;
import java.awt.*;
import java.awt.event.*;

class MainCalc extends Frame implements ActionListener {
	public static final String calcTitle = "Calculator";	
	public static final int calcHeight   = 400;
	public static final int calcWidth    = 300;

	Button num1, num2, num3, num4, num5, num6, num7, num8, num9, num0,
		   addition, subtraction, multiplication, division,
		   clearButton, resultButton, exitButton;

	TextField screen;

	private String calculateString(String str) {
		String compactStrValue = "";
		String operator = "";
		int leftOperand;
		int rightOperand;

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ') {
				compactStrValue += str.charAt(i);
			}
		}

		if (compactStrValue.contains("+")) {
			operator = "+";
		}
		else if (compactStrValue.contains("-")) {
			operator = "-";
		}
		else if (compactStrValue.contains("*")) {
			operator = "*";
		}
		else if (compactStrValue.contains("/")) {
			operator = "/";
		}
		else {
			return str;
		}

		leftOperand = Integer.parseInt(
			compactStrValue.substring(0, compactStrValue.indexOf(operator))
		);
		rightOperand = Integer.parseInt(
			compactStrValue.substring(compactStrValue.indexOf(operator) + 1)
		);

		switch (operator) {
			case "+" : return String.valueOf(leftOperand + rightOperand);
			case "-" : return String.valueOf(leftOperand - rightOperand);
			case "*" : return String.valueOf(leftOperand * rightOperand);
			case "/" : return String.valueOf(leftOperand / rightOperand);
			default : return "";
		}
	}

	MainCalc() {
		num1 = new Button(" 1 ");
		num2 = new Button(" 2 ");
		num3 = new Button(" 3 ");
		num4 = new Button(" 4 ");
		num5 = new Button(" 5 ");
		num6 = new Button(" 6 ");
		num7 = new Button(" 7 ");
		num8 = new Button(" 8 ");
		num9 = new Button(" 9 ");
		num0 = new Button(" 0 ");
		addition       = new Button("+");
		subtraction    = new Button("-");
		multiplication = new Button("*");
		division 	   = new Button("/");
		clearButton  = new Button(" C ");
		resultButton = new Button(" = ");
		exitButton   = new Button("exit");

		screen = new TextField(35);

		add(screen);	
		add(num1);	
		add(num2);	
		add(num3);	
		add(num4);	
		add(num5);	
		add(num6);	
		add(num7);	
		add(num8);	
		add(num9);	
		add(num0);
		add(addition);
		add(subtraction);
		add(multiplication);
		add(division);	
		add(clearButton);	
		add(resultButton);
		add(exitButton);

		setTitle(calcTitle);
		setLayout(new FlowLayout());
		setSize(calcWidth, calcHeight);

		num1.addActionListener(this);	
		num2.addActionListener(this);	
		num3.addActionListener(this);	
		num4.addActionListener(this);	
		num5.addActionListener(this);	
		num6.addActionListener(this);	
		num7.addActionListener(this);	
		num8.addActionListener(this);	
		num9.addActionListener(this);	
		num0.addActionListener(this);
		addition.addActionListener(this);
		subtraction.addActionListener(this);
		multiplication.addActionListener(this);
		division.addActionListener(this);	
		clearButton.addActionListener(this);	
		resultButton.addActionListener(this);	
		exitButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == num1) {
			screen.setText(screen.getText() + "1");
		}
		else if (event.getSource() == num2) {
			screen.setText(screen.getText() + "2");
		}
		else if (event.getSource() == num3) {
			screen.setText(screen.getText() + "3");
		}
		else if (event.getSource() == num4) {
			screen.setText(screen.getText() + "4");
		}
		else if (event.getSource() == num5) {
			screen.setText(screen.getText() + "5");
		}
		else if (event.getSource() == num6) {
			screen.setText(screen.getText() + "6");
		}
		else if (event.getSource() == num7) {
			screen.setText(screen.getText() + "7");
		}
		else if (event.getSource() == num8) {
			screen.setText(screen.getText() + "8");
		}
		else if (event.getSource() == num9) {
			screen.setText(screen.getText() + "9");
		}
		else if (event.getSource() == num0) {
			screen.setText(screen.getText() + "0");
		}
		else if (event.getSource() == addition) {
			screen.setText(screen.getText() + "+");	
		}
		else if (event.getSource() == subtraction) {
			screen.setText(screen.getText() + "-");	
		}
		else if (event.getSource() == multiplication) {
			screen.setText(screen.getText() + "*");	
		}
		else if (event.getSource() == division) {
			screen.setText(screen.getText() + "/");	
		}
		else if (event.getSource() == clearButton) {
			screen.setText("");
		}
		else if (event.getSource() == resultButton) {
			screen.setText(calculateString(screen.getText()));		
		}
		else if (event.getSource() == exitButton) {
			System.exit(0);
		}
		else {
			screen.setText("This button haven't action handler!");
		}
		
	}

	public static void main(String[] args) {
		Output.println("Hello calculation!", Output.RED_COLOR);
	}
}

