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
	
	static Pointf start, end;
	
	// array of gems
	private static Gem[][] grid;
	// array of connected gems
	public static ArrayList<Gem> connectedGems;

	public static ArrayList<Line> lines;
	
	private static int cols = 7;
	private static int rows = 5;
	
	private static Random random;
	
	public Gemboard(){
		grid = new Gem[cols][rows];
		random = new Random();
		start = new Pointf(0, 0);
		end = new Pointf(0, 0);
		
		connectedGems = new ArrayList<Gem>();
		lines = new ArrayList<Line>();
		
		resetBoard();
	}
	
	public void resetBoard(){
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				// if odd and last row, don't add gem
				if(x%2 != 0 && y == rows - 1){
					break;
				}
				// else add gem
				switch(random.nextInt(4)){
				case 0:
					grid[x][y] = new BlueGem(x, y);
					break;
				case 1:
					grid[x][y] = new GreenGem(x, y);
					break;
				case 2:
					grid[x][y] = new RedGem(x, y);
					break;
				case 3:
					grid[x][y] = new YellowGem(x, y);
					break;
				default:
					// in case something happens
					grid[x][y] = new RedGem(x, y);
				}
			}
		}
	}
	
	public void attachToScene(BaseScene gameScene){
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				// DON'T REFER TO EMPTY SPACE
				if(grid[x][y] != null){
					grid[x][y].attachToScene(gameScene);
					gameScene.setOnSceneTouchListener(this);
				}
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
		grid[col][0].attachToScene(SceneManager.getInstance().getCurrentScene());
		
		gem.onDie();
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
