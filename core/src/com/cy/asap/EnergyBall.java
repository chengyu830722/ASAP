package com.cy.asap;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cy.VisualEffect.Phantom;
import com.cy.framework.GameWorld;
import com.cy.framework.GlobalVal;

//能量球，产生后，向主人公方向移动。
public class EnergyBall {
	// 纹理
	Texture texture;
	// 位置大小
	float x;
    float y;
	float width;
	float height;
	// 存在时间
	int liveFrames=0;
	// 速度
	float v;
	GameWorld gameWorld;
	boolean alive = true;
	// 当前速度分量
	float curvx;
	float curvy;

	// 能量球类型
	int type = 0;
	
	// 颜色
	Color color=new Color();

	EnergyBall(float x, float y, float width, float height, int type,
			GameWorld gameWorld) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gameWorld = gameWorld;
		this.type = type;
		texture = GlobalVal.manager.get("data/dot.png", Texture.class);
		v = 1;
	}

	public void update1f() {
		float dstx=gameWorld.hero.getCenterX();
		float dsty=gameWorld.hero.getCenterY();
		if (!gameWorld.hero.alive) {
			// 主人公死亡，能量球保持现在的速度飞出。
			x += curvx;
			y += curvy;
		} else {
			float sx = dstx - x;
			float sy = dsty - y;
			float distance = (float) Math.sqrt(Math.pow(sx, 2)+ Math.pow(sy, 2));
			if (distance > v) {
				curvx = v * sx / distance;
				x += curvx;
				curvy = v * sy / distance;
				y += curvy;
			} else {
				// 被主人公吸收
				alive = false;
				gameWorld.hero.absorbEnergyBall(type);
			}
		}
		v=v+0.3f;
		liveFrames++;
		//每1帧产生一个残影
		Phantom phantom=new Phantom("data/dot.png",12,x,y,width,height,color,this.gameWorld);
		gameWorld.VEList.add(phantom);
	}
	public void render1f(SpriteBatch batch) {
		switch (type) {
		case 1://红色能量
			color.set(1,0,0,1);
			batch.setColor(color);
			break;
		case 2://绿色能量
			color.set(0,1,0,1);
			batch.setColor(color);
			break;
		case 3://蓝色能量
			color.set(0,0,1,1);
			batch.setColor(color);
			break;
		default:
			break;
		}
		batch.draw(texture, x - width / 2, y - height / 2, width, height);
		batch.setColor(1,1,1,1);
	}
	public boolean getAlive()
	{
		return alive;
	}
}
