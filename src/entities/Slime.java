package entities;

import static utilz.Constants.Directions.*;
import static utilz.HelperMethods.CanMoveHere;
import static utilz.HelperMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelperMethods.IsEntityOnFloor;
import static utilz.HelperMethods.IsFloor;
import static utilz.Constants.EnemyConstants.IDLE;
import static utilz.Constants.EnemyConstants.RUNNING;
import static utilz.Constants.EnemyConstants.ATTACK;
import static utilz.Constants.EnemyConstants.SLIME;
import static utilz.Constants.EnemyConstants.SLIME_HEIGHT;
import static utilz.Constants.EnemyConstants.SLIME_WIDTH;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class Slime extends Enemy{

	// Action: Attacking
	private Rect attackBox;
	private int attackBoxOffsetX;
	private int attackBoxOffsetY;
	
	public Slime(double x, double y) {
		super(x, y, SLIME_WIDTH, SLIME_HEIGHT, SLIME);
		initHitBox(x, y, (int)(22 * Game.SCALE),(int)(18 * Game.SCALE));
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox = new Rect(x,y, (int)(46 * Game.SCALE), (int)(30 * Game.SCALE));
		attackBoxOffsetX = (int)(Game.SCALE * 10);
		attackBoxOffsetY = (int)(Game.SCALE * 10);
	}
	
	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	private void updateAttackBox() {
		attackBox.x = hitbox.x - attackBoxOffsetX;
		attackBox.y = hitbox.y - attackBoxOffsetY;
	}

	protected void updateBehavior(int[][] lvlData, Player player) {
		if(firstUpdate) {
			firstUpdateCheck(lvlData);
		}
		if(inAir) {
			updateInAir(lvlData);
		}else {
			switch(enemyState) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				if(canSeePlayer(lvlData, player)) {
					turnTowardsPlayer(player);
					if(isPlayerCloseForAttack(player)) 
						newState(ATTACK);
				}
				move(lvlData);
				break;
			case ATTACK:
				if(aniIndex == 0)					attackChecked = false;
				if(aniIndex == 3 && !attackChecked) checkedEnemyHit(attackBox, player);
				break;
			}
		}
	}

	public int flipX() {
		if(walkDir == RT) return width;
		else 				 return 0;
	}
	
	public int flipW() {
		if(walkDir == RT) return -1;
		else 				 return 1;
	}
	
	
	public void drawAttackBox(Graphics pen, int xLvlOffset) {
		pen.setColor(Color.red);
		pen.drawRect((int)(attackBox.x - xLvlOffset), (int)attackBox.y, (int)attackBox.w, (int)attackBox.h);
	}

}
