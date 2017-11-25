package ParserProg5Privacy;

import ParserRulesPrivacy.MELArules;

public class ModelPrivacy {
	
	public static int _SIMULATION_ID;
	public static int numberOfRuns = TotalPrivacy.TotalPrivacy.numberOfRuns;

	public static void main(String[] args) throws Exception {
			
     	_SIMULATION_ID = 1;
		for(int i = 1; i <= numberOfRuns; i++)
		{   MELAprog5 Parser= new MELAprog5();
		    Parser.parseFromFile(TotalPrivacy.TotalPrivacy.Folder + TotalPrivacy.TotalPrivacy.Model + ".mela");
		    System.out.println("Model parsed correctly."); 
	        System.out.println("Simulation -> " + _SIMULATION_ID ); 
			MELArules Parser2= new MELArules();
			Parser2.parseFromFile("/Users/ludovicaluisavissat/workspacejSSTL/QEST_Privacy_Michele/src/Rules.txt");
			_SIMULATION_ID++;
		}	
	}
}




