package endless.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import endless.Endless;
import endless.entities.blocks.BottomBox;
import endless.entities.ground.Ground;
import endless.entities.player.Player;
import endless.input.DragInput;
import endless.terrain.Background;
import endless.terrain.Cloud;
import endless.utils.debug.RenderableDebug;

/**
 * La pantalla de juego
 * 
 * @author Matías
 *
 */
public class GameScreen extends ScreenAdapter {
	private Stage back, front;
	private ShapeRenderer shaper;
	private Ghost ghost;
	private Background bg;
	private Ground ground;
	private Player player;
	private Array<Actor> backActors, frontActors;
	private boolean debug = true; // TEST
	private final float TIMER = 1.5f;
	private float timer, lastY = 0;

	/**
	 * Inicializa la pantalla de juego como objeto
	 * 
	 * @param game
	 *            clase principal
	 */
	public GameScreen(Endless game) {
		super(game);
		backActors = new Array<Actor>();
		frontActors = new Array<Actor>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see endless.screens.ScreenAdapter#show()
	 */
	@Override
	public void show() {
		back = new Stage(new FitViewport(800, 480));
		front = new Stage(new FitViewport(800, 480));

		shaper = new ShapeRenderer();
		shaper.setProjectionMatrix(back.getViewport().getCamera().combined);

		bg = new Background();
		back.addActor(bg);

		ground = new Ground();
		front.addActor(ground);

		player = new Player();
		front.addActor(player);

		// TEST
		front.addActor(new BottomBox(400));
		// /test

		ghost = new Ghost();
		ghost.addListener(new DragInput(player));
		front.addActor(ghost);
		Gdx.input.setInputProcessor(front);

		timer = MathUtils.random() + TIMER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see endless.screens.ScreenAdapter#render(float)
	 */
	@Override
	public void render(float delta) {
		Endless.clearScreen();
		spawnCloud(delta);
		despawnCloud();
		back.act(delta);
		front.act(delta);
		if (debug) {
			debug();
		} else {
			back.draw();
			front.draw();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see endless.screens.ScreenAdapter#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		back.getViewport().update(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see endless.screens.ScreenAdapter#hide()
	 */
	@Override
	public void hide() {
		back.dispose();
		backActors.clear();
		frontActors.clear();
	}

	/**
	 * Utiliza un {@link ShapeRenderer} para marcar las áreas de los {@link Actor} que tienen que dibujarse
	 */
	private void debug() {
		shaper.begin(ShapeType.Filled);
		backActors = back.getActors();
		drawDebug(backActors);
		frontActors = front.getActors();
		drawDebug(frontActors);
		shaper.end();
	}

	/**
	 * Itera el {@link Array} de {@link Actor}, y transforma los elementos que también sean instancia de
	 * {@link RenderableDebug} a una instancia de dicha clase para ejecutar el método
	 * {@link RenderableDebug#debug(ShapeRenderer)}
	 * 
	 * @param actors
	 */
	private void drawDebug(Array<Actor> actors) {
		for (Actor a : actors) {
			if (a instanceof RenderableDebug) {
				((RenderableDebug) a).debug(shaper);
			}
		}
	}

	/**
	 * {@link Actor} fantasma
	 * 
	 * @author Matías
	 *
	 */
	private class Ghost extends Actor {
		/**
		 * Crea un {@link Actor} incorporeo que se encarga de recibir los eventos de entrada táctil por medio de un
		 * {@link DragListener}
		 */
		private Ghost() {
			setBounds(0, 0, 800, 480);
		}
	}

	/**
	 * Crea una nube en una posición aleatoria
	 * 
	 * @param delta
	 */
	private void spawnCloud(float delta) {
		timer -= delta;
		if (timer <= 0) {
			float rand = 0;
			do {
				rand = MathUtils.random(345, 445) + MathUtils.random();
				if (rand < lastY - 52 || rand > lastY + 52) {
					break;
				}
			} while (true);
			lastY = rand;
			Cloud temp = new Cloud(800, rand);
			back.addActor(temp);
			timer = MathUtils.random() + TIMER;
		}
	}

	/**
	 * Elimina las nubes que salieron de la pantalla
	 */
	private void despawnCloud() {
		Iterator<Actor> iter = back.getActors().iterator();
		while (iter.hasNext()) {
			Actor a = iter.next();
			if (a instanceof Cloud) {
				Cloud temp = (Cloud) a;
				if (temp.getX() + temp.getWidth() < 0) {
					temp.remove();
				}
			}
		}
	}
}