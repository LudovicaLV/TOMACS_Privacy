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
public class BooleanOperators implements DomainFunction<Boolean> {

	@Override
	public Boolean conjunction(Boolean x, Boolean y) {
		return x&&y;
	}

	@Override
	public Boolean disjunction(Boolean x, Boolean y) {
		return x||y;
	}

	@Override
	public Boolean negation(Boolean x) {
		return !x;
	}

	@Override
	public int compare(Boolean x, Boolean y) {
		return (x?1:0)-(y?1:0);
	}

}