package endless.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import endless.Endless;
import endless.utils.debug.RenderableDebug;
import endless.utils.graphics.Animation;

/**
 * El jugador
 * 
 * @author Matías
 *
 */
@SuppressWarnings("unused")
public class Player extends Actor implements RenderableDebug {
	private Body b;
	private boolean jump = false, crouch = false,/* OLD */applyGravity = false, canJump = true, canCrouch = true;
	private boolean stand;
	private final float GRAVITY = -425, TIMER = 0.75f; // OLD
	private float timer = TIMER; // OLD
	private Animation animation;

	/**
	 * Crea el jugador y define su cuerpo por medio de {@link Rectangle}
	 */
	public Player(World world) {
		setBounds(16, 16, 64, 256);
		animation = new Animation(Endless.Entities_Player, true, 0.05f);

		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set((getX() + getWidth() / 2) / 100f, (getY() + getHeight() / 2) / 100f);
		b = world.createBody(bd);
		b.setUserData(this);

		PolygonShape s = new PolygonShape();
		s.setAsBox(getWidth() / 200, getHeight() / 200);
		FixtureDef fd = new FixtureDef();
		fd.shape = s;
		fd.density = 0.5f;
		fd.friction = 0.5f;
		Fixture f = b.createFixture(fd);
		f.setUserData(this);
		s.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
		// OLD
		// setApplyGravity();
		// doActions(delta);
		// bb.set(getX(), getY(), getWidth(), getHeight());

		if (stand) {
			canJump = true;
			canCrouch = true;
		} else {
			canJump = false;
			canCrouch = false;
		}

		if (jump) {
			Vector2 impulse = new Vector2(0, 5.3f), point = b.getPosition();
			b.applyLinearImpulse(impulse, point, true);
			jump = false;
		}

		setPosition(b.getPosition().x * 100, b.getPosition().y * 100, Align.center);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(animation.getActualFrame(Gdx.graphics.getDeltaTime()), getX(), getY(), getOriginX(), getOriginY(),
				getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
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
	 * Modifica el valor de la variable booleana stand
	 * 
	 * @param stand
	 *            nuevo valor booleano
	 */
	public void setStand(boolean stand) {
		this.stand = stand;
	}

	/**
	 * Se debería aplicar gravedad? OLD
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