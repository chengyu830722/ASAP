package com.cy.framework;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cy.asap.CheckPoint;
import com.cy.asap.Enemy;

public class SelectStageScreen implements Screen,GestureListener{
	private MainGame game;  
	private Stage selectStage ;  
    private OrthographicCamera  camera;   //摄像头
    private SpriteBatch batch; 
    Texture bgTexture;
    private ArrayList<CheckPoint> cpList=new ArrayList<CheckPoint>();
   
    public SelectStageScreen(MainGame mainGame)
    {
    	this.game =mainGame;
    }
    
   
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		camera.update();
	        batch.setProjectionMatrix(camera.combined);
	        
	        
	        
		   Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
	       batch.begin();    
			  batch.draw(bgTexture,0, 0,1920, 1200);
	       batch.end();
	       
	       selectStage.act();
	       selectStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		batch= new SpriteBatch();
		selectStage = new Stage();
		
		camera= new OrthographicCamera(480, 800);
		camera.setToOrtho(false, 480, 800);
		camera.update();
		
	
		 bgTexture = GlobalVal.manager.get("data/CP_BJ.jpg", Texture.class);

		 
		//中心点  138，538         其他点信息 746,673  1156，200        1368,770   1756,712
		 //会存到 全局变量里面 循环创建
		CheckPoint c1=new CheckPoint(138,538,1, this.game, this.selectStage);
		cpList.add(c1);
		CheckPoint c2=new CheckPoint(746,673,2, this.game, this.selectStage);
		cpList.add(c2);
		CheckPoint c3=new CheckPoint(1156,200,3, this.game, this.selectStage);
		cpList.add(c3);
		CheckPoint c4=new CheckPoint(1368,770,4, this.game, this.selectStage);
		cpList.add(c4);
		CheckPoint c5=new CheckPoint(1756,712,5, this.game, this.selectStage);
		cpList.add(c5);
		

		InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(this)); //设置手势监听
        multiplexer.addProcessor(selectStage); //设置点击监听
        Gdx.input.setInputProcessor(multiplexer);

		
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
		// TODO Auto-generated method stub
		camera.translate(velocityX,velocityY);
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
		return false;
	}

}
