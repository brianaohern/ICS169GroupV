package com.teamv.capstone.gemboard.gems;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.teamv.capstone.ResourcesManager;
import com.teamv.capstone.gemboard.Gemboard;

public class GreenGem extends Gem{

	public GreenGem(int col, int row) {
		super(col, row);
		
		//gemSprite.setColor(Color.GREEN);
	}

	protected void setSprite(){
		gemSprite = new Sprite(col * (RADIUS + buffer) + buffer, startingY + row * (RADIUS + buffer) + buffer,
				ResourcesManager.getInstance().green_gem, ResourcesManager.getInstance().vbom){
			
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
		    {
				// when finger touches gem
		        if (pSceneTouchEvent.isActionDown())
		        {
		        	start.set(gemSprite.getX() + RADIUS/2, gemSprite.getY() + RADIUS/2);
		        	
		        	int c = (int) ((gemSprite.getX() - buffer) / (RADIUS + buffer));
		        	int r = (int) ((gemSprite.getY() - buffer - startingY) / (RADIUS + buffer));
		        	Gemboard.startList(c, r);
		        }
		        if (pSceneTouchEvent.isActionMove()){
		        	end.set(gemSprite.getX() + RADIUS/2, gemSprite.getY() + RADIUS/2);
		        	
		        	drawLine(this.getVertexBufferObjectManager());
		        }
		        // when finger releases gem
		        if (pSceneTouchEvent.isActionUp())
		        {
		        	drawLine(this.getVertexBufferObjectManager());
		        	Gemboard.executeGems();
		        }
		        return true;
		    };
		};
	}

	@Override
	public void destroyGem() {
		gemSprite.detachSelf();
	}
	
	protected boolean sameColor(Gem gem) {
		if(gem instanceof GreenGem){
			return true;
		}
		return false;
	}
}
