package com.teamv.capstone.gemboard.gems;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.teamv.capstone.BaseScene;
import com.teamv.capstone.ResourcesManager;
import com.teamv.capstone.SceneManager;
import com.teamv.capstone.gemboard.Gemboard;
import com.teamv.capstone.gemboard.Pointf;

public abstract class Gem{

	public Sprite gemSprite;
	
	protected final int RADIUS = 150;
	protected int startingY = 1920/2 + RADIUS;
	protected int buffer = 3;
	
	protected int col, row;
	protected Pointf start, end;
	
	public Gem(int col, int row){
		
		this.col = col;
		this.row = row;
		
		start = Gemboard.getStartPoint();
		end = Gemboard.getEndPoint();
	}
	
	public void update(int col, int row){
		this.col = col;
		this.row = row;
		
		update();
	}
	
	public void update(){
		gemSprite.setX(col * (RADIUS + buffer) + buffer);
		gemSprite.setY(startingY + row * (RADIUS + buffer) + buffer);
		
		// if odd, stagger location by half of radius
		if(col%2 != 0){
			gemSprite.setY(gemSprite.getY() + RADIUS/2);
		}
	}
	
	public void drop(){
		row = row + 1;
		
		update();
	}
	
	// each gem should set their sprite in this method
	// this is important as setting sprite also includes setting their user interaction
	protected void setSprite(ITextureRegion sprite){
		gemSprite = new Sprite(col * (RADIUS + buffer) + buffer, startingY + row * (RADIUS + buffer) + buffer,
				sprite, ResourcesManager.getInstance().vbom){
			
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
		
		////////////////////////////////
		// SIZE AND STUFF
		////////////////////////////////
		
		// gem size, which is constant for now
		gemSprite.setWidth(RADIUS);
		gemSprite.setHeight(RADIUS);
				
		// if odd, stagger location by half of radius
		if(col%2 != 0){
			gemSprite.setY(gemSprite.getY() + RADIUS/2);
		}
	}
	
	// what happens when gem dies
	public void destroyGem(){
		gemSprite.detachSelf();
	}
	
	// determines if the gems are the same
	protected abstract boolean sameColor(Gem gem);
	
	protected void drawLine(VertexBufferObjectManager vbom){
		// distance formula
    	float tempX = (start.getX() - end.getX()) * (start.getX() - end.getX());
    	float tempY = (start.getY() - end.getY()) * (start.getY() - end.getY());
    	float distance = (float) Math.sqrt(tempX + tempY);
    	
    	// use radius to detect range!
    	if(distance <= RADIUS*1.5){
    		
    		//System.out.println("acceptable distance: " + distance);
    		
    		// gem list is not empty
    		// gem is matching colors
    		// gem is not already in the chain
    		if( !Gemboard.connectedGems.isEmpty() &&
    			this.sameColor(Gemboard.connectedGems.get(Gemboard.connectedGems.size() - 1)) &&
    			!Gemboard.connectedGems.contains(this)){
    			
	        	Line line = new Line(start.getX(), start.getY(), end.getX(), end.getY(), vbom);

	        	line.setLineWidth(5);
	        	line.setColor(Color.BLACK);
	            SceneManager.getInstance().getCurrentScene().attachChild(line);
	            Gemboard.lines.add(line);
	            
	            start.setX(gemSprite.getX() + RADIUS/2);
	        	start.setY(gemSprite.getY() + RADIUS/2);
	        	
	        	Gemboard.connectedGems.add(this);
        	}
    	}
	}
	
	public void attachToScene(BaseScene gameScene){
		gameScene.registerTouchArea(gemSprite);
		gameScene.attachChild(gemSprite);
	}
	
	///////////////////////////////////////////
	// SETTERS AND GETTERS
	///////////////////////////////////////////
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
}
