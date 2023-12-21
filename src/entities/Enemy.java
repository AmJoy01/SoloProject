package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;

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
	protected double attackDistance = Game.TILES_SIZE;
	protected int maxHealth;
	protected int currentHealth;	
	protected boolean active = true;
	protected boolean attackChecked;
	
	public Enemy(double x, double y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitBox(x,y,width,height);
	}
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if(!IsEntityOnFloor(hitbox, lvlData)) inAir = true;
		firstUpdate = false;
	}
	
	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;
			}
		}
	}

	public void update() {
		updateAnimationTick();
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
