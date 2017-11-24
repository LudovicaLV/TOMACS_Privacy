/**
 * 
 */
package org.jsstl.monitor.signal;

/**
 * @author ludovicaluisavissat
 *
 */
public class ThreeValuesOperators implements DomainFunction<ThreeValues> {

	@Override
	public ThreeValues conjunction(ThreeValues x, ThreeValues y) {
		if (x == ThreeValues.TRUE && y == ThreeValues.TRUE){
			return ThreeValues.TRUE;
		}else{
			if (x == ThreeValues.FALSE || y == ThreeValues.FALSE){
			return ThreeValues.FALSE;
			}else{
			return ThreeValues.UNKNOWN;
			}
		}
	}

	@Override
	public ThreeValues disjunction(ThreeValues x, ThreeValues y) {
		if (x == ThreeValues.FALSE && y == ThreeValues.FALSE){
			return ThreeValues.FALSE;
		}else{
			if (x == ThreeValues.TRUE || y == ThreeValues.TRUE){
				return ThreeValues.TRUE;
			}else{
				return ThreeValues.UNKNOWN;
			}
		}
	}

	@Override
	public ThreeValues negation(ThreeValues x) {
		if (x == ThreeValues.UNKNOWN){
			return ThreeValues.UNKNOWN;
		}else{
			if  (x == ThreeValues.FALSE){
				return ThreeValues.TRUE;
			}else{
				return ThreeValues.FALSE;
			}
		}
	}
    
	
	@Override
	public int compare(ThreeValues x, ThreeValues y) {		
	    if (x == y) {
	       return 0;
	    } else if (x == ThreeValues.FALSE) {
	        return -1;
	    } else if (x == ThreeValues.TRUE) {
	        return 1;
	    } else if (x == ThreeValues.UNKNOWN && y == ThreeValues.TRUE) {
	        return -1;
	    } else {
	        return 1;
	    }

}

}

