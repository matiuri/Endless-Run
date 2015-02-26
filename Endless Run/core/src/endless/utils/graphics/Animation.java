package endless.utils.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Clase que "anima" las texturas
 * 
 * @author Matías
 *
 */
public class Animation {
	private TextureRegion[] region;
	private int actualFrame = 0;
	private boolean loop;
	private float frameVel, timer;

	/**
	 * Constructor privado para la parte que se repite en todos
	 * 
	 * @param loop
	 * @param frameVel
	 */
	private Animation(boolean loop, float frameVel) {
		this.loop = loop;
		this.frameVel = frameVel;
		timer = this.frameVel;
	}

	/**
	 * Crea una animación
	 * 
	 * @param textures
	 * @param loop
	 * @param frameVel
	 */
	public Animation(Texture[] textures, boolean loop, float frameVel) {
		this(loop, frameVel);
		region = new TextureRegion[textures.length];
		for (int i = 0; i < textures.length; i++) {
			region[i] = new TextureRegion(textures[i]);
		}
	}

	/**
	 * Crea una animación
	 * 
	 * @param textures
	 * @param loop
	 * @param frameVel
	 */
	public Animation(Texture[][] textures, boolean loop, float frameVel) {
		this(loop, frameVel);
		region = new TextureRegion[textures.length * textures[0].length];
		int index = 0;
		for (int i = 0; i < textures.length; i++) {
			for (int j = 0; j < textures[i].length; j++) {
				region[index++] = new TextureRegion(textures[i][j]);
			}
		}
	}

	/**
	 * Crea una animación
	 * 
	 * @param tr
	 * @param loop
	 * @param frameVel
	 */
	public Animation(TextureRegion[] tr, boolean loop, float frameVel) {
		this(loop, frameVel);
		region = new TextureRegion[tr.length];
		for (int i = 0; i < tr.length; i++) {
			region[i] = tr[i];
		}
	}

	/**
	 * Crea una animación
	 * 
	 * @param tr
	 * @param loop
	 * @param frameVel
	 */
	public Animation(TextureRegion[][] tr, boolean loop, float frameVel) {
		this(loop, frameVel);
		region = new TextureRegion[tr.length * tr[0].length];
		int index = 0;
		for (int i = 0; i < tr.length; i++) {
			for (int j = 0; j < tr[i].length; j++) {
				region[index++] = tr[i][j];
			}
		}
	}

	/**
	 * @return el fotograma actual
	 */
	public TextureRegion getActualFrame() {
		return region[actualFrame];
	}

	/**
	 * Actualiza el fotograma actual
	 * 
	 * @param delta
	 * @return el fotograma actual
	 */
	public TextureRegion getActualFrame(float delta) {
		if (actualFrame < region.length - 1) {
			timer -= delta;
			if (timer <= 0) {
				actualFrame++;
				timer = frameVel;
			}
		} else if (loop) {
			timer -= delta;
			if (timer <= 0) {
				actualFrame = 0;
				timer = frameVel;
			}
		}
		return region[actualFrame];
	}
}