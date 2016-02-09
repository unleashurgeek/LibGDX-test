package edu.uci.vgdc.proceduralgame;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import edu.uci.vgdc.proceduralgame.components.Box2DComponent;
import edu.uci.vgdc.proceduralgame.components.PlayerComponent;

public class Player {
	
	private Entity playerEntity;
	private Box2DComponent box2DComponent;
	
	private float velocity = 5f;
	private Vector2 direction = new Vector2();
	
	public Player(Engine engine, World world) {
		this.playerEntity = new Entity();
		this.playerEntity.add(new PlayerComponent());
		
		this.box2DComponent = new Box2DComponent();
		this.box2DComponent.bodyType = BodyType.DynamicBody;
		this.box2DComponent.width = 1f;
		this.box2DComponent.height = 2f;
		this.box2DComponent.fixtureDefs = new FixtureDef[2];
		this.box2DComponent.fixtureDefs[0] = new FixtureDef();
		this.box2DComponent.fixtureDefs[0].isSensor = true;
		this.box2DComponent.fixtureDefs[0].density = 1f;
		this.box2DComponent.fixtureDefs[1] = new FixtureDef();
		
		this.box2DComponent.shapes = new Shape[2];
		this.box2DComponent.shapes[0] = new PolygonShape();
		((PolygonShape)this.box2DComponent.shapes[0]).setAsBox(.65f, 1.2f);
		this.box2DComponent.shapes[1] = new PolygonShape();
		((PolygonShape)this.box2DComponent.shapes[1]).setAsBox(.65f, .4f, new Vector2(0, -.81f), 0);
		
		this.playerEntity.add(box2DComponent);
		engine.addEntity(this.playerEntity);
	}
	
	public void calculateGunDirection(Vector2 mouse) {
		float direction = (float)MathUtils.atan2(mouse.y - this.box2DComponent.body.getPosition().y, mouse.x - this.box2DComponent.body.getPosition().x);
		if ((direction) < 0) direction += 360;
		
		
	}
	
	public void movePlayer(float x, float y) {
		direction.add(x, y);
		if (direction.x != 0) direction.x /= Math.abs(direction.x);
		if (direction.y != 0) direction.y /= Math.abs(direction.y);
	}
	
	public void update() {
		this.box2DComponent.body.setLinearVelocity(new Vector2(direction.x, direction.y).clamp(-1, 1).scl(velocity));
	}
	
	public void shoot() {
		
	}
}
