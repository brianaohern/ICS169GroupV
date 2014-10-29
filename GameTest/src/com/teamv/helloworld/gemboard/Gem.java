package com.teamv.helloworld.gemboard;

import java.util.Random;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.teamv.helloworld.ResourcesManager;

public class Gem {

	//color
	//red, orange, yellow, green, blue, purple
	//0		1		2		3		4		5
	
	public Sprite gemSprite;
	
	private final int RADIUS = 150;
	private int startingY = 1920/2 + RADIUS;
	private int buffer = 3;
	
	private int col, row;
	
	Random random;
	
	public Gem(int col, int row){
		random = new Random();
		this.col = col;
		this.row = row;
		
		gemSprite = new Sprite(col * (RADIUS + buffer) + buffer, startingY + row * (RADIUS + buffer) + buffer,
				ResourcesManager.getInstance().game_region, ResourcesManager.getInstance().vbom){
			
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
		    {
		        if (pSceneTouchEvent.isActionDown())
		        {
		        	switch(random.nextInt(5)){
		        	case 1:
		        		gemSprite.setColor(Color.GREEN);
		        		break;
		        	case 2:
		        		gemSprite.setColor(Color.BLACK);
		        		break;
		        	case 3:
		        		gemSprite.setColor(Color.RED);
		        		break;
		        	case 4:
		        		gemSprite.setColor(Color.BLUE);
		        		break;
		        	default:
		        		gemSprite.setColor(Color.PINK);
		        	}
		        	
		        }
		        
		        if (pSceneTouchEvent.isActionUp())
		        {
		        	gemSprite.setScale(1.0f);
		        }
		        return true;
		    };
		};
		
		gemSprite.setWidth(RADIUS);
		gemSprite.setHeight(RADIUS);
		
		//if odd
		if(col%2 != 0){
			gemSprite.setY(gemSprite.getY() + RADIUS/2);
		}
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
}
