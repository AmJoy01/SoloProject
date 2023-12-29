package entities;

import static utilz.HelperMethods.CanMoveHere;
import static utilz.HelperMethods.GetEntityXPosNextToWall;
import static utilz.HelperMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelperMethods.IsEntityOnFloor;
import static utilz.Constants.PlayerConstants.ATTACKING;
import static utilz.Constants.PlayerConstants.FALLING;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.JUMP;
import static utilz.Constants.PlayerConstants.RUNNING;
import static utilz.Constants.PlayerConstants.WALKING;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;


public class Player extends Entity{

	// Player
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 30;
	private int playerAction = IDLE;
	private boolean moving = false;
	private boolean attacking = false;
	private boolean running = false;
	private boolean left, right, jump;
	private double playerSpeed = 0.5 * Game.SCALE;
	private int[][]lvlData;
	private int flipW = 1; // the width of the character
	private int flipX = 0; // the position of the character
	private double xDrawOffset = 10 * Game.SCALE;
	private double yDrawOffset = 4 * Game.SCALE;
	
	// Physics and Collision 
	private double airSpeed = 0.0;
	private double gravity = 0.04 *Game.SCALE;
	private double jumpSpeed = -2.25 * Game.SCALE;
	private double fallCollision = 0.5 * Game.SCALE;
	private boolean inAir = false;
	
	// Health
	private BufferedImage healthImg;
	
	private int healthImgWidth = (int)(176 * Game.SCALE);
	private int healthImgHeight = (int)(32 * Game.SCALE);
	private int healthImgXPos = (int)(10 * Game.SCALE);
	private int healthImgYPos = (int)(10 * Game.SCALE);
	
	private int healthBarWidth = (int) (139 * Game.SCALE);
	private int healthBarHeight = (int) (10 * Game.SCALE);
	private int healthBarXStart = (int) (32 * Game.SCALE);
	private int healthBarYStart = (int) (11 * Game.SCALE);
	
	private int maxHealth = 100;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;
	
	// Action: Attacking
	private Rect attackBox;
	private boolean attackChecked;
	private Playing playing;
	
	public Player(double x, double y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		loadAnimations();
		initHitBox(x, y, (int)(10*Game.SCALE), (int)(27*Game.SCALE));
		initAttackBox();
	}

	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}
	
	private void initAttackBox() {
		attackBox = new Rect(x, y, (int)(10 * Game.SCALE), (int)(25 * Game.SCALE));
	}


	public void update() {
		updateHealthBar();
		
		if(currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}
		
		updateAttackBox();
		updatePosition();
		
		if(attacking)		checkAttack();
		updateAnimationTick();
		setAnimation();
	}

	private void checkAttack(){
		if(attackChecked || aniIndex != 1)
			return;
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
	}
	
	private void updateAttackBox() {
		if(right)	attackBox.x = hitbox.x + hitbox.w + (int)(Game.SCALE * 2);
		else if(left)	attackBox.x = hitbox.x - hitbox.w - (int)(Game.SCALE * 2);
		attackBox.y = hitbox.y - 20 + (Game.SCALE * 10);
	}


	private void updateHealthBar() {
		healthWidth = (int)((currentHealth / (double)maxHealth) * healthBarWidth); 
	}

	public void render(Graphics pen, int lvlOffset) {
		pen.drawImage(animations[playerAction][aniIndex], (int)((hitbox.x - xDrawOffset) + flipX)- lvlOffset, (int)(hitbox.y - yDrawOffset), width*flipW, height, null);
		
//		drawHitBox(pen, lvlOffset);
//		drawAttackBox(pen, lvlOffset);
		drawHealth(pen);
	}
	
	
	private void drawAttackBox(Graphics pen, int lvlOffset) {
		pen.setColor(Color.red);
		pen.drawRect((int)attackBox.x - lvlOffset, (int)attackBox.y, (int)attackBox.w, (int)attackBox.h);
	}


	private void drawHealth(Graphics pen) {
		pen.drawImage(healthImg, healthImgXPos, healthImgYPos, healthImgWidth, healthImgHeight, null);
		pen.setColor(Color.red);
		pen.fillRect(healthBarXStart + healthImgXPos, healthBarYStart + healthImgYPos, healthWidth, healthBarHeight);
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
				attackChecked = false;
			}
		}
	}
	
	
	private void setAnimation() {
		int startAnimation = playerAction;
		
		if(moving) 	{
			if(running) 	playerAction = RUNNING;
			else 			playerAction = WALKING;	
		}
		else {
			playerAction = IDLE;
		}
		if(inAir) {
			if(airSpeed < 0 )   			playerAction = JUMP;
			else 							playerAction = FALLING;
		}
		if(attacking) {
			playerAction = ATTACKING;
			if(startAnimation != ATTACKING) {
				aniIndex = 1;
				aniTick = 0;
				return;
			}
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
		if(!left && !right && !inAir)	return;
		
		/* xSpeed is x's speed */
		double xSpeed = 0;  
		
		if(left) {
			if(running)	xSpeed -= playerSpeed *1.0;
			xSpeed -= playerSpeed;
			flipW = -1; // flips to the left
			flipX = width; // placed within its width (x+width)
		}
		if(right) {
			if(running)	xSpeed += playerSpeed *1.0;
			xSpeed += playerSpeed;
			flipW = 1; // flips to the right
			flipX = 0; // placed within its width (x)
		}
		
		if(!inAir) {
			if(!IsEntityOnFloor(hitbox, lvlData)) {
				inAir = true;
			}
		}
		
		if(inAir) {
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
		if(inAir)		return;
		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPosition(double xSpeed) {
		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.w, hitbox.h, lvlData)) {
			hitbox.x += xSpeed;
		}else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}
	}

	public void changeHealth(int value) {
		currentHealth += value;
		
		if(currentHealth <= 0) {
			currentHealth = 0;
			//gameOver();
		}else if(currentHealth >= maxHealth)	currentHealth = maxHealth;
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
		if(!IsEntityOnFloor(hitbox, lvlData)) inAir = true;
	}
	
	public void resetDirBooleans() {
		left = false;
		right = false; 
	}
	
	public void setRunning(boolean isRunning) {
		this.running = isRunning;
	}
	
	public void setAttacking(boolean isAttacking) {
		this.attacking = isAttacking;
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
	
	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;
		
		hitbox.x = x;
		hitbox.y = y;
		
		if(!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
		
	}
}
