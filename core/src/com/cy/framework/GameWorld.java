package com.cy.framework;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
	// ���˹�
	public Hero pig;
	// ��
	public ArrayList<Bullet> BulletList;
	public ArrayList<Enemy> EnemyList;
	// BOX2D World
	public World b2world;
	public Box2DDebugRenderer b2debugRenderer;
	// ����ͷ
	public OrthographicCamera camera;
	public OrthographicCamera box2dcamera;

	SpriteBatch batch;
	// ������֡��
	int WorldFrames;
	// MainGame
	MainGame mainGame;

	public GameWorld(MainGame mainGame) {
		this.mainGame = mainGame;
		BulletList = new ArrayList<Bullet>();
		EnemyList = new ArrayList<Enemy>();
		// ��ʼ��������ͷ
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		camera.update();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		// ��ʼ��BOX2D DEBUG RENDER
		b2debugRenderer = new Box2DDebugRenderer();
		box2dcamera = new OrthographicCamera();
		box2dcamera.setToOrtho(false, 480 / GlobalVal.M2P, 800 / GlobalVal.M2P);
		box2dcamera.update();

		// ��ʼ��BOX2D world
		b2world = new World(new Vector2(0, -9.81f), true);
		// ��ʼ����ʼ��
		WorldFrames = 0;
		Rock rock1 = new Rock(240, 100, 480, 20);
		rock1.attachBox2D(b2world);
	}

	public void render1f() {
		// ����
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 0.9f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		// ���Ƶ���
		for (Enemy temp : EnemyList) {
			temp.render1f(batch);
		}
		// �����ӵ�
		for (Bullet temp : BulletList) {
			temp.render1f(batch);
		}
		// ����hero
		// pig.render1f(batch);
		batch.end();
		// ���Դ���
		if (GlobalVal.DEBUG) {
			b2debugRenderer.render(b2world, box2dcamera.combined);
			// ��������
			batch.begin();
			mainGame.bitmapFont.drawWrapped(
					batch,
					"enemy����:" + EnemyList.size() + " BodySize"
							+ b2world.getBodyCount(), 10, 40, 480);
			mainGame.bitmapFont.drawWrapped(batch, "frame:" + WorldFrames, 10,
					70, 300);
			mainGame.bitmapFont.drawWrapped(batch, "fps:"
					+ Gdx.app.getGraphics().getFramesPerSecond(), 10, 100, 300);
			batch.end();
		}
	}

	// BOX2D libgdx �������½�ԭ�㡣
	public void update1f() {
		updateUserInput();

		// ����Enemy
		// 1.�����ӵ�״̬
		for (Iterator<Enemy> it = EnemyList.iterator(); it.hasNext();) {
			Enemy temp = it.next();
			if (!temp.getAlive()) {
				temp.detachBox2D(b2world);
				it.remove();
			} else {
				temp.update1f(batch);
			}
		}
		// �����ӵ�
		for (Iterator<Bullet> it = BulletList.iterator(); it.hasNext();) {
			Bullet temp = it.next();
			if (!temp.getAlive()) {
				temp.detachBox2D(b2world);
				it.remove();
			} else {
				temp.update1f(batch);
			}
		}
		// �������˹�
		// pig.update1f();
		if ((WorldFrames % 10 == 0) && (WorldFrames < WorldFrames+600)) {
			Enemy a = new Enemy(100, 800, 30, 30);
			Enemy b = new Enemy(240, 800, 50, 50);
			Enemy c = new Enemy(340, 800, 30, 30);
			EnemyList.add(a);
			EnemyList.add(b);
			EnemyList.add(c);
			a.attachBox2D(b2world);
			b.attachBox2D(b2world);
			c.attachBox2D(b2world);
		} else {
			int x = 3;
		}
		WorldFrames++;
		// Լ�������(constraint solver)�����ڽ��ģ���е�����Լ��,һ��һ����
		// ������Լ���ᱻ���������,Ȼ�����������һ��Լ����ʱ��,���Ǿͻ���΢������һ����
		// Ҫ�õ����õĽ�,������Ҫ��ε�������Լ�������Ծ��б�Ҫ���Ƶ�������Ĵ����Է�ֹ����ѭ�����Ƽ���������Ϊ10�ܽϺõ�ģ��Ч����
		b2world.step(1.0f / GameUpdate.MAX_FPS, 10, 10);
	}

	private void updateUserInput() {
		if (Gdx.input.isTouched() && Gdx.input.justTouched()) {
			float factorx = 480.0f / Gdx.graphics.getWidth();
			float factory = 800.0f / Gdx.graphics.getHeight();
			// �任��800*480��
			float pointx = (int) (Gdx.input.getX() * factorx);
			float pointy = 800 - (int) (Gdx.input.getY() * factory);
			Bullet arrow = new Bullet(pointx, pointy, 80, 16);
			arrow.attachBox2D(b2world);
			arrow.body.setBullet(true);
			arrow.body.setLinearVelocity(20, 0);
			BulletList.add(arrow);
		}
	}

	public void dispose() {
	}

}
