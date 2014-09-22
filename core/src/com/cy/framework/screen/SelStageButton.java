package com.cy.framework.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cy.framework.GlobalVal;
import com.cy.framework.MainGame;

public class SelStageButton {
	private MainGame game;
	private Image img_cp;
	public float location_x;
	public float location_y;
	public float stageNo = 0;

	public SelStageButton(float x, float y, float num, MainGame mainGame,
			Stage stage) {
		this.location_x = x;
		this.location_y = y;
		this.game = mainGame;
		this.stageNo = num;
		Texture tex = GlobalVal.manager.get("data/SelStageBtn.png", Texture.class);
		img_cp = new Image(tex);
		img_cp.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				GlobalVal.STAGE = stageNo;
				game.setScreen(game.dialogscreen);
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		img_cp.setPosition(this.location_x, this.location_y); // 设置资源位置
		img_cp.setSize(30, 30); // 设置 资源大小
		stage.addActor(img_cp);
	}
}
