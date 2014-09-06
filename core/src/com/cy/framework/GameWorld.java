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
	// 主人公
	public Hero pig;
	// 狼
	public ArrayList<Bullet> BulletList;
	public ArrayList<Enemy> EnemyList;
	// BOX2D World
	public World b2world;
	public Box2DDebugRenderer b2debugRenderer;
	// 摄像头
	public OrthographicCamera camera;
	public OrthographicCamera box2dcamera;

	SpriteBatch batch;
	// 世界总帧数
	int WorldFrames;
	// MainGame
	MainGame mainGame;

	public GameWorld(MainGame mainGame) {
		this.mainGame = mainGame;
		BulletList = new ArrayList<Bullet>();
		EnemyList = new ArrayList<Enemy>();
		// 初始世界摄像头
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		camera.update();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		// 初始化BOX2D DEBUG RENDER
		b2debugRenderer = new Box2DDebugRenderer();
		box2dcamera = new OrthographicCamera();
		box2dcamera.setToOrtho(false, 480 / GlobalVal.M2P, 800 / GlobalVal.M2P);
		box2dcamera.update();

		// 初始化BOX2D world
		b2world = new World(new Vector2(0, -9.81f), true);
		// 初始化起始点
		WorldFrames = 0;
		Rock rock1 = new Rock(240, 100, 480, 20);
		rock1.attachBox2D(b2world);
	}

	public void render1f() {
		// 清屏
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 0.9f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		// 绘制敌人
		for (Enemy temp : EnemyList) {
			temp.render1f(batch);
		}
		// 绘制子弹
		for (Bullet temp : BulletList) {
			temp.render1f(batch);
		}
		// 绘制hero
		// pig.render1f(batch);
		batch.end();
		// 调试代码
		if (GlobalVal.DEBUG) {
			b2debugRenderer.render(b2world, box2dcamera.combined);
			// 绘制文字
			batch.begin();
			mainGame.bitmapFont.drawWrapped(
					batch,
					"enemy数量:" + EnemyList.size() + " BodySize"
							+ b2world.getBodyCount(), 10, 40, 480);
			mainGame.bitmapFont.drawWrapped(batch, "frame:" + WorldFrames, 10,
					70, 300);
			mainGame.bitmapFont.drawWrapped(batch, "fps:"
					+ Gdx.app.getGraphics().getFramesPerSecond(), 10, 100, 300);
			batch.end();
		}
	}

	// BOX2D libgdx 都是坐下角原点。
	public void update1f() {
		updateUserInput();

		// 更新Enemy
		// 1.更新子弹状态
		for (Iterator<Enemy> it = EnemyList.iterator(); it.hasNext();) {
			Enemy temp = it.next();
			if (!temp.getAlive()) {
				temp.detachBox2D(b2world);
				it.remove();
			} else {
				temp.update1f(batch);
			}
		}
		// 更新子弹
		for (Iterator<Bullet> it = BulletList.iterator(); it.hasNext();) {
			Bullet temp = it.next();
			if (!temp.getAlive()) {
				temp.detachBox2D(b2world);
				it.remove();
			} else {
				temp.update1f(batch);
			}
		}
		// 更新主人公
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
		// 约束求解器(constraint solver)：用于解决模拟中的所有约束,一次一个。
		// 单个的约束会被完美的求解,然而当我们求解一个约束的时候,我们就会稍微耽误另一个。
		// 要得到良好的解,我们需要多次迭代所有约束。所以就有必要控制迭代计算的次数以防止无限循环，推荐迭代次数为10能较好的模拟效果。
		b2world.step(1.0f / GameUpdate.MAX_FPS, 10, 10);
	}

	private void updateUserInput() {
		if (Gdx.input.isTouched() && Gdx.input.justTouched()) {
			float factorx = 480.0f / Gdx.graphics.getWidth();
			float factory = 800.0f / Gdx.graphics.getHeight();
			// 变换到800*480下
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
