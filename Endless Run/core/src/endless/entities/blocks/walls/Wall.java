package endless.entities.blocks.walls;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
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

public class Wall extends Actor implements RenderableDebug {
	private TextureRegion region;
	private Color color;
	private Body b;
	
	public Wall(float x, World world) {
		setBounds(x, 16, 64, 800);
		region = new TextureRegion(Endless.Entities_Wall);
		
		float rand = MathUtils.random();
		if (rand < 1 / 3f) {
			color = Color.YELLOW;
		} else if (rand < 2 / 3f) {
			color = Color.GREEN;
		} else {
			color = Color.BLUE;
		}
		
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
	
	@Override
	public void act(float delta) {
		setPosition(b.getPosition().x * 100, b.getPosition().y * 100, Align.center);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(color);
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		Endless.setDefaultColor(batch);
	}
	
	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(color);
		shaper.rect(getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public Color getColor() {
		return color;
	}
}