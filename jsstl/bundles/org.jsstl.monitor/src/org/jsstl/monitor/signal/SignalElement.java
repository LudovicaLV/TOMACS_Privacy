package org.jsstl.monitor.signal;

class SignalElement<D> {
	
	private final double time;
	
	private final D value;
	
	public SignalElement( double time , D value ) {
		this.time = time;
		this.value = value;
	}
	
	public double getTime() {
		return time;
	}
	
	public D getValue() {
		return value;
	}

	public boolean isAfter(SignalElement<D> e) {
		return this.time > e.time;
	}

	public boolean isBefore(SignalElement<D> e) {
		return this.time < e.time;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "["+time+":"+value.toString()+"]";
	}
	
}