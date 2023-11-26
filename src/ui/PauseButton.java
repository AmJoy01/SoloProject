package ui;

import entities.Rect;

public class PauseButton {

	protected int x, y, width, height;
	protected Rect bounds;
	
	
	public PauseButton(int x, int y, int width, int height) {
		this.x = x; 
		this.y = y;
		this.width = width;
		this.height = height;
		createBounds();
	}

	private void createBounds() {
		bounds = new Rect(x, y, width, height);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rect getBounds() {
		return bounds;
	}

	public void setBounds(Rect bounds) {
		this.bounds = bounds;
	}
}
