/**
 * 
 */
package org.jsstl.monitor.signal;

/**
 * @author loreti
 *
 */
public class SignalInterval<D> {

	private SignalElement<D> start;
	private SignalElement<D> end;
	
	public SignalInterval( SignalElement<D> start , SignalElement<D> end ) {
		this.start = start;
		this.end = end;
	}
	
	public double start() {
		return start.getTime();
	}
	
	public SignalElement<D> startElement() {
		return start;
	}
	
	public SignalElement<D> endElement() {
		return end;
	}
	
	public double end() {
		return end.getTime();
	}
	
	public D getValue( double t ) {
		if ((t>=start.getTime())&&(t<end.getTime())) {
			return start.getValue();
		}
		throw new IllegalArgumentException("A value in ["+start.getTime()+","+end.getTime()+") is expected.");
	}

	@Override
	public String toString() {
		return start.toString();
	}


	
}
