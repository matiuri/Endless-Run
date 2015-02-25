package endless.terrain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import endless.Endless;
import endless.utils.debug.RenderableDebug;

/**
 * Nube aleatoria
 * 
 * @author Matías
 *
 */
public class Cloud extends Actor implements RenderableDebug {
	private TextureRegion region;

	/**
	 * Crea una nube en una posición dada
	 * 
	 * @param x
	 * @param y
	 */
	public Cloud(float x, float y) {
		setBounds(x, y, 256, 32);
		region = new TextureRegion(Endless.Terrain_Cloud);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
		moveBy(-100 * delta, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see endless.utils.debug.RenderableDebug#debug(com.badlogic.gdx.graphics.glutils.ShapeRenderer)
	 */
	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(Color.BLACK);
		shaper.rect(getX(), getY(), getWidth(), getHeight());
	}
}
