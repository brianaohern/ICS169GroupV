package com.teamv.helloworld.gemboard;

import com.teamv.helloworld.BaseScene;

public class Gemboard {
	
	// array of gems
	private Gem[][] grid;
	
	private int cols = 7;
	private int rows = 5;
	
	public Gemboard(){
		grid = new Gem[cols][rows];
		
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				// if odd and last row, don't add gem
				if(x%2 != 0 && y == rows - 1){
					break;
				}
				// else add gem
				grid[x][y] = new Gem(x, y);
			}
		}
	}
	
	public void attachToScene(BaseScene gameScene){
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				// DON'T REFER TO EMPTY SPACE
				if(x%2 != 0 && y == rows - 1){
					break;
				}
				// ELSE attach the entity
				gameScene.attachChild(grid[x][y].gemSprite);
			}
		}
	}
}
