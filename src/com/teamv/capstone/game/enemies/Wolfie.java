package com.teamv.capstone.game.enemies;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.game.Enemy;
import com.teamv.capstone.managers.ResourcesManager;

// Wolfie is a temporary enenmy
public class Wolfie extends Enemy{
	
	final static int 		WOLFIE_HEALTH = 10;
	final static int		WOLFIE_ATTACK = 3;
	final static int 		WOLFIE_START_TURN_COUNT = 3;
	final static float		WOLFIE_SCALE = 1f;
	private ColorType 		wolfie_type = ColorType.RED;

	public Wolfie(float x, float y, VertexBufferObjectManager vbom) {
		super(x, y, ResourcesManager.getInstance().wolfie, vbom);
		this.setup(WOLFIE_HEALTH, WOLFIE_ATTACK, WOLFIE_START_TURN_COUNT, wolfie_type, WOLFIE_SCALE);
		this.init();
	}
	
	public Wolfie(VertexBufferObjectManager vbom){
		this(0, 0, vbom);
		this.setType(wolfie_type);
	}
	
	public Wolfie(ColorType type, VertexBufferObjectManager vbom){
		this(vbom);
		this.setUserData(type);
		this.setType(type);
	}
}
