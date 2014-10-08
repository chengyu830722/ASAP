package com.cy.asap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cy.framework.GlobalVal;

public class Hero {
	enum status{fly,shoot};
	public status status;
	public Animation flyAnimation;
	//飞行动画5帧
	public int FLYFRAMES=5;
	public Animation shootAnimation;
	//射击动画4帧
	public int SHOOTFRAMES=4;
	public float stateTime;
	
	//位置大小
	float x;float y;float width;float height;
	public Hero() {
		stateTime=0;
		status=status.fly;
		TextureRegion[] flyFrames= new TextureRegion[FLYFRAMES];
		for (int i = 0; i < FLYFRAMES; i++) {
			Texture texture = GlobalVal.manager.get("data/hero/hero_fly_"+i+".png");
			TextureRegion region = new TextureRegion(texture);
			flyFrames[i]=region;
		}
		flyAnimation = new Animation(0.025f, flyFrames);
		flyAnimation.setPlayMode(PlayMode.REVERSED);
		TextureRegion[] shootFrames= new TextureRegion[SHOOTFRAMES];
		for (int i = 0; i < FLYFRAMES; i++) {
			Texture texture = GlobalVal.manager.get("data/hero/hero_shoot_"+i+".png");
			TextureRegion region = new TextureRegion(texture);
			shootFrames[i]=region;
		}
		shootAnimation = new Animation(0.025f, shootFrames);
	}

	public void render1f(SpriteBatch batch) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime+=Gdx.graphics.getDeltaTime();
		TextureRegion tex=null;
		batch.begin();
		switch (status) {
		case fly:
			tex=flyAnimation.getKeyFrame(stateTime);
			break;
		case shoot:
			tex=shootAnimation.getKeyFrame(stateTime);
			if (shootAnimation.isAnimationFinished(stateTime)) {
				status=status.fly;
			}
			break;
		default:
			break;
		}
		batch.draw(tex,x,y,width,height);
		batch.end();
	}
	public void fly()
	{
		status=status.fly;
	}
	public void shoot()
	{
		status=status.shoot;
	}
	public void update1f() {

	}
}
