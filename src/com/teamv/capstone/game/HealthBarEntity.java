package com.teamv.capstone.game;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.BaseScene;
import com.teamv.capstone.ResourcesManager;
import com.teamv.capstone.utility.Pointf;

public abstract class HealthBarEntity extends Sprite{
	
	Pointf start;
	
	int startHealth;
	int currentHealth;
	int attack;
	
	// health
	Rectangle healthBar;
	int healthBarWidth;
	int healthBarHeight;
	Text healthBarText;
	
	public HealthBarEntity(float x, float y, ITextureRegion region, VertexBufferObjectManager vbom){
		super(x, y, region, vbom);
		
		start = new Pointf(x, y);
		
		healthBarText = new Text(0, 0, ResourcesManager.getInstance().font, "", 20, vbom);
		healthBar = new Rectangle(0, 0, 0, 0, vbom);
		
		init();
	}
	
	public abstract void init();
	
	public void updateHealthBar(){
		if(currentHealth < 0){
			currentHealth = 0;
		}
		healthBar.setWidth(healthBarWidth / 1.0f * currentHealth / startHealth);
	}
	
	public void takeDamage(int damage){
		currentHealth -= damage;
		updateHealthBar();
		if(currentHealth <= 0){
			onDie();
		}
	}
	
	public abstract void onDie();
	
	public void cleanUp(){
		this.detachSelf();
		this.dispose();
		healthBar.detachSelf();
		healthBar.dispose();
		healthBarText.detachSelf();
		healthBarText.dispose();
	}
	
	public void attachToScene(BaseScene gameScene){
		gameScene.attachChild(this);
		gameScene.attachChild(healthBar);
		gameScene.attachChild(healthBarText);
	}
	
	public int getCurrentHealth(){
		return currentHealth;
	}
	
	public void setCurrentHealth(int health){
		currentHealth = health;
	}
	
	public void hideHealthBar(boolean hide){
		if(hide == true){
			healthBar.setVisible(false);
		}else{
			healthBar.setVisible(true);
		}
	}
}


