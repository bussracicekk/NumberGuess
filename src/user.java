import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.TextField;
import java.util.ArrayList;
public class user extends bind {
	private int deleteButton = 0, OkButton = 0, button_count = 0, padding = 10;
	private ArrayList<Button> buttons= new ArrayList<Button>();
	private Button.OnClickListener buttonOnClickListener;
	private String current_text = "";
	TextArea output=new TextArea(30,35);
    TextField edit=new TextField(4);
	
	public user(int x, int y, int button_count) {
		super(x, y);
		this.button_count = button_count;
		inituser();
		initUIuser();
	}

	private void inituser() {
		buttons = new ArrayList<Button>();
		
		buttonOnClickListener = new Button.OnClickListener() {
			@Override
			public void onClick(int id) {
				if(id == OkButton && current_text.length() == 4) {
					String output = getOutput();
					deleteAllChar();
					reset(false);
					alert.onAlert(output);
					return;
				}else if(id == deleteButton && current_text.length() != 0) {
					deleteLastChar();
					buttons.get(OkButton-1).setVisibility(InterFace.unvisible);
					if(current_text.length() == 0) {
						buttons.get(deleteButton-1).setVisibility(InterFace.unvisible);
					}
					return;
				}else if(id > -1 && id <= button_count) {
					if(current_text.length() == 4) {
						return;
					}
					
					current_text += id+"";
					
					buttons.get(deleteButton-1).setVisibility(InterFace.visible);
					buttons.get(id).setVisibility(InterFace.unvisible);
					if(current_text.length() == 4) {
						buttons.get(OkButton-1).setVisibility(InterFace.visible);
					}
				}
			}
		};
	}
	
	
	private void initUIuser() {
		for (int i = 0; i <= button_count; i++) {
			buttons.add(new Button(super.i + (50+padding)*i, super.j, 50, 50).setText(i+"").setID(i).setOnClickListener(buttonOnClickListener));
		}
		deleteButton = buttons.size()+1;
		buttons.add(new Button(super.i + (240+padding), super.j - (60 + padding), 100, 50).setText(String.valueOf("delete")).setID(deleteButton).setOnClickListener(buttonOnClickListener).setVisibility(InterFace.unvisible));
		OkButton = buttons.size()+1;
		buttons.add(new Button(super.i + (100+padding), super.j - (60 + padding), 100, 50).setText("OK").setID(OkButton).setOnClickListener(buttonOnClickListener).setVisibility(InterFace.unvisible));
	}
	private int deleteLastChar() {
		int deleted_id = Integer.parseInt(current_text.substring(current_text.length()-1, current_text.length()));
		current_text = current_text.substring(0, current_text.length()-1);
		buttons.get(deleted_id).setVisibility(InterFace.visible);
		return deleted_id;
	}

	public void reset(boolean vis) {
		setVisibility(vis);
		for (Button button : buttons) {
			button.setVisibility(InterFace.visible);
		}
		current_text = "";
		buttons.get(deleteButton-1).setVisibility(InterFace.unvisible);
		buttons.get(OkButton-1).setVisibility(InterFace.unvisible);
	}
	private void deleteAllChar() {
		for (int i = 0; i < 4; i++) {
			buttons.get(deleteLastChar()).setVisibility(InterFace.visible);
		}
	}
	public user setPadding(int padding) {
		this.padding = padding;
		return this;
	}	
	@Override
	public String getOutput() {
		return current_text;
	}
	
	@Override
	public void rendergraph(Graphics g) {
		super.rendergraph(g);
		if(!super.v) {
			return;
		}
		
		g.setColor(Color.DARK_GRAY);
		g.drawString(current_text, super.i, super.j - 40);
		
		for (Button button : buttons) {
			button.rendergraph(g);
		}
	}
	
	@Override
	public void clickOnMouse(int action, int mouse_x, int mouse_y) {
		if(!v) {
			return;
		}
		for (Button button : buttons) {
			button.clickOnMouse(action, mouse_x, mouse_y);
		}
	}
	
}
class UserHint extends bind {
	
