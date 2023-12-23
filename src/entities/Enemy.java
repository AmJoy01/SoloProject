package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;

import entities.Player;

import static utilz.Constants.EnemyConstants.GetEnemyDmg;
import static utilz.CollisionMethods.*;

import main.Game;

public abstract class Enemy extends Entity{
	protected int aniIndex, enemyState, enemyType;
	protected int aniTick, aniSpeed = 25;
	protected boolean firstUpdate = true;
	protected boolean inAir;
	protected double fallSpeed;
	protected double gravity = 0.04 * Game.SCALE;
	protected double walkSpeed = 0.35 * Game.SCALE;
	protected double walkDir = LT;
	protected int tileY;
	protected double attackDistance = Game.TILES_SIZE - 10;
	protected int maxHealth;
	protected int currentHealth;	
	protected boolean active = true;
	protected boolean attackChecked;
	
	public Enemy(double x, double y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitBox(x,y,width,height);
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
	}
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if(!IsEntityOnFloor(hitbox, lvlData)) inAir = true;
		firstUpdate = false;
	}
	
	protected void updateInAir(int[][] lvlData) {
		if(CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.w, hitbox.h, lvlData)) {
			hitbox.y += fallSpeed;
			fallSpeed += gravity;
		}else {
			inAir = false;
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
			tileY = (int)(hitbox.y / Game.TILES_SIZE);
		}
	}
	
	protected void move(int[][] lvlData) {
		double xSpeed = 0;
		
		if(walkDir == LT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;
		
		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.w, hitbox.h, lvlData)) {
			if(IsFloor(hitbox, xSpeed, lvlData)) {
				hitbox.x += xSpeed;
				return;
			}
		}
		changeWalkDir();
	}
	
	protected void turnTowardsPlayer(Player player) {
		if(hitbox.isRightOf(player.hitbox)) walkDir = RT;
		else						   		walkDir = LT;
	}
	
	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int)(player.getHitBox().y / Game.TILES_SIZE);
		if(playerTileY == tileY) {
			if(isPlayerInRange(player)) {
				if(IsSightClear(lvlData, hitbox, player.hitbox, tileY))
					return true;
			}
		}
		return false;
	}
	
	protected boolean isPlayerInRange(Player player) {
		int absValue = (int)Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance * 5;
	}
	
	protected boolean isPlayerCloseForAttack(Player player) {
		int absValue = (int)Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance;
	}

	
	protected void newState(int enemyState) {
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}
	
	public void hurt(int amount) {
		currentHealth -= amount;
		if(currentHealth <= 0)	newState(DEAD);
		else					newState(IDLE);
	}
	
	protected void checkedEnemyHit(Rect attackBox, Player player) {
		if(attackBox.overlaps(player.hitbox)) {
			player.changeHealth(-GetEnemyDmg(enemyType));
		}
		attackChecked = true;
	}
	
	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;
				switch(enemyState)	{
				case ATTACK: enemyState = IDLE; break;
				case DEAD: active = false; break;
				}
			}
		}
	}

	protected void changeWalkDir() {
		if(walkDir == LT)		walkDir = RT;
		else 					walkDir = LT;
	}

	public void resetEnemy() {
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		fallSpeed = 0;	
	}
	
	public int getAniIndex() {
		return aniIndex;
	}
	
	public int getEnemyState() {
		return enemyState;
	}
	
	public boolean isActive() {
		return active;
	}
}
