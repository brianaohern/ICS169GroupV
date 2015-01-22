package com.teamv.capstone.gemboard.gems;

import java.util.Random;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.gemboard.Gem;
import com.teamv.capstone.managers.ResourcesManager;

// RANDOM GEM THAT GENERATES RANDOM IDENTIFIER
// LOOKS LIKE A RED GEM THOUGH
public class RandGem extends Gem{

	public RandGem(int col, int row, float pX, float pY, VertexBufferObjectManager vbo, PhysicsWorld physicsWorld) {
		
		super(col, row, pX, pY, ResourcesManager.getInstance().red_gem, vbo, physicsWorld);
		
		this.setUserData("RandomRed" + new Random().nextFloat());
	}
}
