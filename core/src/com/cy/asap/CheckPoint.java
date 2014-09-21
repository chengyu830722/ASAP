package com.cy.asap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cy.framework.GlobalVal;
import com.cy.framework.MainGame;


//中心点  138，538         其他点信息 746,673  1156，200        1368,770   1756,712
public class CheckPoint {

	private MainGame game;  //游戏类的引用   主要用于切换屏幕
	private Image img_cp; // 关卡按钮
	public float location_x;
	public float location_y;
	public float stageNo=0;
	public CheckPoint(float x, float y,  float num,MainGame mainGame,Stage stage)
	{
		this.location_x =x;
		this.location_y = y ;
		this.game = mainGame;
		this.stageNo=num;
		
		Texture tex = GlobalVal.manager.get("data/CK1.png", Texture.class);
		img_cp = new Image(tex);
		
		img_cp.addListener(new InputListener(){
	           @Override
	           public boolean touchDown(InputEvent event, float x, float y,
	                   int pointer, int button) {
               // TODO Auto-generated method stub
	          GlobalVal.STAGE=stageNo;
               game.setScreen(game.gs);
               return super.touchDown(event, x, y, pointer, button);
	           }
	       });
	       
		img_cp.setPosition(this.location_x, this.location_y);  //设置资源位置
		img_cp.setSize(30, 30); //设置 资源大小
		stage.addActor(img_cp);
       
	}	
	
}
