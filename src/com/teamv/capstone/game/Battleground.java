package com.teamv.capstone.game;

import java.util.ArrayList;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.enemies.*;
import com.teamv.capstone.gemboard.Gem;
import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.scenes.BaseScene;
import com.teamv.capstone.scenes.GameScene;

/*
 * Battleground contains the enemies and player
 * Player is basically the player stats and UI
 * Enemy is basically a sprite + stats
 * 
 * 
 */
public class Battleground {

	static GameScene gameScene;
	static Wave currentWave;
	Level level;
	Player player;
	VertexBufferObjectManager vbom;
	boolean isFinished = false;

	Enemy target;

	public Battleground(final BaseScene gameScene){
		Battleground.gameScene = (GameScene) gameScene;
		vbom = ResourcesManager.getInstance().vbom;
		player = new Player(1080/40, 1920/4, vbom);
		player.attachToScene(gameScene);

		// mock level -- loading test
		level = new Level("test");
		
		//manual load
		Wave wave1 = new Wave();
		Wave wave2 = new Wave();
		Wave wave3 = new Wave();
		wave1.add(new Wolf(vbom));
		wave2.add(new Wolf(ColorType.YELLOW, vbom));
		wave2.add(new Wolf(ColorType.BLUE, vbom));
		wave3.add(new Wolf(ColorType.RED, vbom));
		wave3.add(new Wolf(vbom));
		wave3.add(new Wolf(ColorType.RED, vbom));
		level.addWave(wave1);
		level.addWave(wave2);
		level.addWave(wave3);
		// end mock level setup

		level.nextBattle();
	}

	public void enterBattle(ArrayList<Gem> gems){
		if(currentWave.getEnemies().size() <= 0)
			return;

		// get target and attack
		Enemy target = currentWave.getTarget();;
		int damage = calculateDamage(gems, target);

		// player attacks enemy
		target.takeDamage(damage);

		if(target.isDead){
			gameScene.unregisterTouchArea(target);
		}

		// update enemy turn count
		for (Enemy enemy : currentWave.getEnemies()){
			enemy.decrementCurrentTurnCount();
			if (enemy.getCurrentTurnCount() == 0) {
				player.takeDamage(enemy.getAttack());
				enemy.resetCurrentTurnCount();
			}
			enemy.updateTurnCount();
		}

		if(currentWave.isFinished()){
			level.nextBattle();
		}

	}

	public static void attachEnemies(){
		for(Enemy enemy : currentWave.getEnemies()){
			enemy.attachToScene(gameScene);
		}
	}

	public int getNumOfEnemies() {
		return currentWave.getEnemies().size();
	}

	public int calculateDamage(ArrayList<Gem> gems, Enemy enemy){
		float green=0, blue=0, red=0, yellow=0;
		for(Gem gem: gems){
			switch((ColorType) gem.getUserData()){
			case GREEN:
				green++;
				break;
			case BLUE:
				blue++;
				break;
			case RED:
				red++;
				break;
			case YELLOW:
				yellow++;
				break;
			}
		}

		switch((ColorType)enemy.getUserData()){
		case RED:
			red *= 1f;
			blue *= 2f;
			green *= 0.5f;
			yellow *= 1f;
			break;
		case BLUE:
			red *= 0.5f;
			blue *= 1f;
			green *= 2f;
			yellow *= 1f;
			break;
		case GREEN:
			red *= 2f;
			blue *= 0.5f;
			green *= 1f;
			yellow *= 1f;
			break;
		case YELLOW:
			red *= 1f;
			blue *= 1f;
			green *= 1f;
			yellow *= 1f;
			break;
		}
		ResourcesManager.getInstance().activity.gameToast("damage: "+(int)(red+blue+green+yellow));
		return (int)(red+blue+green+yellow);
	}
}
