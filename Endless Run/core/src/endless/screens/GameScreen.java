package endless.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import endless.Endless;
import endless.entities.Ground;
import endless.entities.Player;
import endless.input.DragInput;
import endless.terrain.Background;
import endless.utils.debug.RenderableDebug;

public class GameScreen extends ScreenAdapter {
	private Stage stage;
	private ShapeRenderer shaper;
	private Ghost ghost;
	private Background bg;
	private Ground ground;
	private Player player;
	private ArrayList<RenderableDebug> rendDebug;
	private boolean debug = true;

	public GameScreen(Endless game) {
		super(game);
		rendDebug = new ArrayList<RenderableDebug>();
	}

	@Override
	public void show() {
		stage = new Stage(new FitViewport(800, 480));
		shaper = new ShapeRenderer();
		shaper.setProjectionMatrix(stage.getViewport().getCamera().combined);

		bg = new Background();
		rendDebug.add(bg);
		stage.addActor(bg);

		ground = new Ground();
		rendDebug.add(ground);
		stage.addActor(ground);

		player = new Player();
		rendDebug.add(player);
		stage.addActor(player);

		ghost = new Ghost();
		ghost.addListener(new DragInput(player));
		stage.addActor(ghost);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Endless.clearScreen();
		stage.act(delta);
		if (debug) {
			debug();
		} else {
			stage.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void hide() {
		stage.dispose();
		rendDebug.clear();
	}

	private void debug() {
		shaper.begin(ShapeType.Filled);
		for (RenderableDebug rd : rendDebug) {
			rd.debug(shaper);
		}
		shaper.end();
	}

	private class Ghost extends Actor {
		private Ghost() {
			setBounds(0, 0, 800, 480);
		}
	}
}