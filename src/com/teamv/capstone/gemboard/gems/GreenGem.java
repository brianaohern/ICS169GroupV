package com.teamv.capstone.gemboard.gems;

import com.teamv.capstone.ResourcesManager;

public class GreenGem extends Gem{

	public GreenGem(int col, int row) {
		super(col, row);
		
		setSprite(ResourcesManager.getInstance().green_gem);
	}
	
	public String toString(){
		return "Green";
	}
}
