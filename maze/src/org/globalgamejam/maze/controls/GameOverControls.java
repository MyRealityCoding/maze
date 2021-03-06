package org.globalgamejam.maze.controls;

import org.globalgamejam.maze.MazeGame;
import org.globalgamejam.maze.screens.MainMenuScreen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameOverControls extends Stage {

	private MazeGame game;
	
	private int level;
	
	private boolean retry;
	
	public GameOverControls(boolean retry, int level, MazeGame game, int width, int height) {
		super(width, height, true);
		this.game = game;
		this.level = level;
		this.retry = retry;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		super.touchDown(screenX, screenY, pointer, button);
		
		game.setScreen(new MainMenuScreen(game, level, retry));		
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keyCode) {
		super.keyDown(keyCode);
		
		switch (keyCode) {
			case Keys.BACK: case Keys.ESCAPE:
				game.setScreen(new MainMenuScreen(game, level, retry));
				break;
		}
		
		return false;
	}
	
	
}
