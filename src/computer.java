import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class computer {
	
	private ArrayList<String> pos = new ArrayList<String>();//list of combinations 4 digits numbers
	public int digits, num;
	private String number = "";
	public static Random r = new Random();

	public computer(int digits, int numbers) {
		this.digits = digits;
		this.num = numbers;
		
		allCom();
	}
	public computer() {
		
	}
	
	private void allCom() {//find the 4 digits numbers combinations
		for (int i = 0; i < this.num; i++) {
			String string = "";
			string += (i+1);
			for (int j = 0; j < this.num; j++) {
				String string2 = string;
				if(!string2.contains((j+1)+"")){
					string2 += (j+1);
				}
				for (int k = 0; k < this.num; k++) {
					String string3 = string2;
					if(!string3.contains((k+1)+"")){
						string3 += (k+1);
					}
					for (int s = 0;s < this.num; s++) {
						String string4 = string3;
						if(!string4.contains((s+1)+"")){
							string4 += (s+1);
							if(string4.length() == 4) {
								pos.add(string4);
							}
						}
					}
				}
			}
		}
	}
	public String createNumber() {//create random numbers
		r = new Random();
		number = "";
		int randomDigit = r.nextInt(10);
		while(number.length() < 4) {
			randomDigit = r.nextInt(10);
			if(!number.contains(randomDigit + "")) {
				number += randomDigit;
			}
		}
		return number;
	}

	public String hintcomputer(String guess) {//create hint for user
		int sameValues = 0; // sameValues: good digits well placed
		int diffValues = 0; // diffValues: good digits bad placed

		for (int i = 0; i < number.length(); i++) {

			char a = guess.charAt(i);

			if (a == number.charAt(i)) {
				sameValues++;
			} else if (number.contains(String.valueOf(a))) {
				diffValues++;
			}
		}
		String hint = "+" + sameValues + "-" + diffValues;
		return hint;
	}
	
	
	public String guess() {// When possibility list is empty
		if(pos.size() == 0){
			return "OK";
		}
		String randomnumber = null;
		for (int i = 0; i < pos.size(); i++) {
			randomnumber = pos.get(new Random().nextInt(pos.size()));
		}
		return randomnumber;
	}
	public void reset() {
		pos.clear();
		allCom();
	}
	public void resetnumber() {
		number = "";
	}
	
}

class ComPad extends bind {
	
	public String commes = "", comguess = "";
	public ComPad(int x, int y) {
		super(x, y);
	}
	public void computerGuess(String guess) {
		if(guess.equals("OK")) {
			alert.onAlert("OK");
			setMessage("Your number : '" + comguess + "'");//when computer find the user's number
		}else {
			comguess = guess;
			setMessage("MY GUESS : '" + comguess + "' ?");//guess of comouter
		}
	}
	public void setMessage(String message) {
		commes = message;
	}
	
	public void reset(boolean vis) {
		setVisibility(vis);
		commes = " ";
		comguess = "";
	}

	@Override
	public String getOutput() {
		return commes;
	}
	
	@Override
	public void rendergraph(Graphics g) {
		super.rendergraph(g);
		if(!super.v) {
			return;
		}
		g.setColor(Color.DARK_GRAY);
		g.drawString(commes, super.i, super.j+50);
		g.setColor(Color.BLACK);
	}
	
	@Override
	public void clickOnMouse(int action, int mouse_x, int mouse_y) {
		if(!v) {
			return;
		}
	}

}
class Menu extends bind {
	
	private int StartButton = 0 /*Starting button of game*/, paddingmenu = 20;
	private ArrayList<Button> buttonsmenu = new ArrayList<Button>();//buttons list of menu
	private Button.OnClickListener buttonOnClickListenermenu;
	
	public Menu(int x, int y) {
		super(x, y);
		buttonStart();
		initmenu();
	}

	private void buttonStart() {
		buttonOnClickListenermenu = new Button.OnClickListener() {
			@Override
			public void onClick(int id) {
				if(id == StartButton) {//	When click the start button,then user start the game
					alert.onAlert("user");
				}
			}
		};
	}
	
	private void initmenu() {//location of start button
		StartButton = buttonsmenu.size()+1;
		buttonsmenu.add(new Button(super.i + (250+paddingmenu), super.j - (65 + paddingmenu), 150, 80).setText("START").setID(StartButton).setOnClickListener(buttonOnClickListenermenu));
	}

	public Menu setPadding(int padding) {
		this.paddingmenu = padding;
		return this;
	}
	
	@Override
	public void rendergraph(Graphics g) {
		super.rendergraph(g);
		if(!super.v) {
			return;
		}
		g.setColor(Color.BLACK);
		for (Button button : buttonsmenu) {
			button.rendergraph(g);
		}
	}
	
	@Override
	public void clickOnMouse(int action, int mouse_x, int mouse_y) {
		if(!v) {
			return;
		}
		for (Button button : buttonsmenu) {
			button.clickOnMouse(action, mouse_x, mouse_y);
		}
	}

}
