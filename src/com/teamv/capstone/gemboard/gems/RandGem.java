package com.teamv.capstone.gemboard.gems;

import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.ResourcesManager;

// RANDOM GEM THAT GENERATES RANDOM IDENTIFIER
// LOOKS LIKE A RED GEM THOUGH
public class RandGem extends Gem{

	public RandGem(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld) {
		super(pX, pY, ResourcesManager.getInstance().red_gem, vbo, physicsWorld);
	}

	public String toString(){
		return "RANDOMRED" + super.toString();
	}
}
