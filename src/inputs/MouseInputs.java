package inputs;

import java.awt.event.*;

import gamestates.Gamestate;
import main.GamePanel;
public class MouseInputs implements MouseListener, MouseMotionListener{
	
	GamePanel gamePanel;
	public MouseInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().mouseClicked(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().mouseClicked(e);
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
