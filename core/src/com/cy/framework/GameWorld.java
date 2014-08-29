package com.cy.framework;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.cy.asap.Bullet;
import com.cy.asap.Enemy;
import com.cy.asap.Hero;
import com.cy.asap.Rock;


public class GameWorld {
	//主人公
	public Hero pig;
	//狼
    public ArrayList<Bullet> BulletList;
    public ArrayList<Enemy> EnemyList;
    //BOX2D World
	public World b2world;
	public Box2DDebugRenderer b2debugRenderer;
	//摄像头
	public OrthographicCamera camera;
	public OrthographicCamera box2dcamera;
	
	SpriteBatch batch;
	//世界总帧数
	int WorldFames;
	public GameWorld(MainGame mainGame) {
		BulletList=new ArrayList<Bullet>();
		EnemyList=new ArrayList<Enemy>();
		//初始世界摄像头
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		camera.update();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		//初始化BOX2D DEBUG RENDER
		b2debugRenderer=new Box2DDebugRenderer();
		box2dcamera = new OrthographicCamera();
		box2dcamera.setToOrtho(false, 480/GlobalVal.M2P, 800/GlobalVal.M2P);
		box2dcamera.update();
		
		//初始化BOX2D world
		b2world = new World(new Vector2(0, -9.81f), true);
		//初始化起始点
		WorldFames=0;
	}

	public void render1f() {
		// 清屏
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		// 绘制敌人
		for (Enemy temp : EnemyList)
		{
		    temp.render1f(batch);
		}
		// 绘制子弹
		for (Bullet temp : BulletList)
		{
		    temp.render1f(batch);
		}
		// 绘制hero
		//pig.render1f(batch);
		batch.end();
		// 调试代码
		if (GlobalVal.DEBUG)
		{
			b2debugRenderer.render(b2world,box2dcamera.combined);
		}
	}
    //BOX2D libgdx 都是坐下角原点。
	public void update1f() {
		// 更新Enemy
		for (Enemy temp : EnemyList)
		{
		    temp.update1f(batch);
		}
		// 更新子弹
		for (Bullet temp : BulletList)
		{
		    temp.update1f(batch);
		}
		// 更新主人公
		//pig.update1f();
		if(WorldFames%20==0)
		{
			Enemy a=new Enemy(100,800,30,30);
			Enemy b=new Enemy(240,800,50,50);
			Enemy c=new Enemy(340,800,30,30);
			EnemyList.add(a);
			EnemyList.add(b);
			EnemyList.add(c);
			a.attachBox2D(b2world);
			b.attachBox2D(b2world);
			c.attachBox2D(b2world);
			Rock rock1=new  Rock(240,100,480,20);
			rock1.attachBox2D(b2world);
		}
		else
		{
			int x=3;
		}
		WorldFames++;
		//b2world.step(Gdx.graphics.getDeltaTime(), 8, 3);
		b2world.step(1.0f/GameUpdate.MAX_FPS,3, 3);
	}

	public void dispose() {
	}

}
