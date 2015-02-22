package com.teamv.capstone.scenes;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.SAXUtils;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.level.IEntityLoader;
import org.andengine.util.level.LevelLoader;
import org.xml.sax.Attributes;

import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.game.Enemy;
import com.teamv.capstone.game.GameActivity;
import com.teamv.capstone.game.Level;
import com.teamv.capstone.game.Wave;
import com.teamv.capstone.game.enemies.*;
import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.managers.SceneManager;
import com.teamv.capstone.managers.SceneManager.SceneType;

/**
 * 
 * @author Knoll Florian
 * @email myfknoll@gmail.com
 *
 * modified by team v
 *
 */
public class LevelSelectScene extends BaseScene implements IScrollDetectorListener, IOnSceneTouchListener, IClickDetectorListener {

	// ===========================================================
	// Constants
	// ===========================================================
	protected static int LEVELS = 24;
	protected static int LEVEL_ROWS_PER_SCREEN = 10;
	protected static int LEVEL_PADDING = 50;
	protected static int CAMERA_WIDTH = 1080;
	protected static int CAMERA_HEIGHT = 1920;
	//private int mMaxLevelReached = 7;
	// ===========================================================
	// Fields
	// ===========================================================
	// Scrolling
	private SurfaceScrollDetector mScrollDetector;
	private ClickDetector mClickDetector;

	private float mMinY = 0;
	@SuppressWarnings("unused")
	private float mMaxY = 0;
	private float mCurrentY = 0;
	private int iLevelClicked = -1;
	
	private ArrayList<Level> levels;

	@Override
	public void createScene() {
		this.mScrollDetector = new SurfaceScrollDetector(this);
		this.mClickDetector = new ClickDetector(this);
		
		this.setOnSceneTouchListener(this);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);            
		this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
		
		levels = new ArrayList<Level>();
		this.loadXMLData();
		this.createMenuBoxes();
		
