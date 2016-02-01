package edu.uci.vgdc.proceduralgame;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameInput implements InputProcessor {
	
	private final Player player;
	private final GameCamera camera;
	
	private final Array<Integer> heldKeys;
	private Vector2 mousePos;
	
	public GameInput(Player player, GameCamera camera) {
		this.player = player;
		this.camera = camera;
		
		heldKeys = new Array<Integer>();
		mousePos = new Vector2();
	}
	
	public void update() {
		for (int key : heldKeys)
			keyPressed(key);
	}
	
	public boolean keyPressed(int keycode) {
		switch (keycode) {
		
		case Keys.R:
			camera.rumble(.1f);
			return true;
		
		}
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		heldKeys.add(keycode);
		switch (keycode) {
		
		case Keys.W:
			player.movePlayer(0, 1);
			return true;
		case Keys.A:
			player.movePlayer(-1, 0);
			return true;
		case Keys.S:
			player.movePlayer(0, -1);
			return true;
		case Keys.D:
			player.movePlayer(1, 0);
			return true;
			
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		heldKeys.removeValue(keycode, true);
		switch (keycode) {
		
		case Keys.W:
			player.movePlayer(0, -1);
			return true;
		case Keys.A:
			player.movePlayer(1, 0);
			return true;
		case Keys.S:
			player.movePlayer(0, 1);
			return true;
		case Keys.D:
			player.movePlayer(-1, 0);
			return true;
			
		}
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mousePos.set(screenX, screenY);
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mousePos.set(screenX, screenY);
		return false;
	}
	
	@Override
	public boolean scrolled(int amount) { return false; }
	
	@Override
	public boolean keyTyped(char character) { return false; }

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
}
