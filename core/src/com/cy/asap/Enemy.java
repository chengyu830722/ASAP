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
	//��Ϊ��ͼ��Ե�Ŀհף�ʵ�ʵ���Ʒ����ײ�����Ӧ����С
	public float factor=0.9f;
	public float rotation;
	private Body body;
	//���е�ENEMYֻ����һ��PNG��GPU��
	private static final Texture TEXTURE=new Texture(Gdx.files.internal("data/xigua.png"));
	
	public Enemy(float x,float y,float width,float height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		rotation=0;
		TEXTURE.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite = new Sprite(TEXTURE);
		sprite.setSize(width, height);
		sprite.setOrigin(width/2, height/2);
		sprite.setPosition(x-width/2, y-height/2);
		sprite.setRotation(rotation);
	}

	public void render1f(SpriteBatch batch) {
		x=getX();
		y=getY();
		rotation=getRotationDegrees();
		sprite.setPosition(x-width/2, y-height/2);
		sprite.setRotation(rotation);
		sprite.draw(batch);
	}
	public void update1f(SpriteBatch batch) {
	}
	
	//Box2D��غ���
	//BOX2Dԭ�㣬���ϡ�
	public void attachBox2D(World b2world)
	{
		Vector2 position=new Vector2(x/GlobalVal.M2P,y/GlobalVal.M2P);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.angularDamping = 1f;
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		bodyDef.angle =rotation* MathUtils.degreesToRadians;;
		Body body = b2world.createBody(bodyDef);
		this.body = body;
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(width*factor/2/GlobalVal.M2P,height*factor/2/GlobalVal.M2P);
//		polygonShape.setAsBox(width*factor/2/GlobalVal.M2P,height*factor/2/GlobalVal.M2P,position, rotation);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 10f;
		//����
		fixtureDef.restitution =0f;
		//Ħ��
		fixtureDef.friction = 1f;
		body.createFixture(fixtureDef);
		polygonShape.dispose();
	}
	public void detachBox2D(World b2world)
	{
		b2world.destroyBody(body);
		body=null;
	}
	public float getX()
	{
		return body.getPosition().x*GlobalVal.M2P;
	}
	public float getY()
	{
		return body.getPosition().y*GlobalVal.M2P;
	}
	public float getRotationDegrees()
	{
		return body.getAngle() * MathUtils.radiansToDegrees;
	}

}
