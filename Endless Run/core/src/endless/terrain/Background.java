package endless.terrain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import endless.utils.debug.RenderableDebug;

public class Background extends Actor implements RenderableDebug {
	public Background() {
		setBounds(0, 0, 800, 480);
	}

	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(Color.RED);
		shaper.rect(getX(), getY(), getWidth(), getHeight());
	}
}