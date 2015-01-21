package com.teamv.capstone.game;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.teamv.capstone.BaseScene;
import com.teamv.capstone.utility.Point;

public abstract class Enemy extends HealthBarEntity{

	public static final int LEFTALIGN = 0;
	public static final int RIGHTALIGN = 1;

	boolean isDead;
	int turnCounter;
	private Point buffer;
	
	public Enemy(float x, float y, ITextureRegion region, VertexBufferObjectManager vbo) {
		super(x, y, region, vbo);
	}
	
	public Enemy(ITextureRegion region, VertexBufferObjectManager vbo){
		super(0, 0, region, vbo);
	}
	
	public void init(){
		startHealth = 10;
		currentHealth = startHealth;
		buffer = new Point(100, 0);
		
		healthBarText.setX(this.getX());
		healthBarText.setY(this.getY() - (buffer.y + 20));
		healthBarText.setText("1  :");
		
		healthBarWidth  = (int) (this.getWidth() - buffer.x);
		healthBarHeight = 25;
		healthBar.setX(this.getX() + buffer.x);
		healthBar.setY(this.getY() - buffer.y);
		healthBar.setWidth(healthBarWidth);
		healthBar.setHeight(25);
		healthBar.setColor(Color.RED);
	}
	
	public void onDie(){
//		Battleground.enemies.remove(this);
//		isDead = true;
//		cleanUp();	
		init();
	}
	
	public void cleanUp(BaseScene gameScene){
		super.cleanUp();
		gameScene.unregisterTouchArea(this);
	}
	
	public void attachToScene(BaseScene gameScene) {
		super.attachToScene(gameScene);
		gameScene.registerTouchArea(this);
	}
	
	public void setPosition(float pX, float pY){
		super.setPosition(pX, pY);
		healthBarText.setX(pX);
		healthBarText.setY(pY - (buffer.y + 20));
		
		healthBar.setX(pX + buffer.x);
		healthBar.setY(pY - buffer.y);
	}
	
	public void setScale(float scale){
		super.setScale(scale);
		healthBarText.setScale(scale);
		healthBar.setScale(scale);
	}
	
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
    {
		this.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(1.5f, 0.7f, 0.8f))));
		return true;
    }
}
