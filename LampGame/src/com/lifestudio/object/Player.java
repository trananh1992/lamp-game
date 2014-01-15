package com.lifestudio.object;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.lifestudio.manager.ResourcesManager;

public abstract class Player extends AnimatedSprite {

	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------

	private Body body;
	private boolean canRun = false;
	private int footContacts = 0; // avoid multiple jumping in the air
	
	
	public Player(float pX, float pY, VertexBufferObjectManager vbo,
			Camera camera, PhysicsWorld physicsWorld) {
		super(pX, pY, ResourcesManager.getInstance().player_region, vbo);
		createPhysics(camera, physicsWorld);
		camera.setChaseEntity(this);
	}

	public abstract void onDie();

	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld) {
		body = PhysicsFactory.createBoxBody(physicsWorld, this,
				BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

		body.setUserData("player");
		body.setFixedRotation(true); // body will not rotate

		/*
		 * physics connector (so player`s sprite, will automatically update its
		 * position, following body updates)
		 */
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body,
				true, false) {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);

				if (getY() <= 0) {
					onDie();
				}

				if (canRun) {
					body.setLinearVelocity(new Vector2(5, body.getLinearVelocity().y));
				}
			}
		});
	}

	/*
	 * run and animate, we will execute this method, after player`s first touch
	 * of the scene. Latter, every next screen touch will cause player to jump.
	 * 
	 * We defined duration for each player`s tile/frame (in milliseconds) used
	 * for animation, we also set player to animate (continuously by setting
	 * last parameter to true) animating player`s tiled region from first to
	 * last tile (means from 0 to 2)
	 */
	public void setRunning() {
		canRun = true;

		
		final long[] PLAYER_ANIMATE = new long[] { 	40, 40, 40, 40, 40, 40, 40,
													40, 40, 40, 40, 40, 40, 40,
													40, 40, 40, 40, 40, 40, 40,
													40, 40, 40, 40, 40, 40};
		animate(PLAYER_ANIMATE, 0, 26, true);
		
//		final long[] PLAYER_ANIMATE = new long[] { 	40, 40, 40 };
//		animate(PLAYER_ANIMATE, 0, 2, true);
	}
	
	public void jump()
	{
		if (footContacts < 1) 
		{
			return;
		}
		
	    body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, 12)); 
	}
	
	public void increaseFootContacts(){
		footContacts++;
	}
	
	public void decreaseFootContacts(){
		footContacts--;
	}

}
