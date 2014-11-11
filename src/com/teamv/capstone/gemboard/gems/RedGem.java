package com.teamv.capstone.gemboard.gems;

import com.teamv.capstone.ResourcesManager;

public class RedGem extends Gem{
	
	public RedGem(int col, int row) {
		super(col, row);
		
		setSprite(ResourcesManager.getInstance().red_gem);
	}

	protected boolean sameColor(Gem gem) {
		if(gem instanceof RedGem){
			return true;
		}
		return false;
	}
	
	public String toString(){
		return "Red";
	}
}
