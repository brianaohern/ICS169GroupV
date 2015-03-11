package com.teamv.capstone.game.enemies;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.game.Enemy;
import com.teamv.capstone.managers.ResourcesManager;

public class DireWolf extends Enemy{
	
	final static int 		DIRE_WOLF_HEALTH = 12;
	final static int		DIRE_WOLF_ATTACK = 6;
	final static int 		DIRE_WOLF_START_TURN_COUNT = 2;
	final static float		DIRE_WOLF_SCALE = 1f;
	private ColorType 		dire_wolf_type = ColorType.BLUE;

	public DireWolf(float x, float y, VertexBufferObjectManager vbom) {
		super(x, y, ResourcesManager.getInstance().direWolf, vbom);
		this.setup(DIRE_WOLF_HEALTH, DIRE_WOLF_ATTACK, DIRE_WOLF_START_TURN_COUNT, dire_wolf_type, DIRE_WOLF_SCALE);
		this.init();
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
	
	public DireWolf(VertexBufferObjectManager vbom, int health, int attack, int startTurnCount){
		this(0, 0, vbom);
		this.setup(health, attack, startTurnCount, dire_wolf_type, DIRE_WOLF_SCALE);
		this.init();
	}
}
