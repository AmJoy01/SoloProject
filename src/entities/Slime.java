package entities;

import static utilz.Constants.EnemyConstants.SLIME;
import static utilz.Constants.EnemyConstants.SLIME_HEIGHT;
import static utilz.Constants.EnemyConstants.SLIME_WIDTH;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class Slime extends Enemy{

	Rect attackBox;
	
	public Slime(double x, double y) {
		super(x, y, SLIME_WIDTH, SLIME_HEIGHT, SLIME);
		initHitBox(x, y, (int)(13 * Game.SCALE),(int)(11* Game.SCALE));
		initAttackBox();
	}

	private void initAttackBox() {
		attackBox = new Rect(x,y, (int)(32 * Game.SCALE), (int)(32 * Game.SCALE));
	}
	
	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
	}
	
	private void updateBehavior(int[][] lvlData, Player player) {
		if(firstUpdate) {
			firstUpdateCheck(lvlData);
		}
	}

	public void drawAttackBox(Graphics pen, int xLvlOffset) {
		pen.setColor(Color.red);
		pen.drawRect((int)(attackBox.x - xLvlOffset), (int)attackBox.y, (int)attackBox.w, (int)attackBox.h);
	}

}
