package com.teamv.capstone.game;

public abstract class Enemy {
	EnemySprite sprite;
	ColorType type;
	
	
	public Enemy(){
		
	}
	
	public Enemy(ColorType type){
		this.type = type;
	}
	
	protected abstract void loadTexture();
	
}
