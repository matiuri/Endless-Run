package endless.utils.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import endless.entities.blocks.boxes.BottomBox;
import endless.entities.blocks.boxes.Box;
import endless.entities.blocks.boxes.TopBox;
import endless.entities.blocks.walls.Wall;
import endless.entities.ground.Ground;
import endless.entities.player.Player;
import endless.screens.GameScreen;

/**
 * Clase que se encarga de gestionar las colisiones. Implementa {@link ContactListener}
 * 
 * @author Matías
 *
 */
public class Collision implements ContactListener {
	private GameScreen game;
	
	private final boolean TEST = false;
	
	public Collision(GameScreen game) {
		this.game = game;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#beginContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA(), b = contact.getFixtureB();
		
		if (collisionPlayer_Ground(a, b)) {
			((Player) a.getUserData()).setStand(true);
		}
		if (collisionPlayer_Ground(b, a)) {
			((Player) b.getUserData()).setStand(true);
		}
		
		if (collisionPlayer_Box(a, b) || collisionPlayer_Box(b, a)) {
			if (!TEST)
				game.end();
		}
		
		if (collisionPlayer_Wall(a, b) || collisionPlayer_Wall(b, a)) {
			if (!TEST) {
				game.end();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#endContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void endContact(Contact contact) {
		Fixture a = contact.getFixtureA(), b = contact.getFixtureB();
		
		if (collisionPlayer_Ground(a, b)) {
			((Player) a.getUserData()).setStand(false);
		}
		if (collisionPlayer_Ground(b, a)) {
			((Player) b.getUserData()).setStand(false);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#preSolve(com.badlogic.gdx.physics.box2d.Contact,
	 * com.badlogic.gdx.physics.box2d.Manifold)
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// Nothing
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#postSolve(com.badlogic.gdx.physics.box2d.Contact,
	 * com.badlogic.gdx.physics.box2d.ContactImpulse)
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// Nothing
	}
	
	/**
	 * @param a
	 *            {@link Fixture}
	 * @param b
	 *            {@link Fixture}
	 * @return true si uno de los {@link Fixture} es {@link Player} y el otro {@link Ground}
	 */
	private boolean collisionPlayer_Ground(Fixture a, Fixture b) {
		if (a.getUserData() instanceof Player && b.getUserData() instanceof Ground) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @param a
	 *            {@link Fixture}
	 * @param b
	 *            {@link Fixture}
	 * @return true si uno de los {@link Fixture} es {@link Player} y el otro {@link Box}
	 */
	private boolean collisionPlayer_Box(Fixture a, Fixture b) {
		if (a.getUserData() instanceof Player) {
			if (b.getUserData() instanceof BottomBox) {
				return true;
			} else if (b.getUserData() instanceof TopBox) {
				if (((Player) a.getUserData()).isCrouched()) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	private boolean collisionPlayer_Wall(Fixture a, Fixture b) {
		if (a.getUserData() instanceof Player && b.getUserData() instanceof Wall) {
			if (((Player) a.getUserData()).getColor().equals(((Wall) b.getUserData()).getColor())) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
}