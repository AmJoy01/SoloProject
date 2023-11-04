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
	}
	
	protected void drawHitBox(Graphics pen) {
		hitbox.draw(pen);
	}
	
	protected void initHitBox(double x, double y, double width, double height) {
		hitbox = new Rect(x, y, width, height);
	}
	
//	protected void updateHitBox() {
//		hitbox.x = (int)x;
//		hitbox.y = (int)y;
//	}
	
	public Rect getHitBox() {
		return hitbox;
	}
}
