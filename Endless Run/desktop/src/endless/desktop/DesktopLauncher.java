package endless.desktop;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import endless.Endless;

public class DesktopLauncher {

	public static void main(String[] arg) {
		init();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Endless.TITLE + " " + Endless.VERSION;
		config.width = Endless.width;
		config.height = Endless.height;
		config.width = 800;
		config.height = 480;
		config.resizable = Endless.RESIZABLE;
		config.fullscreen = Endless.FULLSCREEN;
		config.vSyncEnabled = Endless.VSYNC;
		config.foregroundFPS = Endless.FPS_F;
		config.backgroundFPS = Endless.FPS_B;
		new LwjglApplication(new Endless(), config);
	}

	private static void init() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		try {
			Endless.width = gd.getDisplayMode().getWidth();
		} catch (Exception e) {
			Endless.width = 1360;
		}

		try {
			Endless.height = gd.getDisplayMode().getHeight();
		} catch (Exception e) {
			Endless.height = 768;

		}
	}
}
