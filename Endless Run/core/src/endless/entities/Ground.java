package endless.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import endless.utils.debug.RenderableDebug;

public class Ground extends Actor implements RenderableDebug {
	private Rectangle bb;

	public Ground() {
		setBounds(0, 0, 800, 16);
		bb = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public Rectangle getBb() {
		return bb;
	}

	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(Color.BLUE);
		shaper.rect(getX(), getY(), getWidth(), getHeight());
	}
}
