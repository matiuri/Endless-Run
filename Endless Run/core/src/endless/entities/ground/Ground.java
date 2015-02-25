package endless.entities.ground;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import endless.Endless;
import endless.entities.player.Player;
import endless.utils.debug.RenderableDebug;

/**
 * El suelo donde corre el {@link Player}
 * 
 * @author Matías
 *
 */
public class Ground extends Actor implements RenderableDebug {
	private TextureRegion region;

	/**
	 * Crea el suelo, que es incorporeo y solo tiene fines visuales
	 */
	public Ground() {
		setBounds(0, 0, 800, 16);
		region = new TextureRegion(Endless.Entities_Ground, 800, 16);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}

	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(Color.BLUE);
		shaper.rect(getX(), getY(), getWidth(), getHeight());
	}
}
