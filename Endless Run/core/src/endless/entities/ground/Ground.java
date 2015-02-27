package endless.entities.ground;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;

import endless.Endless;
import endless.entities.player.Player;
import endless.screens.GameScreen;
import endless.utils.debug.RenderableDebug;

/**
 * El suelo donde corre el {@link Player}
 * 
 * @author Matías
 *
 */
public class Ground extends Actor implements RenderableDebug {
	private TextureRegion region;

	/**
	 * Crea el suelo, que es incorporeo y solo tiene fines visuales
	 */
	public Ground(GameScreen game) {
		setBounds(0, 0, 800, 16);
		region = new TextureRegion(Endless.Entities_Ground, 800, 16);

		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(getWidth() / 200, getHeight() / 200);
		Body b = game.getWorld().createBody(bd);
		b.setUserData(this);

		PolygonShape s = new PolygonShape();
		s.setAsBox(getWidth() / 200, getHeight() / 200);
		FixtureDef fd = new FixtureDef();
		fd.shape = s;
		Fixture f = b.createFixture(fd);
		f.setUserData(this);
		s.dispose();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}

	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(Color.BLUE);
		shaper.rect(getX(), getY(), getWidth(), getHeight());
	}
}
