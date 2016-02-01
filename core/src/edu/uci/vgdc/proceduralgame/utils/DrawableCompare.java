package edu.uci.vgdc.proceduralgame.utils;

import java.util.Comparator;

import com.badlogic.ashley.core.Entity;

import edu.uci.vgdc.proceduralgame.components.Mappers;

public class DrawableCompare implements Comparator<Entity> {
	
	@Override
	public int compare(Entity o1, Entity o2) {
		return ((Mappers.drawable.get(o2).drawPoint == Mappers.drawable.get(o1).drawPoint) ? 0 :
				(Mappers.drawable.get(o2).drawPoint - Mappers.drawable.get(o1).drawPoint) > 0 ? 1 : -1);
	}
}
