package org.jsstl.examples.Privacy;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import org.jsstl.core.formula.Signal;
import org.jsstl.core.formula.SignalStatistics2;
import org.jsstl.core.formula.jSSTLScript;
import org.jsstl.core.monitor.SpatialQuantitativeSignal;
import org.jsstl.core.space.GraphModel;
import org.jsstl.monitor.spatial.ThreeValues;
import org.jsstl.monitor.spatial.QuantitativeThreeValues;
import org.jsstl.monitor.spatial.SpatialThreeValues;
import org.jsstl.monitor.threevalues.ThreeValuesAtomic;
import org.jsstl.xtext.formulas.ScriptLoader;
import Model.GlobalManager;

public class Privacy {
	
	public static void main(String[] args) throws Exception {	
		int runs = 5;
	    String propertyName = "corruptM0";
		
	    //Run simulation (to get the spatial structure)
     	TotalPrivacy.TotalPrivacy.main(null);
					
		// %%%%%%%%%%  GRAPH  %%%%%%%%% //		
		// Designing the grid
		int valueX = GlobalManager.getLocationManager().TwoDx;
		int valueY = GlobalManager.getLocationManager().TwoDy;
		
		GraphModel graph = GraphModel.createGrid(valueX, valueY, 1.0);
		// Computing of the distance matrix
		graph.dMcomputation();

	// %%%%%%%%% PROPERTY %%%%%%% //		
	// loading the formulas files
	ScriptLoader loader  = new ScriptLoader();
	jSSTLScript script = loader.load("data/Privacy.sstl");

/////////////  many RUNS  //////////

	double endT = TotalPrivacy.TotalPrivacy.simulationTime;	
	double deltat = 0.1;

	TotalPrivacy.TotalPrivacy.main(null);
			double [][][] trajInit = ModelPrivacy.SimulatorPrivacy.data;
			double [] timeToInsertInit = ModelPrivacy.SimulatorPrivacy.timeArray;	
			
			Signal signalInit = new Signal(graph, timeToInsertInit, trajInit);	
			String[] varInit = {"S_0", "S_1", "S_2", "S_3", "S_4", "M_0", "M_1", "M_2", "M_3", "M_4", "MN", "C", "X"};
			signalInit.setVariables(varInit);
			signalInit.transfomTimeStep(endT,deltat);
			
		    SpatialQuantitativeSignal qSignalInit = script.quantitativeCheck(new HashMap<>(), propertyName, graph, signalInit);
		    int steps = qSignalInit.getSteps();
			SignalStatistics2 statistic = new SignalStatistics2(graph.getNumberOfLocations(),steps);	
		    statistic.add(qSignalInit.quantTraj());
		
		    for ( int r=1 ; r<=runs ; r++) {
		    	TotalPrivacy.TotalPrivacy.main(null);
		    			
		    	double [][][] traj = ModelPrivacy.SimulatorPrivacy.data;
		    	double [] timeToInsert = ModelPrivacy.SimulatorPrivacy.timeArray;	
		    		
		    	Signal signal = new Signal(graph, timeToInsert, traj);
		   		String[] var = {"S_0", "S_1", "S_2", "S_3", "S_4", "M_0", "M_1", "M_2", "M_3", "M_4", "MN", "C", "X"};
		   		signal.setVariables(var);
		   		signal.transfomTimeStep(endT,deltat);
		   	    SpatialQuantitativeSignal qSignal = script.quantitativeCheck(new HashMap<>(), propertyName, graph, signal);		    	    
	    		statistic.add(qSignal.quantTraj());	
	    		
		    }
		    
	double [][] meanTraj = statistic.getAverageTraj();
	double [][] sdTraj = statistic.getStandardDeviationTraj();


//  write SSTL
	String textS = "";
	for (int i=0; i<meanTraj.length;i++) {
		for (int j = 0; j < meanTraj[0].length; j++) {
				textS += String.format(Locale.US, " %20.10f", meanTraj[i][j]);
		}
		textS += "\n";
	}
	PrintWriter printerS = new PrintWriter("data/" + TotalPrivacy.TotalPrivacy.Model + "SSTL.txt");
	printerS.print(textS);
	printerS.close();

	//evaluation TSTL
	HashMap<Integer,SpatialThreeValues> TSTLValues = new HashMap<>();
	for (int j=0; j < meanTraj[0].length; j++){
	SpatialThreeValues resultEval = new SpatialThreeValues(graph);
	for (int i=0; i < meanTraj.length; i++){		
		double a = meanTraj[i][j] - sdTraj[i][j];
		double b = meanTraj[i][j] + sdTraj[i][j];
		double k = 0.04;
		String check = ">";
		ThreeValues value1 = ThreeValuesAtomic.checkIneq(a, b, k, check);
		resultEval.addLoc(graph.getLocation(i), value1);
		TSTLValues.put(j, resultEval);
	}
	}

	//checks if the previous results are UNKNOWN [corruptM0]
	HashMap<Integer,SpatialThreeValues> TimeTSLEval = new HashMap<>();
	for (int j=0; j < TSTLValues.size(); j++){	
    SpatialThreeValues formula = new SpatialThreeValues(graph);
    for (int i=0; i < graph.getNumberOfLocations(); i++){
		ThreeValues value2 = QuantitativeThreeValues.check(TSTLValues.get(j).spatialThreeValues.get(graph.getLocation(i)), ThreeValues.UNKNOWN);
		formula.addLoc(graph.getLocation(i), value2);
		TimeTSLEval.put(j, formula);
	}
	}
	
	//print TSTL output
	String text = "";		
		for (int i=0; i< graph.getNumberOfLocations(); i++){
			for (int j=0; j<TSTLValues.size();j++) {
			if (TSTLValues.get(j).spatialThreeValues.get(graph.getLocation(i))==ThreeValues.FALSE){
				text += String.format(Locale.US, " %20.10f", 0.2);}else{
					if(TSTLValues.get(j).spatialThreeValues.get(graph.getLocation(i))==ThreeValues.TRUE){
						text += String.format(Locale.US, " %20.10f", 0.8);
					}else{
						text += String.format(Locale.US, " %20.10f", 0.5);
					}
				}			
		}
		text += "\n";
	}		
	PrintWriter printer = new PrintWriter("data/" + TotalPrivacy.TotalPrivacy.Model + "_" + runs + "_" + propertyName + ".txt");
	printer.print(text);
	printer.close();	
	}
	
}
