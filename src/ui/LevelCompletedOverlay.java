package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import ui.UrmButton;
import main.Game;
import utilz.LoadSave;
import gamestates.Playing;

import static utilz.Constants.UI.MenuNextButtons.*;

public class LevelCompletedOverlay {
	private Playing playing;
	private MenuNextButton menu, next;
	private BufferedImage img;
	private int bgX, bgY, bgW, bgH;
	
	public LevelCompletedOverlay(Playing playing) {
		this.playing = playing;
		initImage();
		initButtons();
	}

	private void initButtons() {
		int menuX = (int)(330 * Game.SCALE);
		int nextX = (int)(445 * Game.SCALE);
		int y = (int)(215 * Game.SCALE);
		next = new MenuNextButton(nextX, y, URM_SIZE , URM_SIZE, 0);
		menu = new MenuNextButton(menuX, y, URM_SIZE , URM_SIZE, 2);
	}

	private void initImage() {
		img = LoadSave.GetSprite(LoadSave.COMPLETED_IMG);
		bgW = (int) (img.getWidth() * (Game.SCALE + 1));
		bgH = (int) (img.getHeight() * (Game.SCALE + 1));
		bgX = (int) (Game.GAME_WIDTH / 2 - bgW / 2);
		bgY = (int) (95 * Game.SCALE);
	}
	
	public void update() {
		next.update();
		menu.update();
	}
	
	public void draw(Graphics pen) {
		pen.drawImage(img, bgX, bgY, bgW, bgH, null);
		next.draw(pen);
		menu.draw(pen);
	}
	
	private boolean isIn(MenuNextButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);
		
		if(isIn(menu, e)) {
			menu.setMouseOver(true);
		}else if(isIn(next, e)) {
			next.setMouseOver(true);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(isIn(menu,e)) {
			if(menu.isMousePressed()) {
				System.out.println("menu!");
				playing.resetAll();
				Gamestate.state = Gamestate.MENU;
			}
		}else if(isIn(next, e)) {
			if(next.isMousePressed()) {
				System.out.println("next!");
				playing.loadNextLevel();
			}
		}
		
		menu.resetBools();
		next.resetBools();
	}
	
	public void mousePressed(MouseEvent e) {
		if(isIn(menu,e)) {
			menu.setMousePressed(true);
		}else if(isIn(next, e)) {
			next.setMousePressed(true);
		}
	}
}
