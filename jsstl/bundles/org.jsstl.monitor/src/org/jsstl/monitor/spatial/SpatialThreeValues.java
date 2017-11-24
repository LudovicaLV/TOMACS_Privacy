package org.jsstl.monitor.spatial;

import java.util.HashMap;

import org.jsstl.core.space.GraphModel;
import org.jsstl.core.space.Location;

public class SpatialThreeValues {

	public GraphModel graph;
	public HashMap<Location, ThreeValues> spatialThreeValues;
	
	public SpatialThreeValues(GraphModel g) {
		graph = g;
		spatialThreeValues = new HashMap<Location, ThreeValues>(
				g.getNumberOfLocations());
	}
	
	public void addLoc(Location loc, ThreeValues TVal) {
		this.spatialThreeValues.put(loc, TVal);
	}

	public void clear() {
		spatialThreeValues.clear();
	}
	
}
