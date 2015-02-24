package endless.terrain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import endless.Endless;
import endless.utils.debug.RenderableDebug;

public class Background extends Actor implements RenderableDebug {
	private TextureRegion region;

	public Background() {
		setBounds(0, 0, 800, 480);
		region = new TextureRegion(Endless.Terrain_Background, 800, 480);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}

	@Override
	public void debug(ShapeRenderer shaper) {
		shaper.setColor(Color.RED);
		shaper.rect(getX(), getY(), getWidth(), getHeight());
	}
}