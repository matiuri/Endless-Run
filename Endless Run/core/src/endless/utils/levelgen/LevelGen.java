package endless.utils.levelgen;

import java.util.LinkedList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import endless.entities.blocks.boxes.BottomBox;
import endless.entities.blocks.boxes.TopBox;
import endless.entities.blocks.walls.Wall;

public class LevelGen implements Runnable {
	// FIXME: change levelgen method
	private final byte[] LEVEL_1 = { 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0 };
	private final byte[] LEVEL_2 = { 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0 };
	private final byte[] LEVEL_3 = { 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0 };
	
	private volatile LinkedList<Byte> level;
	private final float TIMER;
	private float timer;
	private final int MAX = 1000000;
	public final Object lockMonitor = new Object();
	private volatile boolean threadRun = true;
	
	public LevelGen(float timer) {
		level = new LinkedList<Byte>();
		TIMER = timer;
		this.timer = timer;
	}
	
	@Override
	public void run() {
		try {
			levelgen();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void levelgen() throws InterruptedException {
		while (threadRun) {
			synchronized (lockMonitor) {
				if (level.size() < MAX) {
					float rand = MathUtils.random();
					if (rand < 1 / 3f) {
						for (byte b : LEVEL_1) {
							level.add(b);
						}
					} else if (rand < 2 / 3f) {
						for (byte b : LEVEL_2) {
							level.add(b);
						}
					} else {
						for (byte b : LEVEL_3) {
							level.add(b);
						}
					}
				} else {
					lockMonitor.wait();
				}
			}
		}
	}
	
	public void end() {
		threadRun = false;
	}
	
	public void spawn(float delta, Stage front, World world) {
		timer -= delta;
		if (timer <= 0) {
			synchronized (lockMonitor) {
				if (level.size() > 0) {
					front.addActor(new Wall(800, world));
					byte b = level.pollFirst();
					if (b == 0) {
						front.addActor(new BottomBox(800, world));
					} else {
						front.addActor(new TopBox(800, world));
					}
					lockMonitor.notify();
				}
			}
			timer = TIMER;
		}
	}
}