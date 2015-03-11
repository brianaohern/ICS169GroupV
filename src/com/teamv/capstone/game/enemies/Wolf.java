package com.teamv.capstone.game.enemies;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.game.Enemy;
import com.teamv.capstone.managers.ResourcesManager;

public class Wolf extends Enemy{
	
	final static int 		WOLF_HEALTH = 8;
	final static int		WOLF_ATTACK = 6;
	final static int 		WOLF_START_TURN_COUNT = 3;
	final static float		WOLF_SCALE = 1f;
	private ColorType 		wolf_type = ColorType.RED;

	public Wolf(float x, float y, VertexBufferObjectManager vbom) {
		super(x, y, ResourcesManager.getInstance().wolf, vbom);
		this.setup(WOLF_HEALTH, WOLF_ATTACK, WOLF_START_TURN_COUNT, wolf_type, WOLF_SCALE);
		this.init();
	}
	
	public Wolf(VertexBufferObjectManager vbom){
		this(0, 0, vbom);
		this.setType(wolf_type);
	}
	
	public Wolf(ColorType type, VertexBufferObjectManager vbom){
		this(vbom);
		this.setUserData(type);
		this.setType(type);
	}
}
