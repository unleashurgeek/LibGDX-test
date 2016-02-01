package edu.uci.vgdc.proceduralgame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class DrawableComponent implements Component {
	public Vector2 position = new Vector2();
	public Vector2 scale = new Vector2(1.0f, 1.0f);
	public float rotation = 0f;
	public float drawPoint = 0f;
	public TextureRegion region = null;
}
