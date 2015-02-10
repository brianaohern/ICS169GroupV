package com.teamv.capstone.game;

import java.io.IOException;

import org.andengine.entity.IEntity;
import org.andengine.util.SAXUtils;
import org.andengine.util.debug.Debug;
import org.andengine.util.level.IEntityLoader;
import org.andengine.util.level.LevelLoader;
import org.xml.sax.Attributes;

import com.teamv.capstone.managers.ResourcesManager;

public class EnemyLoader {

	public void loadXMLData(){
		LevelLoader levelLoader = new LevelLoader();
		
		levelLoader.registerEntityLoader("levelset", new IEntityLoader() {
			@Override
			public IEntity onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				return null;
			}
		});
		
		levelLoader.registerEntityLoader("level", new IEntityLoader() {
			@Override
			public IEntity onLoadEntity(final String pEntityName, final Attributes pAttributes) {
				return null;
			}
		});
		
		levelLoader.registerEntityLoader("wave", new IEntityLoader() {

			@Override
			public IEntity onLoadEntity(String pEntityName, Attributes pAttributes) {
				return null;
			}
		});
		
		levelLoader.registerEntityLoader("enemy", new IEntityLoader() {

			@Override
			public IEntity onLoadEntity(String pEntityName, Attributes pAttributes) {
				final String name = SAXUtils.getAttributeOrThrow(pAttributes, "name");
				final String enemyType = SAXUtils.getAttributeOrThrow(pAttributes, "type");
				
				ColorType type = null;
				switch(enemyType){
				case "red":
					type = ColorType.RED;
					break;
				case "blue":
					type = ColorType.BLUE;
					break;
				case "green":
					type = ColorType.GREEN;
					break;
				case "yellow":
					type = ColorType.YELLOW;
					break;
				case "normal":
				default:
					//do nothing for normal and default
				}
		
				return null;
			}
		});

		try{
			levelLoader.loadLevelFromAsset(ResourcesManager.getInstance().activity.getAssets(), "levels/robin.xml");
		} catch (final IOException e) {
			Debug.e(e);
		}
	}
}
