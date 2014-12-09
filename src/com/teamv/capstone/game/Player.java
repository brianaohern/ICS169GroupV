package com.teamv.capstone.game;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.teamv.capstone.BaseScene;

public class Player extends HealthBarEntity{
	
	private int attack;
	
	int healthBarBufferY = 50;
	int healthBarPosY = 1920/2;
	
	public Player(float x, float y, ITextureRegion region, VertexBufferObjectManager vbo) {
		super(x, y, region, vbo);
	}
	
	public void init(){
		startHealth = 20;
		currentHealth = startHealth;
		
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
	
	public void attackEnemy(Enemy enemy){
		// TODO
	}
	
	public void onDie(){
		// TODO
	}
	
	public void cleanUp(){
		super.cleanUp();
	}
	
	public void attachToScene(BaseScene gameScene){
		super.attachToScene(gameScene);
	}
	
	public int getPlayerAttack(){
		return attack;
	}
	
	public void setPlayerAttack(int atk){
		attack = atk;
	}
	
}
