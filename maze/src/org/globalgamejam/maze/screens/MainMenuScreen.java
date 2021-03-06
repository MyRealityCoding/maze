package org.globalgamejam.maze.screens;

import org.globalgamejam.maze.Assets;
import org.globalgamejam.maze.Levels;
import org.globalgamejam.maze.MazeGame;
import org.globalgamejam.maze.controls.MainMenuControls;
import org.globalgamejam.maze.tweens.ActorTween;
import org.globalgamejam.maze.tweens.SpriteTween;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class MainMenuScreen implements Screen {
	
	private MazeGame game;
	
	private Stage stage;
	
	private Sprite background;
	
	private Batch batch;
	
	private TweenManager tweenManager;
	
	private Sprite minions;
	
	private int nextLevel;
	
	private boolean repeat;
	
	public MainMenuScreen(MazeGame game, int nextLevel) {
		this(game, nextLevel, false);
	}
	
	public MainMenuScreen(MazeGame game, int nextLevel, boolean repeat) {
		this.game = game;
		this.nextLevel = nextLevel;
		this.repeat = repeat;
		
		if (Levels.levels.get(nextLevel) == null) {
			Gdx.app.exit();
		}
	}
	
	public int getNextLevel() {
		return nextLevel;
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		stage.act(delta);
		batch.begin();
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background.draw(batch);
		minions.draw(batch);
		batch.end();
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		if (stage != null) {
			stage.setViewport(width, height);
		} else {
			stage = new MainMenuControls(nextLevel, game, width, height);
			Gdx.input.setInputProcessor(stage);
			Gdx.input.setCatchBackKey(true);
			
			
			final Image image = new Image(Assets.getInstance().get(Assets.LOGO, Texture.class));
			stage.addActor(image);
			
			image.setScale(Gdx.graphics.getHeight() / 1000f);
			image.setColor(1f, 1f, 1f, 0f);
			image.setX(Gdx.graphics.getWidth() / 2 - (image.getWidth() * image.getScaleX()) / 2);
			image.setY(Gdx.graphics.getHeight());
			background.setBounds(0, 0, width, height);
			
			LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = Assets.FONT;
			labelStyle.fontColor = new Color(1f, 1f, 1f, 0.4f);
			
			Label credits = new Label("A GGJ14 Production", labelStyle);

			credits.setFontScale(0.5f);
			credits.setPosition(Gdx.graphics.getWidth() / 2 - credits.getPrefWidth() / 2, 20f);
			stage.addActor(credits);
			
			LabelStyle startStyle = new LabelStyle();
			startStyle.font = Assets.FONT;
			startStyle.fontColor = new Color(1f, 1f, 1f, 1f);
			
			String label = "Enter Level " + nextLevel;
			
			if (repeat) {
				label = "Retry Level " + nextLevel;
			}
			
			Label start = new Label(label, startStyle);
			start.setPosition(Gdx.graphics.getWidth() / 2 - start.getPrefWidth() / 2, Gdx.graphics.getHeight() / 4.5f);
			stage.addActor(start);
			
			minions = new Sprite(Assets.getInstance().get(Assets.MINIONS, Texture.class));
			
			float scale = Gdx.graphics.getWidth() / minions.getWidth();			
			minions.setScale(scale);
			minions.setX(Gdx.graphics.getWidth() / 2 - minions.getWidth() / 2);
			minions.setY(0);
			Tween.to(minions, SpriteTween.ALPHA, 4f)
				.target(0.2f)
				.ease(TweenEquations.easeInOutQuad)
				.repeatYoyo(Tween.INFINITY, 0f)
				.start(tweenManager);
			
			Tween.to(start, ActorTween.ALPHA, 1.7f)
				 .target(0.0f)
				 .ease(TweenEquations.easeInOutCubic)
				 .repeatYoyo(Tween.INFINITY, 0f)
				 .start(tweenManager);
			
			Tween.to(image, ActorTween.ALPHA, 1f)
				  .target(1f)
				  .ease(TweenEquations.easeInOutCubic)
				  .start(tweenManager);
			
			Tween.to(image, ActorTween.POPUP, 1f)
			  .target(Gdx.graphics.getHeight() - image.getHeight() / 1.1f)
			  .ease(TweenEquations.easeInOutElastic)
			  .setCallback(new TweenCallback() {

				@Override
				public void onEvent(int type, BaseTween<?> source) {
					
					Assets.getInstance().get(Assets.MAZEVOICE, Sound.class).play();
					
					Tween.to(image, ActorTween.POPUP, 2f)
					.target(Gdx.graphics.getHeight() - image.getHeight() / 1.3f)
					.ease(TweenEquations.easeInOutQuad)
					.repeatYoyo(Tween.INFINITY, 0f)
					.start(tweenManager);
					
				}
				  
			  })
			  .setCallbackTriggers(TweenCallback.COMPLETE)			  
			  .start(tweenManager);
		}
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		background = new Sprite(Assets.getInstance().get(Assets.BACKGROUND, Texture.class));
		tweenManager = new TweenManager();
		
		Music music = Assets.getInstance().get(Assets.DRIP, Music.class);
		music.setLooping(true);
		music.play();
		music = Assets.getInstance().get(Assets.MENU, Music.class);
		music.setLooping(true);
		music.play();
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
