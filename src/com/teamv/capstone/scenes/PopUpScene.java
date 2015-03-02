package com.teamv.capstone.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.CameraScene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.teamv.capstone.managers.ResourcesManager;


public abstract class PopUpScene extends CameraScene{

	protected int width=0, height=0;
	protected ResourcesManager rs;
	protected VertexBufferObjectManager vbom;
	
	public PopUpScene(){
		this(ResourcesManager.getInstance().camera);
	}
	
	public PopUpScene(Camera camera){
		super(camera);
		rs = ResourcesManager.getInstance();
		vbom = ResourcesManager.getInstance().vbom;
		setBackgroundEnabled(false);
	}
}
