package com.teamv.capstone.scenes;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;

import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.managers.SceneManager;
import com.teamv.capstone.managers.SceneManager.SceneType;

public class PauseMenuScene extends PopUpScene implements IOnMenuItemClickListener{
	
	private ResourcesManager rs;
	private MenuScene menuScene;
	
	private final int MENU_RESUME = 0;
	private final int MENU_QUIT = 1;
	private final int MENU_MENU = 2;
	
	private GameScene gs;
	
	public PauseMenuScene(GameScene gs){
		super();
		this.gs = gs;
	}
	
	public void init(){
		super.init();
		rs = ResourcesManager.getInstance();
		
		menuScene = new MenuScene(rs.camera);
		
		final IMenuItem resumeMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RESUME, rs.resumeButton, rs.vbom), 3f, 2.5f);
		final IMenuItem quitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_QUIT, rs.quitButton, rs.vbom), 3f, 2.5f);
		final IMenuItem menuMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_MENU, rs.menuButton, rs.vbom), 3f, 2.5f);
		
		menuScene.addMenuItem(resumeMenuItem);
		menuScene.addMenuItem(quitMenuItem);
		menuScene.addMenuItem(menuMenuItem);
		
		menuScene.buildAnimations();
		menuScene.setBackgroundEnabled(false);
		
		final float x = resumeMenuItem.getX() + resumeMenuItem.getWidth()*0.9f;
		final float buffer = resumeMenuItem.getHeight()*2;
		final float offset = resumeMenuItem.getHeight()*5;
		
		resumeMenuItem.setPosition(x, resumeMenuItem.getY() - buffer - offset);
		quitMenuItem.setPosition(x, quitMenuItem.getY() - offset);
		menuMenuItem.setPosition(x, menuMenuItem.getY() + buffer - offset);
		
		menuScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuScene);
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
	    switch(pMenuItem.getID())
	    {
	        case MENU_RESUME:
	        	gs.clearChildScene();
	            return true;
	        case MENU_QUIT:
	        	System.exit(0);
	            return true;
	        case MENU_MENU:
	        	SceneManager.getInstance().setScene(SceneType.SCENE_MENU);
	        	return true;
	        default:
	            return false;
	    }
	}
	
}
