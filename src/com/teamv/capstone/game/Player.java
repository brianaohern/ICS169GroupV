package com.teamv.capstone.game;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.teamv.capstone.ResourcesManager;
import com.teamv.capstone.scenes.GameScene;

import android.graphics.Point;

public class Player extends Sprite{
	
	private Point start = new Point(1080/40, 1920/4);
	
	// Stats
	
	private int startHealth = 20;
	private int currentHealth;
	private int attack;
	
	// Player Health bar
	private Rectangle healthBar;
	private int healthBarWidth = 1000;
	private int healthBarHeight = 50;
	private final Text healthBarText;
	private int healthBarBufferY = 50;
	private int heatlhBarPosY = 1920/2;
	private String healthBarStatus;
	
	
	public Player(ITextureRegion region, VertexBufferObjectManager vbo) {
		super(0, 0, region, vbo);
		
		this.setX(start.x);
		this.setY(start.y);
		
		currentHealth = startHealth;
		healthBarStatus = "HP: " + currentHealth + "/" + startHealth;
		
		//healthBarWidth = (int) this.getWidth() - heatlhBarBufferX;
		healthBarText = new Text(start.x, heatlhBarPosY + healthBarBufferY, ResourcesManager.getInstance().font, 
				healthBarStatus, healthBarStatus.length(), vbo);
		
		healthBar = new Rectangle(start.x, heatlhBarPosY + healthBarBufferY, healthBarWidth, healthBarHeight, vbo);
		healthBar.setColor(Color.GREEN);
	}
	
	public void takeDamage(int damage){
		currentHealth -= damage;
		updateHealthBar();
		if(currentHealth <= 0){
			// Game over
		}
	}
	
	public void attackEnemy(Enemy enemy){
		// TODO
	}
	
	public void updateHealthBar(){
		if(currentHealth < 0){
			currentHealth = 0;
		}
		healthBar.setWidth(healthBarWidth / 1.0f * currentHealth / startHealth);
	}
	
	public void onDie(){
		this.detachSelf();
		this.dispose();
		healthBar.detachSelf();
		healthBar.dispose();
		healthBarText.detachSelf();
		healthBarText.dispose();
	}
	
	public void attachToGameScene(GameScene gameScene){
		gameScene.attachChild(this);
		gameScene.attachChild(healthBar);
		gameScene.attachChild(healthBarText);
	}
	
	public int getPlayerCurrentHealth(){
		return currentHealth;
	}
	
	public void setPlayerCurrentHealth(int hp){
		currentHealth = hp;
	}
	
	public int getPlayerAttack(){
		return attack;
	}
	
	public void setPlayerAttack(int atk){
		attack = atk;
	}
	
}
