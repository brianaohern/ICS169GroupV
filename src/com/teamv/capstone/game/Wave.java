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
		
	}
	
	public void parseLevel(){
		
	}
	
	public boolean isFinished(){
		for(Enemy e : enemies){
			if(!e.isDead){
				return false;
			}
		}
		return true;
	}
	
	public void add(Enemy enemy){
		enemies.add(enemy);
	}
}
