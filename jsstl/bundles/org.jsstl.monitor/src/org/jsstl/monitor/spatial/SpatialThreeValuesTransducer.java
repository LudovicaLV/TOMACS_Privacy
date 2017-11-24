package org.jsstl.monitor.spatial;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.jsstl.core.monitor.SpatialBooleanSignal;
import org.jsstl.core.space.DistanceStructure;
import org.jsstl.core.space.Location;
import org.jsstl.util.signal.BooleanSignal;
import org.jsstl.util.signal.BooleanSignalTransducer; 

public class SpatialThreeValuesTransducer {

	// ///////// SURROUND ///////////////////////////////
	public static SpatialThreeValues surround(
			SpatialThreeValues v1, SpatialThreeValues v2, double d1, double d2) {
		SpatialThreeValues v0 = new SpatialThreeValues(v1.graph);	
		for (Location loc : v1.graph.getLocations()) {
			// put threevalue to false for locations that don't satisfy the
			// constraints
			HashMap<Location, ThreeValues> v1Local = resetSignal(
					v1.graph.getDM(), v1, loc, 0, d2);
			HashMap<Location, ThreeValues> v2Local = resetSignal(
					v2.graph.getDM(), v2, loc, d1, d2);			
			ThreeValues currentValue = ThreeValues.FALSE;
				// set the value of phi1 and phi2 for each location
				HashMap<Location, ThreeValues> valueProp1 = valueProp(v1Local);
				HashMap<Location, ThreeValues> valueProp2 = valueProp(v2Local);
				// computation of the value of the surround in all the location
				HashMap<Location, ThreeValues> surroundVal = fixPointSurround(
						valueProp1, valueProp2);
				currentValue = surroundVal.get(loc);		
			v0.addLoc(loc, currentValue);
		}
		return v0;
	}

	// put threevalue to false for locations that don't satisfy the
	// constraints
	private static HashMap<Location, ThreeValues> resetSignal(
			double[][] dM, SpatialThreeValues sv, Location loc, double d1, double d2) {
		DistanceStructure infoGraph = new DistanceStructure(sv.graph);
		ThreeValues FalseValue = ThreeValues.FALSE;
		Set<Location> subSetLoc = infoGraph
				.computSubSetSatisfy(dM, loc, d1, d2);
		HashMap<Location, ThreeValues> vLocal = new HashMap<Location, ThreeValues>();
		vLocal.putAll(sv.spatialThreeValues);
		for (Location location : vLocal.keySet()) {
			if (!subSetLoc.contains(location))
				vLocal.replace(location, FalseValue);
		}
		return vLocal;
	}

	// function to set the value of phi1 and phi2 for each location 
	private static HashMap<Location, ThreeValues> valueProp(
			HashMap<Location, ThreeValues> v1Loc) {
		HashMap<Location, ThreeValues> valProp = new HashMap<Location, ThreeValues>();
		for (Location loc : v1Loc.keySet()) {
			ThreeValues val = v1Loc.get(loc);			
			valProp.put(loc, val);
		}
		return valProp;
	}

	// method to compute the value of the surround in all the loc
	private static HashMap<Location, ThreeValues> fixPointSurround(
			HashMap<Location, ThreeValues> valueProp1,
			HashMap<Location, ThreeValues> valueProp2) {
		ThreeValues value;
		HashMap<Location, ThreeValues> preVal = valueProp1;
		HashMap<Location, ThreeValues> locVal = new HashMap<Location, ThreeValues>();
		int k = 1;
		boolean flag = false;
		while (k <= valueProp1.size() + 1) {
			for (Entry<Location, ThreeValues> e : preVal.entrySet()) {
				value = surroundFcn(k, e.getKey(), preVal, valueProp2);
				locVal.put(e.getKey(), value);
				flag = flag || (!value.equals(e.getValue()));
			}
			if (!flag)
				return preVal;
			preVal = locVal;
			locVal = new HashMap<Location, ThreeValues>();
			flag = false;
			k++;
//			preVal.putAll(locVal);
		}
		return preVal;
	}

	private static ThreeValues surroundFcn(int k, Location loc,
			HashMap<Location, ThreeValues> preVal,
			HashMap<Location, ThreeValues> valueProp2) {
		ThreeValues value;
		ThreeValues valMinAdj = ThreeValues.TRUE;			
		for (Location locAdj : loc.getNeighbourd()) {
			valMinAdj = QuantitativeThreeValues.and(valMinAdj,
					QuantitativeThreeValues.or(preVal.get(locAdj), valueProp2.get(locAdj)));
		}
		value = QuantitativeThreeValues.and(preVal.get(loc), valMinAdj);
		return value;
	}
	
	
	//CHANGED ALL THE OPERATORS TO DEAL WITH THE NEW THREE-VALUED LOGIC

