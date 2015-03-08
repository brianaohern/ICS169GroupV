package com.teamv.capstone.game;

import java.util.ArrayList;


public class Wave{

	/*
	 * an individual wave within a level
	 * 
	 */
	
	ArrayList<Enemy> enemies;
	
	public Wave(ArrayList<Enemy> enemies){
		//load level
		this.enemies = enemies;
	}
	
	public Wave(){
		enemies = new ArrayList<Enemy>();
	}
	
	public void initPlacement(){
		int x = 750;
		switch(enemies.size()){
		case 1:
			enemies.get(0).setPosition(x, 600);
			break;
		case 2:
			enemies.get(0).setPosition(x, 400);
			enemies.get(1).setPosition(x, 700);
			break;
		case 3:
			enemies.get(0).setPosition(x, 200);
			enemies.get(1).setPosition(x, 475);
			enemies.get(2).setPosition(x, 750);
			break;
		default:
			break;
		}
		for(Enemy enemy : enemies){
			enemy.initHealthContainer();
		}
		// turn on indicator
		enemies.get(0).auto.setVisible(true);
	}
	
	public boolean isFinished(){
		if(enemies.isEmpty()){
			return true;
		}
		return false;
	}
	
	public void add(Enemy enemy){
		enemies.add(enemy);
	}
	
	public void remove(Enemy enemy){
		enemies.remove(enemy);
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	public Enemy getTarget(){
		for(Enemy enemy : enemies){
			if(enemy.isTarget){
				return enemy;
			}
		}
		// else return first enemy
		return enemies.get(0);
	}
}
