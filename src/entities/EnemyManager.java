package entities;

import static utilz.Constants.EnemyConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import level.Level;
import utilz.LoadSave;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] slimeArr;
	private ArrayList<Slime> slimes = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}

	public void loadEnemies(Level level) {
		slimes = level.getSlimes();
	}

	public void update(int[][] lvlData, Player player) {
		boolean isAnyActive = false; //is alive
		for(Slime s: slimes) {
			if(s.isActive()) {				
				s.update(lvlData, player);
				isAnyActive = true;
			}
		}
		
		if(!isAnyActive) {
			playing.setLevelCompleted(true);
		}
	}

	public void draw(Graphics pen, int xLvlOffset) {
		drawSlimes(pen, xLvlOffset);
	}
	
	private void drawSlimes(Graphics pen, int xLvlOffset) {
		for(Slime s : slimes) {
			if(s.isActive()) {
				pen.drawImage(
						slimeArr[s.getEnemyState()][s.getAniIndex()], 
						(int)s.getHitBox().x - xLvlOffset - SLIME_DRAWOFFSET_X + s.flipX(), 
						(int)s.getHitBox().y - SLIME_DRAWOFFSET_Y, 
						SLIME_WIDTH * s.flipW(), SLIME_HEIGHT, null);	
			}
//			s.drawHitBox(pen, xLvlOffset);
//			s.drawAttackBox(pen, xLvlOffset);
		}
		
	}

	public void checkEnemyHit(Rect attackBox) {
		for(Slime s : slimes) {
			if(s.isActive()) {				
				if(attackBox.overlaps(s.getHitBox())) {
					s.hurt(10);
					return;
				}
			}
		}
	}
	
	private void loadEnemyImgs() {
		slimeArr = new BufferedImage[4][7];
		BufferedImage temp = LoadSave.GetSprite(LoadSave.GREEN_SLIME);
		for(int row = 0; row < slimeArr.length; row++) {
			for(int col = 0; col < slimeArr[row].length; col++) {
				slimeArr[row][col] = temp.getSubimage(col * SLIME_WIDTH_DEFAULT, row * SLIME_HEIGHT_DEFAULT, SLIME_WIDTH_DEFAULT, SLIME_HEIGHT_DEFAULT);
			}
		}
	}
	
	public void resetAllEnemies() {
		for(Slime s : slimes) {
			s.resetEnemy();
		}
	}
}
