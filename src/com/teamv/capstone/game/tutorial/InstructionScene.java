package com.teamv.capstone.game.tutorial;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.teamv.capstone.game.GameActivity;
import com.teamv.capstone.managers.SceneManager;
import com.teamv.capstone.scenes.PopUpScene;

public class InstructionScene extends PopUpScene{
	
	Sprite popup;
	
	public InstructionScene(){
		super();
	}
	
	public InstructionScene(int width, int height, String text, boolean shouldResetBoard, int boardType) {
		this();
		setUp(width, height, text, shouldResetBoard, boardType);
		if(shouldResetBoard){
			((TutorialGemboard)SceneManager.getInstance().getGameScene().gemboard).loadTutorial(boardType);
		}
		//setBackground(new Background(Color.BLACK));
	}
	
	public void setUp(int width, int height, String text, boolean shouldResetBoard, int boardType){
		popup = new Sprite(GameActivity.WIDTH/2 - width/2, GameActivity.HEIGHT/2-height/2, rs.bgBox, vbom);
		popup.setWidth(width);
		popup.setHeight(height);
		this.attachChild(popup);
	
		Text message= new Text(40, 0, rs.font, text, text.length(), vbom);
		message.setAutoWrap(AutoWrap.WORDS);
		message.setAutoWrapWidth(1080f);
		message.setColor(Color.BLACK);
		popup.attachChild(message);
		
		final Sprite button = new Sprite(0, 0, rs.checkButton, this.vbom){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionUp()){
					SceneManager.getInstance().getGameScene().clearChildScene();
					return true;
				}
				return false;
			}
		};
		float scale = 1.0f;
		button.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(
				new ScaleModifier(1f, scale-0.05f, scale+0.05f), new ScaleModifier(1f, scale+0.05f, scale-0.05f))));
		button.setPosition(popup.getX()+popup.getWidth()-button.getWidth(), popup.getHeight()-button.getHeight());
		popup.attachChild(button);
		this.registerTouchArea(button);
	}
}
