package endless.entities.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import endless.entities.ground.Ground;

/**
 * Clase que extiende a {@link Box}. Se coloca a nivel de {@link Ground}
 * 
 * @author Matías
 *
 */
public class BottomBox extends Box {
	private Rectangle bb;

	/**
	 * Determina la posición del objeto y su cuerpo
	 * 
	 * @param x
	 *            la posición en x
	 */
	public BottomBox(float x) {
		setBounds(x, 16, 64, 64);
		bb = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	// TEST
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
		moveBy(-150 * delta, 0);
	}

	// /test

	/*
	 * (non-Javadoc)
	 * 
	 * @see endless.entities.blocks.Box#debug(com.badlogic.gdx.graphics.glutils.ShapeRenderer)
	 */
	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(Color.MAROON);
		shaper.rect(getX(), getY(), getWidth(), getHeight());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see endless.entities.blocks.Evil#getBb()
	 */
	@Override
	public Rectangle getBb() {
		return bb;
	}
}