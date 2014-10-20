package com.cy.asap;

import com.badlogic.gdx.graphics.Texture;
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
import com.cy.framework.Box2DUserData;
import com.cy.framework.GameWorld;
import com.cy.framework.GlobalVal;

public class Enemy {
	public Sprite sprite;
	public float x;
	public float y;
	public float width;
	public float height;
	// 因为贴图边缘的空白，实际的物品的碰撞体积，应该略小
	private float factor = 0.7f;
	private float rotation;
	private Body body;
	public boolean alive = true;
	public GameWorld gameWorld;
	//状态
	enum Status{NORMAL,AFTERHIT};
	Status status=Status.NORMAL;
	public Enemy(float x, float y, float width, float height,GameWorld gameWorld) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gameWorld=gameWorld;
		rotation = 0;
		Texture tex = GlobalVal.manager.get("data/xigua.png", Texture.class);
		sprite = new Sprite(tex);
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

	public void update1f() {
		//超出世界边界，死亡
		alive = checkInBound();
	}

	public boolean getAlive() {
		return alive;
	}

	private boolean checkInBound() {
		// 检测是否超越边界
		if ((x < (0 - GlobalVal.BOUND))
				|| (x > (GlobalVal.WIDTH + GlobalVal.BOUND))
				|| (y < (0 - GlobalVal.BOUND))
				|| (y > GlobalVal.HEIGHT + GlobalVal.BOUND)) {
			return false;
		} else
			return true;
	}

	// Box2D相关函数
	// BOX2D原点，左上。
	public void attachBox2D(World b2world) {
		Vector2 position = new Vector2(x / GlobalVal.M2P, y / GlobalVal.M2P);

		BodyDef bodyDef = new BodyDef();
		// 阻尼用于减小物体在世界中的速度。阻尼跟摩擦有所不同,摩擦仅在物体有接触的时候才会发生。
		bodyDef.angularDamping = 1f;
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		bodyDef.angle = rotation * MathUtils.degreesToRadians;
		
		Body body = b2world.createBody(bodyDef);
		this.body = body;
		//将指针存入这里，之后可以通过getUserData取得body对应的Enemy对象。
		Box2DUserData data=new Box2DUserData("Enemy",this);
		body.setUserData(data);

		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(width * factor / 2 / GlobalVal.M2P, height
				* factor / 2 / GlobalVal.M2P);
		// polygonShape.setAsBox(width*factor/2/GlobalVal.M2P,height*factor/2/GlobalVal.M2P,position,
		// rotation);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 1f;
		// 弹性
		fixtureDef.restitution = 0.1f;
		// 摩擦
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

	public void kill() {
		alive=false;
		//随机生成能量球
		int type=GlobalVal.r.nextInt(3)+1;
		if (type>=1&&type<=3) {
			EnergyBall ball=new EnergyBall(x, y, 20, 20, type, gameWorld);
			gameWorld.EBList.add(ball);
		}
	}

}
