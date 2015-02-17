package com.teamv.capstone.scenes;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.teamv.capstone.managers.SceneManager;
import com.teamv.capstone.managers.SceneManager.SceneType;

public class PromptScene extends PopUpScene{
	
	public PromptScene(){
		super();
	}
	
	public PromptScene(int width, int height, String text, SceneType type) {
		this();
		setUp(width, height, text, type);
	}
	
	public void init(){
		super.init();
		this.addRectangle();
	}
	
	public void setUp(int width, int height, String text, final SceneType type){
		
		init();
		
		final Text message= new Text(40, 200, rs.font, text, text.length(), vbom);
		message.setScale(1.5f);
		message.setX(message.getX()*1.5f);
		this.popup.attachChild(message);
		
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
		button.setPosition(popup);
		popup.attachChild(button);
		this.registerTouchArea(button);
	}
}