	private int increasebutton = 0, decreasebutton = 0,iplus = 0, dplus = 0, iminus = 0, dminus = 0, ok = 0;
	private ArrayList<Button> buttonshint = new ArrayList<Button>();
	private int paddinghint = 20;
	private Button.OnClickListener buttonOnClick;
	private int samePlace = 0, diffPlace = 0;

	public UserHint(int x, int y) {
		super(x, y);
		initToolsHint();
		initUIHint();
	}

	private void initToolsHint() {
		
		buttonOnClick = new Button.OnClickListener() {
			@Override
			public void onClick(int id) {
				if(id == iplus && (samePlace + diffPlace < 4)) {
					buttonshint.get(increasebutton-1).setText(++samePlace+"");
				}else if(id == iminus && samePlace > 0) {
					buttonshint.get(increasebutton-1).setText(--samePlace+"");
				}else if(id == dplus && (samePlace + diffPlace < 4)) {
					buttonshint.get(decreasebutton-1).setText(++diffPlace+"");
				}else if(id == dminus && diffPlace > 0) {
					buttonshint.get(decreasebutton-1).setText(--diffPlace+"");
				}else if(id == ok) {
					alert.onAlert(getOutput());
					resetHint(false);
				}else if(id == increasebutton) {
					samePlace = 0;
					buttonshint.get(increasebutton-1).setText("+"+samePlace+"");
				}else if(id == decreasebutton) {
					diffPlace = 0;
					buttonshint.get(decreasebutton-1).setText("-"+diffPlace+"");
				}
			}
		};
	}
	
	private void initUIHint() {
		increasebutton = buttonshint.size()+1;
		buttonshint.add(new Button(super.i + (145+paddinghint), super.j - (65 + paddinghint), 50, 50).setText("+"+samePlace+"").setID(increasebutton));
		decreasebutton = buttonshint.size()+1;
		buttonshint.add(new Button(super.i + (175 + paddinghint) + 30, super.j - (65 + paddinghint), 50, 50).setText("-"+samePlace+"").setID(decreasebutton));

		iplus = buttonshint.size()+1;
		buttonshint.add(new Button(super.i + (150+paddinghint), super.j - (65 + paddinghint) - 30, 50, 25).setText(String.valueOf("Plus")).setID(iplus).setOnClickListener(buttonOnClick));
		iminus = buttonshint.size()+1;
		buttonshint.add(new Button(super.i + (150+paddinghint), super.j - (65 + paddinghint) + 55, 50, 25).setText(String.valueOf("Minus")).setID(iminus).setOnClickListener(buttonOnClick));

		dplus = buttonshint.size()+1;
		buttonshint.add(new Button(super.i + (180 + paddinghint) + 30, super.j - (65 + paddinghint) - 30, 50, 25).setText(String.valueOf("Plus")).setID(dplus).setOnClickListener(buttonOnClick));
		dminus = buttonshint.size()+1;
		buttonshint.add(new Button(super.i + (180 + paddinghint) + 30, super.j - (65 + paddinghint) + 55, 50, 25).setText(String.valueOf("Minus")).setID(dminus).setOnClickListener(buttonOnClick));

		ok = buttonshint.size()+1;
		buttonshint.add(new Button(super.i + (240+paddinghint) + 30, super.j - (65 + paddinghint), 120, 50).setText("OK").setID(ok).setOnClickListener(buttonOnClick));
		buttonshint.add(new Button(super.i + (paddinghint) + 30, super.j - (65 + paddinghint), 120, 50).setText("Hint : ").setID(ok));
	}
	
	public UserHint setPadding(int padding) {
		this.paddinghint = padding;
		return this;
	}
	
	public void resetHint(boolean vis) {
		setVisibility(vis);
		samePlace = 0; diffPlace = 0;
		buttonshint.get(increasebutton-1).setText(samePlace+"");
		buttonshint.get(decreasebutton-1).setText(diffPlace+"");
	}
	
