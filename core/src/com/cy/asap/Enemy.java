package com.cy.asap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy {
	public Sprite sprite;
	public float x;
	public float y;
	public float width;
	public float height;
	//因为贴图边缘的空白，实际的物品的碰撞体积，应该略小
	public float factor=0.9f;
	public float rotation;
	
	public Enemy(float x,float y,float width,float height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		rotation=0;
		Texture texture = new Texture(Gdx.files.internal("data/xigua.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite = new Sprite(texture);
		sprite.setSize(width, height);
		sprite.setOrigin(width/2, height/2);
		sprite.setPosition(x-width/2, y-height/2);
		sprite.setRotation(rotation);
	}

	public void render1f(SpriteBatch batch) {
		sprite.draw(batch);
		
	}
	public void update1f(SpriteBatch batch) {
	}
	
	//Box2D相关函数
	public void attachBox2D(World b2world)
	{
		
	}

}
