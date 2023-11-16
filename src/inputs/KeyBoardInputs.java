package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.Gamestate;
import main.GamePanel;

import static utilz.Constants.Directions.*;

public class KeyBoardInputs implements KeyListener{

	private GamePanel gamePanel;
	
	public KeyBoardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void keyPressed(KeyEvent e) {
		/* Depending on the Gamestates, should be able to utilize key events */
		
		switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().keyPressed(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyPressed(e);
			break;
		default: break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().keyReleased(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyReleased(e);
			break;
		default: break;
		}
	}

	public void keyTyped(KeyEvent e) {
		
		
	}

}
