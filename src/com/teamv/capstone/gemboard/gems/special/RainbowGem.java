package com.teamv.capstone.gemboard.gems.special;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.gemboard.Gem;
import com.teamv.capstone.managers.ResourcesManager;

public class RainbowGem extends Gem{

	public RainbowGem(int col, int row, float pX, float pY, VertexBufferObjectManager vbo, PhysicsWorld physicsWorld) {
		super(col, row, pX, pY, ResourcesManager.getInstance().rainbow_gem, vbo, physicsWorld);

		setUserData(ColorType.SPECIAL);
	}
	
	// When this special gem is chained, the player can connect another chain of gems
	protected void onDestroy(){
		// get position of rainbow gem
		
		// look at adjacent gems that are not already in the chain
		
		// create a chain of size 1 or more
		
		// add new chain to connectedGems
		
	}
}
