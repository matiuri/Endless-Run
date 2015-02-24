package endless;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import endless.screens.ScreenAdapter;
import endless.screens.TitleScreen;

public class Endless extends Game {
	public static final String TITLE = "Endless Run", VERSION = "Alpha 0.0";
	public static final int FPS_F = 0, FPS_B = -1;
	public static int width, height;
	public static final boolean RESIZABLE = true, FULLSCREEN = true, VSYNC = true;
	public static final AssetManager MANAGER = new AssetManager();

	public ScreenAdapter titleScreen, gameScreen;

	@Override
	public void create() {
		titleScreen = new TitleScreen(this);
		setScreen(titleScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		MANAGER.dispose();
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
	 *            color
	 * @param a
	 *            transparencia
	 */
	public static void clearScreen(Color color, float a) {
		Gdx.graphics.getGL20().glClearColor(color.r, color.g, color.b, a);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
