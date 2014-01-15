package com.lifestudio.scene;

import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;
import org.andengine.engine.camera.*;

import android.opengl.GLES20;
import android.util.Log;
import android.widget.Toast;

import com.lifestudio.base.BaseScene;
import com.lifestudio.manager.ResourcesManager;
import com.lifestudio.manager.SceneManager;
import com.lifestudio.manager.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{

	// built-in AndEngine MenuScene class
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;
	private Random rand;
	private float randAlpha;
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
		
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0); // activity.finish() sometimes don't help
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	
	// -- Private Methods
	private void createMenuChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0,0);
		
		// Animate the menu buttons, scale up when touched
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
		final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.options_region, vbom), 1.2f, 1);
		
		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(optionsMenuItem);
		
		menuChildScene.buildAnimations(); // -<<<<<<<<<<
		menuChildScene.setBackgroundEnabled(false);
		
		playMenuItem.setPosition(playMenuItem.getX() + 320, playMenuItem.getY() + 70);
		optionsMenuItem.setPosition(optionsMenuItem.getX() + 320, optionsMenuItem.getY() + 50);
		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
	}

	
	private void createBackground() {
		Sprite menu_bg = new Sprite(640, 400, resourcesManager.menu_background_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		}; 
		
		Sprite light = new Sprite(564,280, resourcesManager.light_region,vbom);
		
		light.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		rand = new Random();
		randAlpha = rand.nextFloat();
		
		
		final LoopEntityModifier entityModifier = new LoopEntityModifier(
				new IEntityModifierListener() {
		           

					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
							//randAlpha = rand.nextFloat();
					}

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						// EMPTY
					}
    },
    
    100,
    
    new ILoopEntityModifierListener() {
		
		@Override
		public void onLoopStarted(LoopModifier<IEntity> pLoopModifier, int pLoop,
				int pLoopCount) {
			//System.out.println(randAlpha);
			
		}
		
		@Override
		public void onLoopFinished(LoopModifier<IEntity> pLoopModifier, int pLoop,
				int pLoopCount) {
			// TODO Auto-generated method stub
			
		}
	}, 
	
	new SequenceEntityModifier(
			new AlphaModifier(0.3f, randomInRange(0.6f,1.0f), 0.6f),
            new AlphaModifier(0.2f, 0.6f, randomInRange(0.6f,1.0f)),
            new DelayModifier(0.5f)
			)
	
				
				
	);
		
		light.registerEntityModifier(entityModifier);
		
		/*
		IEntityModifier iem  = new AlphaModifier(6.0f, 1.0f, 0.0f);
		iem.setAutoUnregisterWhenFinished(true);
		light.registerEntityModifier(iem);
		 */
		
		//final LoopEntityModifier iem = new LoopEntityModifier(pEntityModifierListener, pLoopCount, pLoopModifierListener, pEntityModifier)
		
		// Part that animates the sprite sheet
		//AnimatedSprite light_lamp = new AnimatedSprite(563,280, resourcesManager.light_region,vbom);
		
		attachChild(menu_bg);
		attachChild(light);
		
		//attachChild(light_lamp);
		//light_lamp.animate(200);
	}
	
	private float getAlphaRandom() {
		randAlpha = rand.nextFloat();
		return randAlpha;
	}
	
	private float randomInRange(float min, float max) {
		return (float) (Math.random() * (max-min) + min);
	}
	
	private float getFadeOutDuration() {
		return 2.0f;
	}
	
	private float getFadeInDuration() {
		return 1.0f;
	}
	

//	private void createTiledSprite()
//	{
//	        AnimatedSprite as = new AnimatedSprite(0, 0, tiledTextureRegion, vbo);
//	        long[] frameDurration = {100, 100, 100};
//	        as.animate(frameDurration);
//	        scene.attachChild(as);
//	}
	


	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		
		switch(pMenuItem.getID()){
			case MENU_PLAY:
				//Load Game Scene MODAFOCA!
	            SceneManager.getInstance().loadGameScene(engine);
				return true;
				
			case MENU_OPTIONS:
				ResourcesManager.getInstance().pauseMusic();
//				
//				if (ResourcesManager.getInstance().getMusic().isPlaying()) {
//					ResourcesManager.getInstance().getMusic().pause();
//				} else {
//					ResourcesManager.getInstance().play();
//				}
				return true;
			default:
				return false;	
		}
	}

}
