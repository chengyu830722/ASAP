package com.cy.framework.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.cy.framework.GlobalVal;

public class SelStageButton extends Button{

	public float location_x;
	public float location_y;
	public float stageNo = 0;
	public ICallBack cbhandler=null;
	public SelStageButton(Drawable up, Drawable down, Drawable checked)
	{
		super(up, down, checked);
	}
	public void setStageInfo(float x, float y, float num,  ICallBack cb ) {
		this.location_x = x;
		this.location_y = y;
		this.stageNo = num;
		this.setPosition(this.location_x, this.location_y);
		this.setSize(30, 30);
		this.cbhandler = cb;
		this.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				GlobalVal.STAGE = stageNo;
				cbhandler.doSelectStage(stageNo+"");  //执行 SelectStageScreen 的选关方法
				//game.setScreen(game.dialogscreen);
				return false;
			}
		});
	}
}
