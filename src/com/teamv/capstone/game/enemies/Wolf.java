package com.teamv.capstone.game.enemies;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.game.EnemySprite;
import com.teamv.capstone.managers.ResourcesManager;

public class Wolf extends EnemySprite{
	
	final static int WOLF_HEALTH = 10;
	final static int WOLF_ATTACK = 3;
	final static int WOLF_START_TURN_COUNT = 3;
	//final static String WOLF_ELEMENT_TYPE = "Green";

	public Wolf(float x, float y, VertexBufferObjectManager vbom) {
		super(x, y, ResourcesManager.getInstance().wolf, vbom);
		setup(ColorType.GREEN);
	}
	
	public Wolf(VertexBufferObjectManager vbom){
		super(ResourcesManager.getInstance().wolf, vbom);
		setup(ColorType.GREEN);
	}
	
	public Wolf(ColorType type, VertexBufferObjectManager vbom){
		super(type, ResourcesManager.getInstance().wolf, vbom);
		setup(type);
		setType();
	}
	
	public void init(){
		super.init();
	}
	
	public void setup(ColorType type){
		this.setScale(0.75f);
		this.startHealth = WOLF_HEALTH;
		this.attack = WOLF_ATTACK;
		setStartHealth(WOLF_HEALTH);
		setAttack(WOLF_ATTACK);
		setStartTurnCount(WOLF_START_TURN_COUNT);
		setUserData(type);
	}
}
