package com.teamv.capstone.game;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.teamv.capstone.managers.ResourcesManager;
import com.teamv.capstone.scenes.BaseScene;
import com.teamv.capstone.utility.Point;

public abstract class Enemy extends HealthBarEntity{
	
	public static final int LEFTALIGN = 0;
	public static final int RIGHTALIGN = 1;

	public boolean isTarget = false;
	public boolean isDead = false;
	protected int startTurnCount, currentTurnCount;
	protected Text turnCountText;
	protected Sprite typeIcon;
	
	private Point buffer;
	//protected Sprite auto, manual;
	protected Sprite target;
	
	public Enemy(float x, float y, ITextureRegion region, VertexBufferObjectManager vbom) {
		super(x, y, region, vbom);
		turnCountText = new Text(0, 0, ResourcesManager.getInstance().font, "", 20, vbom);
		turnCountText.setScale(1.5f);
		//auto = new Sprite(0, 0, ResourcesManager.getInstance().targetAuto, vbom);
		//manual = new Sprite(0, 0, ResourcesManager.getInstance().targetManual, vbom);
		//auto.setVisible(false);
		//manual.setVisible(false);
		target = new Sprite(0, 0, ResourcesManager.getInstance().targetDefault, vbom);
		target.setVisible(false);
	}
	
	public Enemy(ITextureRegion region, VertexBufferObjectManager vbom){
		this(0, 0, region, vbom);
	}
	
	protected void init(){
		currentHealth = startHealth;
		buffer = new Point(50, 50);

		currentTurnCount = startTurnCount;

		typeIcon.setPosition(this.getX(), this.getY()-10);
		
		// HEALTH BAR STUFFS
		String healthBarStatus = "HP " + currentHealth + "/" + startHealth;
		healthBarText.setPosition(this);
		healthBarText.setText(healthBarStatus);
		
		// temp hardcode
		healthBarWidth  = (int) (this.getWidth() + buffer.x);
		healthBarWidth  = 250;
		//ResourcesManager.getInstance().activity.gameToast(""+healthBarWidth);
		healthBarHeight = 30;
		healthBar.setPosition(this.getX() + buffer.x, this.getY() - buffer.y);
		healthBar.setWidth(healthBarWidth);
		healthBar.setHeight(25);
		healthBar.setColor(Color.RED);
		
		l1.setColor(Color.RED);
		l2.setColor(Color.RED);
		l3.setColor(Color.RED);
		l4.setColor(Color.RED);
		l1.setLineWidth(10);
		l2.setLineWidth(10);
		l3.setLineWidth(10);
		l4.setLineWidth(10);
		
		// TURN COUNT STUFFS
		turnCountText.setPosition(this.getX() + healthBarWidth+buffer.x, this.getY());
		updateTurnCount();
	}
	
	protected void setup(int health, int attack, int turnCounter, ColorType type, float scale){
		this.startHealth = health;
		this.attack = attack;
		this.startTurnCount = turnCounter;
		this.setType(type);
		this.setScale(scale);
	}
	
	public void onDie(){
		Battleground.currentWave.remove(this);
		isDead = true;
		cleanUp();
		updateTurnCount();
	}
	
	public void cleanUp(){
		turnCountText.detachSelf();
		turnCountText.dispose();
		typeIcon.detachSelf();
		typeIcon.dispose();
		super.cleanUp();
	}
	
	public void attachToScene(BaseScene gameScene){
		super.attachToScene(gameScene);
		gameScene.registerTouchArea(this);
		gameScene.attachChild(typeIcon);
		gameScene.attachChild(turnCountText);
		gameScene.attachChild(target);
	}
	
	public void setPosition(float pX, float pY){
		start.set(pX, pY);
		super.setPosition(pX, pY);
		target.setPosition(pX, pY);
		
		healthBarText.setX(pX + (buffer.x));
		healthBarText.setY(pY - (buffer.y + 20));
		
		healthBar.setX(pX + buffer.x);
		healthBar.setY(pY - buffer.y);
		
		typeIcon.setX(pX);
		typeIcon.setY(pY - buffer.y - 10);
		
		turnCountText.setX(pX + healthBarWidth+buffer.x);
		turnCountText.setY(pY - (buffer.y + 20));
		
		l1.setPosition(start.x+buffer.x, start.y-buffer.y, start.x+buffer.x+healthBar.getWidth(), start.y-buffer.y);
		l2.setPosition(start.x+buffer.x, start.y-buffer.y, start.x+buffer.x, start.y-buffer.y+healthBar.getHeight());
		l3.setPosition(start.x+buffer.x,start.y-buffer.y+healthBar.getHeight(), start.x+buffer.x+healthBar.getWidth(), start.y-buffer.y+healthBar.getHeight());
		l4.setPosition(start.x+buffer.x+healthBar.getWidth(), start.y-buffer.y, start.x+buffer.x+healthBar.getWidth(), start.y-buffer.y+healthBar.getHeight());
	}
	
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
    {
		if (pSceneTouchEvent.isActionUp()){
			this.setColor(Color.WHITE);
			if(isTarget){
				this.clearEntityModifiers();
				target.setVisible(false);
				this.isTarget = false;
				return true;
			}
			resetTarget();
			target.setVisible(true);
			target.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(1.5f, 0.95f, 1.05f), new ScaleModifier(1.5f, 1.05f, 0.95f))));
			isTarget = true;
	    } else if(pSceneTouchEvent.isActionDown()){
			switch(this.getUserData().toString()){
			case "RED":
				this.setColor(Color.RED);
				break;
			case "BLUE":
				this.setColor(Color.BLUE);
				break;
			case "GREEN":
				this.setColor(Color.GREEN);
				break;
			case "YELLOW":
				this.setColor(Color.YELLOW);
				break;
			}
	    }
		
		return true;
    }
	
	private void resetTarget(){
		for(Enemy enemy : Battleground.currentWave.getEnemies()){
			enemy.clearEntityModifiers();
			enemy.isTarget = false;
			enemy.target.setVisible(false);
		}
	}
	
	protected void setType(ColorType type){
		this.setUserData(type);
		ResourcesManager rs =  ResourcesManager.getInstance();
		switch(type){
		case RED:
			typeIcon = new Sprite(0, 0, rs.red_gem, rs.vbom);
			break;
		case BLUE:
			typeIcon = new Sprite(0, 0, rs.blue_gem, rs.vbom);
			break;
		case GREEN:
			typeIcon = new Sprite(0, 0, rs.green_gem, rs.vbom);
			break;
		case YELLOW:
			typeIcon = new Sprite(0, 0, rs.yellow_gem, rs.vbom);
			break;
		default:
			ResourcesManager.getInstance().activity.gameToast("Enemy: setType.default");
			break;
		}
		typeIcon.setWidth(40);
		typeIcon.setHeight(40);
	}
	
	public void updateTurnCount(){
		turnCountText.setText(currentTurnCount + "");
	}
	
	public int getCurrentTurnCount(){
		return currentTurnCount;
	}
	
	public void resetCurrentTurnCount(){
		currentTurnCount = startTurnCount;
	}
	
	public void decrementCurrentTurnCount(){
		if (currentTurnCount > 0)
			currentTurnCount--;
	}
}
