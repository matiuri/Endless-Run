package endless.screens;

import java.util.Iterator;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import endless.Endless;
import endless.entities.blocks.boxes.Box;
import endless.entities.ground.Ground;
import endless.entities.player.Player;
import endless.input.DragInput;
import endless.terrain.Background;
import endless.terrain.Cloud;
import endless.utils.concurrent.RandomYCloud;
import endless.utils.debug.RenderableDebug;
import endless.utils.levelgen.LevelGen;
import endless.utils.physics.Collision;

/**
 * La pantalla de juego
 * 
 * @author Matías
 *
 */
public class GameScreen extends ScreenAdapter {
	// <Box2D>
	private World world;
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera b2dCam;
	// </Box2d>
	
	private Stage back, front;
	private ShapeRenderer shaper;
	private Ghost ghost;
	private Background bg;
	private Ground ground;
	private Player player;
	private Array<Actor> backActors, frontActors;
	private final float TIMER_CLOUDS = 1.5f, TIMER_BOXES;
	{
		if (Gdx.app.getType() == ApplicationType.Desktop) {
			TIMER_BOXES = 2f;
		} else {
			TIMER_BOXES = 2.5f;
		}
	}
	private float timer_clouds, accum;
	private Label score;
	private int points = 0;
	private boolean gameOver = false, back_front = false;;
	
	// Threading
	private Thread randCloudThread, levelGenThread;
	// RandomYCloud thread
	private RandomYCloud randCloud;
	public volatile float lastY_clouds = 0;
	public volatile boolean checkedYCloud, randomYCloudThreadRun;
	public final Object lockMonitor_RandomYCloud = new Object();
	// LevelGen Thread
	private LevelGen levelGen;
	
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
		Box2D.init();
		world = new World(new Vector2(0, -9.81f), true);
		b2dr = new Box2DDebugRenderer();
		b2dCam = new OrthographicCamera(8, 4.8f);
		world.setContactListener(new Collision(this));
		
		back = new Stage(new FitViewport(800, 480));
		front = new Stage(new FitViewport(800, 480));
		
		b2dCam.position.x = front.getViewport().getCamera().position.x / 100f;
		b2dCam.position.y = front.getViewport().getCamera().position.y / 100f;
		b2dCam.update();
		
		shaper = new ShapeRenderer();
		shaper.setProjectionMatrix(back.getViewport().getCamera().combined);
		
		bg = new Background();
		back.addActor(bg);
		
		ground = new Ground(this);
		front.addActor(ground);
		
		player = new Player(world);
		front.addActor(player);
		
		score = new Label("Score: ", skin);
		score.setPosition(0, 480 - score.getHeight());
		front.addActor(score);
		
		ghost = new Ghost();
		ghost.addListener(new DragInput(player));
		front.addActor(ghost);
		Gdx.input.setInputProcessor(front);
		front.setKeyboardFocus(ghost);
		
		checkedYCloud = false;
		randomYCloudThreadRun = true;
		
		randCloud = new RandomYCloud(this);
		randCloudThread = new Thread(randCloud, "Random Y Cloud");
		randCloudThread.start();
		
		levelGen = new LevelGen(TIMER_BOXES);
		levelGenThread = new Thread(levelGen, "LevelGen");
		levelGenThread.start();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see endless.screens.ScreenAdapter#render(float)
	 */
	@Override
	public void render(float delta) {
		if (!gameOver) {
			Endless.clearScreen();
			spawnCloud(delta);
			levelGen.spawn(delta, front, world);
			despawn();
			score.setText("Score: " + points);
			if (Endless.DEBUG) {
				debug();
				b2dr.render(world, b2dCam.combined);
			} else {
				back.draw();
				front.draw();
			}
			physics(delta);
			back.act(delta);
			front.act(delta);
		} else {
			game.gameOverScreen = new GameOverScreeen(game, points);
			game.setScreen(game.gameOverScreen);
		}
	}
	
	private void physics(float delta) {
		float fps = Gdx.graphics.getFramesPerSecond();
		accum += Math.min(delta, 0.25f);
		while (accum >= 1 / fps) {
			world.step(1 / fps, 6, 2);
			accum -= 1 / fps;
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
		world.dispose();
		
		synchronized (lockMonitor_RandomYCloud) {
			randomYCloudThreadRun = false;
			lockMonitor_RandomYCloud.notify();
		}
		try {
			randCloudThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		checkedYCloud = false;
		
		synchronized (levelGen.lockMonitor) {
			levelGen.end();
			levelGen.lockMonitor.notify();
		}
		try {
			levelGenThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void end() {
		gameOver = true;
	}
	
	public World getWorld() {
		return world;
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
		timer_clouds -= delta;
		if (timer_clouds <= 0) {
			synchronized (lockMonitor_RandomYCloud) {
				if (checkedYCloud) {
					Cloud temp = new Cloud(800, lastY_clouds);
					back.addActor(temp);
					timer_clouds = MathUtils.random() + TIMER_CLOUDS;
					checkedYCloud = false;
					lockMonitor_RandomYCloud.notify();
				}
			}
		}
	}
	
	/**
	 * Elimina las nubes y las cajas que salieron de la pantalla
	 */
	private void despawn() {
		Iterator<Actor> iter;
		if (back_front) {
			iter = back.getActors().iterator();
		} else {
			iter = front.getActors().iterator();
		}
		while (iter.hasNext()) {
			Actor a = iter.next();
			if (a instanceof Cloud || a instanceof Box) {
				if (a.getX() + a.getWidth() < 0) {
					if (a instanceof Box)
						points += 10;
					a.remove();
				}
			}
		}
	}
}