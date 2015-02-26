package endless;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import endless.screens.ScreenAdapter;
import endless.screens.TitleScreen;

/**
 * Clase Principal
 * 
 * @author Matías
 */
public class Endless extends Game {
	/**
	 * Posibles estados de los FPS
	 * 
	 * @author Matías
	 *
	 */
	private enum FPS {
		NOT_RENDER(-1), UNLIMITED(0), VSYNC(60);

		private int fps;

		/**
		 * Determina el valor de la variable fps
		 * 
		 * @param fps
		 *            los FPS
		 */
		FPS(int fps) {
			this.fps = fps;
		}

		/**
		 * @return la variable fps
		 */
		public int getFps() {
			return fps;
		}
	}

	public static final String TITLE = "Endless Run", VERSION = "Alpha 0.0";
	public static final int FPS_F = FPS.UNLIMITED.getFps(), FPS_B = FPS.NOT_RENDER.getFps();
	public static int width, height;
	public static final boolean RESIZABLE = true, FULLSCREEN = true, VSYNC = true;
	public static final AssetManager MANAGER = new AssetManager();

	public ScreenAdapter titleScreen, gameScreen;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create() {
		assetLoader();
		titleScreen = new TitleScreen(this);
		setScreen(titleScreen);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Game#render()
	 */
	@Override
	public void render() {
		super.render();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Game#dispose()
	 */
	@Override
	public void dispose() {
		checkedDispose(MANAGER);
		checkedDispose(titleScreen);
		checkedDispose(gameScreen);
	}

	private void checkedDispose(Disposable d) {
		if (d != null)
			d.dispose();
	}

	/**
	 * Limpia la pantalla con color negro
	 */
	public static void clearScreen() {
		Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	/**
	 * Limpia la pantalla con el color especificado
	 * 
	 * @param r
	 *            rojo
	 * @param g
	 *            verde
	 * @param b
	 *            azul
	 * @param a
	 *            transparencia
	 */
	public static void clearScreen(float r, float g, float b, float a) {
		Gdx.graphics.getGL20().glClearColor(r, g, b, a);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	/**
	 * Limpia la pantalla con el color especificado
	 * 
	 * @param color
	 *            {@link Color}
	 * @param a
	 *            transparencia
	 */
	public static void clearScreen(Color color, float a) {
		Gdx.graphics.getGL20().glClearColor(color.r, color.g, color.b, a);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public static Texture Terrain_Background, Terrain_Cloud, Entities_Ground, Entities_Box;
	public static Texture[] Entities_Player;

	/**
	 * Cargar recursos en memoria y almacenarlos en las variables de arriba
	 */
	private static void assetLoader() {
		MANAGER.load("Terrain/Background.png", Texture.class);
		MANAGER.load("Terrain/Cloud.png", Texture.class);
		MANAGER.load("Entities/Ground.png", Texture.class);
		MANAGER.load("Entities/Box.png", Texture.class);
		MANAGER.load("Entities/Player/1.png", Texture.class);
		MANAGER.load("Entities/Player/2.png", Texture.class);
		MANAGER.finishLoading();
		Terrain_Background = MANAGER.get("Terrain/Background.png", Texture.class);
		Terrain_Cloud = MANAGER.get("Terrain/Cloud.png", Texture.class);
		Entities_Ground = MANAGER.get("Entities/Ground.png", Texture.class);
		Entities_Box = MANAGER.get("Entities/Box.png", Texture.class);
		Entities_Player = new Texture[2];
		Entities_Player[0] = MANAGER.get("Entities/Player/1.png", Texture.class);
		Entities_Player[1] = MANAGER.get("Entities/Player/2.png", Texture.class);
	}
}