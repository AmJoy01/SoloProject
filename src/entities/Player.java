package entities;


import static utilz.Constants.PlayerConstants.*;
import static utilz.CollisionMethods.*;
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
	private boolean isRunning = false;
	private boolean up, down, left, right, jump;
	private double playerSpeed = 0.5 * Game.SCALE;
	private int[][]lvlData;
	private int flipW = 1; // the width of the character
	private int flipX = 0; // the position of the character
	private double xDrawOffset = 10 * Game.SCALE;
	private double yDrawOffset = 4 * Game.SCALE;
	
	private double airSpeed = 0.0;
	private double gravity = 0.04 *Game.SCALE;
	private double jumpSpeed = -2.25 * Game.SCALE;
	private double fallCollision = 0.5 * Game.SCALE;
	private boolean isInAir = false;
	
	public Player(double x, double y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitBox(x, y, (int)(10*Game.SCALE), (int)(27*Game.SCALE));
	}

	
	public void update() {
		updatePosition();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics pen, int lvlOffset) {
		pen.drawImage(animations[playerAction][aniIndex], (int)((hitbox.x - xDrawOffset) + flipX)- lvlOffset, (int)(hitbox.y - yDrawOffset), width*flipW, height, null);
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
		
		if(isMoving) 	{
			if(isRunning) 	playerAction = RUNNING;
			else 			playerAction = WALKING;
			
		}
		
		else								playerAction = IDLE;
		
		if(isAttacking)						playerAction = ATTACKING;
		
		if(isInAir) {
			if(airSpeed < 0 )   			playerAction = JUMP;
			else 							playerAction = FALLING;
		}
		if(startAnimation != playerAction) 	resetAniTick();
		
	}
	
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}
	
	private void updatePosition() {
		isMoving = false;
		if(jump)	jump();
		if(!left && !right && !isInAir)	return;
		
		/* xSpeed is x's speed */
		double xSpeed = 0;  
		
		if(left) {
			if(isRunning)	xSpeed -= playerSpeed *1.0;
			xSpeed -= playerSpeed;
			flipW = -1; // flips to the left
			flipX = width; // placed within its width (x+width)
		}
		if(right) {
			if(isRunning)	xSpeed += playerSpeed *1.0;
			xSpeed += playerSpeed;
			flipW = 1; // flips to the right
			flipX = 0; // placed within its width (x)
		}
		
		if(!isInAir) {
			if(!isEntityOnFloor(hitbox, lvlData)) {
				isInAir = true;
			}
		}
		
		if(isInAir) {
			if(canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.w, hitbox.h, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPosition(xSpeed);
			}else {
				hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if(airSpeed > 0)		resetInAir();
				else					airSpeed = fallCollision;
				updateXPosition(xSpeed);
			}
		}else {
			updateXPosition(xSpeed);
		}
		isMoving = true;
		
	}
	
	private void jump() {
		if(isInAir)		return;
		isInAir = true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		isInAir = false;
		airSpeed = 0;
	}

	private void updateXPosition(double xSpeed) {
		if(canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.w, hitbox.h, lvlData)) {
			hitbox.x += xSpeed;
		}else {
			hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed);
		}
	}

	private void loadAnimations() {
		
		BufferedImage img = LoadSave.GetSprite(LoadSave.PLAYER);
			
			animations = new BufferedImage[7][8];
			for(int row = 0; row < animations.length; row++) {
				for(int col = 0; col < animations[row].length; col++) {
					animations[row][col] = img.getSubimage(col*32, row*32, 32, 32);
				}
			}
	
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!isEntityOnFloor(hitbox, lvlData)) isInAir = true;
	}
	
	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}
	
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
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
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}
}
