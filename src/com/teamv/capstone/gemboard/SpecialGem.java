package com.teamv.capstone.gemboard;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class SpecialGem extends Gem{

	public SpecialGem(int col, int row, float pX, float pY, ITextureRegion region, VertexBufferObjectManager vbo, PhysicsWorld physicsWorld) {
		super(col, row, pX, pY, region, vbo, physicsWorld);	
	}
	
	protected abstract void onDestroy();
}