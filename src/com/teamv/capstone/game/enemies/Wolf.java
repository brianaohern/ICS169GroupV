package com.teamv.capstone.game.enemies;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.ResourcesManager;
import com.teamv.capstone.game.Enemy;

public class Wolf extends Enemy{

	public Wolf(float x, float y, VertexBufferObjectManager vbom) {
		super(x, y, ResourcesManager.getInstance().wolf, vbom);
	}
	
	public Wolf(VertexBufferObjectManager vbom){
		super(ResourcesManager.getInstance().wolf, vbom);
	}

}
