package com.cy.framework;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.cy.asap.Bullet;
import com.cy.asap.Enemy;

public class Box2DContactListener implements ContactListener {
	//开始接触，代表AAB，不代表一定会发生碰撞，应使用POSTSOLVE
	@Override
	public void beginContact(Contact contact) {
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		Box2DUserData dataA = (Box2DUserData) contact.getFixtureA().getBody()
				.getUserData();
		Box2DUserData dataB = (Box2DUserData) contact.getFixtureB().getBody()
				.getUserData();
		// 当弓箭和ENEMY碰撞时
		if ((dataA == null) || (dataB == null))
			return;
		if ((dataA.classname == "Bullet") && (dataB.classname != "Bullet")) {
				Sound list[] = {
						GlobalVal.manager.get("sound/sd1.mp3", Sound.class),
						GlobalVal.manager.get("sound/sd2.mp3", Sound.class),
						GlobalVal.manager.get("sound/sd3.mp3", Sound.class) };
					Bullet bullet=(Bullet)dataA.obj;
					float v=bullet.body.getLinearVelocity().len();
					if(v>20)
					{
						Enemy temp = (Enemy) dataB.obj;
						temp.sprite.setColor(0, 0, 1, 1);
						int index = GlobalVal.r.nextInt(3);
						int z=3;
					}
		}
		if ((dataA.classname != "Bullet") && (dataB.classname == "Bullet")) {
				Sound list[] = {
						GlobalVal.manager.get("sound/sd1.mp3", Sound.class),
						GlobalVal.manager.get("sound/sd2.mp3", Sound.class),
						GlobalVal.manager.get("sound/sd3.mp3", Sound.class) };
				Bullet bullet=(Bullet)dataB.obj;
				float v=bullet.body.getLinearVelocity().len();
				if(v>20)
				{
					Enemy temp = (Enemy) dataA.obj;
					temp.sprite.setColor(0, 0, 1, 1);
					int index = GlobalVal.r.nextInt(3);
					int z=3;
				}
				// Box2D 并不允许你在回调中改变物理世界，因为你可能会摧毁 Box2D 正在运算的对象!!如果要摧毁对象，见下。
				// final Body toRemove =
				// contact.getFixtureA().getBody().getType() ==
				// BodyType.DynamicBody ?
				// contact.getFixtureA().getBody() :
				// contact.getFixtureB().getBody();
				// Gdx.app.postRunnable(new Runnable() {
				//
				// @Override
				// public void run () {
				// world.destroyBody(toRemove);
				// }
				// });
		}

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
}
