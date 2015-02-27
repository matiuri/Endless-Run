package endless.utils.concurrent;

import com.badlogic.gdx.math.MathUtils;

import endless.screens.GameScreen;

public class RandomYCloud implements Runnable {
	private GameScreen game;

	public RandomYCloud(GameScreen game) {
		this.game = game;
	}

	@Override
	public void run() {
		float rand = 0;
		while (game.randomYCloudThreadRun) {
			synchronized (game.lockMonitor) {
				if (!game.checkedYCloud) {
					rand = MathUtils.random(345, 445) + MathUtils.random();
					if (rand < game.lastY_clouds - 52 || rand > game.lastY_clouds + 52) {
						game.lastY_clouds = rand;
						game.checkedYCloud = true;
					}
				}
			}
		}
	}
}