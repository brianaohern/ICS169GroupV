package com.teamv.capstone.game.enemies;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.game.Enemy;
import com.teamv.capstone.managers.ResourcesManager;

public class Imp extends Enemy{
	
	final static int 		IMP_HEALTH = 50;
	final static int		IMP_ATTACK = 25;
	final static int 		IMP_START_TURN_COUNT = 4;
	final static float		IMP_SCALE = 3f;
	private ColorType 		imp_type = ColorType.YELLOW;

	public Imp(float x, float y, VertexBufferObjectManager vbom) {
		super(x, y, ResourcesManager.getInstance().imp, vbom);
		this.setup(IMP_HEALTH, IMP_ATTACK, IMP_START_TURN_COUNT, imp_type, IMP_SCALE);
	}
	
	public Imp(VertexBufferObjectManager vbom){
		this(0, 0, vbom);
		this.setType(imp_type);
	}
	
	public Imp(ColorType type, VertexBufferObjectManager vbom){
		this(vbom);
		this.setUserData(type);
		this.setType(type);
	}
}
