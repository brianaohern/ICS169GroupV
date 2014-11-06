package com.teamv.capstone.gemboard;

// utility purposes
public class Pointf {
	
	public boolean isActive; // active means currently being chained
	float x;
	float y;
	
	public Pointf(){}
	
	public Pointf(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	//////////////////////////////////////
	// GETTERS AND SETTERS
	//////////////////////////////////////
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public void set(float x, float y){
		this.x = x;
		this.y = y;
	}
}
