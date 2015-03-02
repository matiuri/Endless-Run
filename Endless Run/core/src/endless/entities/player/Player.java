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
public class Player extends Actor implements RenderableDebug {
	private Body b;
	private boolean jump = false, crouch = false, canJump = true, canCrouch = true;
	private boolean stand;
	private Animation animation;
	private final float TIMER = 1.5f;
	private float timer = TIMER;
	private PlayerColor color;
	
	private enum PlayerColor {
		YELLOW(Color.YELLOW), GREEN(Color.GREEN), BLUE(Color.BLUE);
		
		private Color color;
		private float r, g, b;
		
		PlayerColor(Color color) {
			this.color = color;
			r = color.r;
			g = color.g;
			b = color.b;
		}
		
		private PlayerColor next() {
			return (ordinal() < values().length - 1) ? values()[ordinal() + 1] : values()[0];
		}
		
		private PlayerColor prev() {
			return (ordinal() > 0) ? values()[ordinal() - 1] : values()[values().length - 1];
		}
	}
	
	/**
	 * Crea el jugador y define su cuerpo por medio de {@link Rectangle}
	 */
	public Player(World world) {
		setBounds(16, 16, 64, 256);
		animation = new Animation(Endless.Entities_Player, true, 0.075f);
		
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
		
		color = PlayerColor.YELLOW;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
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
			timer = TIMER;
		}
		if (crouch) {
			timer -= delta;
			if (timer <= 0) {
				crouch = false;
				timer = TIMER;
			}
		}
		if (b.getPosition().x != (getX() + getWidth() / 2) / 100) {
			b.setTransform((getX() + getWidth() / 2) / 100, b.getPosition().y, 0);
		}
		
		setPosition(b.getPosition().x * 100, b.getPosition().y * 100, Align.center);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(color.r, color.g, color.b, parentAlpha);
		if (!crouch) {
			batch.draw(animation.getActualFrame(Gdx.graphics.getDeltaTime()), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
					getScaleX(), getScaleY(), getRotation());
		} else {
			batch.draw(animation.getActualFrame(Gdx.graphics.getDeltaTime()), getX(), getY(), getOriginX(), getOriginY(), getWidth(),
					getHeight() * 3 / 4f, getScaleX(), getScaleY(), getRotation());
		}
		Endless.setDefaultColor(batch);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see endless.utils.debug.RenderableDebug#debug(com.badlogic.gdx.graphics.glutils.ShapeRenderer)
	 */
	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(color.color);
		if (crouch) {
			shaper.rect(getX(), getY(), getWidth(), getHeight() * 3 / 4f);
		} else {
			shaper.rect(getX(), getY(), getWidth(), getHeight());
		}
	}
	
	public void nextColor() {
		color = color.next();
	}
	
	// TODO: implement this
	public void prevColor() {
		color = color.prev();
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
		return canJump;
	}
	
	/**
	 * @return si el jugador puede agacharse. En el caso de que esté saltando, siempre devolverá negativo
	 */
	public boolean canCrouch() {
		return canCrouch;
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
	
	public boolean isCrouched() {
		return crouch;
	}
	
	@Override
	public Color getColor() {
		return color.color;
	}
}