package com.teamv.capstone.gemboard;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.primitive.Line;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.color.Color;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.teamv.capstone.game.Battleground;
import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.game.Enemy;
import com.teamv.capstone.game.Player;
import com.teamv.capstone.gemboard.gems.*;
import com.teamv.capstone.gemboard.gems.special.BombGem;
import com.teamv.capstone.gemboard.gems.special.RainbowGem;
import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.scenes.BaseScene;
import com.teamv.capstone.utility.Pointf;

public class Gemboard{
	
	private static Pointf start, end;
	
	// array of gems
	private static Gem[][] grid;
	private static BaseScene gameScene;
	private static PhysicsWorld physicsWorld;
	private static Random random;
	private static Battleground battleground;
	
	// array of connected gems
	public static ArrayList<Gem> connectedGems;
	
	// special gems that have been activated
	public static ArrayList<SpecialGem> activatedGems;
	
	// denotes whether we're in the middle of a combo
	private static boolean combo = false;

	public static ArrayList<Line> lines;
	
	// Current move info
	private static ColorType currentColor = null;
	private static ColorType currentSpecial = null;
	
	private static int cols = 7;
	private static int rows = 5;
	
	// GEM variables
	public static final int RADIUS 	= 1080/cols - 1080%cols;
	public static final int STARTY	= 1920/2 + RADIUS;
	
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
		activatedGems = new ArrayList<SpecialGem>();
		
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
		Gemboard.unshadeBoard();
		Gemboard.setCurrentColor(ColorType.NONE);
		Gemboard.setCurrentSpecial(ColorType.NONE);
		if(minimumConnectedGems() || combo){
			if (!combo) {
				battleground.enterBattle(connectedGems);
			}
			
			ResourcesManager.getInstance().gemDestroySound.play();
			
			for(Gem gem : connectedGems){
				if (gem.getClass() == Bomb.class) {
					Log.d("MyActivity", "Adding bomb to special gems");
					activatedGems.add((SpecialGem)gem);
				} else if (gem.getClass() == Potion.class){
					Log.d("MyActivity", "Adding potion to special gems");
					activatedGems.add((SpecialGem)gem);
				}
				else {
					dropGem(gem);
				}
			}
		}
		eraseLines();
		
		connectedGems.clear();
		lines.clear();
		//printAll();
		
