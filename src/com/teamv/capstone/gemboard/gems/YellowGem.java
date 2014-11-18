package com.teamv.capstone.gemboard.gems;

import com.teamv.capstone.ResourcesManager;

public class YellowGem extends Gem{

	public YellowGem(int col, int row) {
		super(col, row);
		
		setSprite(ResourcesManager.getInstance().yellow_gem);
	}

	public String toString(){
		return "Yellow";
	}
}
