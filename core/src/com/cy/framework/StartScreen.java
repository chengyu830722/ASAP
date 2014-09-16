package com.cy.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class StartScreen implements Screen{
	//TODO 建立一个Stage，把控件添加到Stage中。写各控件的回调函数。
	
	private MainGame game;  //游戏类的引用   主要用于切换屏幕
	private Stage startStage ;  //建立一个舞台类主要用 装载 图片（演员）
	private Texture bgTexture ;  //背景图片纹理
	
    private Texture btn_StartGameTexture;  //设置开始游戏 纹理
    private Image img_start; // 进入游戏 图片按钮

    private SpriteBatch batch; //绘制类
    
  
	public StartScreen(MainGame mainGame) {
		//构造函数传入GMAE类 控制Screen切换 
         this.game =mainGame;
	}


	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
	       batch.begin();    
	       batch.draw(bgTexture,0, 0, 480, 800);
	       batch.end();
	       
	       startStage.act();
	       startStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		//初始化都在 这里
		batch= new SpriteBatch();
		startStage = new Stage();
	
		bgTexture = new Texture(Gdx.files.internal("data/bg.jpg"));
		btn_StartGameTexture = new Texture(Gdx.files.internal("data/Enter.png"));
		img_start = new Image(btn_StartGameTexture);
		
		img_start.addListener(new InputListener(){
	           @Override
	           public boolean touchDown(InputEvent event, float x, float y,
	                   int pointer, int button) {
               // TODO Auto-generated method stub
               game.setScreen(game.gs);
               return super.touchDown(event, x, y, pointer, button);
	           }
	           
	       });
	       
	  img_start.setPosition(startStage.getWidth()/5, startStage.getHeight()/8);
	  startStage.addActor(img_start);
      Gdx.input.setInputProcessor(startStage);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}


}
