package endless.entities.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import endless.utils.debug.RenderableDebug;

/**
 * El jugador
 * 
 * @author Matías
 *
 */
public class Player extends Actor implements RenderableDebug {
	private Rectangle bb;
	private boolean jump = false, crouch = false, applyGravity = false, canJump = true, canCrouch = true;
	private final float GRAVITY = -425, TIMER = 0.75f;
	private float timer = TIMER;

	/**
	 * Crea el jugador y define su cuerpo por medio de {@link Rectangle}
	 */
	public Player() {
		setBounds(16, 16, 64, 256);
		bb = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * @return el cuerpo del jugador
	 */
	public Rectangle getBb() {
		return bb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
		setApplyGravity();
		doActions(delta);
		bb.set(getX(), getY(), getWidth(), getHeight());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see endless.utils.debug.RenderableDebug#debug(com.badlogic.gdx.graphics.glutils.ShapeRenderer)
	 */
	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(Color.WHITE);
		shaper.rect(getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * Define si el jugador está saltando
	 * 
	 * @param jump
	 *            estado que debe tomar la variable
	 */
	public void setJump(boolean jump) {
		this.jump = jump;
	}

	/**
	 * Define si el jugador está agachado
	 * 
	 * @param jump
	 *            estado que debe tomar la variable
	 */
	public void setCrouch(boolean crouch) {
		this.crouch = crouch;
	}

	/**
	 * @return si el jugador puede saltar. En el caso de que esté agachado, siempre devolverá negativo
	 */
	public boolean canJump() {
		return (crouch) ? false : canJump;
	}

	/**
	 * @return si el jugador puede agacharse. En el caso de que esté saltando, siempre devolverá negativo
	 */
	public boolean canCrouch() {
		return (jump) ? false : canCrouch;
	}

	/**
	 * Se debería aplicar gravedad?
	 */
	private void setApplyGravity() {
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
	}

	/**
	 * Realizar acciones
	 * 
	 * @param delta
	 *            tiempo
	 */
	private void doActions(float delta) {
		if (applyGravity) {
			moveBy(0, GRAVITY * delta);
		} else {
			if (crouch) {
				crouch(delta);
			} else {
				setSize(64, 256);
			}
			if (jump) {
				jump(delta);
			}
		}
	}

	/**
	 * Saltar
	 * 
	 * @param delta
	 *            tiempo
	 */
	private void jump(float delta) {
		timer -= delta;
		if (timer > 0) {
			moveBy(0, -GRAVITY * delta);
		} else {
			timer = TIMER;
			jump = false;
		}
	}

	/**
	 * Agacharse
	 * 
	 * @param delta
	 *            tiempo
	 */
	private void crouch(float delta) {
		timer -= delta;
		if (timer > 0) {
			setSize(256, 64);
		} else {
			timer = TIMER;
			crouch = false;
		}
	}
}