package com.lifestudio.scene;


import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;
import com.lifestudio.base.BaseScene;
import com.lifestudio.manager.ResourcesManager;
import com.lifestudio.manager.SceneManager.SceneType;

public class LoadingScene extends BaseScene {

	@Override
	public void createScene() {
		setBackground(new Background(Color.BLACK));
		attachChild( new Text(640, 400, resourcesManager.font, "Loading...", vbom));
		
	}

	// We don't want to do anything while the screen is loading
	@Override
	public void onBackKeyPressed() {
		return;
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	

}
