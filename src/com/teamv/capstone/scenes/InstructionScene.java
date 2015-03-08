package com.teamv.capstone.scenes;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.teamv.capstone.game.GameActivity;
import com.teamv.capstone.game.tutorial.TutorialGemboard;
import com.teamv.capstone.managers.SceneManager;

public class InstructionScene extends PopUpScene{
	
	Rectangle popup;
	
	public InstructionScene(){
		super();
	}
	
	public InstructionScene(int width, int height, String text, boolean shouldResetBoard, int boardType) {
		this();
		setUp(width, height, text, shouldResetBoard, boardType);
	}
	
	public void setUp(int width, int height, String text, final boolean shouldResetBoard, final int boardType){
		popup = new Rectangle(GameActivity.WIDTH/2 - width/2, GameActivity.HEIGHT/2-height/2, width, height, vbom);
		this.attachChild(popup);
		
		final Text message= new Text(40, 0, rs.font, text, text.length(), vbom);
		message.setColor(Color.BLACK);
		popup.attachChild(message);
		
		final Sprite button = new Sprite(0, 0, rs.checkButton, this.vbom){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionUp()){
					if(shouldResetBoard){
						((TutorialGemboard)SceneManager.getInstance().getGameScene().gemboard).loadTutorial(boardType);
					}
					SceneManager.getInstance().getGameScene().clearChildScene();
					return true;
				}
				return false;
			}
		};
		button.setPosition(popup.getX()+popup.getWidth()-button.getWidth(), popup.getHeight()-button.getHeight());
		popup.attachChild(button);
		this.registerTouchArea(button);
	}
}
