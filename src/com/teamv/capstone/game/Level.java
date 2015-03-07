package com.teamv.capstone.game;

import java.util.ArrayList;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.managers.SceneManager;
import com.teamv.capstone.scenes.GameScene;

public class Level {
	protected ArrayList<Wave> level;
	protected String name = "";
	protected int currentBattle = 0;
	protected int size = 0;
	protected VertexBufferObjectManager vbom;
	
	public Level(){
		level = new ArrayList<Wave>();
		vbom = ResourcesManager.getInstance().vbom;
	}
	
	public Level(String name){
		this();
		this.name = name;
	}

	public void nextBattle(Wave wave){
		//set up next battle
		wave.initPlacement();
		Battleground.currentWave = wave;
		Battleground.attachEnemies();
	}

	public void nextBattle(){
		if(currentBattle < level.size()){
			nextBattle(level.get(currentBattle));
			currentBattle++;
		}
		else{
			((GameScene) SceneManager.getInstance().getCurrentScene()).endGame(true);
		}
	}
	
	public void addWave(Wave wave){
		level.add(wave);
		size++;
	}
	
	public Wave getLastWave(){
		return level.get(size - 1);
	}
	
	public String getName(){
		return name;
	}
	
	public int getCurrentWaveCount(){
		return currentBattle;
	}
	
	public int getMaxWaveCount(){
		return size;
	}
}
