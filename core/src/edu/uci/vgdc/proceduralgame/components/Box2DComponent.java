package edu.uci.vgdc.proceduralgame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

public class Box2DComponent implements Component {
	public Vector2 startPosition = new Vector2();
	public float startAngle = 0f;
	public float mass = 1f;		// Defaults to same mass as player.
	public float width = 0f;	// Must be set or IllegalArguementException will be thrown!
	public float height = 0f;	// Must be set or IllegalArguementException will be thrown!
	
	public Body body = null;
	public Shape[] shapes = null;
	public BodyDef bodyDef = null;
	public Fixture[] fixtures = null;
	public FixtureDef[] fixtureDefs = null;
	public BodyType bodyType = BodyType.StaticBody;
}
