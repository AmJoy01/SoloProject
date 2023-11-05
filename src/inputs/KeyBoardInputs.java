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
	private static final int JP_KEY = KeyEvent.VK_SPACE;
	
	
	
	
	public KeyBoardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case UP_KEY: gamePanel.getGame().getPlayer().setUp(true); 		break;
		case DN_KEY: gamePanel.getGame().getPlayer().setDown(true); 	break;
		case LT_KEY: gamePanel.getGame().getPlayer().setLeft(true); 	break;
		case RT_KEY: gamePanel.getGame().getPlayer().setRight(true); 	break;
		case JP_KEY: gamePanel.getGame().getPlayer().setJump(true); 	break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case UP_KEY: gamePanel.getGame().getPlayer().setUp(false);		break;
		case DN_KEY: gamePanel.getGame().getPlayer().setDown(false);  	break;
		case LT_KEY: gamePanel.getGame().getPlayer().setLeft(false);  	break;
		case RT_KEY: gamePanel.getGame().getPlayer().setRight(false); 	break;
		case JP_KEY: gamePanel.getGame().getPlayer().setJump(false); 
		}
	}

	public void keyTyped(KeyEvent e) {
		
		
	}

}
