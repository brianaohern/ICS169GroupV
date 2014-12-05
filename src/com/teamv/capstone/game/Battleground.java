package com.teamv.capstone.game;

import java.util.ArrayList;

import com.teamv.capstone.BaseScene;
import com.teamv.capstone.ResourcesManager;
import com.teamv.capstone.gemboard.gems.Gem;
import com.teamv.capstone.scenes.GameScene;

/*
 * Battleground contains the enemies and player
 * Player is basically the player stats and UI
 * Enemy is basically a sprite + stats
 * 
 * 
 */
public class Battleground {
	
	GameScene gameScene;
	ArrayList<Enemy> enemies;
	ArrayList<Battle> level;
	int currentBattle = 0;
	Player player;
	
	
	public Battleground(BaseScene gameScene){
		this.gameScene = (GameScene) gameScene;
		player = new Player();
		enemies = new ArrayList<Enemy>();
		enemies.add(new Enemy(ResourcesManager.getInstance().wolf, ResourcesManager.getInstance().vbom));
		attachEnemies();
	}
	
	public void nextBattle(Battle battle){
		//set up next battle
	}
	
	public void nextBattle(){
		Battle battle = level.get(currentBattle);
		if(battle != null)
			this.nextBattle(battle);
		currentBattle++;
	}
	
	public int calculateDamage(ArrayList<Gem> gems){
		int damage = 0;
//		for(Gem gem : Gemboard.connectedGems){
//			damage++;
//		}
		damage += gems.size();
		return damage;
	}
	
	private void attachEnemies(){
		for(Enemy enemy : enemies){
			gameScene.attachChild(enemy);
		}
	}
}
