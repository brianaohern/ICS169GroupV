package com.teamv.helloworld.gemboard;

import org.andengine.entity.sprite.Sprite;

import com.teamv.helloworld.ResourcesManager;

public class Gem {

	//color
	//red, orange, yellow, green, blue, purple
	//0		1		2		3		4		5
	
	public Sprite gemSprite;
	
	int startingY = 960;
	
	public Gem(int col, int row){
		gemSprite = new Sprite(col * 180, startingY + row * 180, ResourcesManager.getInstance().game_region, ResourcesManager.getInstance().vbom);
		gemSprite.setWidth(180);
		gemSprite.setHeight(180);
	}
}
