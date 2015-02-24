package endless.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import endless.entities.Player;

public class DragInput extends DragListener {
	private Player player;
	private float y;
	private int pointer;

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
			if (delta >= 20) {
				player.setJump(true);
				player.setCrouch(false);
				System.out.println("Jump");
				cancel();
			} else if (delta <= -20) {
				player.setJump(false);
				player.setCrouch(true);
				System.out.println("Crouch");
				cancel();
			}
		}
	}
}