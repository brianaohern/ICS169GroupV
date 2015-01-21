package com.teamv.capstone.scenes;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.IFont;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.teamv.capstone.BaseScene;
import com.teamv.capstone.ResourcesManager;
import com.teamv.capstone.SceneManager.SceneType;
import com.teamv.capstone.game.Battleground;
import com.teamv.capstone.gemboard.Gemboard;

public class GameScene extends BaseScene
{
	///VARIABLES
	private PhysicsWorld physicsWorld;
	private Gemboard gemboard;
	public Battleground bg;
	
    @Override
    public void createScene()
    {
    	createBackground();
    	createDebuggerHUD();
        createPhysics();
        
        bg = new Battleground(this);
        gemboard = new Gemboard(this, physicsWorld, bg);
        
        this.setOnSceneTouchListener(gemboard);
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
    	final Text fpsText = new Text(40, 50, font, "FPS:", "FPS: XXXXX".length(),vbom);
    	final Text gemCount = new Text(40, 100, font, "# of Gems:", "# of Gems: XXX".length() ,vbom);
    	final Text gemChain = new Text(40, 150, font, "Gems Chained:", "Gems Chained: XXX".length() ,vbom);
    	final Text numOfEnemies = new Text(40, 200, font, "Number of Enemies: ", "Number of Enemies: X".length(), vbom);
    	
    	this.attachChild(fpsText);
    	this.attachChild(gemCount);
    	this.attachChild(gemChain);
    	this.attachChild(numOfEnemies);
    	 
    	this.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback()
    	{
    	    @Override
    	    public void onTimePassed(final TimerHandler pTimerHandler)
    	    {
    	        fpsText.setText("FPS: " + (int)fpsCounter.getFPS());
    	        gemCount.setText("# of Gems: " + gemboard.getGemCount());
    	        gemChain.setText("Gems Chained: " + Gemboard.connectedGems.size());
    	        numOfEnemies.setText("Number of Enemies: " + bg.getNumOfEnemies());
    	    }
    	}));
    }
    
    private void createPhysics()
    {
        physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, 0), false);
        registerUpdateHandler(physicsWorld);
    }
}