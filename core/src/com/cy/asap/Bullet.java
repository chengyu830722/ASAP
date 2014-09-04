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

public class Bullet {
	public Sprite sprite;
	public float x;
	public float y;
	public float width;
	public float height;
	// ��Ϊ��ͼ��Ե�Ŀհף�ʵ�ʵ���Ʒ����ײ�����Ӧ����С
	private float factor = 0.7f;
	private float rotation;
	public Body body;
	private boolean alive = true;;
	// Textureֻ����һ��PNG��GPU��
	private static final Texture TEXTURE = new Texture(
			Gdx.files.internal("data/arrow.png"));

	public Bullet(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		rotation = 0;
		TEXTURE.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite = new Sprite(TEXTURE);
		sprite.setSize(width, height);
		sprite.setOrigin(width / 2, height / 2);
		sprite.setPosition(x - width / 2, y - height / 2);
		sprite.setRotation(rotation);
	}

	public void render1f(SpriteBatch batch) {
		x = getX();
		y = getY();
		rotation = getRotationDegrees();
		sprite.setPosition(x - width / 2, y - height / 2);
		sprite.setRotation(rotation);
		sprite.draw(batch);
	}

	public void update1f(SpriteBatch batch) {
		alive = checkInBound();
	}

	public boolean getAlive() {
		return alive;
	}

	private boolean checkInBound() {
		// ����Ƿ�Խ�߽�
		if ((x < (0 - GlobalVal.BOUND))
				|| (x > (GlobalVal.WIDTH + GlobalVal.BOUND))
				|| (y < (0 - GlobalVal.BOUND))
				|| (y > GlobalVal.HEIGHT + GlobalVal.BOUND)) {
			return false;
		} else
			return true;
	}

	// Box2D��غ���
	// BOX2Dԭ�㣬���ϡ�
	public void attachBox2D(World b2world) {
		Vector2 position = new Vector2(x / GlobalVal.M2P, y / GlobalVal.M2P);

		BodyDef bodyDef = new BodyDef();
		// �������ڼ�С�����������е��ٶȡ������Ħ��������ͬ,Ħ�����������нӴ���ʱ��Żᷢ����
		bodyDef.angularDamping = 1f;
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		bodyDef.angle = rotation * MathUtils.degreesToRadians;
		;
		Body body = b2world.createBody(bodyDef);
		this.body = body;

		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(width * factor / 2 / GlobalVal.M2P, height
				* factor / 2 / GlobalVal.M2P);
		// polygonShape.setAsBox(width*factor/2/GlobalVal.M2P,height*factor/2/GlobalVal.M2P,position,
		// rotation);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 10f;
		// ����
		fixtureDef.restitution = 0.1f;
		// Ħ��
		fixtureDef.friction = 1f;
		body.createFixture(fixtureDef);
		polygonShape.dispose();
	}

	public void detachBox2D(World b2world) {
		b2world.destroyBody(body);
		body = null;
	}

	public float getX() {
		return body.getPosition().x * GlobalVal.M2P;
	}

	public float getY() {
		return body.getPosition().y * GlobalVal.M2P;
	}

	public float getRotationDegrees() {
		return body.getAngle() * MathUtils.radiansToDegrees;
	}

}
