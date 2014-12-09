package com.teamv.capstone.game;

import java.util.ArrayList;

import com.teamv.capstone.BaseScene;
import com.teamv.capstone.ResourcesManager;
import com.teamv.capstone.game.enemies.*;
import com.teamv.capstone.gemboard.Gem;
import com.teamv.capstone.gemboard.Gemboard;
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
	Gemboard gemboard;
	static ArrayList<Enemy> enemies;
	ArrayList<Battle> level;
	int currentBattle = 0;
	Player player;
	
	
	public Battleground(BaseScene gameScene, Gemboard gemboard){
		this.gameScene = (GameScene) gameScene;
		this.gemboard = gemboard;
		player = new Player(1080/40, 1920/4, ResourcesManager.getInstance().vbom);
		player.attachToScene(this.gameScene);
		enemies = new ArrayList<Enemy>();
		enemies.add(new Wolf(1080/2, 1920/4, ResourcesManager.getInstance().vbom));
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
	
	public static void enterBattle(ArrayList<Gem> gems){
		if(enemies.size() <= 0)
			return;
		
		int damage = 0;
		
		// calculate damage...math, math
		damage += gems.size();
		
		// get target and attack
		Enemy enemy = enemies.get(0);
		
		// attack enemy
		enemy.takeDamage(damage);
	}
	
	private void attachEnemies(){
		for(Enemy enemy : enemies){
			enemy.attachToScene(gameScene);
		}
	}

	public int getNumOfEnemies() {
		return enemies.size();
	}
}