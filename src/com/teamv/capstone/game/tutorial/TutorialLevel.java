package com.teamv.capstone.game.tutorial;

import com.teamv.capstone.game.Level;
import com.teamv.capstone.game.Wave;
import com.teamv.capstone.game.enemies.*;

public class TutorialLevel extends Level{
	
	public TutorialLevel(String name){
		super(name);
		final Wave w1 = new Wave();
		final Wave w2 = new Wave();
		final Wave w3 = new Wave();
		
		w1.add(new Wolf(this.vbom));
		w2.add(new Wolf(this.vbom));
		w2.add(new DireWolf(this.vbom));
		w3.add(new Imp(this.vbom));
		w3.add(new DireWolf(this.vbom));
		w3.add(new Wolf(this.vbom));
		
		this.addWave(w1);
		this.addWave(w2);
		this.addWave(w3);
	}
}
