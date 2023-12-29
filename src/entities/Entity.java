package entities;

import java.awt.Color;
import java.awt.Graphics;

public class Entity{
	public double x;
	public double y;
	protected int width, height;
	protected Rect hitbox;
	
	public Entity(double x, double y, int width, int height) {
		this.x = x;
		this.y = y; 
		this.width = width;
		this.height = height;
	}
	
	protected void drawHitBox(Graphics pen, int xLvlOffset) {
		pen.setColor(Color.GREEN);	
		pen.drawRect((int)hitbox.x - xLvlOffset, (int)hitbox.y, (int)hitbox.w, (int)hitbox.h);
	}
	
	protected void initHitBox(double x, double y, int width, int height) {
		hitbox = new Rect(x, y, width, height);
	}

	public Rect getHitBox() {
		return hitbox;
	}
}
