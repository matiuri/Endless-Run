package endless.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import endless.Endless;

/**
 * La pantalla de título
 * 
 * @author Matías
 *
 */
public class TitleScreen extends ScreenAdapter {
	private Stage stage;
	private Table table;
	private TextButton play, exit;

	/**
	 * Crea la pantalla de título como objeto
	 * 
	 * @param game
	 *            clase principal
	 */
	public TitleScreen(Endless game) {
		super(game);
	}

	@Override
	public void show() {
		stage = new Stage(new ScreenViewport());
		table = new Table(skin);
		stage.addActor(table);
		table.setFillParent(true);
		table.add("Endless Run").padRight(10);
		table.add(Endless.VERSION);
		table.row();
		table.add(play = new TextButton("Play", skin)).colspan(2).fill();
		table.row();
		table.add(exit = new TextButton("Exit", skin)).colspan(2).fill();

		play.addCaptureListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.gameScreen = new GameScreen(game);
				game.setScreen(game.gameScreen);
			}
		});

		exit.addCaptureListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});

		Gdx.input.setInputProcessor(stage);
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