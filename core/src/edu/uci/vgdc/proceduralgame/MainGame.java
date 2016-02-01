package edu.uci.vgdc.proceduralgame;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import edu.uci.vgdc.proceduralgame.components.Box2DComponent;
import edu.uci.vgdc.proceduralgame.listeners.Box2DListener;

public class MainGame extends ApplicationAdapter {
	GameRenderer batch;
	
	private Engine engine;
	private Box2DListener box2DListener;
	
	private World physicsWorld;
	private Box2DDebugRenderer debugRenderer;
	public float TIMESTEP = 1/300f;
	
	private GameCamera camera;
	
	private Player player;
	
	@SuppressWarnings("unchecked")
	@Override
	public void create () {
		batch = new GameRenderer();
		
		camera = new GameCamera();
		engine = new Engine();
		
		physicsWorld = new World(new Vector2(0,0), true);
		debugRenderer = new Box2DDebugRenderer();
		
		box2DListener = new Box2DListener(physicsWorld);
		engine.addEntityListener(Family.all(Box2DComponent.class).get(), 0, box2DListener);
		
		player = new Player(engine, physicsWorld);
		
		Gdx.input.setInputProcessor(new GameInput(player, camera));
	}
	
	public void update() {
		((GameInput)Gdx.input.getInputProcessor()).update();
		
		player.update();
		
		camera.update();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.getProjViewMatrix());
		batch.begin();
		
		// Render Steps
		// 1. Floor/Background
		// 2. Entity bilayer
		// 3. Black tile borders
		// 4. HUD
		
		batch.end();
		
		debugRenderer.render(physicsWorld, camera.getProjViewMatrix());
		
		doUpdateStep(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void resize(int width, int height) {
		camera.resize(width, height);
	}
	
	private float accumulator = 0;
	private void doUpdateStep(float deltaTime) {
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		while (accumulator >= TIMESTEP) {
			physicsWorld.step(TIMESTEP, 6, 2);
			engine.update(TIMESTEP);
			update();
			accumulator -= TIMESTEP;
		}
	}
}
