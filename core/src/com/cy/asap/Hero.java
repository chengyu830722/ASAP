package com.cy.asap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cy.framework.GameWorld;
import com.cy.framework.GlobalVal;

public class Hero {
	enum enumStatus{fly,shoot,flytoshoot};
	public enumStatus status;
	public GameWorld gameWorld;
	//飞行动画5帧
	public Animation flyAnimation;
	public int FLYFRAMES=4;
	
	//射击动画4帧
	public Animation shootAnimation;
	public int SHOOTFRAMES=4;
	public float stateTime;
	
	//位置大小
	float x;float y;float width;float height;
	float dstX;float dstY;
	float moveframes;
	public boolean alive = true;
	
	//能量系统
	int curEnergyType=0;
	int curEnergyLv=0;
	public Hero(float x,float y ,float width ,float height,GameWorld world) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.gameWorld=world;
		stateTime=0;
		status=enumStatus.fly;
		TextureRegion[] flyFrames= new TextureRegion[FLYFRAMES];
		for (int i = 0; i < FLYFRAMES; i++) {
			Texture texture = GlobalVal.manager.get("data/hero/hero_"+i+".png");
			TextureRegion region = new TextureRegion(texture);
			flyFrames[i]=region;
		}
		flyAnimation = new Animation(0.35f, flyFrames);
		flyAnimation.setPlayMode(PlayMode.LOOP);
		TextureRegion[] shootFrames= new TextureRegion[SHOOTFRAMES];
		for (int i = 0; i < FLYFRAMES; i++) {
			Texture texture = GlobalVal.manager.get("data/hero/hero_"+i+".png");
			TextureRegion region = new TextureRegion(texture);
			shootFrames[i]=region;
		}
		shootAnimation = new Animation(0.1f, shootFrames);
		flyAnimation.setPlayMode(PlayMode.LOOP);
	}

	public void render1f(SpriteBatch batch) {
		stateTime+=Gdx.graphics.getDeltaTime();
		TextureRegion tex=null;
		switch (status) {
		case fly:
			tex=flyAnimation.getKeyFrame(stateTime);
			break;
		case shoot:
			tex=shootAnimation.getKeyFrame(stateTime);
//			if (shootAnimation.isAnimationFinished(stateTime)) {
//				status=enumStatus.fly;
				//射出
//				Bullet arrow = new Bullet(x, y, 40, 8);
//				arrow.attachBox2D(gameWorld.b2world);
//				arrow.body.setBullet(true);
//				arrow.body.setLinearVelocity(30, 0);
//				gameWorld.BulletList.add(arrow);
//			}
			batch.setColor(1,0,0,0.5f);
			break;
		case flytoshoot:
			tex=shootAnimation.getKeyFrame(stateTime);
		default:
			break;
		}
		batch.draw(tex,x,y,width,height);
		batch.setColor(1,1,1,1f);
	}
	public void fly()
	{
		status=enumStatus.fly;
	}
	public void shoot()
	{
		status=enumStatus.shoot;
	}
	public void flytoshoot(float X,float Y)
	{
		//设定移动的状态，和目标位置，移动帧数。
		status=enumStatus.flytoshoot;
		dstX=0;
		dstY=Y-height/2;
		//20帧内移动到目标地点。
		moveframes=10;
	}
	public void update1f() {
		switch (status) {
		case fly:
			break;
		case shoot:
			if (shootAnimation.isAnimationFinished(stateTime)) {
				status=enumStatus.fly;
			}
			break;
		case flytoshoot:
			x+=(dstX-x)/moveframes;
			y+=(dstY-y)/moveframes;
			moveframes--;
			if (moveframes==0) {
				status=enumStatus.shoot;
				//移动到地点先射出箭，再播放攻击动画
				Bullet arrow = new Bullet(x+width/2, y+height/2, 40, 8);
				arrow.attachBox2D(gameWorld.b2world);
				arrow.body.setBullet(true);
				arrow.body.setLinearVelocity(30, 0);
				gameWorld.BulletList.add(arrow);
			}
		default:
			break;
		}
	}

	//吸收能量,
	public void absorbEnergyBall(int type) {
		switch (type) {
		case 1:
			if (alive) {
				
			}
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		default:
			break;
		}
	}
	
	public float getCenterX()
	{
		return x+width/2;
	}
	public float getCenterY()
	{
		return y+height/2;
	}
}
