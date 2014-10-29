package com.teamv.helloworld.gemboard;

import org.andengine.entity.sprite.Sprite;

import com.teamv.helloworld.ResourcesManager;

public class Gem {

	//color
	//red, orange, yellow, green, blue, purple
	//0		1		2		3		4		5
	
	public Sprite gemSprite;
	
	private final int RADIUS = 150;
	private int startingY = 1920/2 + RADIUS;
	private int buffer = 3;
	
	public Gem(int col, int row){
		gemSprite = new Sprite(col * (RADIUS + buffer) + buffer, startingY + row * (RADIUS + buffer) + buffer, ResourcesManager.getInstance().game_region, ResourcesManager.getInstance().vbom);
		gemSprite.setWidth(RADIUS);
		gemSprite.setHeight(RADIUS);
		
		//if odd
		if(col%2 != 0){
			gemSprite.setY(gemSprite.getY() + RADIUS/2);
		}
	}
}
