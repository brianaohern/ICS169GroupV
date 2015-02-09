package com.teamv.capstone.game;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.SAXUtils;
import org.andengine.util.debug.Debug;
import org.andengine.util.level.IEntityLoader;
import org.andengine.util.level.LevelLoader;
import org.xml.sax.Attributes;

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
	ArrayList<Wave> level;
	int currentBattle = 0;
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
		level = new ArrayList<Wave>();
		
		// xml load
		LevelLoader levelLoader = new LevelLoader();
		//levelLoader.setAssetBasePath("assets/levels/");
		
		levelLoader.registerEntityLoader("level", new IEntityLoader() {
			@Override
			public IEntity onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				final String name = SAXUtils.getAttributeOrThrow(pAttributes, "name");
				final String levelType = SAXUtils.getAttributeOrThrow(pAttributes, "type");
				return gameScene;
			}
		});
		
		levelLoader.registerEntityLoader("wave", new IEntityLoader() {

			@Override
			public IEntity onLoadEntity(String pEntityName, Attributes pAttributes) {
				Wave wave = new Wave();
				level.add(wave);
				return null;
			}
		});
		
		levelLoader.registerEntityLoader("enemy", new IEntityLoader() {

			@Override
			public IEntity onLoadEntity(String pEntityName, Attributes pAttributes) {
				final String name = SAXUtils.getAttributeOrThrow(pAttributes, "name");
				//final String enemyType = SAXUtils.getAttributeOrThrow(pAttributes, "type");

				Enemy enemy = null;
				switch(name){
				case "wolf":
					enemy = new Wolf(vbom);
					break;
				default:
					ResourcesManager.getInstance().activity.gameToast("xml level loader -- default break");
				}
				
				if(enemy!=null)
					level.get(level.size()-1).add(enemy);
				
				return null;
			}
		});
		


		try{
			levelLoader.loadLevelFromAsset(ResourcesManager.getInstance().activity.getAssets(), "levels/robin.xml");
		} catch (final IOException e) {
			Debug.e(e);
		}
		//manual load
//		Wave wave1 = new Wave();
//		Wave wave2 = new Wave();
//		Wave wave3 = new Wave();
//		wave1.add(new Wolf(vbom));
//		wave2.add(new Wolf(ColorType.YELLOW, vbom));
//		wave2.add(new Wolf(ColorType.BLUE, vbom));
//		wave3.add(new Wolf(ColorType.RED, vbom));
//		wave3.add(new Wolf(vbom));
//		wave3.add(new Wolf(ColorType.RED, vbom));
//		level.add(wave1);
//		level.add(wave2);
//		level.add(wave3);
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
		if(currentBattle < level.size()){
			nextBattle(level.get(currentBattle));
			currentBattle++;
		}
		else{
			gameScene.endGame(true);
		}
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
			nextBattle();
		}

	}

	private void attachEnemies(){
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
