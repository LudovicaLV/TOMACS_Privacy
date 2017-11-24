package TotalPrivacy;

import ParserProg5Privacy.ModelPrivacy;

public class Writing {
	
	//used in SimulatorCells
	public static String Log (){	
		String log = "/Users/ludovicaluisavissat/workspacejSSTL/QEST_Privacy3/src/Output/Log/Output" +  ModelPrivacy._SIMULATION_ID;
		return log;
	}

	//used in SimulatorCells
	public static String Meta (){	
		String meta = "/Users/ludovicaluisavissat/workspacejSSTL/QEST_Privacy3/src/Output/Meta/Output" +  ModelPrivacy._SIMULATION_ID;
		return meta;
	}
	
	//used in SimulatorCells
	public static String Data (){	
		String meta = "/Users/ludovicaluisavissat/workspacejSSTL/QEST_Privacy3/src/Output/Data/Output" +  ModelPrivacy._SIMULATION_ID;
		return meta;
	}

	//used in SimulatorCells
	public static String Cost (){	
		String meta = "/Users/ludovicaluisavissat/workspacejSSTL/QEST_Privacy3/src/Output/Cost/Output" +  ModelPrivacy._SIMULATION_ID;
		return meta;
	}
}
