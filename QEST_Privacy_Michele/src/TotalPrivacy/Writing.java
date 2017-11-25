package TotalPrivacy;

import ParserProg5Privacy.ModelPrivacy;

public class Writing {
	
	//used in SimulatorPrivacy
	public static String Log (){	
		String log = TotalPrivacy.Folder + "Output/Log/Output" +  ModelPrivacy._SIMULATION_ID;
		return log;
	}

	//used in SimulatorPrivacy
	public static String Meta (){	
		String meta = TotalPrivacy.Folder + "Output/Meta/Output" +  ModelPrivacy._SIMULATION_ID;
		return meta;
	}
	
	//used in SimulatorPrivacy
	public static String Data (){	
		String meta = TotalPrivacy.Folder + "Output/Data/Output" +  ModelPrivacy._SIMULATION_ID;
		return meta;
	}

	//used in SimulatorPrivacy
	public static String Cost (){	
		String meta = TotalPrivacy.Folder + "Output/Cost/Output" +  ModelPrivacy._SIMULATION_ID;
		return meta;
	}
}
