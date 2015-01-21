package com.teamv.capstone.game.enemies;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.ResourcesManager;
import com.teamv.capstone.game.Enemy;

public class Wolf extends Enemy{
	
	final static int WOLF_HEALTH = 10;
	final static int WOLF_ATTACK = 1;
	final static int WOLF_START_TURN_COUNT = 3;

	public Wolf(float x, float y, VertexBufferObjectManager vbom) {
		super(x, y, ResourcesManager.getInstance().wolf, vbom);
		this.setScale(0.75f);
		setStartHealth(WOLF_HEALTH);
		setAttack(WOLF_ATTACK);
		setStartTurnCount(WOLF_START_TURN_COUNT);
	}
	
	public Wolf(VertexBufferObjectManager vbom){
		super(ResourcesManager.getInstance().wolf, vbom);
		this.setScale(0.75f);
		setStartHealth(WOLF_HEALTH);
		setAttack(WOLF_ATTACK);
		setStartTurnCount(WOLF_START_TURN_COUNT);
	}

}
