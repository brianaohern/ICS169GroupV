package com.teamv.capstone.gemboard;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.badlogic.gdx.physics.box2d.Body;
import com.teamv.capstone.BaseScene;
import com.teamv.capstone.ResourcesManager;
import com.teamv.capstone.game.Battleground;
import com.teamv.capstone.gemboard.gems.*;
import com.teamv.capstone.utility.Pointf;

public class Gemboard implements IOnSceneTouchListener{
	
	private static Pointf start, end;
	
	// array of gems
	private static Gem[][] grid;
	private static BaseScene gameScene;
	private static PhysicsWorld physicsWorld;
	
	// array of connected gems
	public static ArrayList<Gem> connectedGems;

	public static ArrayList<Line> lines;
	
	private static int cols = 7;
	private static int rows = 5;
	
	// GEM variables
	public static final int RADIUS 	= 1080/cols - 1080%cols;
	public static final int STARTY	= 1920/2 + RADIUS;
	
	private static Random random;
	private static Battleground battleground;
	
	public Gemboard(BaseScene gameScene, PhysicsWorld physicsWorld, Battleground battleground){
		Gemboard.gameScene = gameScene;
		Gemboard.physicsWorld = physicsWorld;
		Gemboard.battleground = battleground;
		
		grid = new Gem[cols][rows];
		random = new Random();
		start = new Pointf(0, 0);
		end = new Pointf(0, 0);
		
		connectedGems = new ArrayList<Gem>();
		lines = new ArrayList<Line>();
		
		// while the gemboard has no possible moves, reset the board
		while(hasNoMoreMoves())
			resetBoard();
		
		//drawGrid();
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
					detachGem(grid[x][y]);
				}
				grid[x][y] = randomGem(x, y);
				attachGem(grid[x][y]);
			}
		}
	}
	
	public static void executeGems() {
		if(connectedGems.size() >= 3){
			battleground.enterBattle(connectedGems);
			for(Gem gem : connectedGems){
				dropGem(gem);
			}
		}
		for(Line line : lines){
			line.detachSelf();
			line.dispose();
			line = null;
		}
		
		connectedGems.clear();
		lines.clear();
		//printAll();
		
		if(hasNoMoreMoves()){
			System.out.println("NO MORE MOVES");
			ResourcesManager.getInstance().activity.gameToast("No more moves");
			resetBoard();
		}
		//drawGrid();
	}
	
	// drops every gem above the parameter gem, then drop a new gem
	private static void dropGem(Gem gem){
		int col = gem.getCol();
		
		for(int i = gem.getRow(); i > 0; i--){
			grid[col][i] = grid[col][i - 1];
			grid[col][i].drop();
		}
		
		grid[col][0] = randomGem(col, 0);
		//grid[col][0] = new RandGem(col, 0, col * RADIUS, y, ResourcesManager.getInstance().vbom, physicsWorld);
		
		attachGem(grid[col][0]);
		
		detachGem(gem);
		gem = null;
	}
	
	private static void attachGem(Gem gem){
		gameScene.attachChild(gem);
		gem.registerEntityModifier(new AlphaModifier((float)0.2, 0, 1));
//		gem.registerEntityModifier(new AlphaModifier(1, 0, 1));
		gameScene.registerTouchArea(gem);
	}
	
	private static void detachGem(Gem gem){
		gameScene.unregisterTouchArea(gem);
		destroyBody(gem);
		gem.cleanUp();
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
		float x = col * RADIUS;
		float y = STARTY + row * (RADIUS);
		if(col%2 != 0){
			y += RADIUS/2;
		}
		
		// add gems and whatnot in here
		switch(random.nextInt(4)){
		case 0:
			return new BlueGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case 1:
			return new GreenGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case 2:
			return new YellowGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case 3:
			return new RedGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		}
		return new RedGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
	}
	
	private static boolean hasNoMoreMoves(){
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				if(grid[x][y] != null){
					int count = 0;
					Object userData = grid[x][y].getUserData();
					
					count += checkGem(x, y+1, userData);
					count += checkGem(x, y-1, userData);
					count += checkGem(x+1, y, userData);
					count += checkGem(x-1, y, userData);
					
					if(x%2 == 0){
						count += checkGem(x-1, y-1, userData);
						count += checkGem(x+1, y-1, userData);
					} else{
						count += checkGem(x-1, y+1, userData);
						count += checkGem(x+1, y+1, userData);
					}
					
					if(count >= 2){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private static int checkGem(int c, int r, Object userData){
		if(		(c < 0 || r < 0) ||
				(c >= cols || r >= rows)){
			return 0;
		}
		else if(grid[c][r] != null && grid[c][r].getUserData().equals(userData)){
			return 1;
		}
		return 0;
	}
	
	private static void destroyBody(final Gem gem)
	{
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable(){

			@Override
			public void run() {

				final Body body = gem.body;
				physicsWorld.unregisterPhysicsConnector(physicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(gem));
				physicsWorld.destroyBody(body);
			}});

	}
	
	public static void startList(int c, int r){
		connectedGems.add(grid[c][r]);
	}
	
	public void attachPhysics(){
		
	}
	
	public static Pointf getStartPoint(){
		return start;
	}
	
	public static Pointf getEndPoint(){
		return end;
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
	
	public int getGemCount(){
		return gemboardSize();
	}
	
	@SuppressWarnings("unused")
	private static void drawGrid(){
		for(int y = 0; y <= rows; y++){
			Line line = new Line(0, STARTY + y * RADIUS, 1080, STARTY + y * RADIUS, ResourcesManager.getInstance().vbom);
	    	line.setLineWidth(10f);
	    	line.setColor(Color.WHITE);
	        gameScene.attachChild(line);
		}
		for(int x = 0; x <= cols; x++){
			Line line = new Line(x * RADIUS, 0, x * RADIUS, 1920, ResourcesManager.getInstance().vbom);
	    	line.setLineWidth(10f);
	    	line.setColor(Color.WHITE);
	        gameScene.attachChild(line);
		}
		
	}
}
