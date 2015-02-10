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
	//final static String WOLF_ELEMENT_TYPE = "Green";

	public Wolf(float x, float y, VertexBufferObjectManager vbom) {
		super(x, y, ResourcesManager.getInstance().wolf, vbom);
	}
	
	public Wolf(VertexBufferObjectManager vbom){
		this(0, 0, vbom);
	}
	
	public Wolf(ColorType type, VertexBufferObjectManager vbom){
		super(type, ResourcesManager.getInstance().wolf, vbom);
		// only set type if user overrides default type
		wolf_type = type;
		this.setType();
	}
	
	public void init(){
		super.init();
		this.setScale(0.75f);
		this.setup(WOLF_HEALTH, WOLF_ATTACK, WOLF_START_TURN_COUNT, wolf_type, WOLF_SCALE);
	}
	
}
