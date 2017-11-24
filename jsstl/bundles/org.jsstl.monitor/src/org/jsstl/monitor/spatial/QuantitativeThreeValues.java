package org.jsstl.monitor.spatial;

import org.jsstl.util.signal.QuantitativeSignal;

public class QuantitativeThreeValues {
	
	// ///////// AND ///////////////////////////////
	public static ThreeValues and(ThreeValues v1, ThreeValues v2) {		
		if (v1 == ThreeValues.TRUE && v2 == ThreeValues.TRUE){
			return ThreeValues.TRUE;
		}else{
			if (v1 == ThreeValues.FALSE || v2 == ThreeValues.FALSE){
			return ThreeValues.FALSE;
			}else{
			return ThreeValues.UNKNOWN;
			}
		}
	}

	// ///////// OR ///////////////////////////////
	public static ThreeValues or(ThreeValues v1, ThreeValues v2) {	
		if (v1 == ThreeValues.FALSE && v2 == ThreeValues.FALSE){
			return ThreeValues.FALSE;
		}else{
			if (v1 == ThreeValues.TRUE || v2 == ThreeValues.TRUE){
				return ThreeValues.TRUE;
			}else{
				return ThreeValues.UNKNOWN;
			}
		}
	}

	// ///////// NOT ///////////////////////////////
	public static ThreeValues not(ThreeValues v1) {
		if (v1 == ThreeValues.UNKNOWN) {
			return ThreeValues.UNKNOWN;
		} else {
			if (v1 == ThreeValues.FALSE) {
				return ThreeValues.TRUE;
			} else {
				return ThreeValues.FALSE;
			}
		}
	}
	
	
	public static ThreeValues check(ThreeValues v1, ThreeValues v2) {
		if (v1 == v2) {
			return ThreeValues.TRUE;
		} else {
				return ThreeValues.FALSE;			
		}
	}




}
