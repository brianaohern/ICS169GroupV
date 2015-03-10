package com.teamv.capstone.scenes;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.teamv.capstone.game.GameActivity;
import com.teamv.capstone.managers.SceneManager;
import com.teamv.capstone.managers.SceneManager.SceneType;

public class ResultScene extends PopUpScene{
	
	Sprite popup;
	
	public ResultScene(){
		super();
	}
	
	public ResultScene(int width, int height, String text, SceneType type) {
		this();
		setUp(width, height, text, type);
	}
	
	public void setUp(int width, int height, String text, final SceneType type){
		
		popup = new Sprite(GameActivity.WIDTH/2 - width/2, GameActivity.HEIGHT/2-height/2, rs.bgBox, vbom);
		popup.setWidth(width);
		popup.setHeight(height);
		this.attachChild(popup);
		
		final Text message= new Text(GameActivity.WIDTH/2 - width/2 + text.length()/2, GameActivity.HEIGHT/2 - 100, rs.font, text, text.length(), vbom);
		message.setScale(1.5f);
		message.setX(message.getX()*1.5f);
		this.attachChild(message);
		
		final Sprite button = new Sprite(0, 0, rs.menuButton, this.vbom){

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionUp()){
					SceneManager.getInstance().setScene(type);
				}
				return true;
			}
		};
		button.setScale(2f);
		button.setColor(Color.BLUE);
		button.setPosition(GameActivity.WIDTH/2 - button.getWidth()/2, GameActivity.HEIGHT/2 + button.getHeight());
		this.attachChild(button);
		this.registerTouchArea(button);
	}
}
