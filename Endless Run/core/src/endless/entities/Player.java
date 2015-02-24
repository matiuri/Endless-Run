package endless.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import endless.utils.debug.RenderableDebug;

@SuppressWarnings("unused")
public class Player extends Actor implements RenderableDebug {
	private Rectangle bb;
	private boolean jump = false, crouch = false;

	public Player() {
		setBounds(16, 16, 128, 256);
	}

	public Rectangle getBb() {
		return bb;
	}

	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(Color.WHITE);
		shaper.rect(getX(), getY(), getWidth(), getHeight());
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void setCrouch(boolean crouch) {
		this.crouch = crouch;
	}
}