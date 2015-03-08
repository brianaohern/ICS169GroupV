package com.teamv.capstone.game.tutorial;

import java.util.ArrayList;

import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.teamv.capstone.game.Battleground;
import com.teamv.capstone.gemboard.Gem;
import com.teamv.capstone.gemboard.Gemboard;
import com.teamv.capstone.gemboard.gems.BlueGem;
import com.teamv.capstone.gemboard.gems.Bomb;
import com.teamv.capstone.gemboard.gems.GreenGem;
import com.teamv.capstone.gemboard.gems.Potion;
import com.teamv.capstone.gemboard.gems.RedGem;
import com.teamv.capstone.gemboard.gems.YellowGem;
import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.scenes.BaseScene;

public class TutorialGemboard extends Gemboard{
	
	// BAD NAMING SO IT'S EASIER TO HARDCODE
	private final int G = 1; // GREEN
	private final int Y = 2; // YELLOW
	private final int R = 3; // RED
	private final int B = 4; // BLUE
	private final int BB = 5;// BOMB
	private final int H = 6; // HEAL
	
	private int[] queue1 = {Y,R,G,Y,Y,B,R};
	private int[] queue2 = {Y,G,G,G,B,B};
	private int[] queue3 = {G,B,G,R,G,B,Y,B,Y,B,H,Y,R,G,G};
	
	private static ArrayList<Integer> queue;
	
	private int[][] tutorial1 = {
			{G,Y,R,B,Y},
			{R,G,Y,G},
			{B,R,G,R,Y},
			{Y,Y,G,B},
			{G,R,B,R,G},
			{B,G,B,Y},
			{B,G,R,G,Y}
	};
	
	private int[][] tutorial2 = {
			{G,Y,R,B,Y},
			{R,G,Y,G},
			{B,R,G,R,Y},
			{Y,Y,R,B},
			{R,R,B,R,G},
			{B,G,B,Y},
			{B,G,R,G,Y}
	};
	
	private int[][] tutorial3 = {
			{G,Y,R,B,R},
			{R,G,Y,Y},
			{Y,B,R,BB,G},
			{Y,G,Y,Y},
			{B,G,R,R,G},
			{R,B,G,Y},
			{B,G,R,G,Y}
	};
	
	public TutorialGemboard(BaseScene gameScene, PhysicsWorld physicsWorld, Battleground battleground) {
		super(gameScene, physicsWorld, battleground);
		queue = new ArrayList<Integer>();
		loadTutorial(1);
	}
	
	public void loadTutorial(int tutorial){
		switch(tutorial){
		case 1:
			loadBoard(tutorial1);
			loadQueue(queue1);
			break;
		case 2:
			loadBoard(tutorial2);
			loadQueue(queue2);
			break;
		case 3:
			loadBoard(tutorial3);
			loadQueue(queue3);
			break;
		default:
			ResourcesManager.getInstance().activity.gameToast("TutorialGemboard.loadTutorial.default");	
		}
	}
	
	private void loadBoard(int[][] colors){
		clearBoard();
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				if(x%2 != 0 && y == rows - 1){
					break;
				}
				grid[x][y] = customGem(x, y, colors[x][y]);
				attachGem(grid[x][y]);
			}
		}
	}
	
	private void loadQueue(int[] colors){
		queue.clear();
		for(int i = 0; i < colors.length-1; i++){
			queue.add(colors[i]);
		}
	}
	
	protected void dropGem(Gem gem){
		int col = gem.getCol();
		
		for(int i = gem.getRow(); i > 0; i--){
			grid[col][i] = grid[col][i - 1];
			grid[col][i].drop();
		}
		
		if(queue.isEmpty()){
			grid[col][0] = randomGem(col, 0);
		}
		else{
			grid[col][0] = customGem(col, 0, queue.get(0));
			queue.remove(0);
		}
		attachGem(grid[col][0]);
		
		detachGem(gem);
		gem = null;
	}
	
	private static void clearBoard(){
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
		case B:
			return new BlueGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case G:
			return new GreenGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case Y:
			return new YellowGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case R:
			return new RedGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case BB:
			return new Bomb(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case H:
			return new Potion(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		}
		return new RedGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
	}
	

}
