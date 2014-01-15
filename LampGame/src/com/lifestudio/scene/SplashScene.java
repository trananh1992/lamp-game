package com.lifestudio.scene;

import android.opengl.GLES20;

import com.lifestudio.base.BaseScene;
import com.lifestudio.manager.SceneManager.SceneType;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

public class SplashScene extends BaseScene{

	private Sprite splash;
	private Sprite fadeSprite;
	
	@Override
	public void createScene() {
		splash = new Sprite(0, 0, resourcesManager.splash_region, vbom);
//		{
//		    @Override
//		    protected void preDraw(GLState pGLState, Camera pCamera) 
//		    {
//		       super.preDraw(pGLState, pCamera);
//		       pGLState.enableDither();
//		    }
//		};
		// TODO: Consertar, piscando : ALPHA 1 -> 0 nao de 255-0
		//
		splash.setPosition(640, 400);
		
		
		fadeSprite = new Sprite(640, 400, resourcesManager.black_region, vbom);
		
		fadeSprite.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		IEntityModifier iem  = new AlphaModifier(6.0f, 1.0f, 0.0f);
		iem.setAutoUnregisterWhenFinished(true);
		fadeSprite.registerEntityModifier(iem);
		
		attachChild(splash);
		attachChild(fadeSprite);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene()
	{
	    splash.detachSelf();
	    splash.dispose();
	    this.detachSelf();
	    this.dispose();
	}

}



