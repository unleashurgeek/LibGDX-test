package edu.uci.vgdc.proceduralgame;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class GameInput implements InputProcessor {
	
	private final GameManager manager;
	
	private final Array<Integer> heldKeys;
	private final Array<Integer> mouseHeldKeys;
	private Vector2 mousePos;
	
	public GameInput(GameManager manager) {
		this.manager = manager;
		
		heldKeys = new Array<Integer>();
		mouseHeldKeys = new Array<Integer>();
		mousePos = new Vector2();
	}
	
	public void update() {
		for (int key : heldKeys)
			keyPressed(key);
		
		for (int button : mouseHeldKeys)
			mousePressed(button);
		
		this.manager.getPlayer().calculateGunDirection(this.manager.getCamera().unproject(mousePos));
	}
	
	public boolean mousePressed(int button) {
		switch (button) {
		
		case 0:
			this.manager.getPlayer().shoot();
			return true;
		}
		return false;
	}
	
	public boolean keyPressed(int keycode) {
		switch (keycode) {
		
		case Keys.R:
			this.manager.getCamera().rumble(.05f);
			return true;
		
		}
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		heldKeys.add(keycode);
		switch (keycode) {
		
		case Keys.W:
			this.manager.getPlayer().movePlayer(0, 1);
			return true;
		case Keys.A:
			this.manager.getPlayer().movePlayer(-1, 0);
			return true;
		case Keys.S:
			this.manager.getPlayer().movePlayer(0, -1);
			return true;
		case Keys.D:
			this.manager.getPlayer().movePlayer(1, 0);
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		heldKeys.removeValue(keycode, true);
		switch (keycode) {
		
		case Keys.W:
			this.manager.getPlayer().movePlayer(0, -1);
			return true;
		case Keys.A:
			this.manager.getPlayer().movePlayer(1, 0);
			return true;
		case Keys.S:
			this.manager.getPlayer().movePlayer(0, 1);
			return true;
		case Keys.D:
			this.manager.getPlayer().movePlayer(-1, 0);
			return true;
			
		}
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		mouseHeldKeys.add(button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		mouseHeldKeys.removeValue(button, true);
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
}
