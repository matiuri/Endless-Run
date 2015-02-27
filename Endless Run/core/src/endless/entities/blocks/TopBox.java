package endless.entities.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import endless.Endless;

public class TopBox extends Box {
	private Body b;

	/**
	 * Determina la posición del objeto y su cuerpo
	 * 
	 * @param x
	 *            la posición en x
	 */
	public TopBox(float x, World world) {
		super(Endless.Entities_Box);
		setBounds(x, 240, 64, 64);

		BodyDef bd = new BodyDef();
		bd.type = BodyType.KinematicBody;
		bd.position.set((getX() + getWidth() / 2) / 100f, (getY() + getHeight() / 2) / 100f);
		b = world.createBody(bd);
		b.setUserData(this);
		b.setLinearVelocity(-2.5f, 0);

		PolygonShape s = new PolygonShape();
		s.setAsBox(getWidth() / 200, getHeight() / 200);
		FixtureDef fd = new FixtureDef();
		fd.shape = s;
		fd.density = 0.5f;
		fd.friction = 0.5f;
		fd.isSensor = true;
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
		setPosition(b.getPosition().x * 100, b.getPosition().y * 100, Align.center);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see endless.entities.blocks.Box#debug(com.badlogic.gdx.graphics.glutils.ShapeRenderer)
	 */
	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(Color.MAROON);
		shaper.rect(getX(), getY(), getWidth(), getHeight());
	}
}
