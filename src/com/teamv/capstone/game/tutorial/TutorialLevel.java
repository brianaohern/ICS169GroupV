package com.teamv.capstone.game.tutorial;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import com.teamv.capstone.game.Level;
import com.teamv.capstone.game.Wave;
import com.teamv.capstone.game.enemies.*;
import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.managers.SceneManager;

public class TutorialLevel extends Level{
	
	public TutorialLevel(String name){
		super(name);
		final Wave w1 = new Wave();
		final Wave w2 = new Wave();
		final Wave w3 = new Wave();
		
		// Tutorial enemies
		w1.add(new Wolf(this.vbom, 4, 3, 3));
		w2.add(new Wolf(this.vbom, 4, 3, 3));
		w2.add(new DireWolf(this.vbom, 6, 3, 2));
		w3.add(new Imp(this.vbom, 10, 5, 4));
		w3.add(new DireWolf(this.vbom, 6, 3, 1));
		w3.add(new Wolf(this.vbom, 4, 3, 3));
		
		this.addWave(w1);
		this.addWave(w2);
		this.addWave(w3);
	}
	
	public void nextWave(){
		if(currentWave == 1){
			ResourcesManager.getInstance().engine.registerUpdateHandler(new TimerHandler(0.9f, new ITimerCallback() 
			{
				public void onTimePassed(final TimerHandler pTimerHandler) 
				{
					SceneManager.getInstance().getGameScene().enterInstructionScene("Match the gem type to do extra damage.", true, 2);
				}
			}));
		}else if(currentWave == 2){
			ResourcesManager.getInstance().engine.registerUpdateHandler(new TimerHandler(0.9f, new ITimerCallback() 
			{
				public void onTimePassed(final TimerHandler pTimerHandler) 
				{
					SceneManager.getInstance().getGameScene().enterInstructionScene("The bomb will consume surrounding gems, adding power to your attack.", true, 3);
				}
			}));
		}
		super.nextWave();
	}
	
	
	
}
