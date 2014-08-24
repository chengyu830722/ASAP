package com.cy.framework;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	public MainGame game;
	// TestProcess p;
	public GameProcess p;

	public GameScreen(MainGame game) {
		this.game = game;
		p = new GameProcess(game);
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
		//worldrender+worldupdate+
		p.update();
	}

	@Override
	public void show() {
	}

	@Override
	public void dispose() {
		p.dispose();
	}

	@Override
	public void resume() {
	}
}
