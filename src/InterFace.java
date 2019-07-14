import java.awt.Graphics;

public abstract class InterFace {
	
	public static final int move = 0, press = 1, release = 2;
	public static boolean visible = true, unvisible = false;
	protected int i = 0, j = 0, width = 0, height = 0, paddinginterface = 0;
	protected boolean v = true, a = true;
	public InterFace(int i, int j, int width, int height) {
		this.i = i;
		this.j = j;
		this.width = width;
		this.height = height;
	}
	public InterFace(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public InterFace() {
		
	}
	public InterFace setVisibility(boolean state) {
		this.v= state;
		return this;
	}
	
	public void rendergraph(Graphics g) {
		
	}
	public void clickOnMouse(int action, int mouse_x, int mouse_y) {
		
	}

}
