package com.teamv.capstone.game.enemies;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.game.Enemy;
import com.teamv.capstone.managers.ResourcesManager;

public class Wolf extends Enemy{
	
	final static int 		WOLF_HEALTH = 10;
	final static int		WOLF_ATTACK = 3;
	final static int 		WOLF_START_TURN_COUNT = 3;
	final static float		WOLF_SCALE = 0.75f;
	private ColorType 		wolf_type = ColorType.GREEN;

	public Wolf(float x, float y, VertexBufferObjectManager vbom) {
		super(x, y, ResourcesManager.getInstance().wolf, vbom);
		this.setup(WOLF_HEALTH, WOLF_ATTACK, WOLF_START_TURN_COUNT, wolf_type, WOLF_SCALE);
	}
	
	public Wolf(VertexBufferObjectManager vbom){
		this(0, 0, vbom);
	}
	
	public Wolf(ColorType type, VertexBufferObjectManager vbom){
		this(vbom);
		this.setUserData(type);
		this.setType(type);
	}
}
