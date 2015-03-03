package endless.input;

import com.badlogic.gdx.Input.Keys;
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
	private boolean drag;
	
	/**
	 * Crea el procesador de entrada táctil
	 * 
	 * @param player
	 *            el jugador
	 */
	public DragInput(Player player) {
		this.player = player;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.scenes.scene2d.utils.DragListener#dragStart(com.badlogic.gdx.scenes.scene2d.InputEvent,
	 * float, float, int)
	 */
	@Override
	public void dragStart(InputEvent event, float x, float y, int pointer) {
		this.y = y;
		this.pointer = pointer;
		drag = true;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.scenes.scene2d.utils.DragListener#drag(com.badlogic.gdx.scenes.scene2d.InputEvent, float,
	 * float, int)
	 */
	@Override
	public void drag(InputEvent event, float x, float y, int pointer) {
		if (drag) {
			if (this.pointer == pointer) {
				float delta = y - this.y;
				if (delta >= 20 && player.canJump()) {
					player.setJump(true);
					player.setCrouch(false);
					drag = false;
				} else if (delta <= -20 && player.canCrouch()) {
					player.setJump(false);
					player.setCrouch(true);
					drag = false;
				}
			}
		}
	}
	
	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		switch (keycode) {
		case Keys.W:
		case Keys.UP:
			if (player.canJump()) {
				player.setJump(true);
				player.setCrouch(false);
			}
			return true;
		case Keys.S:
		case Keys.DOWN:
			if (player.canCrouch()) {
				player.setJump(false);
				player.setCrouch(true);
			}
			return true;
		case Keys.SPACE:
			player.nextColor();
			return true;
		case Keys.SHIFT_LEFT:
		case Keys.SHIFT_RIGHT:
			player.prevColor();
			return true;
		default:
			return true;
		}
	}
}