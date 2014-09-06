package com.cy.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MainGame extends Game {
	public GameScreen gs;
	public StartScreen startscreen;
	public OrthographicCamera camera;
	public OrthographicCamera verticalcamera;
	public BitmapFont bitmapFont;
	public Music Music;
	public float musicvolume;
	public float soundvolume;

	// 屏幕变换因子
	public float factorx;
	public float factory;

	@Override
	public void create() {
		musicvolume = 0.5f;
		soundvolume = 0.6f;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		camera.update();

		verticalcamera = new OrthographicCamera();
		verticalcamera.setToOrtho(false, 800, 480);
		verticalcamera.rotate(90);
		verticalcamera.translate(-160, 160);
		verticalcamera.update();

		// 加载字体
		bitmapFont = new BitmapFont(Gdx.files.internal("font/chn.fnt"), false);
		bitmapFont.setScale(0.8f);
		this.gs = new GameScreen(this);

		// 屏幕变换因子
		factorx = 800.0f / Gdx.graphics.getWidth();
		factory = 480.0f / Gdx.graphics.getHeight();

		this.startscreen = new StartScreen(this);
		setScreen(startscreen);
	}

	public void playmusic(String name) {
		if (Music != null) {
			Music.dispose();
		}
		Music = Gdx.audio.newMusic(Gdx.files.internal("music/" + name));
		Music.setVolume(musicvolume);
		Music.setLooping(true);
		Music.play();
	}
}
