package com.teamv.capstone.game.enemies;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.game.Enemy;
import com.teamv.capstone.managers.ResourcesManager;

public class DireWolf extends Enemy{
	
	final static int 		DIRE_WOLF_HEALTH = 6;
	final static int		DIRE_WOLF_ATTACK = 3;
	final static int 		DIRE_WOLF_START_TURN_COUNT = 2;
	final static float		DIRE_WOLF_SCALE = 3f;
	private ColorType 		dire_wolf_type = ColorType.BLUE;

	public DireWolf(float x, float y, VertexBufferObjectManager vbom) {
		super(x, y, ResourcesManager.getInstance().direWolf, vbom);
		this.setup(DIRE_WOLF_HEALTH, DIRE_WOLF_ATTACK, DIRE_WOLF_START_TURN_COUNT, dire_wolf_type, DIRE_WOLF_SCALE);
	}
	
	public DireWolf(VertexBufferObjectManager vbom){
		this(0, 0, vbom);
		this.setType(dire_wolf_type);
	}
	
	public DireWolf(ColorType type, VertexBufferObjectManager vbom){
		this(vbom);
		this.setUserData(type);
		this.setType(type);
	}
}
