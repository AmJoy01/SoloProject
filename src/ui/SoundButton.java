package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

import static utilz.Constants.UI.PauseButtons.*;

public class SoundButton extends PauseButton{

	private BufferedImage[][] soundImgs;
	private boolean mouseOver, mousePressed;
	private boolean muted;
	private int rowIndex, colIndex; // row and column index
	
	public SoundButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		loadSoundImgs();
	}

	private void loadSoundImgs() {
		BufferedImage temp = LoadSave.GetSprite(LoadSave.SOUND_BUTTONS);
		soundImgs = new BufferedImage[2][3];
		for(int row = 0; row < soundImgs.length; row++) {
			for(int col = 0; col < soundImgs[row].length; col++) {
				soundImgs[row][col] = temp.getSubimage(col * SOUND_SIZE_DEFAULT, row* SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
			}
		}
	}
	
	public void update() {
		if(muted)			rowIndex = 1;
		else 				rowIndex = 0;
		colIndex = 0;
		if(mouseOver)		colIndex = 1;
		if(mousePressed)	colIndex = 2;
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
	
	public void draw(Graphics pen) {
		pen.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
	}


	public boolean isMouseOver() {
		return mouseOver;
	}


	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}


	public boolean isMousePressed() {
		return mousePressed;
	}


	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}


	public boolean isMuted() {
		return muted;
	}


	public void setMuted(boolean muted) {
		this.muted = muted;
	}

}
