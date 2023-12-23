package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

public class GameOverOverlay {
	
	private Playing playing; 
	
	public GameOverOverlay(Playing playing) {
		this.playing = playing;
	}
	
	public void draw(Graphics pen) {
		//create faded dark background screen
		pen.setColor(new Color(0,0,0,200));
		pen.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		
		pen.setColor(Color.white);
		pen.drawString("Game Over", Game.GAME_WIDTH/2, 150);
		pen.drawString("Press esc to enter Main Menu!", Game.GAME_WIDTH/2, 300);
		
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			playing.resetAll();
			Gamestate.state = Gamestate.MENU;
		}
	}
}
