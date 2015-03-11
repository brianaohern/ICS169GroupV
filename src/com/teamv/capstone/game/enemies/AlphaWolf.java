package com.teamv.capstone.game.enemies;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.game.Enemy;
import com.teamv.capstone.managers.ResourcesManager;

public class AlphaWolf extends Enemy{
	
	final static int 		ALPHA_WOLF_HEALTH = 40;
	final static int		ALPHA_WOLF_ATTACK = 10;
	final static int 		ALPHA_WOLF_START_TURN_COUNT = 4;
	final static float		ALPHA_WOLF_SCALE = 0.75f;
	private ColorType 		alpha_wolf_type = ColorType.RED;

	public AlphaWolf(float x, float y, VertexBufferObjectManager vbom) {
		super(x, y, ResourcesManager.getInstance().alphaWolf, vbom);
		this.setup(ALPHA_WOLF_HEALTH, ALPHA_WOLF_ATTACK, ALPHA_WOLF_START_TURN_COUNT, alpha_wolf_type, ALPHA_WOLF_SCALE);
		this.init();
	}
	
	public AlphaWolf(VertexBufferObjectManager vbom){
		this(0, 0, vbom);
		this.setType(alpha_wolf_type);
	}
	
	public AlphaWolf(ColorType type, VertexBufferObjectManager vbom){
		this(vbom);
		this.setUserData(type);
		this.setType(type);
	}
}
