package com.cy.framework.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cy.framework.GlobalVal;
import com.cy.framework.MainGame;

public class SelectStageScreen implements Screen, GestureListener {
	private MainGame game;
	private Stage selectStage;
	private Image bgimage;
	//
	float speedx;
	float speedy;

	public SelectStageScreen(MainGame mainGame) {
		this.game = mainGame;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		selectStage.act();
		selectStage.draw();
		// 惯性
		if (!Gdx.input.isTouched()) {
			if ((speedx > 0.001f) || (speedy > 0.001f)) {
				moveCamera(speedx, speedy);
				speedx = speedx * 0.95f;
				speedy = speedy * 0.95f;
			}
		}
		// DELAY
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		selectStage = new Stage(new FitViewport(480, 800));
		bgimage = new Image(GlobalVal.manager.get("data/SelStageBG.png",
				Texture.class));
		selectStage.addActor(bgimage);
		for (int i = 0; i < GlobalVal.stagelist.length; i++) {
			int x = GlobalVal.stagelist[i].x;
			int y = GlobalVal.stagelist[i].y;
			int no = GlobalVal.stagelist[i].stageno;
			LevelFactory fac = new LevelFactory(x, y, no, this.game);
			selectStage.addActor(fac.createLevel());
		}
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GestureDetector(this)); // 设置手势监听
		multiplexer.addProcessor(selectStage); // 设置点击监听
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void hide() {
		this.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		selectStage.dispose();

	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		speedx = velocityX/100.0f;
		speedy = velocityY/100.0f;
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		moveCamera(deltaX, deltaY);
		return false;
	}

	private void moveCamera(float deltaX, float deltaY) {
		selectStage.getViewport().getCamera().position.x -= deltaX;
		selectStage.getViewport().getCamera().position.y += deltaY;
		if (selectStage.getViewport().getCamera().position.x < 240) {
			selectStage.getViewport().getCamera().position.x = 240;
		}
		if (selectStage.getViewport().getCamera().position.x > bgimage
				.getWidth() - 240) {
			selectStage.getViewport().getCamera().position.x = bgimage
					.getWidth() - 240;
		}
		if (selectStage.getViewport().getCamera().position.y < 400) {
			selectStage.getViewport().getCamera().position.y = 400;
		}
		if (selectStage.getViewport().getCamera().position.y > bgimage
				.getHeight() - 400) {
			selectStage.getViewport().getCamera().position.y = bgimage
					.getHeight() - 400;
		}
	}
}
