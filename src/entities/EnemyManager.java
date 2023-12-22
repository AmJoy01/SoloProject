package entities;

import static utilz.Constants.EnemyConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import utilz.LoadSave;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] slimeArr;
	private ArrayList<Slime> slimes = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}

	private void addEnemies() {
		slimes = LoadSave.GetSlimes();
	}

	public void update(int[][] lvlData) {
		for(Slime s: slimes) {
			s.update(lvlData);
		}
	}

	public void draw(Graphics pen, int xLvlOffset) {
		drawSlimes(pen, xLvlOffset);
	}
	
	private void drawSlimes(Graphics pen, int xLvlOffset) {
		for(Slime s : slimes) {
			pen.drawImage(
					slimeArr[s.getEnemyState()][s.getAniIndex()], 
					(int)s.getHitBox().x - xLvlOffset - SLIME_DRAWOFFSET_X, 
					(int)s.getHitBox().y - SLIME_DRAWOFFSET_Y, 
					SLIME_WIDTH, SLIME_HEIGHT, null);
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
}
