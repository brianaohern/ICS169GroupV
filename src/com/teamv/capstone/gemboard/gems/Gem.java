package com.teamv.capstone.gemboard.gems;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.teamv.capstone.SceneManager;
import com.teamv.capstone.gemboard.Gemboard;
import com.teamv.capstone.gemboard.Pointf;

public abstract class Gem extends Sprite{

	protected Pointf start		= Gemboard.getStartPoint();
	protected Pointf end		= Gemboard.getEndPoint();
	private Body body;
	private boolean isDropping = false;
	private float targetY;
	
	public Gem(float pX, float pY, ITextureRegion region, VertexBufferObjectManager vbo, PhysicsWorld physicsWorld){
		super(pX, pY, region, vbo);
		
		// gem size
		setWidth(Gemboard.RADIUS);
		setHeight(Gemboard.RADIUS);
		
		targetY = pY;
		
		createPhysics(physicsWorld);
	}
	
	private void createPhysics(PhysicsWorld physicsWorld)
	{        
	    body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.KinematicBody, PhysicsFactory.createFixtureDef(0.5f, 0, 0.5f));
	    body.setFixedRotation(true);
	    
	    physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
	    {
	        @Override
	        public void onUpdate(float pSecondsElapsed)
	        {
	            super.onUpdate(pSecondsElapsed);
	            
	            if(mY >= targetY){
            		isDropping = false;
            	}
	            if(isDropping){
	            	body.setLinearVelocity(new Vector2(0, 15f));
	            } else{
	            	body.setLinearVelocity(new Vector2(0, 0));
	            }
	        }
	    });
	}
	
	/*
	 *	needed to align the gems since gravity doesn't pull perfectly 
	 */
//	private void alignGem(){
//		
//	}

	// drop the gem, so row++
	public void drop(){
		this.setY(mY + Gemboard.RADIUS);
		targetY = getY();
		isDropping = true;
	}
	
	// what happens when gem dies, cleans up
	public void onDie(){
		this.detachSelf();
		this.dispose();
	}
	
	protected void drawLine(VertexBufferObjectManager vbom){
		
    	/* location is adjacent to gem, gem list is not empty
		 * gem is matching colors, gem is not already in the chain */
		if( isAdjacent() &&
			!Gemboard.connectedGems.isEmpty() &&
			this.sameColor(Gemboard.connectedGems.get(Gemboard.connectedGems.size() - 1)) &&
			!Gemboard.connectedGems.contains(this)){
			
        	Line line = new Line(start.getX(), start.getY(), end.getX(), end.getY(), vbom);

        	line.setLineWidth(5);
        	line.setColor(Color.BLACK);
            SceneManager.getInstance().getCurrentScene().attachChild(line);
            Gemboard.lines.add(line);
            
            // set point to middle of gem
            start.setX(getX() + Gemboard.RADIUS/2);
        	start.setY(getY() + Gemboard.RADIUS/2);
        	
        	Gemboard.connectedGems.add(this);
    	}
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
    {
		if(!isDropping)
		{
			// when finger touches gem
		    if (pSceneTouchEvent.isActionDown())
		    {
		    	start.set(mX + Gemboard.RADIUS/2, mY + Gemboard.RADIUS/2);
		    	Gemboard.connectedGems.add(this);
	        	System.out.println("Added " + this.getUserData());
	        	System.out.println(start.getX() + " " + start.getY());
		    }
		    if (pSceneTouchEvent.isActionMove())
		    {
		    	end.set(mX + Gemboard.RADIUS/2, mY + Gemboard.RADIUS/2);
	        	System.out.println(end.getX() + " " + end.getY());
		    	drawLine(this.getVertexBufferObjectManager());
		    }
		    // when finger releases gem
		    if (pSceneTouchEvent.isActionUp())
		    {
		    	drawLine(this.getVertexBufferObjectManager());
		    	Gemboard.executeGems();
		    }
		}
        return true;
    };
	
	// determines if the gems are the same
	protected boolean sameColor(Gem gem) {
		return(this.getUserData().equals(gem.getUserData()));
	}
	
	// used for drawing lines; the basic math
	private boolean isAdjacent(float x1, float x2, float y1, float y2){
		// distance formula
		float x = x1 - x2;
		float y = y1 - y2;
		float distance = (float) Math.sqrt(x*x + y*y);
    	
    	return distance < Gemboard.RADIUS * 1.5;		
	}
	
	// used for drawing lines, can be combined with the other isAdjacents
	private boolean isAdjacent(){
		return isAdjacent(start.getX(), end.getX(), start.getY(), end.getY());
	}
	
	///////////////////////////////////////////
	// SETTERS AND GETTERS
	///////////////////////////////////////////
	
//	float x = col * RADIUS;
//	float y = STARTY + row * (RADIUS) + BUFFER;
	public int getRow(){
		return (int) ((mY - Gemboard.STARTY) / (Gemboard.RADIUS));
	}
	
	public int getCol(){
		return (int) (mX / Gemboard.RADIUS);
	}
}
