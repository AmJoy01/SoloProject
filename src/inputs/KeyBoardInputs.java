package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

import static utilz.Constants.Directions.*;

public class KeyBoardInputs implements KeyListener{

	private GamePanel gamePanel;
	private static final int UP_KEY = KeyEvent.VK_W;
	private static final int DN_KEY = KeyEvent.VK_S;
	private static final int LT_KEY = KeyEvent.VK_A;
	private static final int RT_KEY = KeyEvent.VK_D;
	
	
	
	public KeyBoardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case UP_KEY:
			gamePanel.getGame().getPlayer().setUp(true);
			break;
		case KeyEvent.VK_A:
			gamePanel.getGame().getPlayer().setLeft(true);
			break;
		case KeyEvent.VK_S:
			gamePanel.getGame().getPlayer().setDown(true);
			break;
		case KeyEvent.VK_D:
			gamePanel.getGame().getPlayer().setRight(true);
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case UP_KEY:
			gamePanel.getGame().getPlayer().setUp(false);
			break;
		case KeyEvent.VK_A:
			gamePanel.getGame().getPlayer().setLeft(false);
			break;
		case KeyEvent.VK_S:
			gamePanel.getGame().getPlayer().setDown(false);
			break;
		case KeyEvent.VK_D:
			gamePanel.getGame().getPlayer().setRight(false);
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
		
		
	}

}
