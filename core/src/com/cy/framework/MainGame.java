package com.cy.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.cy.framework.screen.DialogScreen;
import com.cy.framework.screen.GameScreen;
import com.cy.framework.screen.SelectStageScreen;
import com.cy.framework.screen.StartScreen;

public class MainGame extends Game {
	public GameScreen gamescreen;
	public StartScreen startscreen;
	public DialogScreen dialogscreen;
	public SelectStageScreen selectstagescreen;
	
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
		this.selectstagescreen=new SelectStageScreen(this);
		
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
		GlobalVal.manager.load("data/SelStageBG.png", Texture.class, param); //加载选关的背景
		GlobalVal.manager.load("data/SelStageBtn.png", Texture.class, param); 
		GlobalVal.manager.load("data/SelStageBtn2.png", Texture.class, param);
		
		GlobalVal.manager.load("data/WindowBG.png", Texture.class, param); //弹出框
		GlobalVal.manager.load("data/Cancel.png", Texture.class, param); //弹出框 取消
		GlobalVal.manager.load("data/OK.png", Texture.class, param); //弹出框 确定
		
		GlobalVal.manager.load("data/hero/hero_0.png", Texture.class, param);
		GlobalVal.manager.load("data/hero/hero_1.png", Texture.class, param);
		GlobalVal.manager.load("data/hero/hero_2.png", Texture.class, param);
		GlobalVal.manager.load("data/hero/hero_3.png", Texture.class, param);
		
		GlobalVal.manager.load("data/dot.png", Texture.class, param);
		
		GlobalVal.manager.load("data/hub/bsxg1.png", Texture.class, param);
		GlobalVal.manager.load("data/hub/bsxg2.png", Texture.class, param);
		GlobalVal.manager.load("data/hub/bsxg3.png", Texture.class, param);
		GlobalVal.manager.load("data/hub/bsxg4.png", Texture.class, param);
		GlobalVal.manager.load("data/hub/bsxg5.png", Texture.class, param);
		
		GlobalVal.manager.load("sound/sd1.mp3", Sound.class);
		GlobalVal.manager.load("sound/sd2.mp3", Sound.class);
		GlobalVal.manager.load("sound/sd3.mp3", Sound.class);
		
		
		GlobalVal.manager.finishLoading();
		
		//加载texpacker
//		manager.load("data/pack", TextureAtlas.class);
//		TextureAtlas tex2=manager.get("data/pack", TextureAtlas.class);
//		Texture tex=manager.get("data/pack", TextureAtlas.class).findRegion("particle-star");
	}
}

