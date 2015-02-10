package com.teamv.capstone.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.teamv.capstone.game.Enemy;
import com.teamv.capstone.game.EnemyType;
import com.teamv.capstone.game.enemies.Wolf;

public class EnemyXMLLoader extends DefaultHandler{
	
	private Enemy enemy = null;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	
	private String name = "";
	private int health;
	private int attack;
	private int turn;
	private int width;
	private int height;
	private EnemyType type;
	private String image = "";


	public void startElement(final String namespaceURI, final String lName, final String qName, final Attributes attrs) {
		switch(qName){
		case "Enemy":
			name = attrs.getValue("name");
			break;
		case "Stats":
			health	= Integer.valueOf(attrs.getValue("HP"));
			attack 	= Integer.valueOf(attrs.getValue("ATTACK"));
			turn 	= Integer.valueOf(attrs.getValue("TURN"));
			break;
		case "Image":
			image = attrs.getValue("value");
			break;
		}
	}

	public void endElement(final String namespaceURI, final String sName, final String qName) {
		switch(qName){
		case "Enemy":
			enemy = new Wolf(null);
		}
	}

}
