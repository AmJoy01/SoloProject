package entities;


import static utilz.Constants.PlayerConstants.*;
import static utilz.CollisionMethods.*;
import static utilz.CollisionMethods.*;

import java.awt.Color;
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
	private boolean moving = false;
	private boolean isAttacking = false;
	private boolean isRunning = false;
	private boolean left, right, jump;
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
	
	private BufferedImage healthImg;
	
	private int healthWidth = (int)(176 * Game.SCALE);
	private int healthHeight = (int)(32 * Game.SCALE);
	private int healthXPos = (int)(10 * Game.SCALE);
	private int healthYPos = (int)(10 * Game.SCALE);
	
	private int healthBarWidth = (int) (139 * Game.SCALE);
	private int healthBarHeight = (int) (10 * Game.SCALE);
	private int healthBarXStart = (int) (32 * Game.SCALE);
	private int healthBarYStart = (int) (11 * Game.SCALE);
	
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
		drawHealth(pen);
	}
	
	private void drawHealth(Graphics pen) {
		pen.drawImage(healthImg, healthXPos, healthYPos, healthWidth, healthHeight, null);
		pen.setColor(Color.red);
		pen.fillRect(healthBarXStart + healthXPos, healthBarYStart + healthYPos, healthBarWidth, healthBarHeight);
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
		
		if(moving) 	{
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
		moving = false;
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
			if(!IsEntityOnFloor(hitbox, lvlData)) {
				isInAir = true;
			}
		}
		
		if(isInAir) {
			if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.w, hitbox.h, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPosition(xSpeed);
			}else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if(airSpeed > 0)		resetInAir();
				else					airSpeed = fallCollision;
				updateXPosition(xSpeed);
			}
		}else {
			updateXPosition(xSpeed);
		}
		moving = true;
		
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
		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.w, hitbox.h, lvlData)) {
			hitbox.x += xSpeed;
		}else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
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
		healthImg = LoadSave.GetSprite(LoadSave.HEALTH_BAR);
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(hitbox, lvlData)) isInAir = true;
	}
	
	public void resetDirBooleans() {
		left = false;
		right = false; 
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
