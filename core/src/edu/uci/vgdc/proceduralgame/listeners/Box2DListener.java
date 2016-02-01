package edu.uci.vgdc.proceduralgame.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import edu.uci.vgdc.proceduralgame.components.Box2DComponent;
import edu.uci.vgdc.proceduralgame.components.Mappers;

public class Box2DListener implements EntityListener {

	private final World world;
	
	public Box2DListener(World world) {
		this.world = world;
	}
	
	@Override
	public void entityAdded(Entity entity) {
		Box2DComponent bC = Mappers.box2D.get(entity);
		if (bC.width == 0f || bC.height == 0f)
			throw new IllegalArgumentException("Width and/or Height must be set with a value greater than zero!");
		
		if (bC.body == null) {
			if (bC.bodyDef == null) {
				bC.bodyDef = new BodyDef();
				bC.bodyDef.position.set(bC.startPosition);
				bC.bodyDef.angle = bC.startAngle;
				bC.bodyDef.linearDamping = 10f;
				bC.bodyDef.angularDamping = 10f;
				bC.bodyDef.fixedRotation = true;
			}
			bC.bodyDef.type = bC.bodyType;
			bC.body = this.world.createBody(bC.bodyDef);
			bC.body.setUserData(entity);
			
			Shape[] shapes = bC.shapes;
			if (shapes == null) {
				shapes = new Shape[1];
				shapes[0] = new PolygonShape();
				((PolygonShape)shapes[0]).setAsBox(bC.width/2, bC.height/2);
			}
			FixtureDef[] fixtureDefs = bC.fixtureDefs;
			if (fixtureDefs == null) {
				fixtureDefs = new FixtureDef[1];
				fixtureDefs[0] = new FixtureDef();
				fixtureDefs[0].density = bC.mass;
			}
			
			if (fixtureDefs.length != shapes.length)
				throw new IllegalArgumentException("You must have the same amount of Shapes as Fixtures!");
			bC.fixtures = new Fixture[fixtureDefs.length];
			for (int i = 0; i < fixtureDefs.length; i++) {
				fixtureDefs[i].shape = shapes[i];
				bC.fixtures[i] = bC.body.createFixture(fixtureDefs[i]);
				shapes[i].dispose();
			}	
		}
	}

	@Override
	public void entityRemoved(Entity entity) {
		Box2DComponent bC = Mappers.box2D.get(entity);
		if (bC.body != null) {
			world.destroyBody(bC.body);
			bC.body = null;
		}
	}

}
