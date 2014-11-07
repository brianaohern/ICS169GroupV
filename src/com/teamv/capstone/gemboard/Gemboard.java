package com.teamv.capstone.gemboard;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.primitive.Line;

import com.teamv.capstone.BaseScene;
import com.teamv.capstone.gemboard.gems.*;

public class Gemboard{
	
	static Pointf start, end;
	
	// array of gems
	private static Gem[][] grid;
	// array of connected gems
	public static ArrayList<Gem> connectedGems;

	public static ArrayList<Line> lines;
	
	private static int cols = 7;
	private static int rows = 5;
	
	private Random random;
	
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
					gameScene.registerTouchArea(grid[x][y].gemSprite);
					gameScene.attachChild(grid[x][y].gemSprite);
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
		for(Gem gem : connectedGems){
			gem.destroyGem();
			grid[gem.getCol()][gem.getRow()] = null;
			gem = null;
		}
		for(Line line : lines){
			line.detachSelf();
		}
		connectedGems.clear();
		
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
