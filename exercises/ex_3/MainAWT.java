package exercises;
import aux_tools.*;
import engine.exceptions.*;
import java.awt.*;
import java.awt.event.*;

public class MainAWT extends Frame implements ActionListener {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws MainException {
		MainAWT awt = new MainAWT();	
	}

	MainAWT() {
		Button button = new Button("Push for close the window");
		button.setBounds(450, 360, 300, 80);

		add(button);
		setSize(1200, 800);
		setLayout(null);
		setVisible(true);

		button.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		Output.println("It's work!");
		System.exit(0);
	}
}

