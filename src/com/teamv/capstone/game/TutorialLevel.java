package com.teamv.capstone.game;

import com.teamv.capstone.game.enemies.Imp;

public class TutorialLevel extends Level{
		
	public TutorialLevel(String name){
		super(name);
		final Wave w1 = new Wave();
		w1.add(new Imp(this.vbom));
		this.addWave(w1);
	}
}
