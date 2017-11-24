package Model;

import java.util.Arrays;

public class Cost {
	
	public static double Update(double cost, String nameToCheck){
		if (Arrays.asList(TotalPrivacy.TotalPrivacy.ListAction).contains(nameToCheck)){
			int positionAction = Arrays.asList(TotalPrivacy.TotalPrivacy.ListAction).indexOf(nameToCheck);
			double costToAdd = TotalPrivacy.TotalPrivacy.ListCost[positionAction];
		    double costUp = cost + costToAdd;
		    return costUp;
		}else{
		return cost;
	}
	}

}
