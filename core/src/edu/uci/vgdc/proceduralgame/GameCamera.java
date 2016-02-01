package edu.uci.vgdc.proceduralgame;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.uci.vgdc.proceduralgame.utils.CustomRandom;

public class GameCamera {
	private Viewport viewport;
	private Camera camera;
	
	private float gameScale = 0.02f;
	//private float lerp = 0.02f;
	
	private float radius = 0f;
	private float randomAngle = 0f;
	
	private Vector3 targetPosition = new Vector3();
	private CustomRandom cRand = new CustomRandom();
	
	public GameCamera() {
		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);
		((ScreenViewport)viewport).setUnitsPerPixel(gameScale);
	}
	
	public void rumble(float initialRadius) {
		radius = initialRadius;
		randomAngle = cRand.nextInt()%360;
	}
	
	public void update() {
		//Vector3 cPos = camera.position;
		//camera.position.x += ((targetPosition.x - cPos.x) * lerp) + (MathUtils.sinDeg(randomAngle) * radius);
		//camera.position.y += ((targetPosition.y - cPos.y) * lerp) + (MathUtils.cosDeg(randomAngle) * radius);
		camera.position.x = targetPosition.x + MathUtils.sinDeg(randomAngle) * radius;
		camera.position.y = targetPosition.y + MathUtils.cosDeg(randomAngle) * radius;
		camera.update();
		
		randomAngle += (150 + cRand.nextInt()%60);
		radius *= 0.9f;
	}
	
	public float getGameScale() {
		return gameScale;
	}
	
	public void setGameScale(float newScale) {
		// TODO: Fix this method.
		this.gameScale = newScale;
		((ScreenViewport)viewport).setUnitsPerPixel(gameScale);
	}
	
	public Vector2 project(Vector2 coords) {
		Vector3 res = camera.project(new Vector3(coords.x, coords.y, 0));
		return new Vector2(res.x, res.y);
	}
	
	public Vector2 unproject(Vector2 coords) {
		Vector3 res = camera.unproject(new Vector3(coords.x, coords.y, 0));
		return new Vector2(res.x, res.y);
	}
	
	public void setTarget(Vector2 newPos) {
		targetPosition = new Vector3(newPos, 0f);
	}
	
	public Matrix4 getProjViewMatrix() {
		return this.camera.combined;
	}
	
	public Vector2 getPosition() {
		return new Vector2(camera.position.x, camera.position.y);
	}
	
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	public Vector2 getViewportSize() {
		return new Vector2(viewport.getWorldWidth(), viewport.getWorldHeight());
	}
}
