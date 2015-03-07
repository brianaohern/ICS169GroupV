package com.teamv.capstone.game.tutorial;

import java.util.ArrayList;

import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.teamv.capstone.game.Battleground;
import com.teamv.capstone.gemboard.Gem;
import com.teamv.capstone.gemboard.Gemboard;
import com.teamv.capstone.gemboard.gems.BlueGem;
import com.teamv.capstone.gemboard.gems.GreenGem;
import com.teamv.capstone.gemboard.gems.RedGem;
import com.teamv.capstone.gemboard.gems.YellowGem;
import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.scenes.BaseScene;

public class TutorialGemboard extends Gemboard{

	private final int GREEN = 1;
	private final int YELLOW = 2;
	private final int RED = 3;
	private final int BLUE = 4;
	
	private ArrayList<Integer> queue;
	
	private int[][] tutorial1 = {
			{1,2,3,4,2},
			{3,1,2,1},
			{4,3,1,3,2},
			{2,2,1,4},
			{1,3,4,3,1},
			{4,1,4,2},
			{4,1,3,1,2}
	};
//	private int[][] tutorial2 = {1};
//	private int[][] tutorial3 = {1};
	
	public TutorialGemboard(BaseScene gameScene, PhysicsWorld physicsWorld, Battleground battleground) {
		super(gameScene, physicsWorld, battleground);
		queue = new ArrayList<Integer>();
		loadTutorial1();
	}
	
	// HARDCODED GEMBOARD
	public void loadTutorial1(){
		resetBoard();
		queue.clear();
		
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				if(x%2 != 0 && y == rows - 1){
					break;
				}
				grid[x][y] = customGem(x, y, tutorial1[x][y]);
				attachGem(grid[x][y]);
			}
		}
	}
	
	public void loadTutorial2(){
		resetBoard();
		queue.clear();
	}
	
	public void loadTutorial3(){
		resetBoard();
		queue.clear();
	}
	
	protected static void dropGem(Gem gem){
		int col = gem.getCol();
		
		for(int i = gem.getRow(); i > 0; i--){
			grid[col][i] = grid[col][i - 1];
			grid[col][i].drop();
		}
		
		grid[col][0] = randomGem(col, 0); // NEED TO MODIFY
		
		attachGem(grid[col][0]);
		
		detachGem(gem);
		gem = null;
	}
	
	private static void resetBoard(){
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				// if odd and last row, don't add gem
				if(x%2 != 0 && y == rows - 1){
					break;
				}
				// clean up if there's anything else in there
				else if(grid[x][y] != null){
					detachGem(grid[x][y]);
				}
			}
		}
	}
	
	protected Gem customGem(int col, int row, int color){
		float x = col * RADIUS;
		float y = STARTY + row * (RADIUS);
		if(col%2 != 0)
			y += RADIUS/2;
		
		switch(color){
		case BLUE:
			return new BlueGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case GREEN:
			return new GreenGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case YELLOW:
			return new YellowGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case RED:
			return new RedGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		}
		return new RedGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
	}
}
