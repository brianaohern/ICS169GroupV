package com.teamv.capstone.gemboard.gems;

import com.teamv.capstone.ResourcesManager;

// RANDOM GEM THAT GENERATES RANDOM IDENTIFIER
// LOOKS LIKE A RED GEM THOUGH
public class RandGem extends Gem{

	public RandGem(int col, int row) {
		super(col, row);
		
		setSprite(ResourcesManager.getInstance().red_gem);
	}

	public String toString(){
		return "RANDOMRED" + super.toString();
	}
}
