package com.teamv.capstone.gemboard.gems;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.gemboard.Gem;
import com.teamv.capstone.managers.ResourcesManager;

public class BlueGem extends Gem{

	public BlueGem(int col, int row, float pX, float pY, VertexBufferObjectManager vbo, PhysicsWorld physicsWorld) {
		super(col, row, pX, pY, ResourcesManager.getInstance().blue_gem, vbo, physicsWorld);

		setUserData("Blue");
	}
}
