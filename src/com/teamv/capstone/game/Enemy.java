package com.teamv.capstone.game;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Enemy extends Sprite{
	
	// stats and whatnot
	int counter;
	int health;
	float attack;
	float defense;

	public Enemy(float pX, float pY, ITextureRegion region, VertexBufferObjectManager vbo, PhysicsWorld physicsWorld) {
		super(pX, pY, region, vbo);
	}
	
	
	public void onDie(){
		this.detachSelf();
		this.dispose();
	}
}
