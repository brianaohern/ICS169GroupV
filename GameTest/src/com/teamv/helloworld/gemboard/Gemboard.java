package com.teamv.helloworld.gemboard;

import com.teamv.helloworld.BaseScene;

public class Gemboard {
	
	// array of gems
	private Gem[][] grid;
	
	private int cols = 6;
	private int rows = 5;
	
	public Gemboard(){
		grid = new Gem[cols][rows];
		
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				grid[x][y] = new Gem(x, y);
			}
		}
		
//		for(int x = 0; x < cols; x++){
//			for(int y = 0; y < rows; y++){
//				grid[x][y].add(new Gem(x, y));
//			}
//		}
	}
	
	public void attach(BaseScene gameScene){
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				gameScene.attachChild(grid[x][y].gemSprite);
			}
		}
	}
}
