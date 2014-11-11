package com.teamv.capstone.gemboard.gems;

import com.teamv.capstone.ResourcesManager;

public class YellowGem extends Gem{

	public YellowGem(int col, int row) {
		super(col, row);
		
		setSprite(ResourcesManager.getInstance().yellow_gem);
	}

	protected boolean sameColor(Gem gem) {
		if(gem instanceof YellowGem){
			return true;
		}
		return false;
	}
	
	public String toString(){
		return "Yellow";
	}
}
