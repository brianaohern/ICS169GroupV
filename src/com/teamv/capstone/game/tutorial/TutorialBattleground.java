package com.teamv.capstone.game.tutorial;


import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import com.teamv.capstone.game.Battleground;
import com.teamv.capstone.game.ColorType;
import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.managers.SceneManager;
import com.teamv.capstone.scenes.BaseScene;

public class TutorialBattleground extends Battleground{
	boolean showInstructions = true;
	public TutorialBattleground(BaseScene gameScene) {
		super(gameScene);
	}
	
	public void enterBattle(int green, int blue, int red, int yellow, int bomb){
		super.enterBattle(green, blue, red, yellow, bomb);
		if(showInstructions){
//			((TutorialGemboard)SceneManager.getInstance().getGameScene().gemboard).ClearBoard();
			switch(this.level.getCurrentWaveCount()){
			case 1:
				ResourcesManager.getInstance().engine.registerUpdateHandler(new TimerHandler(0.9f, new ITimerCallback() 
				{
					public void onTimePassed(final TimerHandler pTimerHandler) 
					{
						SceneManager.getInstance().getGameScene().enterInstructionScene("Good job, now finish it off!", false, 0);
//						((TutorialGemboard)SceneManager.getInstance().getGameScene().gemboard).overrideColor(ColorType.RED);
					}
				}));
				break;
			case 2:
				ResourcesManager.getInstance().engine.registerUpdateHandler(new TimerHandler(0.9f, new ITimerCallback() 
				{
					public void onTimePassed(final TimerHandler pTimerHandler) 
					{
						SceneManager.getInstance().getGameScene().enterInstructionScene("Great! Now try these blue gems!", false, 0);
//						((TutorialGemboard)SceneManager.getInstance().getGameScene().gemboard).overrideColor(ColorType.BLUE);
					}
				}));
				break;
			case 3:
				//ResourcesManager.getInstance().activity.gameToast("tutorial3 instructions health");
				ResourcesManager.getInstance().engine.registerUpdateHandler(new TimerHandler(0.9f, new ITimerCallback() 
				{
					public void onTimePassed(final TimerHandler pTimerHandler) 
					{
						SceneManager.getInstance().getGameScene().enterInstructionScene("Use this potion to restore your health.", false, 0);
//						((TutorialGemboard)SceneManager.getInstance().getGameScene().gemboard).overrideColor(ColorType.BLUE);
					}
				}));
				showInstructions = false;
				break;
			default:
				ResourcesManager.getInstance().activity.gameToast("TutorialBattleground.enterBattle.default");
			}
//			((TutorialGemboard)SceneManager.getInstance().getGameScene().gemboard).shadeBoard();
		}
	}
}
