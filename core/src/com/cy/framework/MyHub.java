package com.cy.framework;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class MyHub {
	private Texture energyBall1;
	private Texture energyBall2;
	private Texture energyBall3;
	
	private Animation ignitionBallAni;
	private TextureRegion ignitionTex;
	
	int curEnergyType;
	int curEnergyLv;
	
	int ignitionlv;
	
	//状态
	enum HubStatus{NORMAL,IGNITION};
	HubStatus status=HubStatus.NORMAL;
	float stateTime=0;
	
	//配置HUB的各种对象的位置
	private float BALL1X=100;
	private float BALL1Y=80;
	private float BALLRADIUS=30;
	private float IGNITIONRADIUS=40;
	private float BALLBETWEEN=10;
			
	// 初始化纹理，动画
	public MyHub() {
		TextureRegion[] ignitionBallFrames= new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			Texture texture = GlobalVal.manager.get("data/hub/bsxg"+(i+1)+".png");
			TextureRegion region = new TextureRegion(texture);
			ignitionBallFrames[i]=region;
		}
		ignitionBallAni = new Animation(0.1f, ignitionBallFrames);
		ignitionBallAni.setPlayMode(PlayMode.NORMAL);
		energyBall1=GlobalVal.manager.get("data/dot.png");
		energyBall2=GlobalVal.manager.get("data/dot.png");
		energyBall3=GlobalVal.manager.get("data/dot.png");
	}
	
	//设置能量状态
	public void setEnergyStatus(int lv,int type)
	{
		curEnergyLv=lv;
		curEnergyType=type;
		status=HubStatus.IGNITION;
	}
	//点燃灯的动画
	public void playIgnition(int lv)
	{
		ignitionlv=lv;
		status=HubStatus.IGNITION;
		stateTime=0;
	}
	public void render1f(SpriteBatch batch)
	{
		//绘制texture
		switch (curEnergyType) {
		case 1:
			batch.setColor(Color.RED);
			break;
		case 2:
			batch.setColor(Color.GREEN);
			break;
		case 3:
			batch.setColor(Color.BLUE);
			break;
		default:
			break;
		}
		for (int i = 0; i < curEnergyLv; i++) {
			float x=BALL1X+(BALLRADIUS*2+BALLBETWEEN)*i;
			batch.draw(energyBall1, x-BALLRADIUS, BALL1Y-BALLRADIUS, BALLRADIUS*2, BALLRADIUS*2);
		}
		//播放动画ignitionBall
		switch (status) {
		case IGNITION:
			if (curEnergyLv==0) {
				try {
					throw new Exception("IGNITION when energylv=0");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (ignitionBallAni.isAnimationFinished(stateTime)) {
				status=HubStatus.NORMAL;
				break;
			}
			ignitionTex=ignitionBallAni.getKeyFrame(stateTime);
			stateTime += Gdx.graphics.getDeltaTime();
			float x=BALL1X+(BALLRADIUS*2+BALLBETWEEN)*(curEnergyLv-1);
			batch.draw(ignitionTex, x-IGNITIONRADIUS,BALL1Y-IGNITIONRADIUS,IGNITIONRADIUS*2,IGNITIONRADIUS*2);	
			break;
		case NORMAL:
			break;
		default:
			break;
		}
		batch.setColor(Color.WHITE);
	};
	
	public void update1f()
	{
		
	};
	
	
}
