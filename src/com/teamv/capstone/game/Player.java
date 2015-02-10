package com.teamv.capstone.game;

import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.teamv.capstone.managers.ResourcesManager;

public class Player extends HealthBarEntity{
	
	int healthBarBufferY, healthBarPosY;
	
	public Player(float x, float y, VertexBufferObjectManager vbo) {
		super(x, y, ResourcesManager.getInstance().mainCharacter, vbo);
		setPosition(x, y);
	}
	
	public void init(){
		startHealth = 20;
		currentHealth = startHealth;
		
		healthBarPosY = 1920/2;
		healthBarBufferY = 50;
		
		String healthBarStatus = "HP: " + currentHealth + "/" + startHealth;
		healthBarText.setX(start.x);
		healthBarText.setY(healthBarPosY + healthBarBufferY);
		healthBarText.setText(healthBarStatus);

		healthBarWidth = 1000;
		healthBarHeight = 50;
		healthBar.setX(start.x);
		healthBar.setY(healthBarPosY + healthBarBufferY);
		healthBar.setWidth(healthBarWidth);
		healthBar.setHeight(healthBarHeight);
		healthBar.setColor(Color.GREEN);
	}
	
	public void onDie(){
		Battleground.gameScene.endGame(false);
	}
	
	public int getPlayerAttack(){
		return attack;
	}
	
	public void setPlayerAttack(int atk){
		attack = atk;
	}
}
