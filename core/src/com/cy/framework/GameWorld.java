package com.cy.framework;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.cy.asap.Bullet;
import com.cy.asap.Enemy;
import com.cy.asap.Hero;


public class GameWorld {
	//���˹�
	public Hero pig;
	//��
    public ArrayList<Bullet> BulletList;
    public ArrayList<Enemy> EnemyList;
    //BOX2D World
	public World b2world;
	public Box2DDebugRenderer b2debugRenderer;
	//����ͷ
	public OrthographicCamera camera;
	public OrthographicCamera box2dcamera;
	
	SpriteBatch batch;
	//������֡��
	int WorldFames;
	public GameWorld(MainGame mainGame) {
		BulletList=new ArrayList<Bullet>();
		EnemyList=new ArrayList<Enemy>();
		//��ʼ��������ͷ
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		camera.update();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		//��ʼ��BOX2D DEBUG RENDER
		b2debugRenderer=new Box2DDebugRenderer();
		box2dcamera = new OrthographicCamera();
		box2dcamera.setToOrtho(false, 480/GlobalVal.M2P, 800/GlobalVal.M2P);
		box2dcamera.update();
		
		//��ʼ��BOX2D world
		b2world = new World(new Vector2(0, -9.81f), true);
		//��ʼ����ʼ��
		WorldFames=0;
	}

	public void render1f() {
		// ����
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		// ���Ƶ���
		for (Enemy temp : EnemyList)
		{
		    temp.render1f(batch);
		}
		// �����ӵ�
		for (Bullet temp : BulletList)
		{
		    temp.render1f(batch);
		}
		// ����hero
		//pig.render1f(batch);
		batch.end();
		// ���Դ���
		if (GlobalVal.DEBUG)
		{
			b2debugRenderer.render(b2world,box2dcamera.combined);
		}
	}

	public void update1f() {
		
		// ����Enemy
		for (Enemy temp : EnemyList)
		{
		    temp.update1f(batch);
		}
		// �����ӵ�
		for (Bullet temp : BulletList)
		{
		    temp.update1f(batch);
		}
		// �������˹�
		//pig.update1f();
		if(WorldFames==0)
		{
			Enemy a=new Enemy(0,0,50,50);
			Enemy b=new Enemy(0,400,50,50);
			Enemy c=new Enemy(240,800,50,50);
			EnemyList.add(a);
			EnemyList.add(b);
			EnemyList.add(c);
			a.attachBox2D(b2world);
			b.attachBox2D(b2world);
			c.attachBox2D(b2world);
		}
	}

	public void dispose() {
	}

}