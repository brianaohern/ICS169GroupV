package com.teamv.capstone.scenes;

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
import org.andengine.util.color.Color;

import com.teamv.capstone.game.GameActivity;
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
	protected static int LEVELS = 30;
	protected static int LEVEL_COLUMNS_PER_SCREEN = 1;
	protected static int LEVEL_ROWS_PER_SCREEN = 10;
	protected static int LEVEL_PADDING = 50;
	protected static int CAMERA_WIDTH = 1080;
	protected static int CAMERA_HEIGHT = 1920;
	private int mMaxLevelReached = 7;
	// ===========================================================
	// Fields
	// ===========================================================
	// Scrolling
	private SurfaceScrollDetector mScrollDetector;
	private ClickDetector mClickDetector;

	private float mMinY = 0;
	private float mMaxY = 0;
	private float mCurrentY = 0;
	private int iLevelClicked = -1;

	@Override
	public void createScene() {
		this.createMenuBoxes();
		this.mScrollDetector = new SurfaceScrollDetector(this);
		this.mClickDetector = new ClickDetector(this);

		this.setOnSceneTouchListener(this);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);            
		this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
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
		System.out.println("maxy: " + mMaxY);
//		System.out.println("centery: " + camera.getCenterY());

		 if ( ((mCurrentY - pDistanceY) < mMinY) || ((mCurrentY - pDistanceY) > 3898f) )
             return;
		this.camera.offsetCenter(0, -pDistanceY);
		mCurrentY -= pDistanceY;
	}

	private void createMenuBoxes() {
		// calculate the amount of required columns for the level count
		int totalRows = (LEVELS / LEVEL_COLUMNS_PER_SCREEN) + 1;

		// Calculate space between each level square
		int spaceBetweenRows = (CAMERA_HEIGHT / LEVEL_ROWS_PER_SCREEN) - LEVEL_PADDING;

		//Current Level Counter
		int iLevel = 1;

		//Create the Level selectors, one row at a time.
		final ITextureRegion region = this.resourcesManager.level_region;
		int boxX = (int) (GameActivity.WIDTH/2 - region.getWidth()/2), boxY = LEVEL_PADDING;
		for (int y = 0; y < totalRows; y++) {
			for (int x = 0; x < LEVEL_COLUMNS_PER_SCREEN; x++) {

				//On Touch, save the clicked level in case it's a click and not a scroll.
				final int levelToLoad = iLevel;

				// Create the rectangle. If the level selected
				// has not been unlocked yet, don't allow loading.
				
				Sprite box = new Sprite(boxX, boxY, region, this.vbom) {
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						if (levelToLoad >= mMaxLevelReached)
							iLevelClicked = -1;
						else
							iLevelClicked = levelToLoad;
						return false;
					}
				};

				this.attachChild(box);

				//Center for different font size
				if (iLevel < 10) {
					this.attachChild(new Text(boxX + 18, boxY + 15, this.resourcesManager.font, String.valueOf(iLevel), this.vbom));
				}
				else {
					this.attachChild(new Text(boxX + 10, boxY + 15, this.resourcesManager.font, String.valueOf(iLevel), this.vbom));
				}

				this.registerTouchArea(box);

				iLevel++;

				if (iLevel > LEVELS)
					break;
			}

			if (iLevel > LEVELS)
				break;

			boxY += spaceBetweenRows + LEVEL_PADDING;
		}

		//Set the max scroll possible, so it does not go over the boundaries.
		mMaxY = boxY - CAMERA_HEIGHT + 200;
		System.out.println(mMaxY);
	}
	
	private void loadLevel(final int level){
		 if (level != -1) {
             this.activity.runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                             iLevelClicked = -1;
                             SceneManager.getInstance().createGameScene();
                     }
             });
     }
	}

	@Override
	public void onClick(ClickDetector pClickDetector, int pPointerID, float pSceneX, float pSceneY) {
		loadLevel(iLevelClicked);
	};

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_LEVEL_SELECT;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStarted(ScrollDetector pScollDetector,
			int pPointerID, float pDistanceX, float pDistanceY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollFinished(ScrollDetector pScollDetector,
			int pPointerID, float pDistanceX, float pDistanceY) {
		// TODO Auto-generated method stub

	}
}
