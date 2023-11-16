package gamestates;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
public interface StateMethods {
// Collection of methods each class to create if implemented
	
	public void update();
	public void draw(Graphics pen);
	public void mouseClicked(MouseEvent e);
	public void mousePressed(MouseEvent e);
	public void mouseReleased(MouseEvent e);
	public void mouseMoved(MouseEvent e);
	public void keyPressed(KeyEvent e);
	public void keyReleased(KeyEvent e);
}
