package com.teamv.capstone.game;

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
		buffer = new Point(100, 60);
		
		healthBarText.setX(start.x);
		healthBarText.setY(start.y - (buffer.y + 20));
		healthBarText.setText("1  :");
		
		healthBarWidth  = (int) (this.getWidth() - buffer.x);
		healthBarHeight = 25;
		healthBar.setX(start.x + buffer.x);
		healthBar.setY(start.y - buffer.y);
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
	
	public void cleanUp(){
		super.cleanUp();
	}
	
	public void attachToScene(BaseScene gameScene) {
		super.attachToScene(gameScene);
	}
	
}
