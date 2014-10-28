package com.teamv.helloworld.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import com.badlogic.gdx.math.Vector2;
import com.teamv.helloworld.BaseScene;
import com.teamv.helloworld.SceneManager.SceneType;
import com.teamv.helloworld.gemboard.Gem;
import com.teamv.helloworld.gemboard.Gemboard;

public class GameScene extends BaseScene
{
	///VARIABLES
	private HUD gameHUD;
	private Text scoreText;
	private PhysicsWorld physicsWorld;
	
	private Gemboard gemboard;
	private Gem gem;
	
    @Override
    public void createScene()
    {
    	createBackground();
        createHUD();
        createPhysics();
        
//        for(int i = 0; i < 6; i++){
//        	gem = new Gem(i, 0);
//        	this.attachChild(gem.gemSprite);
//        }
        
        gemboard = new Gemboard();
        gemboard.attach(this);
        
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
        setBackground(new Background(Color.BLUE));
    }

    
    private void createHUD()
    {
//        gameHUD = new HUD();
//        
//        // CREATE SCORE TEXT
//        scoreText = new Text(20, 420, resourcesManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
//        scoreText.setSkewCenter(0, 0);    
//        scoreText.setText("Score: 0");
//        gameHUD.attachChild(scoreText);
//        
//        camera.setHUD(gameHUD);
    }
    
    private void createPhysics()
    {
        physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -17), false); 
        registerUpdateHandler(physicsWorld);
    }
}