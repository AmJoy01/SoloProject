package gamestates;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import ui.MenuButton;
import utilz.LoadSave;
import main.Game;

public class Menu extends State implements StateMethods{

	private int ENTER_KEY = KeyEvent.VK_ENTER;
	private int menuX, menuY, menuWidth, menuHeight;
	
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg, darkBackgroundImg;

	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackground();
		darkBackgroundImg = LoadSave.GetSprite(LoadSave.BACKGROUND_MENU);
	}

	
	
	private void loadBackground() {
		backgroundImg = LoadSave.GetSprite(LoadSave.MENU_BACKGROUND);
		menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
		menuX = Game.GAME_WIDTH/2 - menuWidth /2;
		menuY = Game.GAME_HEIGHT/9; // 896 / 9 = 99.55555556 but we're returning an int so 99
	}



	private void loadButtons() {
		buttons[0] = new MenuButton(Game.GAME_WIDTH/2, (int) (150*Game.SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new MenuButton(Game.GAME_WIDTH/2, (int) (220*Game.SCALE), 1, Gamestate.OPTIONS);
		buttons[2] = new MenuButton(Game.GAME_WIDTH/2, (int) (290*Game.SCALE), 2, Gamestate.QUIT);
		
	}

	@Override
	public void update() {
		for(MenuButton mb : buttons)	mb.update();
		
	}

	@Override
	public void draw(Graphics pen) {
		pen.drawImage(darkBackgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT,null);
		pen.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
		for(MenuButton mb : buttons)	mb.draw(pen);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e,mb)) {
				mb.setMousePressed(true);
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				if(mb.isMousePressed())		mb.applyGamestate();
				break;
			}
		}
		resetButtons();
	}

	private void resetButtons() {
		for(MenuButton mb : buttons)	mb.resetBools();
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons)	mb.setMouseOver(false);
		
		for(MenuButton mb : buttons) {
			if(isIn(e, mb))		mb.setMouseOver(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == ENTER_KEY)		Gamestate.state = Gamestate.PLAYING;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
