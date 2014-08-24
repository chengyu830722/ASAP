package com.cy.framework;


public class GameProcess extends GameUpdate {
	MainGame mainGame;
	GameWorld gWorld;
	Boolean pause;

	// GameProcess������Ϸ���̡���ͣ��������
	// �����л���ʱ��ͨ������worldrender��worldupdate����֡��
	// ��Ϸ��RENDER��UPDATE��GameWorld����
	GameProcess(MainGame mainGame) {
		pause = false;
		this.mainGame = mainGame;
		loadnewworld();
	}

	public void loadnewworld() {
		gWorld = new GameWorld(mainGame);
	}

	@Override
	public void worldrender() {
		if (!pause) {
			gWorld.render1f();
		}
	}

	@Override
	public void worldupdate() {
		if (!pause) {
			gWorld.update1f();
		}
	}

	public void dispose() {
		gWorld.dispose();
	}
}
