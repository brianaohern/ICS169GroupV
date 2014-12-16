package com.teamv.capstone.utility;

// utility purposes
public class Pointf {
	
	public boolean isActive; // active means currently being chained
	public float x;
	public float y;
	
	public Pointf(){}
	
	public Pointf(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	//////////////////////////////////////
	// GETTERS AND SETTERS
	//////////////////////////////////////
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
