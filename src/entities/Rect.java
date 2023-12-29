package entities;

import java.awt.Color;
import java.awt.Graphics;

public class Rect {
	public double x;
	public double y;
	public double w;
	public double h;
	
	public Rect(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public boolean contains(int mx, int my) {
		return (mx >= x  )   && 
				   (mx <= x+w)   && 			   
				   (my >= y  )   && 
				   (my <= y+h);
	}
	
	public boolean overlaps(Rect r) {
		return (x 		<= r.x + r.w) && 
			   (x + w 	>= r.x		) &&
			   (y 		<= r.y + r.h) && 
			   (y + h 	>= r.y		);
	}
	
	public boolean isRightOf(Rect r) {
		return x + w < r.x;
	}
	
}
