package org.jsstl.monitor.threevalues;

import org.jsstl.monitor.spatial.ThreeValues;

public class ThreeValuesAtomic {
	
	// p in [a,b], check k
	
	public static ThreeValues checkIneq(double a, double b, double k, String check){
		if (check == ">"){
			if (k < a){
				return ThreeValues.TRUE;
			}else{
				if (k > b){
					return ThreeValues.FALSE;
				}else{
					return ThreeValues.UNKNOWN;
				}
			}
		}else{
			if (check == "<") {
				if (k < a){
					return ThreeValues.FALSE;
				}else{
					if (k > b){
						return ThreeValues.TRUE;
					}else{
						return ThreeValues.UNKNOWN;
					}
				}
				
			}else{
				return null;
			}
		}
	}
	

}