		///////
		///////
		if (activatedGems.size() > 0) {
			final float time = 2.0f;
			
			// update enemy turn count
			ResourcesManager.getInstance().engine.registerUpdateHandler(new TimerHandler(time, new ITimerCallback() 
			{
				public void onTimePassed(final TimerHandler pTimerHandler) 
				{
					ResourcesManager.getInstance().engine.unregisterUpdateHandler(pTimerHandler);
					
					for (Gem gem : activatedGems) {
						if(gem.getClass() == Bomb.class){
							Log.d("MyActivity", "Bomb");
							for (Gem adj : getAdjacentGems(gem)) {
								if (adj != null && !connectedGems.contains(adj) && !activatedGems.contains(adj)) {
									if (adj.getClass() == Bomb.class) {
										Log.d("MyActivity", "Found adjacent bomb");
									}
									if (adj.getClass() == Potion.class){
										Log.d("MyActivity", "Found adjacent potion");
									}
									connectedGems.add((Gem)adj); 
								}
							}
							Log.d("MyActivity", "Dropped bomb");
						}
						else if(gem.getClass() == Potion.class){
							Log.d("MyActivity", "Potion");
							// Increase health by X amount
							// Battleground.increaseHealAmount();
							Log.d("MyActivity", "Dropped potion");
						}
						dropGem(gem);
					}
					
					activatedGems.clear();
					
					if(hasNoMoreMoves()){
						//System.out.println("NO MORE MOVES");
						ResourcesManager.getInstance().activity.gameToast("No more moves");
						resetBoard();
					}
					//drawGrid();
					
					if (connectedGems.size() > 0) {
						Log.d("MyActivity", "Comboing. connectedGems size: " + connectedGems.size());
						combo = true;
						executeGems();
					} else {
						combo = false;
					}
				}
			}));
		}
		///////
		///////
		else {
			if(hasNoMoreMoves()){
				//System.out.println("NO MORE MOVES");
				ResourcesManager.getInstance().activity.gameToast("No more moves");
				resetBoard();
			}
			//drawGrid();
			
			combo = false;
		}
	}
	
	// whether or not we have enough gems to comprise a valid move
	private static boolean minimumConnectedGems() {
		return connectedGems.size() >= 3;
	}
	
	// erases each line connecting the gems in your move
	private static void eraseLines() {
		for(Line line : lines){
			line.detachSelf();
			line.dispose();
			line = null;
		}
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
	
	// return random gem
	private static Gem randomGem(int col, int row){
		float x = col * RADIUS;
		float y = STARTY + row * (RADIUS);
		if(col%2 != 0){
			y += RADIUS/2;
		}
		
		// add gems and whatnot in here
<<<<<<< HEAD
		switch(random.nextInt(10)){
		case 0 | 1:
=======
		if (random.nextInt(25) < 1) {
			return new Bomb(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		} else if (random.nextInt(25) < 1) {
			return new Potion(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		}
		
		switch(random.nextInt(4)){
		case 0:
>>>>>>> origin/master
			return new BlueGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case 2 | 3:
			return new GreenGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case 4 | 5:
			return new YellowGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case 6 | 7:
			return new RedGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case 8:
			return new BombGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
		case 9:
			return new RainbowGem(col, row, x, y, ResourcesManager.getInstance().vbom, physicsWorld);
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
	
	// USED BY GAME ENTITIES; MOVED FROM Gem.java
	public static ArrayList<String> getDamageType(){
		ArrayList<String> damageType = new ArrayList<String>();
		for(Gem gem : connectedGems){
			damageType.add((String)gem.getUserData());
		}
		return damageType;	
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
	
	public static void shadeBoard(Gem gem) {
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				if(grid[x][y] != null) {
					if (!grid[x][y].sameColor(gem)) {
						grid[x][y].shadeGem();
					}
				}
			}
		}
	}
	
	public static void unshadeBoard() {
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				if(grid[x][y] != null) {
					grid[x][y].revertColor();
				}
			}
		}
	}
	
	public static void startList(int c, int r){
		connectedGems.add(grid[c][r]);
	}
	
	public void attachPhysics(){
		
	}
	
	private static ArrayList<Gem> getAdjacentGems(Gem gem){
		ArrayList<Gem> adjacentGems = new ArrayList<Gem>();
		// if the column is odd
		if (gem.getCol() % 2 == 0) {
			// if the gem is not all the way to the left 
			if (gem.getCol() > 0) {
				// if the gem is not at the top
				if (gem.getRow() > 0) {
					// add the gem to the top-left
					adjacentGems.add(grid[gem.getCol()-1][gem.getRow()-1]);
					// add the gem above
					adjacentGems.add(grid[gem.getCol()][gem.getRow()-1]);
				}
				// if the gem is not at the bottom
				if (gem.getRow() < 4) {
					// add the gem to the bottom-left
					adjacentGems.add(grid[gem.getCol()-1][gem.getRow()]);
					// add the gem below
					adjacentGems.add(grid[gem.getCol()][gem.getRow()+1]);
				}
			}
			// if the gem is not all the way to the right
			if (gem.getCol() < 6) {
				if (gem.getRow() > 0) {
					// add the gem to the top-right
					adjacentGems.add(grid[gem.getCol()+1][gem.getRow()-1]);
				}
				if (gem.getRow() < 4) {
					// add the gem to the bottom-right
					adjacentGems.add(grid[gem.getCol()+1][gem.getRow()]);
				}
			}
		}
		// if the column is even
		else {
			// add the gem to the top-left
			adjacentGems.add(grid[gem.getCol()-1][gem.getRow()]);
			// add the gem to the bottom-left
			adjacentGems.add(grid[gem.getCol()-1][gem.getRow()+1]);
			// if the gem is not at the top
			if (gem.getRow() > 0) {
				// add the gem above
				adjacentGems.add(grid[gem.getCol()][gem.getRow()-1]);
			}
			// if the gem is not at the bottom
			if (gem.getRow() < 3) {
				// add the gem below
				adjacentGems.add(grid[gem.getCol()][gem.getRow()+1]);
			}
			// add the gem to the top-right
			adjacentGems.add(grid[gem.getCol()+1][gem.getRow()]);
			// add the gem to the bottom-right
			adjacentGems.add(grid[gem.getCol()+1][gem.getRow()+1]);
		}
		return adjacentGems;
	}
	
	public static ColorType getCurrentColor () {
		return currentColor;
	}
	
	public static void setCurrentColor(Gem gem) {
		currentColor = (ColorType) gem.getUserData();
	}
	
	public static void setCurrentColor(ColorType color) {
		currentColor = color;
	}
	
	public static ColorType getCurrentSpecial () {
		return currentSpecial;
	}
	
	public static void setCurrentSpecial(Gem gem) {
		currentSpecial = (ColorType) gem.getUserData();
	}
	
	public static void setCurrentSpecial(ColorType color) {
		currentSpecial = color;
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
		System.out.println("Gemboard.java -- gem size: " + gemboardSize());
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
