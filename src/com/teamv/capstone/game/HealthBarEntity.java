package com.teamv.capstone.game;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.gemboard.Gem;
import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.scenes.BaseScene;
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
	
	// turn count
	Text turnCountText;
	
	public HealthBarEntity(float x, float y, ITextureRegion region, VertexBufferObjectManager vbom){
		super(x, y, region, vbom);
		
		start = new Pointf(x, y);
		
		healthBarText = new Text(0, 0, ResourcesManager.getInstance().font, "", 20, vbom);
		healthBar = new Rectangle(0, 0, 0, 0, vbom);
		
		turnCountText = new Text(0, 0, ResourcesManager.getInstance().font, "", 20, vbom);
		
		init();
	}
	
	public abstract void init();
	
	public void updateHealthBar(){
		if(currentHealth < 0){
			currentHealth = 0;
		}
		healthBar.setWidth(healthBarWidth / 1.0f * currentHealth / startHealth);
		healthBarText.setText("HP: " + currentHealth + "/" + startHealth);
	}
	
	public void takeDamage(int damage){
		if(Gem.getDamageType().contains(this.getUserData())){
			damage *= 2;
			System.out.println("IT'S A CRIT! Damage: " + damage);
		}
		currentHealth -= damage;
		updateHealthBar();
		if(currentHealth <= 0){
			onDie();
		}
	}
	
	public void moveHealthBarEntityTo(HealthBarEntity target){
		//Move this health bar entity's position to target's position
		float startX = this.start.x;
		float startY = this.start.y;
		float targetX = target.start.x;
		float targetY = target.start.y;
		
		System.out.println("This Position: " + this.start.x + ", " + this.start.y);		
		System.out.println("Target Position: " + target.start.x + ", " + target.start.y);
		this.registerEntityModifier(new MoveModifier(1, startX, targetX, startY, targetY));

		this.registerEntityModifier(new MoveModifier(1, targetX, startX, targetY, startY));
		
	}
	
	public abstract void onDie();
	
	public void cleanUp(){
		this.detachSelf();
		this.dispose();
		healthBar.detachSelf();
		healthBar.dispose();
		healthBarText.detachSelf();
		healthBarText.dispose();
		turnCountText.detachSelf();
		turnCountText.dispose();
	}
	
	public void attachToScene(BaseScene gameScene){
		gameScene.attachChild(this);
		gameScene.attachChild(healthBar);
		gameScene.attachChild(healthBarText);
		gameScene.attachChild(turnCountText);
	}
	
	public int getStartHealth(){
		return startHealth;
	}
	
	public void setStartHealth(int health){
		startHealth = health;
	}
	
	public int getCurrentHealth(){
		return currentHealth;
	}
	
	public void setCurrentHealth(int health){
		currentHealth = health;
	}
	
	public int getAttack(){
		return attack;
	}
	
	public void setAttack(int atk){
		attack = atk;
	}
	
	public void hideHealthBar(boolean hide){
		if(hide == true){
			healthBar.setVisible(false);
		}else{
			healthBar.setVisible(true);
		}
	}
}


