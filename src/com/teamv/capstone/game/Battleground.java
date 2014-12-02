package com.teamv.capstone.game;

import java.util.ArrayList;

import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.teamv.capstone.gemboard.gems.Gem;

/*
 * Battleground contains the enemies and player
 * Player is basically the player stats and UI
 * Enemy is basically a sprite + stats
 * 
 * 
 */
public class Battleground {
	
	ArrayList<Enemy> enemies;
	ArrayList<Battle> level;
	int currentBattle = 0;
	Player player;
	
	public Battleground(PhysicsWorld physicsWorld){
		player = new Player();
		enemies = new ArrayList<Enemy>();
	}
	
	public void nextBattle(Battle battle){
		//set up next battle
	}
	
	public void nextBattle(){
		Battle battle = level.get(currentBattle);
		if(battle != null)
			this.nextBattle(battle);
		currentBattle++;
	}
	
	public int calculateDamage(ArrayList<Gem> gems){
		int damage = 0;
//		for(Gem gem : Gemboard.connectedGems){
//			damage++;
//		}
		damage += gems.size();
		return damage;
	}
}
