package org.jsst.example.Cholera;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jsstl.core.formula.AtomicFormula;
import org.jsstl.core.formula.EventuallyFormula;
import org.jsstl.core.formula.EverywhereFormula;
import org.jsstl.core.formula.Formula;
import org.jsstl.core.formula.GloballyFormula;
import org.jsstl.core.formula.NotFormula;
import org.jsstl.core.formula.ParametricExpression;
import org.jsstl.core.formula.ParametricInterval;
import org.jsstl.core.formula.Signal;
import org.jsstl.core.formula.SignalExpression;
import org.jsstl.core.formula.SomewhereFormula;
import org.jsstl.core.formula.SurroundFormula;
import org.jsstl.core.formula.jSSTLScript;
import org.jsstl.core.monitor.SpatialBooleanSignal;
import org.jsstl.core.monitor.SpatialQuantitativeSignal;
import org.jsstl.core.space.GraphModel;
import org.jsstl.io.FolderSignalReader;
import org.jsstl.io.GraphModelReader;
import org.jsstl.io.SyntaxErrorExpection;
import org.jsstl.io.TraGraphModelReader;
import org.jsstl.io.TxtSpatialBoolSat;
import org.jsstl.io.TxtSpatialQuantSat;
import org.jsstl.util.signal.BooleanSignal;
import org.jsstl.util.signal.QuantitativeSignal;
import org.jsstl.xtext.formulas.ScriptLoader;

public class CholeraSystem {

	public static void main(String[] args) throws IOException, SyntaxErrorExpection {
		
//		/// %%%%%%%  GRAPH    %%%%%   /////////
		TraGraphModelReader graphread = new TraGraphModelReader();
		GraphModel graph = graphread.read("data/space.tra");
		graph.dMcomputation();
		//GraphModel aGrid = GraphModel.createGrid(10, 10, 1.0);
//		
		
		/// %%%%%%%  PROPERTY %%%%%   //////////
		ScriptLoader loader  = new ScriptLoader();
		jSSTLScript script = loader.load("data/Cholera.sstl");
		String[] formulae = script.getFormulae();  	
		
//		/// %%%%%%%  DATA import %%%%%%%%%%%%/////////
		String [] var = script.getVariables();
		FolderSignalReader readSignal = new FolderSignalReader(graph, var);
		File folder = new  File("data/ALLdata");
		Signal signal = readSignal.read(folder);
		
		
		
		System.out.println(Arrays.toString( formulae )); 			
//		Formula phi = script.getFormula( formulae[0] );
//		
//		/// %%%%%%%  CHECK    %%%%%   /////////
		//HashMap[] p = new HashMap[];
		SpatialBooleanSignal b = script.booleanCheck(new HashMap<>(), formulae[0], graph, signal);
		SpatialQuantitativeSignal q = script.quantitativeCheck(new HashMap<>(), formulae[0],graph, signal);
		
		double[] boolSat = b.boolSat();
		double[] quantSat = q.quantSat();
 		
//		
//		/// %%%%%%%  OUTPUT    %%%%%   /////////
		System.out.println(Arrays.toString(boolSat));
		System.out.println(Arrays.toString(quantSat));
		
		TxtSpatialQuantSat outDataQuant = new TxtSpatialQuantSat();
		outDataQuant.write(quantSat, "data/dataSatQuant.txt");
		TxtSpatialBoolSat outDataBool = new TxtSpatialBoolSat();
		outDataBool.write(boolSat, "data/dataSatBool.txt");
	}

}
