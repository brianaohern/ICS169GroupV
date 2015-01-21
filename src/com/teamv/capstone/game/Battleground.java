package com.teamv.capstone.game;

import java.util.ArrayList;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

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
	static Wave currentWave;
	ArrayList<Wave> level;
	int currentBattle = 0;
	Player player;
	VertexBufferObjectManager vbom;
	
	Enemy target;
	
	
	public Battleground(BaseScene gameScene, Gemboard gemboard){
		this.gameScene = (GameScene) gameScene;
		this.gemboard = gemboard;
		vbom = ResourcesManager.getInstance().vbom;
		player = new Player(1080/40, 1920/4, vbom);
		player.attachToScene(this.gameScene);
		
		// mock level
		level = new ArrayList<Wave>();
		Wave wave1 = new Wave();
		Wave wave2 = new Wave();
		Wave wave3 = new Wave();
		wave1.add(new Wolf(vbom));
		wave1.add(new Wolf(vbom));
		wave1.add(new Wolf(vbom));
		wave3.add(new Wolf(vbom));
		wave3.add(new Wolf(vbom));
		wave3.add(new Wolf(vbom));
		level.add(wave1);
		level.add(wave2);
		level.add(wave3);
		// end mock level setup
		
		this.nextBattle();
	}
	
	public void nextBattle(Wave wave){
		//set up next battle
		wave.initPlacement();
		currentWave = wave;
		attachEnemies();
	}
	
	public void nextBattle(){
		Wave battle = level.get(currentBattle);
		if(battle != null){
			this.nextBattle(battle);
		}
		currentBattle++;
	}
	
	public static void enterBattle(ArrayList<Gem> gems){
		if(currentWave.getEnemies().size() <= 0)
			return;
		
		int damage = 0;
		
		// calculate damage...math, math
		damage += gems.size();
		
		// get target and attack
		Enemy enemy = currentWave.getTarget();;
		
		// attack enemy
		enemy.takeDamage(damage);
	}
	
	private void attachEnemies(){
		for(Enemy enemy : currentWave.getEnemies()){
			enemy.attachToScene(gameScene);
		}
	}

	public int getNumOfEnemies() {
		return currentWave.getEnemies().size();
	}
}
