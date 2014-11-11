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
		System.out.println("GEM CHAIN SIZE: " + connectedGems.size());
		if(connectedGems.size() >= 3){
			for(Gem gem : connectedGems){
				dropGem(gem);
			}
		}
		for(Line line : lines){
			line.detachSelf();
		}
		
		connectedGems.clear();
		
		printBoard();
		
		System.out.println("GEMS LEFT IN GEMBOARD: " + gemboardSize());
	}
	
	private static void dropGem(Gem gem){
		for(int i = gem.getRow(); i > 0; i--){
			grid[gem.getCol()][i] = grid[gem.getCol()][i - 1];
			
			grid[gem.getCol()][i].drop();
		}
		
		//grid[gem.getCol()][0] = randomGem(gem.getCol(), 0);
		grid[gem.getCol()][0] = new RedGem(gem.getCol(), 0);
		grid[gem.getCol()][0].attachToScene(SceneManager.getInstance().getCurrentScene());
		
		gem.destroyGem();
		gem = null;
	}
	
	/*private static void dropGems() {
	// An array of ArrayLists holdings the coordinates of our nulls
	ArrayList<Pointf>[] nullList = new ArrayList[7];
	
	// For each gem in connected gems (since items in this list have been deleted, all spaces are null)
	for (Gem gem : connectedGems) {
		// Add a point containing column and row of deleted gem to the ArrayList of same index as deleted gem's column
		nullList[gem.getCol()].add(new Pointf(gem.getCol(), gem.getRow()));
		// Sort the positions from row of highest index to row of lowest index
		Collections.sort(nullList[gem.getCol()], new Comparator<Pointf>() {
			public int compare (Pointf point1, Pointf point2) {
				return  (int)(point2.getY() - point1.getY());
			}
		});
	}
	// for all columns of nullList
	for (int x = 0; x < nullList.length; x++) {
		// if the column contains any points
		if (!nullList[x].isEmpty()) {
			// for all rows of the gemboard
			for (int y = rows - 1; y >= 0; y--) {
				// if odd and last row, don't check
				if(x%2 != 0 && y == rows - 1) {
					break;
				}
				// the number of empty spaces below the gem
				int numEmptySpacesBelow = 0;
				// for each point in this column of nullList (remember x,y is col,row)
				for (Pointf emptySpace : nullList[x]) {
					// if the row of emptySpace is below (greater index) than row of current gem
					if (emptySpace.y > grid[x][y].getRow()) {
						// increment number of empty spaces below current gem by one
						numEmptySpacesBelow++;
					}
				}
				// Move gem down by number of empty spaces below it
				grid[x][y + numEmptySpacesBelow] = grid[x][y];
				grid[x][y].gemSprite.detachSelf();
				grid[x][y + numEmptySpacesBelow].attachToScene(SceneManager.getInstance().getCurrentScene());
			}
		}
	}
	
	for(int x = 0; x < cols; x++) {
		for(int y = 0; y < rows; y++) {				
			// if odd and last row, don't add gem
			if(x%2 != 0 && y == rows - 1) {
				break;
			}
			if(grid[x][y] == null) {
				for(int z = y-1; z >= 0; z--) {
					grid[x][z + 1] = grid[x][z];
					grid[x][z + 1].gemSprite.detachSelf();
					grid[x][z + 1].attachToScene(SceneManager.getInstance().getCurrentScene());
				}
				// Add gem from the top (Currently only red)
				grid[x][0] = new RedGem(x, 0);
			}
		}
	}
}*/
	
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
