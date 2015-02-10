package com.teamv.capstone.managers;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.GameActivity;

import android.graphics.Color;

/**
 * @author Mateusz Mysliwiec
 * @author www.matim-dev.com
 * @version 1.0
 */
public class ResourcesManager
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final ResourcesManager INSTANCE = new ResourcesManager();
    
    public Engine engine;
    public GameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    
    //splash
    public ITextureRegion splash_region;
    private BitmapTextureAtlas splashTextureAtlas;
    
    //menu
    public ITextureRegion menu_background_region;
    public ITextureRegion play_region;
    public ITextureRegion options_region;
    private BitmapTextureAtlas menuTextureAtlas;
    public Font font;
    
    //levelselect
    public BitmapTextureAtlas lsTextureAtlas;
    public ITextureRegion level_region;
    
    //game.gems
    public ITextureRegion red_gem;
    public ITextureRegion blue_gem;
    public ITextureRegion green_gem;
    public ITextureRegion yellow_gem;
    private BitmapTextureAtlas gemsTextureAtlas;
    //game.enemies
    public ITextureRegion wolf;
    private BitmapTextureAtlas wolfTextureAtlas;
    //game.player
    public ITextureRegion mainCharacter;
    private BitmapTextureAtlas mainCharacterTextureAtlas;
    //game.pausemenu
    private BitmapTextureAtlas pauseMenuTextureAtlas;
    public ITextureRegion startButton;
    public ITextureRegion quitButton;
    public ITextureRegion helpButton;
    public ITextureRegion resumeButton;
    public ITextureRegion menuButton;
    public ITextureRegion creditsButton;
    
    //audio
    public Sound gemSelectSound;
    public Sound gemDestroySound;
    public Sound meleeAttackSound;
    
    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------

    public void loadMenuResources()
    {
        loadMenuGraphics();
        loadMenuAudio();
        loadMenuFonts();
    }
    
    public void loadGameResources()
    {
        //loadGameGraphics();
        loadGameFonts();
        loadGameAudio();
    }
    
    public void loadLevelSelectResources(){
    	loadLevelSelectGraphics();
    	loadGameGraphics();
    	loadLevelSelectFonts();
    }
    
    private void loadMenuGraphics()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	menuTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1480, 1980, TextureOptions.BILINEAR);
    	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png", 0, 0);
    	play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png", 1080, 0);
    	options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options.png", 1080, 100);
    
    	menuTextureAtlas.load();
    }
    
    private void loadMenuFonts()
    {
        FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }
    
    private void loadMenuAudio()
    {
        
    }
    
    private void loadLevelSelectGraphics(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	lsTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 600, 100, TextureOptions.BILINEAR);
        level_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(lsTextureAtlas, activity, "level.png", 0, 0);
        lsTextureAtlas.load();
    }

    private void loadLevelSelectFonts()
    {
    	FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }

    private void loadGameGraphics()
    {
    	// game.gems
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/gems/");
        gemsTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 484, 488, TextureOptions.BILINEAR);
        red_gem = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gemsTextureAtlas, activity, "fire_red.png", 0, 0);
        blue_gem = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gemsTextureAtlas, activity, "water_blue.png", 228, 0);
        yellow_gem = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gemsTextureAtlas, activity, "sun_yellow.png", 0, 244);
        green_gem = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gemsTextureAtlas, activity, "leaf_green.png", 240, 244);
        gemsTextureAtlas.load();    
        // end game.gems
        
        // game.enemies
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/enemies/");
        wolfTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 400, 278, TextureOptions.BILINEAR);
        wolf = BitmapTextureAtlasTextureRegionFactory.createFromAsset(wolfTextureAtlas, activity, "wolfie.png", 0, 0);
        wolfTextureAtlas.load();
        //end game.enemies
        
        // game.player
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/player/");
        mainCharacterTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 300, 500, TextureOptions.BILINEAR);
        mainCharacter = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainCharacterTextureAtlas, activity, "player.png", 0, 0);
        mainCharacterTextureAtlas.load();
        //end game.player
        
        //PAUSE MENU
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        pauseMenuTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 200, 300, TextureOptions.BILINEAR);
        TiledTextureRegion buttons = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset
        		(pauseMenuTextureAtlas, activity, "buttons.png", 0, 0, 1, 6);
        startButton 	= buttons.getTextureRegion(0);
        quitButton 		= buttons.getTextureRegion(1);
        helpButton 		= buttons.getTextureRegion(2);
        resumeButton 	= buttons.getTextureRegion(3);
        menuButton 		= buttons.getTextureRegion(4);
        creditsButton 	= buttons.getTextureRegion(5);
        pauseMenuTextureAtlas.load();
        
    }
    
    private void loadGameFonts()
    {
    	FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 1080, 1920, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }
    
    private void loadGameAudio()
    {
		try {
			gemSelectSound = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity.getApplicationContext(), "sfx/gem_select.wav");
			gemDestroySound = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity.getApplicationContext(), "sfx/gem_destroy.mp3");
			meleeAttackSound = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity.getApplicationContext(), "sfx/melee_attack.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void loadSplashScreen()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1080, 1920, TextureOptions.BILINEAR);
    	splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
    	splashTextureAtlas.load();
    }
    
    public void unloadSplashScreen()
    {
    	splashTextureAtlas.unload();
    	splash_region = null;
    }
    
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static ResourcesManager getInstance()
    {
        return INSTANCE;
    }
}