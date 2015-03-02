package endless.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import endless.Endless;

public class GameOverScreeen extends ScreenAdapter {
	private final int SCORE;
	private Stage stage;
	private Table table;
	private Label gameOver;
	private TextButton title, retry;
	
	public GameOverScreeen(Endless game, int score) {
		super(game);
		SCORE = score;
	}
	
	@Override
	public void show() {
		stage = new Stage(new ScreenViewport());
		table = new Table(skin);
		stage.addActor(table);
		table.setFillParent(true);
		table.add(gameOver = new Label("Game Over", skin)).colspan(2);
		gameOver.setColor(Color.ORANGE);
		table.row();
		table.add("Score:");
		table.add(Integer.toString(SCORE));
		table.row();
		table.add(title = new TextButton("Continue", skin)).fill();
		table.add(retry = new TextButton("Retry", skin)).fill();
		Gdx.input.setInputProcessor(stage);
		title.addCaptureListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(game.titleScreen);
			}
		});
		
		retry.addCaptureListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.gameScreen = new GameScreen(game);
				game.setScreen(game.gameScreen);
			}
		});
	}
	
	@Override
	public void render(float delta) {
		Endless.clearScreen(Color.RED, 1);
		stage.act(delta);
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}
	
	@Override
	public void hide() {
		stage.dispose();
	}
}
