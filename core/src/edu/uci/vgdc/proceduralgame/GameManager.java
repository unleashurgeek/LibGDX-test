package edu.uci.vgdc.proceduralgame;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import edu.uci.vgdc.proceduralgame.components.Box2DComponent;
import edu.uci.vgdc.proceduralgame.listeners.Box2DListener;
import edu.uci.vgdc.proceduralgame.utils.Hasher;
import edu.uci.vgdc.proceduralgame.utils.Util;
import net.jpountz.xxhash.XXHashFactory;

public class GameManager {
	
	private final Hasher gameHasher;
	
	private final Engine engine;
	private final World physicsWorld;
	private final GameCamera camera;
	private final GameInput gameInput;
	private final Player player;
	
	public boolean DEBUG = true;
	private Box2DDebugRenderer debugRenderer;
	
	
	@SuppressWarnings("unchecked")
	public GameManager(long seed) {
		this.gameHasher = new Hasher(XXHashFactory.fastestInstance().hash64(), seed);

		this.engine = new Engine();
		this.physicsWorld = new World(new Vector2(0, 0), true);
		
		// Listener's here
		engine.addEntityListener(Family.all(Box2DComponent.class).get(), 0 , new Box2DListener(physicsWorld));
		
		// System's here
		
		this.player = new Player(engine, physicsWorld);
		this.camera = new GameCamera();
		this.gameInput = new GameInput(this);
		
		if (DEBUG)
			debugRenderer = new Box2DDebugRenderer();
		
		// TODO: Call level creation
	}
	
	public void update() {
		this.gameInput.update();
		
		this.player.update();
		
		// TODO: Set Camera position locked to player.
		this.camera.update();
	}
	
	public void render(GameRenderer batch) {
		batch.setProjectionMatrix(this.camera.getProjViewMatrix());
		batch.begin();
		
		// Render Steps
		// 1. Floor/Background
		// 2. Entity bilayer
		// 3. Black tile borders
		// 4. HUD
		
		batch.end();
		
		if (DEBUG)
			debugRender(batch);
	}
	
	public void debugRender(GameRenderer batch) {
		Util.debugRenderer.setProjectionMatrix(this.camera.getProjViewMatrix());
		Util.debugRenderer.begin(ShapeRenderer.ShapeType.Line);
		
		// 1x1 = meter square
		// 2x2 = Tile
		// 4x4 = Grid
		// 8x8 = min-room Size.
		Util.DrawSquare(new Vector2(-4f, -4f), new 	Vector2(4f, 4f));
		Util.DrawSquare(new Vector2(-4f, -4f), new Vector2(0f, 0f));
		Util.DrawSquare(new Vector2(-4f, -4f), new Vector2(-2f, -2f));
		Util.DrawSquare(new Vector2(-4f, -4f), new Vector2(-3f, -3f));
		Util.DrawSquare(new Vector2(-2f, -4f), new Vector2(0f, -2f));
		
		Util.debugRenderer.end();
		
		debugRenderer.render(this.physicsWorld, this.camera.getProjViewMatrix());
	}
	
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Engine getEngine() {
		return this.engine;
	}
	
	public World getPhysicsWorld() {
		return this.physicsWorld;
	}
	
	public GameCamera getCamera() {
		return this.camera;
	}
	
	public GameInput getInput() {
		return this.gameInput;
	}
}
