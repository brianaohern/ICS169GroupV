package com.teamv.capstone.game;

import java.util.ArrayList;

import com.teamv.capstone.managers.SceneManager;
import com.teamv.capstone.scenes.GameScene;

public class Level {
	private ArrayList<Wave> level;
	private String name = "";
	private int currentBattle = 0;
	private int size = 0;
	
	public Level(){
		level = new ArrayList<Wave>();
	}
	
	public Level(String name){
		level = new ArrayList<Wave>();
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
	
	public void setName(String name){
		this.name = name;
	}
}
