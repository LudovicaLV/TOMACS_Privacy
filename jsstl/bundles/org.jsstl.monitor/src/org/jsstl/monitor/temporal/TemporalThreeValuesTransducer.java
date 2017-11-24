package org.jsstl.monitor.temporal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.jsstl.core.space.DistanceStructure;
import org.jsstl.core.space.Location;
import org.jsstl.monitor.spatial.QuantitativeThreeValues;
import org.jsstl.monitor.spatial.SpatialThreeValues;
import org.jsstl.monitor.spatial.SpatialThreeValuesTransducer;
import org.jsstl.monitor.spatial.ThreeValues;
import org.jsstl.monitor.threevalues.ThreeValuesAtomic;
import org.jsstl.util.signal.BooleanSignal;
import org.jsstl.util.signal.QuantitativeSignal;
import org.jsstl.util.signal.TimeInterval;

public class TemporalThreeValuesTransducer {
		
	/////////// TSTL UNTIL ///////////////////////////////
	public static HashMap<Integer,SpatialThreeValues> until(HashMap<Integer,SpatialThreeValues> TimeTSLValues1, HashMap<Integer,SpatialThreeValues> TimeTSLValues2, double t1, double t2, double delta) {
		HashMap<Integer,SpatialThreeValues> resultTSTL = new HashMap<Integer,SpatialThreeValues>();
		//for (Location loc : TimeTSLValues2.get(0).graph.getLocations()){
			int limit = (int) (t2/delta);
			int boundPsi2 = TimeTSLValues2.size() - limit;
			for (int i = 0; i < boundPsi2; i++){
				int start = i + (int) (t1/delta);
				int stop = i + (int) (t2/delta);
				for (int j = start; j < stop; j++){
					
					SpatialThreeValues v2 = TimeTSLValues2.get(i);
					
					SpatialThreeValues result = new SpatialThreeValues(TimeTSLValues2.get(0).graph);
					for (int l=0; l < TimeTSLValues2.get(i).spatialThreeValues.size(); l++){								
						ThreeValues value = ThreeValues.TRUE;
						result.addLoc(TimeTSLValues2.get(0).graph.getLocation(l), value);
					}
					
					SpatialThreeValues result1 = TimeTSLValues1.get(i);
					SpatialThreeValues resultNew1 = SpatialThreeValuesTransducer.and(result, result1);
					int newi = i+1;
					for (int k=newi; k < j; k++){
						SpatialThreeValues resultNew = SpatialThreeValuesTransducer.and(resultNew1, TimeTSLValues2.get(k));
						resultNew1.clear();
						resultNew1 = resultNew;
					}					
					SpatialThreeValues finalResult = SpatialThreeValuesTransducer.and(resultNew1, v2);
					resultTSTL.put(i, finalResult);
					
				}		
		}	
		return resultTSTL;
}

	/////////// TSTL EVENTUALLY ///////////////////////////////
	public static HashMap<Integer,SpatialThreeValues> eventually(HashMap<Integer,SpatialThreeValues> TimeTSLValues,
			double t1, double t2, double delta) {
		
		//this produces a HashMap of just True SpatialThreeValues
		HashMap<Integer,SpatialThreeValues> True = new HashMap<Integer,SpatialThreeValues>();		
		for (int j=0; j < TimeTSLValues.size(); j++){
		SpatialThreeValues spTV = new SpatialThreeValues(TimeTSLValues.get(j).graph);
		for (int i=0; i < TimeTSLValues.get(j).spatialThreeValues.size(); i++){		
			ThreeValues value = ThreeValues.TRUE;
			spTV.addLoc(TimeTSLValues.get(j).graph.getLocation(i), value);
		}
		True.put(j, spTV);
		}
		HashMap<Integer,SpatialThreeValues> resultTSTL = until(True, TimeTSLValues, t1, t2, delta);
		return resultTSTL;
	}

	// ///////// TSTL ALWAYS ///////////////////////////////
	public static HashMap<Integer,SpatialThreeValues> globally(HashMap<Integer,SpatialThreeValues> TimeTSLValues,
			double t1, double t2, double delta) {
		
		//this produces a HashMap of negation of SpatialThreeValues
		HashMap<Integer,SpatialThreeValues> neg = new HashMap<Integer,SpatialThreeValues>();		
		for (int j=0; j < TimeTSLValues.size(); j++){
			SpatialThreeValues formulaNeg = SpatialThreeValuesTransducer.not(TimeTSLValues.get(j));
			neg.put(j,formulaNeg);
		}		
		HashMap<Integer,SpatialThreeValues> resultTSTLToNeg = eventually(neg, t1, t2, delta);		
		HashMap<Integer,SpatialThreeValues> resultTSTL = new HashMap<Integer,SpatialThreeValues>();		
		for (int j=0; j < resultTSTLToNeg.size(); j++){
			SpatialThreeValues formulaNeg2 = SpatialThreeValuesTransducer.not(resultTSTLToNeg.get(j));
			resultTSTL.put(j,formulaNeg2);
		}		
		return resultTSTL;
	}

}

