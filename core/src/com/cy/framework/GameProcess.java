package com.cy.framework;


public class GameProcess extends GameUpdate {
	MainGame mainGame;
	GameWorld gWorld;
	Boolean pause;

	// GameProcess控制游戏进程。暂停，结束。
	// 在运行缓慢时，通过控制worldrender，worldupdate来跳帧。
	// 游戏的RENDER和UPDATE由GameWorld负责。
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
