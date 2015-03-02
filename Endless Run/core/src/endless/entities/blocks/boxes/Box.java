package endless.entities.blocks.boxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import endless.utils.debug.RenderableDebug;

/**
 * Clase abstracta que extiende a {@link Actor} e implementa a {@link RenderableDebug}
 * 
 * @author Matías
 *
 */
public abstract class Box extends Actor implements RenderableDebug {
	private TextureRegion region;

	protected Box(Texture texture) {
		region = new TextureRegion(texture);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}

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