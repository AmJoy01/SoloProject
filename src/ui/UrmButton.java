package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

import static utilz.Constants.UI.URMButtons.*;
public class UrmButton extends PauseButton{

	private BufferedImage[] urmImgs;
	private int rowIndex, index;
	
	private boolean mouseOver, mousePressed;
	
	public UrmButton(int x, int y, int width, int height, int rowIndex) {
		super(x, y, width, height);
		this.rowIndex = rowIndex;
		loadImgs();
	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSprite(LoadSave.URM_BUTTONS);
		urmImgs = new BufferedImage[3];
		for(int i = 0; i < urmImgs.length; i++) {
			urmImgs[i] = temp.getSubimage(i*URM_WIDTH_DEFAULT, rowIndex*URM_HEIGHT_DEFAULT, URM_WIDTH_DEFAULT, URM_HEIGHT_DEFAULT);
		}
	}

	public void update() {
		index = 0;
		if(mouseOver)		index = 1;
		if(mousePressed)	index = 2;
	}
	
	public void draw(Graphics pen) {
		pen.drawImage(urmImgs[index], x, y, URM_WIDTH * 4, URM_HEIGHT * 4, null);
	}
	
	public void resetBools()
	{
		mouseOver = false;
		mousePressed = false;
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
}
