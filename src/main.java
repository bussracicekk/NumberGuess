import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/* This program has been done for CS-Tech*/

public class main {
	
	private static ComPad comPad;
	private static user user;
	private static UserHint hint;
	private static Menu menu;
	private static Guesses guess;
	private static computer computer, com;
	private static JTextPane textPane;
	public static Window w;
	
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
		com = new computer(0, 9);
		computer = new computer();//computer can random numbers
		
		w = new Window(800, 400);//main window
		user = (user) new user(10, 200,9)//user class is invoked with this
		.setbind(new bind.AlertListener() {
			@Override
			public void onAlert(String output) {
				
				String userGuessResult = computer.hintcomputer(output); //guesses of user
				guess.addToHistory(output + " [" + userGuessResult + "]");
				if(userGuessResult.equals("+4-0")) {
					comPad.setMessage(" CONGRUTILATIONS, YOU WON !!!");//When user is won
					user.setVisibility(InterFace.unvisible);
					return;
				}
				comPad.computerGuess(com.guess());
				hint.setVisibility(InterFace.visible);
				user.setVisibility(InterFace.unvisible);
			}
		})
		.setVisibility(InterFace.unvisible);
		
		w.addInterFace(user);//user interface
		
		hint = (UserHint) new UserHint(50, 280)
		.setbind(new bind.AlertListener() {
			@Override
			public void onAlert(String output) {
				if(output.equals("40")) {
					comPad.setMessage("Computer WON !!!");//When computer is won
					hint.setVisibility(InterFace.unvisible);
				}else {
					comPad.setMessage("LET'S GUESS MY NUMBER : ");
					user.setVisibility(InterFace.visible);
				}
			}
		})
		.setVisibility(InterFace.unvisible);
		
		w.addInterFace(hint);//hint interface
		comPad = (ComPad) new ComPad(20, 40)
		.setbind(new bind.AlertListener() {
			@Override
			public void onAlert(String output) {
				hint.setVisibility(InterFace.unvisible);
			}
		})
		.setVisibility(InterFace.unvisible);
		w.addInterFace(comPad);
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
				
		w.addInterFace(menu);//menu interface
		guess = (Guesses) new Guesses(630, 50)
		.setbind(new bind.AlertListener() {
			@Override
			public void onAlert(String output) {
				if(output.equals("reset")) {
					//Reset of datas
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
						
		w.addInterFace(guess);//guess interface
	}

	
}
