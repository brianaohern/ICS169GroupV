package com.teamv.capstone.game;

import java.util.ArrayList;


public class Wave{

	/*
	 * an individual wave within a level
	 * 
	 */
	
	ArrayList<EnemySprite> enemies;
	
	public Wave(ArrayList<EnemySprite> enemies){
		//load level
		this.enemies = enemies;
	}
	
	public Wave(){
		enemies = new ArrayList<EnemySprite>();
	}
	
	public void parseLevel(){
		
	}
	
	public void initPlacement(){
		switch(enemies.size()){
		case 1:
			enemies.get(0).setPosition(600, 600);
			break;
		case 2:
			enemies.get(0).setPosition(600, 400);
			enemies.get(1).setPosition(600, 700);
			break;
		case 3:
			enemies.get(0).setPosition(600, 200);
			enemies.get(1).setPosition(600, 475);
			enemies.get(2).setPosition(600, 750);
			break;
		default:
				
		}
	}
	
	public boolean isFinished(){
		if(enemies.isEmpty()){
			return true;
		}
		return false;
	}
	
	public void add(EnemySprite enemy){
		enemies.add(enemy);
	}
	
	public void remove(EnemySprite enemy){
		enemies.remove(enemy);
	}

	public ArrayList<EnemySprite> getEnemies() {
		return enemies;
	}
	
	public EnemySprite getTarget(){
		for(EnemySprite enemy : enemies){
			if(enemy.isTarget){
				return enemy;
			}
		}
		// else return first enemy
		return enemies.get(0);
	}
}
