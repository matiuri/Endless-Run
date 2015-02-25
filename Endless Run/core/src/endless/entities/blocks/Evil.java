package endless.entities.blocks;

import com.badlogic.gdx.math.Rectangle;

import endless.entities.player.Player;

/**
 * Indica que la clase que la implemente va a dañar al {@link Player}
 * 
 * @author Matías
 *
 */
public interface Evil {
	/**
	 * @return el cuerpo del objeto
	 */
	Rectangle getBb();
}