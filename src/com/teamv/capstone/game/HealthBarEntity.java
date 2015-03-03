package com.teamv.capstone.game;

import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseStrongInOut;
import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.scenes.BaseScene;
import com.teamv.capstone.utility.Pointf;

public abstract class HealthBarEntity extends Sprite{
	
	protected Pointf start;
	protected int startHealth;
	protected int currentHealth;
	protected int attack;
	
	protected Rectangle healthBar;
	protected int healthBarWidth;
	protected int healthBarHeight;
	protected Text healthBarText;
	protected Line l1, l2, l3, l4;
	
	public HealthBarEntity(float x, float y, ITextureRegion region, VertexBufferObjectManager vbom){
		super(x, y, region, vbom);
		super.setPosition(x, y);
		start = new Pointf(x, y);
		healthBarText = new Text(0, 0, ResourcesManager.getInstance().font, "", 20, vbom);
		healthBar = new Rectangle(0, 0, 0, 0, vbom);
		l1 = new Line(0,0,0,0,vbom);
		l2 = new Line(0,0,0,0,vbom);
		l3 = new Line(0,0,0,0,vbom);
		l4 = new Line(0,0,0,0,vbom);
	}
	
	protected abstract void init();
	
	public void updateHealthBar(){
		if(currentHealth < 0){
			currentHealth = 0;
		}
		healthBar.setWidth(healthBarWidth / 1.0f * currentHealth / startHealth);
		healthBarText.setText("HP: " + currentHealth + "/" + startHealth);
	}
	
	public void takeDamage(int damage){
		currentHealth -= damage;
		updateHealthBar();
		if(currentHealth <= 0){
			onDie();
		}
	}
	
	public void attackHealthBarEntity(final HealthBarEntity target, int damage){
		target.currentHealth -= damage;
		target.updateHealthBar();
		if(target.currentHealth <= 0){
			target.onDie();
		}
	}
	
	public void moveToEntityStartPosition(HealthBarEntity target){
		final Path path = new Path(3).to(this.start.x, this.start.y).to(target.start.x,  target.start.y).to(this.start.x, this.start.y);
		PathModifier pathMod = new PathModifier(0.8f, path, EaseStrongInOut.getInstance());
		registerEntityModifier(pathMod);
	}
	
	public abstract void onDie();
	
	public void cleanUp(){
		this.detachSelf();
		this.dispose();
		healthBar.detachSelf();
		healthBar.dispose();
		healthBarText.detachSelf();
		healthBarText.dispose();
		l1.detachSelf();
		l1.dispose();
		l2.detachSelf();
		l2.dispose();
		l3.detachSelf();
		l3.dispose();
		l4.detachSelf();
		l4.dispose();
	}
	
	public void attachToScene(BaseScene gameScene){
		gameScene.attachChild(this);
		gameScene.attachChild(l1);
		gameScene.attachChild(l2);
		gameScene.attachChild(l3);
		gameScene.attachChild(l4);
		gameScene.attachChild(healthBar);
		gameScene.attachChild(healthBarText);
	}
	
	public int getAttack(){
		return attack;
	}
	
	public void setHealthBarVisibility(boolean value){
		healthBar.setVisible(value);
	}
	
	protected void drawHealthBorder(){
		l1.setPosition(healthBar.getX(), healthBar.getY(), healthBar.getX()+healthBar.getWidth(), healthBar.getY());
		l2.setPosition(healthBar.getX(), healthBar.getY(), healthBar.getX(), healthBar.getY()+healthBar.getHeight());
		l3.setPosition(healthBar.getX(), healthBar.getY()+healthBar.getHeight(), healthBar.getX()+healthBar.getWidth(), healthBar.getY()+healthBar.getHeight());
		l4.setPosition(healthBar.getX()+healthBar.getWidth(), healthBar.getY(), healthBar.getX()+healthBar.getWidth(), healthBar.getY()+healthBar.getHeight());
		l1.setColor(Color.RED);
		l2.setColor(Color.RED);
		l3.setColor(Color.RED);
		l4.setColor(Color.RED);
		l1.setLineWidth(10);
		l2.setLineWidth(10);
		l3.setLineWidth(10);
		l4.setLineWidth(10);
	}
}


