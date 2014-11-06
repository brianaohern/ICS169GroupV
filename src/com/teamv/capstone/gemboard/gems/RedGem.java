package com.teamv.capstone.gemboard.gems;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.teamv.capstone.ResourcesManager;
import com.teamv.capstone.gemboard.Gemboard;

public class RedGem extends Gem{
	
	public RedGem(int col, int row) {
		super(col, row);
		
		gemSprite.setColor(Color.RED);
	}

	protected void setSprite(){
		gemSprite = new Sprite(col * (RADIUS + buffer) + buffer, startingY + row * (RADIUS + buffer) + buffer,
				ResourcesManager.getInstance().game_region, ResourcesManager.getInstance().vbom){
			
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
		    {
				// when finger touches gem
		        if (pSceneTouchEvent.isActionDown())
		        {
		        	start.setX(gemSprite.getX() + RADIUS/2);
		        	start.setY(gemSprite.getY() + RADIUS/2);
		        }
		        if (pSceneTouchEvent.isActionMove()){
		        	end.setX(gemSprite.getX() + RADIUS/2);
		        	end.setY(gemSprite.getY() + RADIUS/2);
		        	
		        	drawLine(this.getVertexBufferObjectManager());
		        }
		        // when finger releases gem
		        if (pSceneTouchEvent.isActionUp())
		        {
		        	drawLine(this.getVertexBufferObjectManager());
		        	Gemboard.connectedGems.clear();
		        }
		        return true;
		    };
		};
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
	}
	
	protected boolean sameColor(Gem gem) {
		if(gem instanceof RedGem){
			return true;
		}
		return false;
	}
}
