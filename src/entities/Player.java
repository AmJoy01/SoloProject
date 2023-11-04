package entities;


import static utilz.Constants.PlayerConstants.*;
import static utilz.CollisionMethods.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import main.Game;
import utilz.LoadSave;


public class Player extends Entity{

	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 30;
	private int playerAction = IDLE;
	private boolean isMoving = false;
	private boolean isAttacking = false;
	private boolean up, down, left, right;
	private double playerSpeed = 2.0;
	private int[][]lvlData;
	private int flipW = 1; // the width of the character
	private int flipX = 0; // the position of the character
	private double xDrawOffset = 10 * Game.SCALE;
	private double yDrawOffset = 4 * Game.SCALE;
	public Player(double x, double y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitBox(x, y, 10*Game.SCALE, 28*Game.SCALE);
	}

	
	public void update() {
		updatePosition();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics pen) {
		pen.drawImage(animations[playerAction][aniIndex], (int)((hitbox.x - xDrawOffset) + flipX), (int)(hitbox.y - yDrawOffset), width*flipW, height, null);
		drawHitBox(pen);
	}
	
	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				isAttacking = false;
			}
		}
	}
	
	
	private void setAnimation() {
		int startAnimation = playerAction;
		if(isMoving) {
			playerAction = WALKING;
		}else {
			playerAction = IDLE;
		}
		if(isAttacking) {
			playerAction = ATTACKING;
		}
		if(startAnimation != playerAction) {
			resetAniTick();
		}
		
	}
	
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}
	
	private void updatePosition() {
		isMoving = false;
		if(!left && !right && !up && !down)	return;
		
		/* dx is x's speed */
		double dx = 0;  
		
		/* dy is y's speed */
		double dy = 0; 
		
		if(left && !right) {
			dx = -playerSpeed;
			flipW = -1; // flips to the left
			flipX = width; // placed within its width (x+width)
		}
		if(right && !left) {
			dx = playerSpeed;
			flipW = 1; // flips to the right
			flipX = 0; // placed within its width (x)
		}
		if(up && !down)		dy = -playerSpeed;
		if(down && !up)		dy = playerSpeed;
		
		if(canMoveHere(hitbox.x + dx, hitbox.y + dy, hitbox.w, hitbox.h, lvlData)) {
			hitbox.x += dx;
			hitbox.y += dy;
			isMoving = true;
		}
	}

	private void loadAnimations() {
		
		BufferedImage img = LoadSave.GetSprite(LoadSave.PLAYER);
			
			animations = new BufferedImage[9][8];
			for(int row = 0; row < animations.length; row++) {
				for(int col = 0; col < animations[row].length; col++) {
					animations[row][col] = img.getSubimage(col*32, row*32, 32, 32);
				}
			}
	
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
	}
	
	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}
	
	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}

	public boolean isUp() {
		return up;
	}


	public void setUp(boolean up) {
		this.up = up;
	}


	public boolean isDown() {
		return down;
	}


	public void setDown(boolean down) {
		this.down = down;
	}


	public boolean isLeft() {
		return left;
	}


	public void setLeft(boolean left) {
		this.left = left;
	}


	public boolean isRight() {
		return right;
	}


	public void setRight(boolean right) {
		this.right = right;
	}
	
	
}
