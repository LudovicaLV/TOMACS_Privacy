/**
 * 
 */
package org.jsstl.monitor.signal;

/**
 * Domain functions for Boolean signals.
 * 
 * @author loreti
 *
 */
public class DoubleOperators implements DomainFunction<Double> {

	@Override
	public Double conjunction(Double x, Double y) {
		return Math.min(x, y);
	}

	@Override
	public Double disjunction(Double x, Double y) {
		return Math.max(x, y);
	}

	@Override
	public Double negation(Double x) {
		return -x;
	}

	@Override
	public int compare(Double x, Double y) {
		return x.compareTo(y);
	}



}
