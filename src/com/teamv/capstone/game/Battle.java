package com.teamv.capstone.game;

import java.util.ArrayList;


public class Battle {

	/*
	 * Battle is basically level
	 * 
	 */
	
	ArrayList<Enemy> enemies;
	
	public Battle(ArrayList<Enemy> enemies){
		//load level
		this.enemies = enemies;
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
}
