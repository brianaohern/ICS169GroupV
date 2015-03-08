package com.teamv.capstone.managers;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.teamv.capstone.game.Level;
import com.teamv.capstone.game.tutorial.TutorialLevel;
import com.teamv.capstone.scenes.BaseScene;
import com.teamv.capstone.scenes.GameScene;
import com.teamv.capstone.scenes.LevelSelectScene;
import com.teamv.capstone.scenes.LoadingScene;
import com.teamv.capstone.scenes.MainMenuScene;
import com.teamv.capstone.scenes.SplashScene;

/**
 * @author Mateusz Mysliwiec
 * @author www.matim-dev.com
 * @version 1.0
 */
public class SceneManager
{
    //---------------------------------------------
    // SCENES
    //---------------------------------------------
    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene loadingScene;
    private BaseScene levelSelectScene;
    
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final SceneManager INSTANCE = new SceneManager();
    
    private SceneType currentSceneType = SceneType.SCENE_SPLASH;
    
    private BaseScene currentScene;
    
    private Engine engine = ResourcesManager.getInstance().engine;
    
    public enum SceneType
    {
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING,
        SCENE_LEVEL_SELECT
    }
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    public void setScene(BaseScene scene)
    {
        engine.setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
    }
    
    public void setScene(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_GAME:
                setScene(gameScene);
                break;
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_LOADING:
                setScene(loadingScene);
                break;
            case SCENE_LEVEL_SELECT:
            	setScene(levelSelectScene);
            	break;
            default:
                break;
        }
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static SceneManager getInstance()
    {
        return INSTANCE;
    }
    
    public SceneType getCurrentSceneType()
    {
        return currentSceneType;
    }
    
    public BaseScene getCurrentScene()
    {
        return currentScene;
    }
    
    public GameScene getGameScene(){
    	return (GameScene) gameScene;
    }
    
    
    ///////////////////////////////
    // CREATE AND DESTORY
    ///////////////////////////////////
    
    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
    {
        ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }
    
    private void disposeSplashScene()
    {
        ResourcesManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }
    
    public void createMenuScene()
    {
    	ResourcesManager.getInstance().loadMenuResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        SceneManager.getInstance().setScene(menuScene);
        disposeSplashScene();
    }
    
    public void createGameScene(Level level){
    	ResourcesManager.getInstance().loadGameResources();
    	gameScene = new GameScene(level);
    	SceneManager.getInstance().setScene(gameScene);
    	((GameScene) gameScene).loadGemboard();
    }
    
    public void createGameScene(TutorialLevel level){
    	ResourcesManager.getInstance().loadGameResources();
    	gameScene = new GameScene(level);
    	SceneManager.getInstance().setScene(gameScene);
    	((GameScene) gameScene).loadTutorialBoard();
    }
    
    public void createLevelSelectScene(){
    	ResourcesManager.getInstance().loadLevelSelectResources();
    	levelSelectScene = new LevelSelectScene();
    	SceneManager.getInstance().setScene(levelSelectScene);
    }
}