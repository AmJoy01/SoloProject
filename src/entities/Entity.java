package entities;

import java.awt.Graphics;

public class Entity{
	protected double x, y;
	protected int width, height;
	protected Rect hitbox;
	
	public Entity(double x, double y, int width, int height) {
		this.x = x;
		this.y = y; 
		this.width = width;
		this.height = height;
		initHitBox();
	}
	
	protected void drawHitBox(Graphics pen) {
		hitbox.draw(pen);
	}
	
	protected void initHitBox() {
		hitbox = new Rect((int)x, (int)y, width, height);
	}
	
	protected void updateHitBox() {
		hitbox.x = (int)x;
		hitbox.y = (int)y;
	}
	
	public Rect getHitBox() {
		return hitbox;
	}
}
