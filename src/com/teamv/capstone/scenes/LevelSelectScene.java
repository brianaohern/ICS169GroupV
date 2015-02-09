package com.teamv.capstone.scenes;

import java.util.ArrayList;
import java.util.List;
   
import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;

import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.managers.SceneManager.SceneType;

import android.widget.Toast;

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
        protected static int CAMERA_WIDTH = 480;
        protected static int CAMERA_HEIGHT = 320;
 
        protected static int FONT_SIZE = 24;
        protected static int PADDING = 50;
        
        protected static int MENUITEMS = 7;
        
 
        // ===========================================================
        // Fields
        // ===========================================================
        private Scene mScene;
        private Camera mCamera;
         
        private BitmapTextureAtlas mMenuTextureAtlas;        
        private ITextureRegion mMenuLeftTextureRegion;
        private ITextureRegion mMenuRightTextureRegion;
        
        private Sprite menuleft;
        private Sprite menuright;
 
        // Scrolling
        private SurfaceScrollDetector mScrollDetector;
        private ClickDetector mClickDetector;
 
        private float mMinX = 0;
        private float mMaxX = 0;
        private float mCurrentX = 0;
        private int iItemClicked = -1;
        
        private Rectangle scrollBar;        
        private List<TextureRegion> columns = new ArrayList<TextureRegion>();

        // ===========================================================
        // Constructors
        // ===========================================================
 
        // ===========================================================
        // Getter & Setter
        // ===========================================================
 
        // ===========================================================
        // Methods for/from SuperClass/Interfaces
        // ===========================================================
 


 
        @Override
        public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
                this.mClickDetector.onTouchEvent(pSceneTouchEvent);
                this.mScrollDetector.onTouchEvent(pSceneTouchEvent);
                return true;
        }
 
        @Override
		public void onScroll(ScrollDetector pScollDetector, int pPointerID, float pDistanceX, float pDistanceY) {

        		//Disable the menu arrows left and right (15px padding)
	        	if(mCamera.getXMin()<=15)
	             	menuleft.setVisible(false);
	             else
	             	menuleft.setVisible(true);
	        	 
	        	 if(mCamera.getXMin()>mMaxX-15)
		             menuright.setVisible(false);
		         else
		        	 menuright.setVisible(true);
	             	
                //Return if ends are reached
                if ( ((mCurrentX - pDistanceX) < mMinX)  ){                	
                    return;
                }else if((mCurrentX - pDistanceX) > mMaxX){
                	
                	return;
                }
                
                //Center camera to the current point
                this.mCamera.offsetCenter(-pDistanceX,0 );
                mCurrentX -= pDistanceX;
                	
               
                //Set the scrollbar with the camera
                float tempX =mCamera.getCenterX()-CAMERA_WIDTH/2;
                // add the % part to the position
                tempX+= (tempX/(mMaxX+CAMERA_WIDTH))*CAMERA_WIDTH;      
                //set the position
                scrollBar.setPosition(tempX, scrollBar.getY());
                
                //set the arrows for left and right
                menuright.setPosition(mCamera.getCenterX()+CAMERA_WIDTH/2-menuright.getWidth(),menuright.getY());
                menuleft.setPosition(mCamera.getCenterX()-CAMERA_WIDTH/2,menuleft.getY());
                
              
                
                //Because Camera can have negativ X values, so set to 0
            	if(this.mCamera.getXMin()<0){
            		this.mCamera.offsetCenter(0,0 );
            		mCurrentX=0;
            	}
            	
 
        }
 
        @Override
		public void onClick(ClickDetector pClickDetector, int pPointerID, float pSceneX, float pSceneY) {
                loadLevel(iItemClicked);
        };
 
        // ===========================================================
        // Methods
        // ===========================================================
        
        private void CreateMenuBoxes() {
        	
             int spriteX = PADDING;
        	 int spriteY = PADDING;
        	 
        	 //current item counter
             int iItem = 1;

        	 for (int x = 0; x < columns.size(); x++) {
        		 
        		 //On Touch, save the clicked item in case it's a click and not a scroll.
                 final int itemToLoad = iItem;
        		 
        		 Sprite sprite = new Sprite(spriteX,spriteY,(ITextureRegion)columns.get(x), this.vbom){
        			 
        			 public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                         iItemClicked = itemToLoad;
                         return false;
        			 }        			 
        		 };        		 
        		 iItem++;
        		
        		 
        		 this.mScene.attachChild(sprite);        		 
        		 this.mScene.registerTouchArea(sprite);        		 
   
        		 spriteX += 20 + PADDING+sprite.getWidth();
			}
        	
        	 mMaxX = spriteX - CAMERA_WIDTH;
        	 
        	 //set the size of the scrollbar
        	 float scrollbarsize = CAMERA_WIDTH/((mMaxX+CAMERA_WIDTH)/CAMERA_WIDTH);
        	 scrollBar = new Rectangle(0,CAMERA_HEIGHT-20,scrollbarsize, 20, this.vbom);
        	 scrollBar.setColor(1,0,0);
        	 this.mScene.attachChild(scrollBar);
        	 
        	 menuleft = new Sprite(0,CAMERA_HEIGHT/2-mMenuLeftTextureRegion.getHeight()/2,mMenuLeftTextureRegion, this.vbom);
        	 menuright = new Sprite(CAMERA_WIDTH-mMenuRightTextureRegion.getWidth(),CAMERA_HEIGHT/2-mMenuRightTextureRegion.getHeight()/2,mMenuRightTextureRegion, this.vbom);
        	 this.mScene.attachChild(menuright);
        	 menuleft.setVisible(false);
        	 this.mScene.attachChild(menuleft);
        }
        
        
 
        //Here is where you call the item load.
        private void loadLevel(final int iLevel) {
                if (iLevel != -1) {
                       this.activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                			
                                        Toast.makeText(ResourcesManager.getInstance().activity, "Load Item" + String.valueOf(iLevel), Toast.LENGTH_SHORT).show();
                                        iItemClicked = -1;
                                }
                        });
                }
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

		@Override
		public void createScene() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onBackKeyPressed() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public SceneType getSceneType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void disposeScene() {
			// TODO Auto-generated method stub
			
		}


}
 