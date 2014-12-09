package com.teamv.capstone.game;

import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.teamv.capstone.BaseScene;
import com.teamv.capstone.ResourcesManager;

public class Enemy extends HealthBarEntity{

	public static final int LEFTALIGN = 0;
	public static final int RIGHTALIGN = 1;

	int turnCounter;
	float attack;
	float defense;

	private Text turnText;
	private Point buffer;
	
	public Enemy(float x, float y, ITextureRegion region, VertexBufferObjectManager vbo) {
		super(x, y, region, vbo);
		
		// init happens in super
		turnText = new Text(start.x, start.y - (buffer.y + 80), ResourcesManager.getInstance().font, "Turns: 1", "Turns: X".length(), vbo);
	}
	
	public void init(){
		startHealth = 10;
		currentHealth = startHealth;
		buffer = new Point(100, 60);
		
		healthBarText.setX(start.x);
		healthBarText.setY(start.y - (buffer.y + 20));
		healthBarText.setText("HP:");
		
		healthBarWidth  = (int) (this.getWidth() - buffer.x);
		healthBarHeight = 25;
		healthBar.setX(start.x + buffer.x);
		healthBar.setY(start.y - buffer.y);
		healthBar.setWidth(healthBarWidth);
		healthBar.setHeight(25);
		healthBar.setColor(Color.RED);
	}
	
	public void onDie(){
		Battleground.enemies.remove(this);
		cleanUp();		
	}
	
	public void cleanUp(){
		super.cleanUp();
		turnText.detachSelf();
		turnText.dispose();
	}
	
	public void attachToScene(BaseScene gameScene) {
		super.attachToScene(gameScene);
		gameScene.attachChild(turnText);
	}
	
}
