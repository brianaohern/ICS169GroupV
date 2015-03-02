package com.teamv.capstone.game.enemies;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.game.Enemy;
import com.teamv.capstone.managers.ResourcesManager;

public class Zombie extends Enemy{
	
	final static int 		ZOMBIE_HEALTH = 20;
	final static int		ZOMBIE_ATTACK = 1;
	final static int 		ZOMBIE_START_TURN_COUNT = 1;
	final static float		ZOMBIE_SCALE = 3f;
	private ColorType 		zombie_type = ColorType.GREEN;

	public Zombie(float x, float y, VertexBufferObjectManager vbom) {
		super(x, y, ResourcesManager.getInstance().zombie, vbom);
		this.setup(ZOMBIE_HEALTH, ZOMBIE_ATTACK, ZOMBIE_START_TURN_COUNT, zombie_type, ZOMBIE_SCALE);
		this.init();
	}
	
	public Zombie(VertexBufferObjectManager vbom){
		this(0, 0, vbom);
		this.setType(zombie_type);
	}
	
	public Zombie(ColorType type, VertexBufferObjectManager vbom){
		this(vbom);
		this.setUserData(type);
		this.setType(type);
	}
}
