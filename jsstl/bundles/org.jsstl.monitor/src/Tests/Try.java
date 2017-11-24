package Tests;

import org.jsstl.core.space.GraphModel;
import org.jsstl.core.space.Location;
import org.jsstl.monitor.spatial.QuantitativeThreeValues;
import org.jsstl.monitor.spatial.SpatialThreeValues;
import org.jsstl.monitor.spatial.SpatialThreeValuesTransducer;
import org.jsstl.monitor.spatial.ThreeValues;

public class Try {

	public static void main(String[] args) {
			
		ThreeValues v1 = ThreeValues.FALSE;
		ThreeValues v2 = ThreeValues.UNKNOWN;
		ThreeValues v3 = ThreeValues.TRUE;
		ThreeValues v4 = QuantitativeThreeValues.not(v1);
		ThreeValues v5 = QuantitativeThreeValues.and(v1,v2);
		ThreeValues v6 = QuantitativeThreeValues.and(v2,v3);
		ThreeValues v7 = QuantitativeThreeValues.or(v1,v2);
		ThreeValues v8 = QuantitativeThreeValues.or(v2,v3);
		
		System.out.println(v4 + " " + v5 + " " + v6 + " " + v7 + " " + v8);
		
		Location l0 = new Location("zero", 0);
		Location l1 = new Location("one", 1);
		Location l2 = new Location("two", 2);
		Location l3 = new Location("three", 3);
		
		l0.addNeighbour(l1);
		l1.addNeighbour(l2);
		l2.addNeighbour(l3);
		
		GraphModel g = new GraphModel();
		
		g.addLoc("zero", 0);
		g.addLoc("one", 1);
		g.addLoc("two", 2);
		g.addLoc("three", 3);
		
		g.addEdge(0, 1, 1.0);
		g.addEdge(1, 2, 1.0);
		g.addEdge(2, 3, 1.0);
		
		g.dMcomputation();	
		
		SpatialThreeValues valuesLoc = new SpatialThreeValues(g);		
		valuesLoc.addLoc(l0, ThreeValues.FALSE);
		valuesLoc.addLoc(l1, ThreeValues.UNKNOWN);
		valuesLoc.addLoc(l2, ThreeValues.TRUE);
		valuesLoc.addLoc(l3, ThreeValues.TRUE);
		
		SpatialThreeValues try1 = SpatialThreeValuesTransducer.somewhere(valuesLoc, 1, 2);
		
		for (int i=0; i < try1.spatialThreeValues.size(); i++){
			System.out.println(try1.spatialThreeValues.get(g.getLocations().get(i)));
		}
		
		SpatialThreeValues valuesLoc2 = new SpatialThreeValues(g);		
		valuesLoc2.addLoc(l0, ThreeValues.TRUE);
		valuesLoc2.addLoc(l1, ThreeValues.TRUE);
		valuesLoc2.addLoc(l2, ThreeValues.UNKNOWN);
		valuesLoc2.addLoc(l3, ThreeValues.TRUE);
		
//		SpatialThreeValues try2 = SpatialThreeValuesTransducer.surround(valuesLoc, valuesLoc2, 1, 2);
//		
//		for (int i=0; i < try1.spatialThreeValues.size(); i++){
//			System.out.println(try2.spatialThreeValues.get(g.getLocations().get(i)));
//		}

	}
}