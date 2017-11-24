/**
 * 
 */
package org.jsstl.monitor.signal;

import java.util.ListIterator;

/**
 * @author loreti
 *
 */
public class BackwardSignalCursor<D> implements SignalCursor<D> {
	
	private SignalElement<D> current;
	private SignalElement<D> next;
	private final Signal<D> signal;
	private ListIterator<SignalElement<D>> iterator;
	private double time;

	public BackwardSignalCursor( Signal<D> signal ) {
		this.signal = signal;
		init();
	}

	private void init() {
		this.iterator = signal.getElementsIterator( true );
		this.current = null;
		this.next = null;
		this.time = Double.NaN;
		if (this.iterator.hasPrevious()) {
			this.next = this.iterator.previous();
		}		
	}

	/* (non-Javadoc)
	 * @see org.jsstl.monitor.signal.SignalCursor#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return next != null;
	}
	
	/* (non-Javadoc)
	 * @see org.jsstl.monitor.signal.SignalCursor#getTime()
	 */
	@Override
	public double getTime() {
		return time;
	}
	
	/* (non-Javadoc)
	 * @see org.jsstl.monitor.signal.SignalCursor#nextTime()
	 */
	@Override
	public double nextTime() {
		if (next == null) {
			return Double.NaN;
		}
		return next.getTime();
	}

	/* (non-Javadoc)
	 * @see org.jsstl.monitor.signal.SignalCursor#getValue()
	 */
	@Override
	public D getValue() {
		if (current == null) {
			return null;
		}
		if (time == current.getTime()) {
			return current.getValue();
		}
		if (next != null) {
			return next.getValue();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.jsstl.monitor.signal.SignalCursor#moveTo(double)
	 */
	@Override
	public void moveTo( double t ) {
		if ((current == null)&&(next == null)) {
			throw new IllegalStateException();
		}
		if ((current == null)&&(t>next.getTime())) {
			throw new IllegalStateException();
		}
		if ((t>time)||(t<signal.getInitialTime())) {
			throw new IllegalArgumentException();
		}
		while ((current==null)||((next!=null)&&(next.getTime()>=t))) {
			moveToNext();
		}
		this.time = t;
	}

	/* (non-Javadoc)
	 * @see org.jsstl.monitor.signal.SignalCursor#next()
	 */
	@Override
	public boolean moveToNext() {
		this.current = this.next;
		this.time = (this.current != null?this.current.getTime():Double.NaN);
		if (iterator.hasPrevious()) {
			this.next = iterator.previous();
		} else {
			this.next = null;
		}
		return hasNext();
	}


}
