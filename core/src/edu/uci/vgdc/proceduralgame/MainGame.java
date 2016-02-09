package edu.uci.vgdc.proceduralgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MainGame extends ApplicationAdapter {
	GameRenderer batch;
	public float TIMESTEP = 1/300f;
	
	private GameManager gameManager;
	
	@Override
	public void create () {
		batch = new GameRenderer();
		
		gameManager = new GameManager(System.currentTimeMillis());		
		Gdx.input.setInputProcessor(gameManager.getInput());
	}
	
	public void update() {
		gameManager.update();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameManager.render(batch);
		
		doUpdateStep(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void resize(int width, int height) {
		gameManager.getCamera().resize(width, height);
	}
	
	private float accumulator = 0;
	private void doUpdateStep(float deltaTime) {
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		while (accumulator >= TIMESTEP) {
			gameManager.getPhysicsWorld().step(TIMESTEP, 6, 2);
			gameManager.getEngine().update(TIMESTEP);
			update();
			accumulator -= TIMESTEP;
		}
	}
}
