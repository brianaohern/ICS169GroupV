package com.teamv.capstone;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

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
        
    private BuildableBitmapTextureAtlas menuTextureAtlas;
    
    public Font font;
    
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
        loadGameGraphics();
        loadGameFonts();
        loadGameAudio();
    }
    
    private void loadMenuGraphics()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1080, 1980, TextureOptions.BILINEAR);
    	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png");
    	//play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
    	//options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options.png");
    	       
    	try 
    	{
    	    this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.menuTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
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