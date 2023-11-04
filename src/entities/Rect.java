package entities;

import java.awt.Color;
import java.awt.Graphics;

public class Rect {
	double x;
	double y;
	double w;
	double h;
	
	public Rect(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void draw(Graphics pen) {
		pen.setColor(Color.PINK);
		pen.drawRect((int)x, (int)y, (int)w, (int)h);
	}
}
