package com.teamv.capstone.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.game.GameActivity;
import com.teamv.capstone.managers.ResourcesManager;


public abstract class PopUpScene extends CameraScene{

	int width=0, height=0;
	Rectangle popup;
	protected ResourcesManager rs;
	protected VertexBufferObjectManager vbom;
	
	public PopUpScene(){
		super(ResourcesManager.getInstance().camera);
		init();
	}
	
	public PopUpScene(Camera camera){
		super(camera);
		init();
	}
	
	public PopUpScene(int width, int height){
		super(ResourcesManager.getInstance().camera);
		this.width = width;
		this.height = height;
		init();
	}
	
	public PopUpScene(int width, int height, Camera camera){
		super(camera);
		this.width = width;
		this.height = height;
		init();
	}
	
	protected void init(){
		rs = ResourcesManager.getInstance();
		vbom = ResourcesManager.getInstance().vbom;
		setBackgroundEnabled(false);
	}
	
	protected void addRectangle(){
		popup = new Rectangle(GameActivity.WIDTH/2 - width/2, GameActivity.HEIGHT/2 - height/2, width, height, vbom);
		this.attachChild(popup);
	}
}