	@Override
	public String getOutput() {
		return samePlace + "" +diffPlace;
	}
	
	@Override
	public void rendergraph(Graphics g) {
		super.rendergraph(g);
		if(!super.v) {
			return;
		}
		g.setColor(Color.WHITE);
		g.drawString("+", super.i-20, super.j - 45);
		g.drawString("-", super.i+80, super.j - 45);
		for (Button button : buttonshint) {
			button.rendergraph(g);
		}
	}
	
	@Override
	public void clickOnMouse(int action, int mouse_x, int mouse_y) {
		if(!v) {
			return;
		}
		for (Button button : buttonshint) {
			button.clickOnMouse(action, mouse_x, mouse_y);
		}
	}

}
class PreviousGuess extends bind {

    private int BACK_BUTTON_ID = 0;
	
	private ArrayList<Button> buttons;
	private Button.OnClickListener buttonOnClickListener;
	private String text = "";
	public PreviousGuess(int x, int y) {
		super(x, y);
		initTools();
		initUIGuess();
	}

	private void initTools() {
		buttons = new ArrayList<Button>();
		
		buttonOnClickListener = new Button.OnClickListener() {
			@Override
			public void onClick(int id) {
				
			}
		};
	}
	
	private void initUIGuess() {
		BACK_BUTTON_ID = buttons.size()+1;
		buttons.add(new Button(super.i, super.j, 25, 15).setText("AAAA").setID(BACK_BUTTON_ID).setColor(new Color(130, 130, 130), new Color(134, 134, 134)).setOnClickListener(buttonOnClickListener));
	}
	public PreviousGuess setHeaderText(String text) {
		buttons.get(BACK_BUTTON_ID-1).setText(text);
		return this;
	}
	public PreviousGuess setText(String text) {
		this.text = text;
		return this;
	}
	
	@Override
	public void rendergraph(Graphics g) {
		super.rendergraph(g);
		if(!super.v) {
			return;
		}
		
		g.setColor(new Color(160, 160, 160));
		g.fillRect(super.i, super.j, 148, 15);
		
		g.setColor(Color.WHITE);
		g.drawString(text, super.i+28, super.j+11);
		for (Button button : buttons) {
			button.rendergraph(g);
		}
	}
	
	@Override
	public void clickOnMouse(int action, int mouse_x, int mouse_y) {
		if(!v) {
			return;
		}
		for (Button button : buttons) {
			button.clickOnMouse(action, mouse_x, mouse_y);
		}
	}

}
class Guesses extends bind {
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private ArrayList<PreviousGuess> guesses = new ArrayList<PreviousGuess>();;
	private Button.OnClickListener buttonOnClick;
	public Guesses(int x, int y) {
		super(x, y);
	}
	public void reset(boolean vis) {
		setVisibility(vis);
		guesses.clear();
	}
	public void addToHistory(String data) {
		guesses.add(new PreviousGuess(super.i+1, super.j + 8 + guesses.size() * 16).setText(data).setHeaderText((guesses.size()+1+"")));
	}

	@Override
	public void rendergraph(Graphics g) {
		super.rendergraph(g);
		if(!super.v) {
			return;
		}
		g.setColor(Color.BLACK);
		g.drawRect(super.i, super.j+7, 150, 221);
		g.fillRect(super.i-1, super.j-20, 150+2, 25);
		
		g.setColor(Color.WHITE);
		g.drawString("Your Previous Guesses", super.i+5, super.j);
		for (Button button : buttons) {
			button.rendergraph(g);
		}
		
		for (PreviousGuess element : guesses) {
			element.rendergraph(g);
		}
	}
	
	@Override
	public void clickOnMouse(int action, int mouse_x, int mouse_y) {
		if(!v) {
			return;
		}
		for (Button button : buttons) {
			button.clickOnMouse(action, mouse_x, mouse_y);
		}
		for (PreviousGuess element : guesses) {
			element.clickOnMouse(action, mouse_x, mouse_y);
		}
	}

}
