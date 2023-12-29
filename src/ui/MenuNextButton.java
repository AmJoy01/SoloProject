package ui;

import static utilz.Constants.UI.MenuNextButtons.URM_DEFAULT_SIZE;
import static utilz.Constants.UI.MenuNextButtons.URM_SIZE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import gamestates.Playing;

public class MenuNextButton extends PauseButton{
	private BufferedImage[] mnImgs;
	private int rowIndex, index;
	
	private boolean mouseOver, mousePressed;
	
	public MenuNextButton(int x, int y, int width, int height, int rowIndex) {
		super(x, y, width, height);
		this.rowIndex = rowIndex;
		loadImgs();
	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSprite(LoadSave.MR_BUTTONS);
		mnImgs = new BufferedImage[3];
		for(int i = 0; i < mnImgs.length; i++) {
			mnImgs[i] = temp.getSubimage(i*URM_DEFAULT_SIZE, rowIndex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
		}
	}

	public void update() {
		index = 0;
		if(mouseOver)		index = 1;
		if(mousePressed)	index = 2;
	}
	
	public void draw(Graphics pen) {
		pen.drawImage(mnImgs[index], x, y, URM_SIZE, URM_SIZE, null);
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
