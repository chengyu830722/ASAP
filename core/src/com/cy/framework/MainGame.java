package com.cy.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MainGame extends Game {
	public GameScreen gamescreen;
	public StartScreen startscreen;
	public DialogScreen dialogscreen;
	
	public OrthographicCamera camera;
	public OrthographicCamera verticalcamera;
	
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

		//加载资源
		GlobalVal.manager = new AssetManager();
		LoadAssets(GlobalVal.manager);
		
		this.gamescreen = new GameScreen(this);
		this.startscreen = new StartScreen(this);
		this.dialogscreen=new DialogScreen(this);
		
		// 屏幕变换因子
		factorx = 800.0f / Gdx.graphics.getWidth();
		factory = 480.0f / Gdx.graphics.getHeight();
		setScreen(startscreen);
	}

	@Override
	public void dispose() {
		GlobalVal.manager.clear();
		super.dispose();
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
	
	public void LoadAssets(AssetManager manager)
	{
		// 加载字体
		manager.load("font/chn.fnt", BitmapFont.class);
		// 加载纹理
		TextureParameter param = new TextureParameter();
		param.minFilter = TextureFilter.Linear;
		param.genMipMaps = true;
		GlobalVal.manager.load("data/xigua.png", Texture.class, param);
		GlobalVal.manager.load("data/arrow.png", Texture.class, param);
		GlobalVal.manager.finishLoading();
	}
}

