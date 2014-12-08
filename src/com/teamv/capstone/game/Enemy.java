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

public class Enemy extends Sprite{

	public static final int LEFTALIGN = 0;
	public static final int RIGHTALIGN = 1;

	private Point start = new Point(1080/2, 1920/4);

	// stats and whatnot; variables name pending
	int turnCounter;
	float attack;
	float defense;

	// display stuffs
	private Rectangle healthbar;
	private int HPlength;
	private final Text hpText;
	private final Text turnText;
	private int xBuffer = 100;
	private int yBuffer =  60;
	
	private int health;
	private int startHealth = 10;
	
	public Enemy(ITextureRegion region, VertexBufferObjectManager vbo) {
		super(0, 0, region, vbo);

		this.setX(start.x);
		this.setY(start.y);
		
		health = startHealth;
		
		HPlength = (int) this.getWidth() - xBuffer;
		hpText = new Text(start.x, start.y - (yBuffer + 20), ResourcesManager.getInstance().font, "HP:", "HP:".length(), vbo);
		turnText = new Text(start.x, start.y - (yBuffer + 80), ResourcesManager.getInstance().font, "Turns: 1", "Turns: X".length(), vbo);
		
		healthbar = new Rectangle(start.x + xBuffer, start.y - yBuffer, HPlength, 25, vbo);
		healthbar.setColor(Color.RED);
	}

	
	public void takeDamage(int damage){
		health -= damage;
		updateHealthBar();
		if(health <= 0){
			Battleground.enemies.remove(this);
			onDie();
		}
	}
	
	public void updateHealthBar(){
		if(health < 0){
			health = 0;
		}
		healthbar.setWidth(HPlength / 1.0f * health / startHealth);
	}

	public void onDie(){
		this.detachSelf();
		this.dispose();
		healthbar.detachSelf();
		healthbar.dispose();
		hpText.detachSelf();
		hpText.dispose();
		turnText.detachSelf();
		turnText.dispose();
	}
	
	public void attachToScene(GameScene gameScene) {
		gameScene.attachChild(this);
		gameScene.attachChild(healthbar);
		gameScene.attachChild(hpText);
		gameScene.attachChild(turnText);
	}
	
}
