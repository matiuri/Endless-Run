package endless.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import endless.utils.debug.RenderableDebug;

public class Player extends Actor implements RenderableDebug {
	private Rectangle bb;
	private boolean jump = false, crouch = false, applyGravity = false, canJump = true, canCrouch = true;
	private final float GRAVITY = -425, TIMER = 0.5f;
	private float timer = TIMER;

	public Player() {
		setBounds(16, 16, 64, 256);
		bb = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public Rectangle getBb() {
		return bb;
	}

	@Override
	public void act(float delta) {
		if (getY() <= 16) {
			setY(16);
			applyGravity = false;
			canJump = true;
			canCrouch = true;
		} else if (jump) {
			applyGravity = false;
			canJump = false;
			canCrouch = false;
		} else {
			applyGravity = true;
			canJump = false;
			canCrouch = true;
		}

		if (applyGravity) {
			moveBy(0, GRAVITY * delta);
		} else {
			if (crouch) {
				timer -= delta;
				if (timer > 0) {
					setSize(256, 64);
				} else {
					timer = TIMER;
					crouch = false;
				}
			} else {
				setSize(64, 256);
			}
			if (jump) {
				timer -= delta;
				if (timer > 0) {
					moveBy(0, -GRAVITY * delta);
				} else {
					timer = TIMER;
					jump = false;
				}
			}
		}
		bb.set(getX(), getY(), getWidth(), getHeight());
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

	public boolean canJump() {
		return (crouch) ? false : canJump;
	}

	public boolean canCrouch() {
		return (jump) ? false : canCrouch;
	}
}