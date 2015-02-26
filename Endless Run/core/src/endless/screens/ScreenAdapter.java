package endless.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

import endless.Endless;

/**
 * La pantalla base
 * 
 * @author Matías
 *
 */
public abstract class ScreenAdapter implements Screen, Disposable {
	protected Endless game;
	protected Skin skin;

	/**
	 * Crea una pantalla
	 * 
	 * @param game
	 *            clase principal
	 */
	public ScreenAdapter(Endless game) {
		this.game = game;
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
	}

	@Override
	public abstract void show();

	@Override
	public abstract void render(float delta);

	@Override
	public abstract void resize(int width, int height);

	@Override
	public abstract void hide();

	@Override
	public void pause() {
		// Implementable
	}

	@Override
	public void resume() {
		// Implementable
	}

	@Override
	public void dispose() {
		// Implementable
	}
}