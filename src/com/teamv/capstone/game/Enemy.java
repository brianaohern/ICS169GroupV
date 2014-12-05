package com.teamv.capstone.game;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.Point;

public class Enemy extends Sprite{
	
	public static final int LEFTALIGN = 0;
	public static final int RIGHTALIGN = 1;
	
	private Point start = new Point(1080/2, 100);
	
	// stats and whatnot
	int counter;
	int health;
	float attack;
	float defense;
	
	

	public Enemy(ITextureRegion region, VertexBufferObjectManager vbo) {
		super(0, 0, region, vbo);
		
		this.setX(start.x);
		this.setY(start.y);
	}
	
	public void onDie(){
		this.detachSelf();
		this.dispose();
	}
}
