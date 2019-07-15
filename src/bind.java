import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class bind extends InterFace {
	protected bind.AlertListener alert;
	public bind(int x, int y) {
		super(x, y);
	}
	public String getOutput() {
		return "";
	}
	
	public InterFace setbind(bind.AlertListener bindListen) {
		this.alert = bindListen;
		return this;
	}
	
	public interface AlertListener {
		public void onAlert(String output);
	}
	
}
class Button extends InterFace{
	
	protected int id = 0;
	private String string = "";
	private int text_x = 0, text_y = 0;
	private Color defaultColor = Color.WHITE;
	private Hint hint = null;
	private OnClickListener onClick;

	
	public Button(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public Button setText(String text) {
		this.string = text;
		return this;
	}
	
	public Button setColor(Color color, Color color2) {
		this.defaultColor = color;
		return this;
	}
	
	public Button setVisibility(boolean state) {
		this.v = state;
		return this;
	}

	public Button setID(int id) {
		this.id = id;
		return this;
	}
	
	public Button setOnClickListener(OnClickListener onClick) {
		this.onClick = onClick;
		return this;
	}
	
	public int getId() {
		return id;
	}
	
	public Button addHint(String hint) {
		this.hint = new Hint(hint);
		return this;
	}
	
	private void centerText(Graphics g) {
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(string, g);
		
		this.text_x = super.i + (super.width - (int) bounds.getWidth()) / 2;
		this.text_y = super.j + (int) bounds.getHeight();
	}
	
	@Override
	public void rendergraph(Graphics g) {
		super.rendergraph(g);
		if(!super.v) {
			return;
		}
			g.setColor(defaultColor);
        g.fillRect(super.i, super.j + super.paddinginterface, super.width, super.height);
		g.setColor(Color.BLACK);
		
		
		centerText(g);
		g.drawString(string, text_x, text_y + super.paddinginterface);
		
		if(this.hint != null) {
			hint.rendergraph(g);
		}
	}
	
	@Override
	public void clickOnMouse(int action, int mouse_x, int mouse_y) {
		if(!v) {
			return;
		}
		super.a = false;
		if((mouse_x > super.i && mouse_x < super.i + super.width) && (mouse_y > super.j && mouse_y < super.j + super.height)) {
			super.a = true;
			super.paddinginterface = -2;
			
			if(this.hint != null) {
				hint.setVisibility(InterFace.visible);
				hint.clickOnMouse(action, mouse_x, mouse_y);
			}
		}else {
			super.paddinginterface = 0;
			if(this.hint != null) {
				hint.setVisibility(InterFace.unvisible);
			}
		}
		
		if(super.a) {
			switch (action) {
			case InterFace.move:
				break;

			case InterFace.press:
				break;
			case InterFace.release:
				if(onClick != null) {
					onClick.onClick(this.id);
				}
				break;
			}
		}
	}
	
	public interface OnClickListener{
		public void onClick(int id);
	}
	
	public class Hint extends InterFace{
		
		private String hint_text;
		public Hint(String text) {
			this.hint_text = text;
			this.setVisibility(InterFace.unvisible);
		}
		public Hint setVisibility(boolean state) {
			this.v = state;
			return this;
		}
		@Override
		public void rendergraph(Graphics g) {
			super.rendergraph(g);
			if(!super.v) {
				return;
			}
			g.setColor(new Color(125, 192, 104, 150));
	        g.fillRect(super.i, super.j-15, (int) (hint_text.length() * 7f), 20);
			g.setColor(new Color(255, 255, 255, 150));
			g.drawString(hint_text, super.i+7, super.j);
		}
		
		@Override
		public void clickOnMouse(int action, int mouse_x, int mouse_y) {
			if(super.v) {
				super.i = mouse_x;
				super.j = mouse_y;
			}
		}
		
	}
}
class Canvas extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private RenderCallback renderCallback;
	
	public Canvas(int WIDTH, int HEIGHT) {
		super.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void setRenderCallback(RenderCallback renderCallback) {
		this.renderCallback = renderCallback;
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderCallback.render(g);
    }

	public interface RenderCallback {
		public void render(Graphics g);
	}
	
}
class Window extends JFrame implements MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private int WIDTH = 800, HEIGHT = 600, ExitButton = 0, LineButton = 0;
	private ArrayList<InterFace> interfs = new ArrayList<InterFace>();
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private Canvas.RenderCallback renderCallback;
	private Button.OnClickListener OnClick;

	public Window(int WIDTH, int HEIGHT) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		
		initToolsWindow();
		initwindow();
	}
	
	private void initwindow() {
		super.setContentPane(canvas);
		super.setUndecorated(true);
		pack();
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.addMouseListener(this);
		super.addMouseMotionListener(this);
		super.setVisible(true);
		
		ExitButton = buttons.size()+1;//This button is used for exit from the game
		buttons.add(new Button(this.WIDTH - 36 - 4, 4, 40, 25).setText("EXIT").setID(ExitButton).setColor(Color.RED, Color.BLACK).setOnClickListener(OnClick));
		LineButton = buttons.size()+1;
		buttons.add(new Button(this.WIDTH - 60 - 5, 4, 25, 25).setText("-").setID(LineButton).setColor(Color.GRAY, Color.BLACK).setOnClickListener(OnClick));
	}
	private void initToolsWindow() {	
		OnClick = new Button.OnClickListener() {
			
			@Override
			public void onClick(int id) {
				if(id == ExitButton) {
					dispatchEvent(new WindowEvent(getFrames()[0], WindowEvent.WINDOW_CLOSING));
				} else if(id == LineButton) {
					setExtendedState(getExtendedState() | JFrame.ICONIFIED);
				}
				
			}
		};
		renderCallback = new Canvas.RenderCallback() {
			
			@Override
			public void render(Graphics g) {
				renderGraphics(g);
			}
		};
		
		canvas = new Canvas(this.WIDTH, this.HEIGHT);
		canvas.setRenderCallback(renderCallback);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					repaint();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public void addInterFace(InterFace interf) {
		interfs.add(interf);
	}
	
	private void renderGraphics(Graphics g) {
		for (Button button : buttons) {
			button.rendergraph(g);
		}
		
        for (InterFace interf : interfs) {
			interf.rendergraph(g);
		}
	}
	
	private void update(int action, int mouse_x, int mouse_y) {
		for (InterFace interf : interfs) {
			interf.clickOnMouse(action, mouse_x, mouse_y);
		}
		
		for (Button button : buttons) {
			button.clickOnMouse(action, mouse_x, mouse_y);
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {		
	}

	
	@Override
	public void mousePressed(MouseEvent event) {
		update(InterFace.press, event.getX(), event.getY());
		super.repaint();
	}
	@Override
	public void mouseReleased(MouseEvent event) {
		update(InterFace.release, event.getX(), event.getY());
		super.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		update(InterFace.move, event.getX(), event.getY());
		super.repaint();
	}

}
