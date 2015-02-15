package com.teamv.capstone.gemboard.gems.special;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.gemboard.Gem;
import com.teamv.capstone.managers.ResourcesManager;

public class BombGem extends Gem{

	public BombGem(int col, int row, float pX, float pY, VertexBufferObjectManager vbo, PhysicsWorld physicsWorld) {
		super(col, row, pX, pY, ResourcesManager.getInstance().bomb_gem, vbo, physicsWorld);

		setUserData(ColorType.SPECIAL);
	}
	
	// When this special gem is chained, destroy adjacent gems relative to the gem's location on the grid.
	protected void onDestroy(){
		// Get position of bomb on the grid [col][row]
		
		// From the bomb's position on the grid, store adjacent gems and their types
		
		// Remove/destroy adjacent gems
		
		// Drop gems
		
	}
}
