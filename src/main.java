import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/* This program has been done for CS-Tech*/

public class main {
	
	private static Window window;
	private static ComPad comPad;
	private static user user;
	private static UserHint hint;
	private static Menu menu;
	private static Guesses guess;
	private static computer computer;
	private static computer com;
	private static JTextPane textPane;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("CS-TECH");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
		JPanel buttonsPanel = new JPanel();
		contentPane.add(buttonsPanel, BorderLayout.PAGE_END);
		textPane = new JTextPane();
		textPane.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textPane);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		com = new computer(4, 9);
		computer = new computer();
		
		window = new Window("", 800, 400);
		user = (user) new user(10, 200,9)
		.setbind(new bind.AlertListener() {
			@Override
			public void onAlert(String output) {
				
				String userGuessResult = computer.hintcomputer(output); 
				guess.addToHistory(output + " [" + userGuessResult + "]");
				if(userGuessResult.equals("+4-0")) {
					comPad.setMessage(" CONGRUTILATIONS, YOU WON !!!");
					user.setVisibility(InterFace.unvisible);
					return;
				}
				comPad.computerGuess(com.guess()); // take ai's guess about user's number and display it on screen
				hint.setVisibility(InterFace.visible);
				user.setVisibility(InterFace.unvisible);
			}
		})
		.setVisibility(InterFace.unvisible);
		
		window.addDrawable(user);
		
		hint = (UserHint) new UserHint(50, 280)
		.setbind(new bind.AlertListener() {
			@Override
			public void onAlert(String output) {
				if(output.equals("40")) {
					comPad.setMessage("Computer WON !!!");
					hint.setVisibility(InterFace.unvisible);
				}else {
					comPad.setMessage("LET'S GUESS MY NUMBER : ");
					user.setVisibility(InterFace.visible);
				}
			}
		})
		.setVisibility(InterFace.unvisible);
		
		window.addDrawable(hint); // add pad to window
		comPad = (ComPad) new ComPad(20, 40)
		.setbind(new bind.AlertListener() {
			@Override
			public void onAlert(String output) {
				hint.setVisibility(InterFace.unvisible);
			}
		})
		.setVisibility(InterFace.unvisible);
		window.addDrawable(comPad); // add pad to window
		menu = (Menu) new Menu(70, 210)
		.setbind(new bind.AlertListener() {
			@Override
			public void onAlert(String output) {
				comPad.setMessage("LET'S GUESS MY NUMBER");
				user.setVisibility(InterFace.visible);
				comPad.setVisibility(InterFace.visible);
				menu.setVisibility(InterFace.unvisible);
				guess.setVisibility(InterFace.visible);
				computer.createNumber();
			}
		});
				
		window.addDrawable(menu); // add pad to window
		guess = (Guesses) new Guesses(630, 50)
		.setbind(new bind.AlertListener() {
			@Override
			public void onAlert(String output) {
				if(output.equals("reset")) {
					guess.reset(false);
					comPad.reset(false);
					user.reset(false);
					hint.resetHint(false);
					com.reset();
					computer.resetnumber();
				}
			}
		})
		.setVisibility(InterFace.unvisible);
						
		window.addDrawable(guess);
	}

	
}