	// ///////// AND ///////////////////////////////
	public static SpatialThreeValues and(SpatialThreeValues v1,
			SpatialThreeValues v2) {
		ThreeValues v0;
		SpatialThreeValues vsp0 = new SpatialThreeValues(v1.graph);
		for (Entry<Location, ThreeValues> e : v1.spatialThreeValues.entrySet()) {
			v0 = QuantitativeThreeValues.and(
					e.getValue(),
					v2.spatialThreeValues.get(e.getKey()));
			vsp0.addLoc(e.getKey(), v0);
		}
		return vsp0;
	}

	// ///////// OR ///////////////////////////////
	public static SpatialThreeValues or(SpatialThreeValues v1,
			SpatialThreeValues v2) {

		ThreeValues v0;
		SpatialThreeValues vsp0 = new SpatialThreeValues(v1.graph);
		for (Entry<Location, ThreeValues> e : v1.spatialThreeValues.entrySet()) {
			v0 = QuantitativeThreeValues.or(
					e.getValue(),
					v2.spatialThreeValues.get(e.getKey()));
			vsp0.addLoc(e.getKey(), v0);
		}
		return vsp0;
	}

	// ///////// NOT ///////////////////////////////
	public static SpatialThreeValues not(SpatialThreeValues v1) {

		ThreeValues v0;
		SpatialThreeValues vsp0 = new SpatialThreeValues(v1.graph);
		for (Entry<Location, ThreeValues> e : v1.spatialThreeValues.entrySet()) {
			v0 = QuantitativeThreeValues.not(e.getValue());
			vsp0.addLoc(e.getKey(), v0);
		}
		return vsp0;
	}



	// ///////// SOMEWHERE ///////////////////////////////
	public static SpatialThreeValues somewhere(
			SpatialThreeValues v1, double d1, double d2) {
		DistanceStructure infoGraph = new DistanceStructure(v1.graph);
		ThreeValues v0;
		SpatialThreeValues vsp0 = new SpatialThreeValues(v1.graph);
		for (Location loc : v1.graph.getLocations()) {
			Set<Location> subSetLoc = infoGraph.computSubSetSatisfy(v1.graph.getDM(), loc,
					d1, d2);
			if (subSetLoc.isEmpty()) {
				//CHECK!
					v0 = ThreeValues.FALSE;
			} else {
				Iterator<Location> locationIterator = subSetLoc.iterator();
				Location current = locationIterator.next();
				v0 = v1.spatialThreeValues.get(current);
				while (locationIterator.hasNext()) {
					v0 = QuantitativeThreeValues.or(v0,
							v1.spatialThreeValues.get(locationIterator
									.next()));
				}
			}
			vsp0.addLoc(loc, v0);
		}
		return vsp0;
	}

	// ///////// EVERYWHERE ///////////////////////////////
	public static SpatialThreeValues everywhere(
			SpatialThreeValues v1, double d1, double d2) {

		DistanceStructure infoGraph = new DistanceStructure(v1.graph);
		ThreeValues v0;
		SpatialThreeValues vsp0 = new SpatialThreeValues(v1.graph);
		for (Location loc : v1.graph.getLocations()) {
			Set<Location> subSetLoc = infoGraph.computSubSetSatisfy(v1.graph.getDM(), loc,
					d1, d2);
			if (subSetLoc.isEmpty()) {
				//CHECK!!
				v0 = ThreeValues.FALSE;
			} else {
				Iterator<Location> locationIterator = subSetLoc.iterator();
				Location current = locationIterator.next();
				v0 = v1.spatialThreeValues.get(current);
				while (locationIterator.hasNext()) {
					v0 = QuantitativeThreeValues.and(v0,
							v1.spatialThreeValues.get(locationIterator
									.next()));
				}
			}
			vsp0.addLoc(loc, v0);
		}
		return vsp0;
	}
	
	
	// ///////// EVALUATE TRUTH VALUE ///////////////////////////////
	public static SpatialThreeValues evaluate(SpatialThreeValues v1, ThreeValues check) {

		ThreeValues v0;
		SpatialThreeValues vsp0 = new SpatialThreeValues(v1.graph);
		for (Entry<Location, ThreeValues> e : v1.spatialThreeValues.entrySet()) {
			v0 = QuantitativeThreeValues.check(e.getValue(), check);
			vsp0.addLoc(e.getKey(), v0);
		}
		return vsp0;
	}


}
