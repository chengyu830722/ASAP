/**
 * 
 */
package com.cy.VisualEffect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cy.framework.GameWorld;
import com.cy.framework.GlobalVal;

/**
 * @author cy
 * 用来制造一个幻影。持续frames帧，逐渐变淡。
 */
public class Phantom extends VisualEffect{
	//纹理
	Texture texture;
	//位置大小
	float x;float y;float width;float height;
	//持续帧数
	int duration=30;
	//存在时间
	int liveFrames;
	//褪色值，褪色越多值越大 0.0f-1
	float fade=0.7f;
	Color color;
	public Phantom(String texname,int frames,float x,float y,float width,float height,Color color, GameWorld gameWorld)
	{
		this.gameWorld=gameWorld;
		texture=GlobalVal.manager.get(texname);
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.color=color;
	}
	
	@Override
	public void render1f(SpriteBatch batch) {
//		batch.setColor(0.4f, 0.4f, (float) (0.8f+0.2*(liveFrames/duration)), 1-fade*liveFrames/duration);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.setColor(color);
		float e=(1-liveFrames*1.0f/duration);
		color.a=e;
		color.r+=0.1*e;
		batch.draw(texture,x-width/2*e,y-height/2*e,width*e,height*e);
		batch.setColor(1,1,1,1);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void update1f() {
		liveFrames++;
		if (liveFrames==duration) {
			alive=false;
		}
	}

}
