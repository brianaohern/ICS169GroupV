package com.teamv.capstone.gemboard;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import com.teamv.capstone.BaseScene;
import com.teamv.capstone.SceneManager;
import com.teamv.capstone.gemboard.gems.*;

public class Gemboard implements IOnSceneTouchListener{
	
	private static Pointf start, end;
	
	// array of gems
	private static Gem[][] grid;
	private static BaseScene gameScene;
	// array of connected gems
	public static ArrayList<Gem> connectedGems;

	public static ArrayList<Line> lines;
	
	private static int cols = 7;
	private static int rows = 5;
	
	private static Random random;
	
	public Gemboard(BaseScene gameScene){
		Gemboard.gameScene = gameScene;
		grid = new Gem[cols][rows];
		random = new Random();
		start = new Pointf(0, 0);
		end = new Pointf(0, 0);
		
		connectedGems = new ArrayList<Gem>();
		lines = new ArrayList<Line>();
		
		// while the gemboard has no possible moves, reset the board
		while(hasNoMoreMoves())
			resetBoard();
	}
	
	public static void resetBoard(){
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				// if odd and last row, don't add gem
				if(x%2 != 0 && y == rows - 1){
					break;
				}
				// clean up if there's anything else in there
				else if(grid[x][y] != null){
					grid[x][y].onDie(gameScene);
				}
				grid[x][y] = randomGem(x, y);
				grid[x][y].attachToScene(gameScene);
			}
		}
	}
	
	public static Pointf getStartPoint(){
		return start;
	}
	
	public static Pointf getEndPoint(){
		return end;
	}

	public static void startList(int c, int r) {
		connectedGems.add(grid[c][r]);
	}

	public static void executeGems() {
		
		if(connectedGems.size() >= 3){
			for(Gem gem : connectedGems){
				dropGem(gem);
			}
		}
		for(Line line : lines){
			line.detachSelf();
		}
		
		connectedGems.clear();
		//printAll();
		
		if(hasNoMoreMoves()){
			System.out.println("NO MORE MOVES");
			resetBoard();
		}
	}
	
	// drops every gem above the parameter gem, then drop a new gem
	private static void dropGem(Gem gem){
		// column of gem
		int col = gem.getCol();
		
		for(int i = gem.getRow(); i > 0; i--){
			grid[col][i] = grid[col][i - 1];
	
			grid[col][i].drop();
		}
		
		grid[col][0] = randomGem(col, 0);
		//grid[col][0] = new RandGem(col, 0);
		grid[col][0].attachToScene(SceneManager.getInstance().getCurrentScene());
		
		gem.onDie(gameScene);
		gem = null;
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.isActionUp()){
			executeGems();
		}
		return false;
	}
	
	// return random gem
	private static Gem randomGem(int col, int row){
		// add gems and whatnot in here
		switch(random.nextInt(4)){
		case 0:
			return new BlueGem(col, row);
		case 1:
			return new GreenGem(col, row);
		case 2:
			return new YellowGem(col, row);
		case 3:
			return new RedGem(col, row);
		}
		// in case something goes wrong, return red
		return new RedGem(col, row);
	}
	
	private static boolean hasNoMoreMoves(){
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				if(grid[x][y] != null){
					int count = 0;
					String gemType = grid[x][y].toString();
					
					count += checkGem(x, y+1, gemType);
					count += checkGem(x, y-1, gemType);
					count += checkGem(x+1, y, gemType);
					count += checkGem(x-1, y, gemType);
					
					if(x%2 == 0){
						count += checkGem(x-1, y-1, gemType);
						count += checkGem(x+1, y-1, gemType);
					} else{
						count += checkGem(x-1, y+1, gemType);
						count += checkGem(x+1, y+1, gemType);
					}
					
					if(count >= 2){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private static int checkGem(int c, int r, String type){
		if(		(c < 0 || r < 0) ||
				(c >= cols || r >= rows)){
			return 0;
		}
		else if(grid[c][r] != null && grid[c][r].toString().equals(type)){
			return 1;
		}
		return 0;
	}
	
	//////////////////////////////////////////////////
	// DEBUG AND INFO methods
	//////////////////////////////////////////////////
	
	@SuppressWarnings("unused")
	private static void printAll(){
		printBoardSize();
		printBoard();
	}
	
	private static void printBoard() {
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				if(x%2 != 0 && y == rows - 1){
					break;
				}
				if(grid[x][y] != null) {
					System.out.println("[" + x + "][" + y + "] : " + grid[x][y].toString());
				}
				else {
					System.out.println("[" + x + "][" + y + "] : NULL");
				}
			}
		}
	}
	
	private static void printBoardSize(){
		System.out.println("GEMS LEFT IN GEMBOARD: " + gemboardSize());
	}
	
	private static int gemboardSize(){
		int gems = 0;
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				// DON'T REFER TO EMPTY SPACE
				if(grid[x][y] != null){
					gems++;
				}
			}
		}
		return gems;
	}
}
