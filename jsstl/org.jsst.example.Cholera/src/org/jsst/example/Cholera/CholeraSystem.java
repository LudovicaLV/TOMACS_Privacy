package org.jsst.example.Cholera;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.jsstl.core.formula.Signal;
import org.jsstl.core.formula.jSSTLScript;
import org.jsstl.core.space.GraphModel;
import org.jsstl.io.FolderSignalReader;
import org.jsstl.io.SyntaxErrorExpection;
import org.jsstl.io.TraGraphModelReader;
import org.jsstl.io.TxtSpatialBoolSat;
import org.jsstl.io.TxtSpatialQuantSat;
import org.jsstl.xtext.formulas.*;

public class CholeraSystem {

	public static void main(String[] args) throws IOException, SyntaxErrorExpection {
		
//		/// %%%%%%%  GRAPH    %%%%%   /////////
		TraGraphModelReader graphread = new TraGraphModelReader();
		GraphModel graph = graphread.read("data/space.tra");
		graph.dMcomputation();
		
//		/// %%%%%%%  PROPERTY %%%%%   //////////
		ScriptLoader loader  = new ScriptLoader();
		jSSTLScript script = loader.load("data/Cholera.sstl");
		
//		/// %%%%%%%  DATA import %%%%%%%%%%%%/////////
		String [] var = script.getVariables();
		FolderSignalReader readSignal = new FolderSignalReader(graph, var);
		File folder = new  File("data/ALLdata");
		Signal signal = readSignal.read(folder);
		
//		/// %%%%%%%  CHECK    %%%%%   /////////
		double[] boolSat  = script.booleanSat("phi1", new HashMap<>(), graph, signal); 		
		double[] quantSat  = script.quantitativeSat("phi1", new HashMap<>(), graph, signal);
		
//		/// %%%%%%%  OUTPUT    %%%%%   /////////
		System.out.println(Arrays.toString(boolSat));
		System.out.println(Arrays.toString(quantSat));
		
		TxtSpatialQuantSat outDataQuant = new TxtSpatialQuantSat();
		outDataQuant.write(quantSat, "data/dataSatQuant.txt");
		TxtSpatialBoolSat outDataBool = new TxtSpatialBoolSat();
		outDataBool.write(boolSat, "data/dataSatBool.txt");
	}

}
