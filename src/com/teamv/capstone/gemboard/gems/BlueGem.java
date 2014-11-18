package com.teamv.capstone.gemboard.gems;

import com.teamv.capstone.ResourcesManager;

public class BlueGem extends Gem{

	public BlueGem(int col, int row) {
		super(col, row);
		
		setSprite(ResourcesManager.getInstance().blue_gem);
	}

	public String toString(){
		return "Blue";
	}
}
