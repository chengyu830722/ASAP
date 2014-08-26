package com.cy.asap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.cy.framework.GlobalVal;

public class Enemy {
	public Sprite sprite;
	public float x;
	public float y;
	public float width;
	public float height;
	//因为贴图边缘的空白，实际的物品的碰撞体积，应该略小
	public float factor=0.9f/GlobalVal.M2P;
	public float rotation;
	private Body body;
	
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
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(x,y);
		Body body = b2world.createBody(bodyDef);
		this.body = body;
		
		PolygonShape polygonShape = new PolygonShape();
		Vector2 position=new Vector2(x*factor,y*factor);
		polygonShape.setAsBox(width*factor/2,height*factor/2,position, rotation);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.friction=1;
		body.createFixture(fixtureDef);
		polygonShape.dispose();
	}
	public void detachBox2D(World b2world)
	{
		b2world.destroyBody(body);
		body=null;
	}
	public float getBOX2DX()
	{
		return body.getPosition().x*GlobalVal.M2P;
		
		sprite.setOrigin(bounds.width/2, bounds.height/2);
		sprite.setSize(bounds.width,bounds.height);
		sprite.setPosition(getBox2DX()-bounds.width/2, getBox2DY()-bounds.height/2);
		rotation = body.getAngle() * MathUtils.radiansToDegrees;
		sprite.setRotation(rotation);
		sprite.draw(batch);
	}
	public float getBOX2DY()
	{
		return body.getPosition().x*GlobalVal.M2P;
	}
	
}
