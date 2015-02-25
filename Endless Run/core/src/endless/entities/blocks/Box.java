package endless.entities.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import endless.utils.debug.RenderableDebug;

/**
 * Clase abstracta que extiende a {@link Actor} e implementa a {@link Evil} y a {@link RenderableDebug}
 * 
 * @author Matías
 *
 */
public abstract class Box extends Actor implements Evil, RenderableDebug {

	/*
	 * (non-Javadoc)
	 * 
	 * @see endless.utils.debug.RenderableDebug#debug(com.badlogic.gdx.graphics.glutils.ShapeRenderer)
	 */
	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(Color.MAROON);
		shaper.rect(getX(), getY(), getWidth(), getHeight());
	}
}