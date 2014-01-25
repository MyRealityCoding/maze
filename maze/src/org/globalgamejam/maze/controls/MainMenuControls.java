package org.globalgamejam.maze.controls;

import java.io.IOException;

import org.globalgamejam.maze.MazeGame;
import org.globalgamejam.maze.io.MazeFileReader;
import org.globalgamejam.maze.screens.IngameScreen;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainMenuControls extends Stage {

	private MazeGame game;
	
	public MainMenuControls(MazeGame game, int width, int height) {
		super(width, height, true);
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		super.touchDown(screenX, screenY, pointer, button);
		
		MazeFileReader reader = new MazeFileReader();
		
		try {
			game.setScreen(new IngameScreen(game, reader.read("test.mz")));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
}