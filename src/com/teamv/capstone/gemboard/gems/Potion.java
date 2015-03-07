package com.teamv.capstone.gemboard.gems;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.managers.ResourcesManager;

public class Potion extends SpecialGem{
	public Potion(int col, int row, float pX, float pY, VertexBufferObjectManager vbo, PhysicsWorld physicsWorld) {
		super(col, row, pX, pY, ResourcesManager.getInstance().potion, vbo, physicsWorld);
		
		setUserData(ColorType.POTION);
	}
}