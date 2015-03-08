package com.teamv.capstone.game;

import java.util.ArrayList;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.managers.SceneManager;
import com.teamv.capstone.scenes.GameScene;

public class Level {
	protected ArrayList<Wave> level;
	protected String name = "";
	protected int currentWave = 0;
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

	public void nextWave(Wave wave){
		//set up next battle
		wave.initPlacement();
		Battleground.currentWave = wave;
		Battleground.attachEnemies();
	}

	public void nextWave(){
		if(currentWave < level.size()){
			nextWave(level.get(currentWave));
			currentWave++;
		}
		else{
			((GameScene) SceneManager.getInstance().getCurrentScene()).enterEndScene(true);
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
		return currentWave;
	}
	
	public int getMaxWaveCount(){
		return size;
	}
}