		setBackground(new Background(Color.WHITE));
	}

	@Override
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
		this.mClickDetector.onTouchEvent(pSceneTouchEvent);
		this.mScrollDetector.onTouchEvent(pSceneTouchEvent);
		return true;
	}

	@Override
	public void onScroll(ScrollDetector pScollDetector, int pPointerID, float pDistanceX, float pDistanceY) {
//		System.out.println("y offset: " + pDistanceY);
//		System.out.println("(mCurrentY - pDistanceY): " + (mCurrentY - pDistanceY));
//		System.out.println("miny: " + mMinY);
//		System.out.println("maxy: " + mMaxY);
//		System.out.println("centery: " + camera.getCenterY());

		// mMaxY IS HARDCODED in this check
		if ( ((mCurrentY - pDistanceY) < mMinY) || ((mCurrentY - pDistanceY) > 1202f) )
			return;
		this.camera.offsetCenter(0, -pDistanceY);
		mCurrentY -= pDistanceY;
	}

	private void createMenuBoxes() {
		
		// Calculate space between each level square
		int spaceBetweenRows = (CAMERA_HEIGHT / LEVEL_ROWS_PER_SCREEN) - LEVEL_PADDING;
		//Current Level Counter
		int iLevel = 1;
		//mMaxLevelReached = LEVELS;

		//Create the Level selectors, one row at a time.
		final ITextureRegion region = this.resourcesManager.level_region;
		int boxX = (int) (GameActivity.WIDTH/2 - region.getWidth()/2), boxY = LEVEL_PADDING;
		for (int y = 0; y < LEVELS; y++) {

			//On Touch, save the clicked level in case it's a click and not a scroll.
			final int levelToLoad = iLevel;

			Sprite box = new Sprite(boxX, boxY, region, this.vbom) {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					//if (levelToLoad >= mMaxLevelReached)
					//	iLevelClicked = -1;
					//else
						iLevelClicked = levelToLoad;
					return false;
				}
			};
			this.attachChild(box);

			String text = String.valueOf(iLevel) + " : " + levels.get(y).getName();
			//Center for different font size
			if (iLevel < 10) {
				this.attachChild(new Text(boxX + 38, boxY + 15, this.resourcesManager.font, text, this.vbom));
			}
			else {
				this.attachChild(new Text(boxX + 30, boxY + 15, this.resourcesManager.font, text, this.vbom));
			}

			this.registerTouchArea(box);

			iLevel++;

			if (iLevel > LEVELS)
				break;

			boxY += spaceBetweenRows + LEVEL_PADDING;
		}

		//Set the max scroll possible, so it does not go over the boundaries.
		mMaxY = boxY - CAMERA_HEIGHT + 200;
		//System.out.println(mMaxY);
	}

	private void loadLevel(final int index){
		if (index > -1) {
			this.activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					iLevelClicked = -1;
					camera.setCenter(GameActivity.WIDTH/2, GameActivity.HEIGHT/2);
					SceneManager.getInstance().createGameScene(levels.get(index));
				}
			});
		}
	}
	
	private void loadXMLData(){
		LevelLoader levelLoader = new LevelLoader();
		
		levelLoader.registerEntityLoader("levelset", new IEntityLoader() {
			@Override
			public IEntity onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				LEVELS = SAXUtils.getIntAttributeOrThrow(pAttributes, "number");
				return null;
			}
		});
		
		levelLoader.registerEntityLoader("level", new IEntityLoader() {
			@Override
			public IEntity onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				final Level level = new Level(SAXUtils.getAttributeOrThrow(pAttributes, "name"));
				//final String levelType = SAXUtils.getAttributeOrThrow(pAttributes, "type");
				levels.add(level);
				return null;
			}
		});
		
		levelLoader.registerEntityLoader("wave", new IEntityLoader() {

			@Override
			public IEntity onLoadEntity(String pEntityName, Attributes pAttributes) {
				Wave wave = new Wave();
				levels.get(levels.size() - 1).addWave(wave);
				return null;
			}
		});
		
		levelLoader.registerEntityLoader("enemy", new IEntityLoader() {

			@Override
			public IEntity onLoadEntity(String pEntityName, Attributes pAttributes) {
				final String name = SAXUtils.getAttributeOrThrow(pAttributes, "name");
				final String enemyType = SAXUtils.getAttributeOrThrow(pAttributes, "type");
				
				ColorType type = null;
				switch(enemyType){
				case "red":
					type = ColorType.RED;
					break;
				case "blue":
					type = ColorType.BLUE;
					break;
				case "green":
					type = ColorType.GREEN;
					break;
				case "yellow":
					type = ColorType.YELLOW;
					break;
				case "normal":
				default:
					//do nothing for normal and default
				}
				
				Enemy enemy = null;
				switch(name){
				case "wolfie":
					if(type != null)
						enemy = new Wolfie(type, vbom);
					else
						enemy = new Wolfie(vbom);	// Default type to show players
					break;
				case "wolf":
					if(type != null)
						enemy = new Wolf(type, vbom);
					else
						enemy = new Wolf(vbom);	// Default type to show players
					break;
				case "dire wolf":
					if(type != null)
						enemy = new DireWolf(type, vbom);
					else
						enemy = new DireWolf(vbom);	// Default type to show players
					break;
				case "imp":
					if(type != null)
						enemy = new Imp(type, vbom);
					else
						enemy = new Imp(vbom);	// Default type to show players
					break;
				default:
					ResourcesManager.getInstance().activity.gameToast("xml level loader -- default break");
				}
				
				if(enemy!=null)
					levels.get(levels.size() - 1).getLastWave().add(enemy);
				return null;
			}
		});

		try{
			levelLoader.loadLevelFromAsset(ResourcesManager.getInstance().activity.getAssets(), "levels/robin.xml");
		} catch (final IOException e) {
			Debug.e(e);
		}
	}

	public void onClick(ClickDetector pClickDetector, int pPointerID, float pSceneX, float pSceneY) {
		ResourcesManager.getInstance().activity.gameToast("level: "+iLevelClicked);
		loadLevel(iLevelClicked-1);
	};
	
	public void onBackKeyPressed() {}

	public SceneType getSceneType() {
		return SceneType.SCENE_LEVEL_SELECT;
	}

	public void disposeScene() {}

	public void onScrollStarted(ScrollDetector pScollDetector, int pPointerID, float pDistanceX, float pDistanceY) {}

	public void onScrollFinished(ScrollDetector pScollDetector, int pPointerID, float pDistanceX, float pDistanceY) {}
}
