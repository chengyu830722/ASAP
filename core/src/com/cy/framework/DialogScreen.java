package com.cy.framework;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DialogScreen implements Screen {
	public MainGame game;
	private MyDialogBox dialog;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	public DialogScreen(MainGame game) {
		this.game = game;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void hide() {
		this.dispose();
	}

	@Override
	public void render(float delta) {
		// 清屏
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		dialog.draw(batch);
		batch.end();
		dialog.update1f();
	    if (!dialog.isalive)
	    {
	    	dialog.finish();
	    	game.setScreen(game.gamescreen);
	    }
	    //DELAY
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void show() {
		dialog = new MyDialogBox();
		dialog.beginscript("test.xml");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		camera.update();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void dispose() {
		batch.dispose();
		batch=null;
	}

	@Override
	public void resume() {
	}
}