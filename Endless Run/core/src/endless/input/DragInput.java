package endless.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import endless.entities.player.Player;

/**
 * El procesador de entrada táctil
 * 
 * @author Matías
 *
 */
public class DragInput extends DragListener {
	private Player player;
	private float y;
	private int pointer;

	/**
	 * Crea el procesador de entrada táctil
	 * 
	 * @param player
	 *            el jugador
	 */
	public DragInput(Player player) {
		this.player = player;
	}

	@Override
	public void dragStart(InputEvent event, float x, float y, int pointer) {
		this.y = y;
		this.pointer = pointer;
	}

	@Override
	public void drag(InputEvent event, float x, float y, int pointer) {
		if (this.pointer == pointer) {
			float delta = y - this.y;
			if (delta >= 20 && player.canJump()) {
				player.setJump(true);
				player.setCrouch(false);
				cancel();
			} else if (delta <= -20) {
				if (player.canCrouch()) {
					player.setJump(false);
					player.setCrouch(true);
				}
				cancel();
			}
		}
	}
}