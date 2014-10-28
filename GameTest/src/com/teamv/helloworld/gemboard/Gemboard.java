package com.teamv.helloworld.gemboard;

import java.util.ArrayList;
import java.util.List;

import com.teamv.helloworld.BaseScene;

public class Gemboard {
	
	// array of gems
	private List<Gem>[][] grid;
	
	private int cols = 6;
	private int rows = 5;
	
	public Gemboard(){
		grid = new List[cols][rows];
		
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				grid[x][y] = new ArrayList<Gem>();
			}
		}
		
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				grid[x][y].add(new Gem(x, y));
			}
		}
	}
	
	public void attach(BaseScene gameScene){
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				for(Gem gem : grid[x][y]){
					gameScene.attachChild(gem.gemSprite);
				}
			}
		}
	}
}
