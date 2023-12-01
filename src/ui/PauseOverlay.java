package ui;

import static utilz.Constants.UI.PauseButtons.SOUND_SIZE;
import static utilz.Constants.UI.URMButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class PauseOverlay {

	private Playing playing;
	
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH; // background x,y,width, and height
	
	/*******************BUTTONS***********************/
	private SoundButton musicButton, sfxButton;
	private UrmButton menuBtn, replayBtn, unpauseBtn;
	private VolumeButton volumeButton;
	
	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		createSoundButtons();
		createUrmButtons();
		createVolumeButton();
	}
	
	private void createVolumeButton() {
		int volumeX = (int) (309 * Game.SCALE);
		int volumeY = (int) (278 * Game.SCALE);
		volumeButton = new VolumeButton(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
	}

	private void createUrmButtons() {
		int menuX = (int) (313 * Game.SCALE);
		int buttonY = (int) (300 * Game.SCALE);
		int replayY = (int) (230 * Game.SCALE);
		int unpauseY = (int) (160 * Game.SCALE);
		
		menuBtn = new UrmButton(menuX, buttonY, URM_WIDTH * 4, URM_HEIGHT* 4, 2);
		replayBtn = new UrmButton(menuX, replayY, URM_WIDTH * 4, URM_HEIGHT* 4, 1);
		unpauseBtn = new UrmButton(menuX, unpauseY, URM_WIDTH * 4, URM_HEIGHT* 4, 0);
	}

	private void createSoundButtons() {
		int soundX = (int)(450 * Game.SCALE);
		int musicY = (int)(140 * Game.SCALE);
		int sfxY = (int) (186 * Game.SCALE);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSprite(LoadSave.PAUSE_MENU);
		bgW = (int)(backgroundImg.getWidth() * Game.SCALE * 4);
		bgH = (int)(backgroundImg.getHeight() * Game.SCALE * 4);
		bgX = Game.GAME_WIDTH/2 - bgW / 2;
		bgY = (int) (25 * Game.SCALE);
		
	}

	public void update() {
//		musicButton.update();
//		sfxButton.update();
		
		menuBtn.update();
		replayBtn.update();
		unpauseBtn.update();
		
//		volumeButton.update();
	}
	
	public void draw(Graphics pen) {
		/*BACKGROUND*/
		pen.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
		
		/*SOUND BUTTONS*/
//		musicButton.draw(pen);
//		sfxButton.draw(pen);
		
		/*MENU, REPLAY LEVEL, UNPAUSE BUTTONS*/
		menuBtn.draw(pen);
		replayBtn.draw(pen);
		unpauseBtn.draw(pen);
		
		/*VOLUME BUTTON*/
//		volumeButton.draw(pen);
	}
	
	public void mouseDragged(MouseEvent e) {
		if(volumeButton.isMousePressed())	volumeButton.changeX(e.getX());
	}
	
	public void mousePressed(MouseEvent e) {
		if(isIn(e, musicButton)) 	musicButton.setMousePressed(true);
		else if(isIn(e, sfxButton))	sfxButton.setMousePressed(true);
		else if(isIn(e, menuBtn))	menuBtn.setMousePressed(true);
		else if(isIn(e, replayBtn))	replayBtn.setMousePressed(true);
		else if(isIn(e, unpauseBtn))	unpauseBtn.setMousePressed(true);
		else if(isIn(e, volumeButton))	volumeButton.setMousePressed(true);
	}

	
	public void mouseReleased(MouseEvent e) {
		if(isIn(e, musicButton)) {	
			if(musicButton.isMousePressed())	musicButton.setMuted(!musicButton.isMuted());
		}
		else if(isIn(e,sfxButton)) {
			if(sfxButton.isMousePressed())		sfxButton.setMuted(!sfxButton.isMuted());;
		}
		else if(isIn(e, menuBtn)) {
			if(menuBtn.isMousePressed()) {
				Gamestate.state = Gamestate.MENU;
				playing.unpauseGame();
			}
		}else if(isIn(e, replayBtn)) {
			if(replayBtn.isMousePressed())		System.out.println("Replay level underconstruction");
		}else if(isIn(e, unpauseBtn)) {
			if(unpauseBtn.isMousePressed())		playing.unpauseGame();
		}
		
		musicButton.resetBools();
		sfxButton.resetBools();
		
		menuBtn.resetBools();
		replayBtn.resetBools();
		unpauseBtn.resetBools();
		
		volumeButton.resetBools();
	}

	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		menuBtn.setMouseOver(false);
		replayBtn.setMouseOver(false);
		unpauseBtn.setMouseOver(false);
		volumeButton.setMouseOver(false);
		
		if(isIn(e, musicButton))		musicButton.setMouseOver(true);
		else if(isIn(e, sfxButton))		sfxButton.setMouseOver(true);
		else if(isIn(e, menuBtn))		menuBtn.setMouseOver(true);
		else if(isIn(e, replayBtn))		replayBtn.setMouseOver(true);
		else if(isIn(e, unpauseBtn))	unpauseBtn.setMouseOver(true);
		else if(isIn(e, volumeButton))	volumeButton.setMouseOver(true);
		
	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}
