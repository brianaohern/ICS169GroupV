package com.teamv.capstone.gemboard.gems;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

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
		
		// initiates gem sprite and interaction
		setSprite();
		
		// gem size, which is constant for now
		gemSprite.setWidth(RADIUS);
		gemSprite.setHeight(RADIUS);
		
		// if odd, stagger location by half of radius
		if(col%2 != 0){
			gemSprite.setY(gemSprite.getY() + RADIUS/2);
		}
	}
	
	// each gem should set their sprite in this method
	// this is important as setting sprite also includes setting their user interaction
	protected abstract void setSprite();
	
	// what happens when gem dies
	public abstract void destroyGem();
	
	// determines if the gems are the same
	protected abstract boolean sameColor(Gem gem);
	
	protected void drawLine(VertexBufferObjectManager vbom){
		// distance formula
    	float tempX = (start.getX() - end.getX()) * (start.getX() - end.getX());
    	float tempY = (start.getY() - end.getY()) * (start.getY() - end.getY());
    	float distance = (float) Math.sqrt(tempX + tempY);
    	
    	// use radius to detect range!
    	if(distance <= RADIUS*1.5){
    		
    		System.out.println("acceptable distance: " + distance);
    		
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
