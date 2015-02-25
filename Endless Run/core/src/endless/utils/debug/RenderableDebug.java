package endless.utils.debug;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * @author Matías
 *
 */
public interface RenderableDebug {
	/**
	 * Dibuja la superficie del actor. Se debe llamar a {@link ShapeRenderer#begin(ShapeType)} y pasarle como parámetro
	 * {@link ShapeType#Filled} antes de llamar a uno o varios de estos métodos y se debe llamar a
	 * {@link ShapeRenderer#end()} al finalizar.
	 * 
	 * @param shaper
	 *            {@link ShapeRenderer}
	 */
	void debug(ShapeRenderer shaper);
}
