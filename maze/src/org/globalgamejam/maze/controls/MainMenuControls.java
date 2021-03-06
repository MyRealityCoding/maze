package org.globalgamejam.maze.controls;

import java.io.IOException;

import org.globalgamejam.maze.Assets;
import org.globalgamejam.maze.Levels;
import org.globalgamejam.maze.MazeGame;
import org.globalgamejam.maze.io.MazeFileReader;
import org.globalgamejam.maze.screens.IngameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainMenuControls extends Stage {

	private MazeGame game;
	
	private int level;
	
	public MainMenuControls(int level, MazeGame game, int width, int height) {
		super(width, height, true);
		this.game = game;
		this.level = level;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		super.touchDown(screenX, screenY, pointer, button);
		
		MazeFileReader reader = new MazeFileReader();
		
		try {
			Assets.getInstance().get(Assets.MENU, Music.class).stop();
			
			
			String name = Levels.levels.get(level);
			game.setScreen(new IngameScreen(level, game, reader.read(name)));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
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
				Gdx.app.exit();
				break;
		}
		
		return false;
	}
	
	
}
