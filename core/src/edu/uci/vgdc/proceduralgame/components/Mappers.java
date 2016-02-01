package edu.uci.vgdc.proceduralgame.components;

import com.badlogic.ashley.core.ComponentMapper;

public class Mappers {
	public static final ComponentMapper<DrawableComponent> drawable = ComponentMapper.getFor(DrawableComponent.class);
	public static final ComponentMapper<Box2DComponent> box2D = ComponentMapper.getFor(Box2DComponent.class);
}
