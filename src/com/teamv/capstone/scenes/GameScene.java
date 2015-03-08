package com.teamv.capstone.scenes;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.IFont;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.teamv.capstone.game.Battleground;
import com.teamv.capstone.game.GameActivity;
import com.teamv.capstone.game.Level;
import com.teamv.capstone.game.tutorial.TutorialGemboard;
import com.teamv.capstone.gemboard.Gemboard;
import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.managers.SceneManager.SceneType;

public class GameScene extends BaseScene
{
	///VARIABLES
	public static PhysicsWorld physicsWorld;
	public Gemboard gemboard;
	public static Battleground arena;
	private PauseMenuScene mPauseScene;
	private Level level;

	public GameScene(Level level) {
		super();
		arena.enterLevel(level);
		this.level = level;
	}

	@Override
	public void createScene()
	{
		createBackground();
		createDebuggerHUD();
		createPhysics();

		mPauseScene = new PauseMenuScene(this);
		
		final GameScene gs = this;
		final Sprite pauseButton = new Sprite(0, 0, this.resourcesManager.options_region, this.vbom){

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionUp()){
					gs.setChildScene(mPauseScene, false, true, true);
				}
				return true;
			}
		};
		pauseButton.setX(GameActivity.WIDTH-pauseButton.getWidth());

		this.attachChild(pauseButton);
		this.registerTouchArea(pauseButton);

		arena = new Battleground(this);
		ResourcesManager.getInstance().bgm.play();
	}

	@Override
	public void onBackKeyPressed()
	{

	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene()
	{
		
	}

	///CREATE STUFF
	private void createBackground()
	{
		//    	float r = 99.0f	/255;
		//    	float g = 33.0f	/255;
		//    	float b = 10.0f	/255;
		//      setBackground(new Background(r, g, b));
		setBackground(new Background(Color.BLACK));
	}

	// DEBUGGER HUD
	private void createDebuggerHUD()
	{
		final FPSCounter fpsCounter = new FPSCounter();
		this.engine.registerUpdateHandler(fpsCounter);
		IFont font = ResourcesManager.getInstance().font;
		
		final Text fpsText = new Text(40, 0, font, "FPS:", "FPS: XXXXX".length(), vbom);
		final Text waveCount = new Text(40, 50, font, "Wave ", "Wave X of X".length(), vbom);
		final Text numOfEnemies = new Text(40, 100, font, "Number of Enemies: ", "Number of Enemies: X".length(), vbom);
		final Text gemChain = new Text(40, 150, font, "Gems Chained:", "Gems Chained: XXX".length(), vbom);
	
		this.attachChild(waveCount);
		this.attachChild(fpsText);
		this.attachChild(gemChain);
		this.attachChild(numOfEnemies);

		this.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback()
		{
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler)
			{
				waveCount.setText("Wave " + level.getCurrentWaveCount() + " of " + level.getMaxWaveCount());
				fpsText.setText("FPS: " + (int)fpsCounter.getFPS());
				gemChain.setText("Gems Chained: " + Gemboard.connectedGems.size());
				numOfEnemies.setText("Number of Enemies: " + arena.getNumOfEnemies());
			}
		}));
	}

	private void createPhysics()
	{ 
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, 0), false);
		registerUpdateHandler(physicsWorld);
	}

	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent){
		super.onSceneTouchEvent(pSceneTouchEvent);

		if (pSceneTouchEvent.isActionUp()){
			gemboard.executeGems();
			return true;
		}
		return false;
	}
	
	public void loadTutorialBoard() {
		gemboard = new TutorialGemboard(this, physicsWorld, arena);
	}
	
	public void loadGemboard(){
		gemboard = new Gemboard(this, physicsWorld, arena);
	}

	public void enterEndScene(boolean winGame) {
		ResultScene resultScene = null;
		ResourcesManager.getInstance().bgm.stop();
		if(winGame){
			resultScene = new ResultScene(600, 800, "You Win", SceneType.SCENE_MENU);
		}
		else{
			resultScene = new ResultScene(600, 800, "You Lose!", SceneType.SCENE_MENU);
		}
		this.setChildScene(resultScene, false, true, true);
	}
	
	public void enterInstructionScene(String instruction, boolean shouldResetBoard, int boardType){
		InstructionScene instructions = new InstructionScene(1080, 200, instruction, shouldResetBoard, boardType);
		this.setChildScene(instructions, false, true, true);
	}
}