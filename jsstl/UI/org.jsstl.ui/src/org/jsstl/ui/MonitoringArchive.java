/**
 * 
 */
package org.jsstl.ui;

import java.util.LinkedList;
import java.util.Map;

import org.jsstl.core.monitor.SpatialBooleanSignal;
import org.jsstl.core.monitor.SpatialQuantitativeSignal;
import org.jsstl.core.space.Location;

/**
 * @author loreti
 *
 */
public class MonitoringArchive {
	
	public static final Integer BOOLEAN_SIGNAL_CATEGORY = 0;

	public static final Integer QUANTITATIVE_SIGNAL_CATEGORY = 1;

	LinkedList<BooleanMonitoringResult> booleanResults = new LinkedList<>();

	LinkedList<QuantitativeMonitoringResult> quantiativeResults = new LinkedList<>();
	
	public MonitoringArchive() {
		
	}
	
	public void addBooleanResult(Map<String, Double> parameterValues, String formula, SpatialBooleanSignal sbs) {
		booleanResults.add(new BooleanMonitoringResult(parameterValues, formula, sbs));
	}

	public void addQuantitativeResult(Map<String, Double> parameterValues, String formula,
			SpatialQuantitativeSignal sbs) {
		quantiativeResults.add(new QuantitativeMonitoringResult(parameterValues, formula, sbs));
	}
	
	public Object[] getCategories() {
		return new Integer[] { BOOLEAN_SIGNAL_CATEGORY , QUANTITATIVE_SIGNAL_CATEGORY };
	}
	
	public class BooleanMonitoringResult {
		
		public Map<String,Double> parameters;
		public String formula;
		public SpatialBooleanSignal spatialSignal;
		
		public BooleanMonitoringResult(Map<String, Double> parameters, String formula,
				SpatialBooleanSignal spatialSignal) {
			super();
			this.parameters = parameters;
			this.formula = formula;
			this.spatialSignal = spatialSignal;
		}

		public Object[] getChildren() {
			Object[] toReturn = new Object[spatialSignal.graph.getNumberOfLocations()];
			for (Location loc : spatialSignal.graph.getLocations()) {
				toReturn[loc.getPosition()] = new LocationBasedResult(loc, this);
			}
			return toReturn;
		}
		
		public String toString() {
			return formula+(parameters.isEmpty()?"":parameters.toString());
		}

	}
	
	public class QuantitativeMonitoringResult {
		
		public Map<String,Double> parameters;
		public String formula;
		public SpatialQuantitativeSignal spatialSignal;
		
		public QuantitativeMonitoringResult(Map<String, Double> parameters, String formula,
				SpatialQuantitativeSignal spatialSignal) {
			super();
			this.parameters = parameters;
			this.formula = formula;
			this.spatialSignal = spatialSignal;
		}
		
		public Object[] getChildren() {
			Object[] toReturn = new Object[spatialSignal.graph.getNumberOfLocations()];
			for (Location loc : spatialSignal.graph.getLocations()) {
				toReturn[loc.getPosition()] = new LocationBasedResult(loc, this);
			}
			return toReturn;
		}
		
		public String toString() {
			return formula+(parameters.isEmpty()?"":parameters.toString());
		}

	}
	
	public class LocationBasedResult {
		
		public Object parent;
		public Location location;
		
		public LocationBasedResult( Location location , Object parent ) {
			this.location = location;
			this.parent = parent;
		}
		
		public String toString() {
			return location.getLabel();
		}
	}

	public LinkedList<BooleanMonitoringResult> getBooleanResults() {
		return this.booleanResults;
	}
	
	public LinkedList<QuantitativeMonitoringResult> getQuantitativeResults() {
		return this.quantiativeResults;
	}

}
