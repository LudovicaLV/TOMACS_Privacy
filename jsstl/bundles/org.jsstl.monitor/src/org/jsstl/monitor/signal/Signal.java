/**
 * 
 */
package org.jsstl.monitor.signal;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author loreti
 *
 */
public class Signal<D> {

	private LinkedList<SignalElement<D>> data;
	
	
	public Signal( ) {
		this.data = new LinkedList<>();
	}
	
	public Signal( double[] timesteps , D[] data ) {
		super();
		
		this.data = new LinkedList<>();
		if (timesteps.length != data.length) {
			throw new IllegalArgumentException();
		}
		if (timesteps.length==0) {
			return ;
		}
		double last = -Double.MAX_VALUE;
		for( int i=0 ; i<timesteps.length ; i++ ) {
			if (last>=timesteps[i]) {
				throw new IllegalArgumentException();
			}
			last = timesteps[i];
			this.add( new SignalElement<D>(timesteps[i], data[i]));
		}
	}
	
//	protected AbstractSignal<D> trim( int new_start , int new_end ) {
//		if ((new_start<this.t_start)||(new_end>this.t_end)) {
//			throw new IllegalArgumentException();
//		}
//		if ((new_start == this.t_start)&&(new_end == this.t_end)) {
//			return this;
//		}
//		return null;
//	}
	
	protected ListIterator<SignalElement<D>> getElementsIterator() {
		return getElementsIterator(false);
	}
		
	protected ListIterator<SignalElement<D>> getElementsIterator(boolean reversed) {
		return data.listIterator(reversed?data.size():0);

	}

	public ForwardSignalCursor<D> getCursor() {
		return new ForwardSignalCursor<>(this);
	}
	
	public void add( double time , D value ) {
		add( new SignalElement<D>(time, value));
	}
	
	protected void add( SignalElement<D> e) {
		if (this.data.isEmpty()) {
			this.data.add(e);
		} else {
			if (e.isAfter( this.data.peekLast() )) {
				this.data.add( e );
				return ;
			}
			if (e.isBefore( this.data.peekFirst() )) {
				this.data.addFirst( e );
				return ;
			}
			throw new IllegalArgumentException();		
		}
	}
	
	protected void addAll( Iterable<SignalElement<D>> data ) {
		for (SignalElement<D> signalElement : data) {
			this.add(signalElement);
		}
	}
	
	public double getInitialTime() {
		return (this.data.isEmpty()?Double.NaN:this.data.peekFirst().getTime());
	}

	public double getFinalTime() {
		return (this.data.isEmpty()?Double.NaN:this.data.peekLast().getTime());
	}
	
	public static <D> Signal<D> apply( Signal<D> s1 , Signal<D> s2 , BinaryOperator<D> op ) {
		Signal<D> toReturn = new Signal<D>();
		if (s1.data.isEmpty()||s2.data.isEmpty()) {
			return toReturn;
		}
		ForwardSignalCursor<D> thisCursor = s1.getCursor();
		ForwardSignalCursor<D> otherCursor = s2.getCursor();
		
		//Invariant: signal contains the result of op( this , other ) up to time. 
		double time = Math.max( s1.getInitialTime() , s2.getInitialTime() );
		thisCursor.moveTo(time);
		otherCursor.moveTo(time);
		toReturn.add( time , op.apply(thisCursor.getValue(), otherCursor.getValue()) );
		while (thisCursor.hasNext()&&otherCursor.hasNext()) {			
			time = Math.min(thisCursor.nextTime(), otherCursor.nextTime());			
			thisCursor.moveTo(time);
			otherCursor.moveTo(time);
			toReturn.add( time , op.apply(thisCursor.getValue(), otherCursor.getValue()) );
		} 		
		return toReturn;
	}
	
	public Signal<D> compact( ) {
		Signal<D> signal = new Signal<>();
		SignalElement<D> lastRecorded = null;
		SignalElement<D> lastViewed = null;
		for (SignalElement<D> next : data) {
			if ((lastRecorded == null)||(!lastRecorded.getValue().equals(next.getValue()))) {
				signal.add(next);
				lastRecorded = next;
			}
			lastViewed = lastRecorded;
		}
		if (lastViewed != lastRecorded) {
			signal.add(lastViewed);
		}
		return signal;
	}
	
//	public static <D> Signal<D> apply( Signal<D> s , UnaryOperator<D> op ) {
//		Signal<D> toReturn = new Signal<>();
//		for (SignalElement<D> next : s.data) {
//			toReturn.add(new SignalElement<D>(next.getTime(), op.apply(next.getValue())));
//		}
//		return toReturn;
//	}
	
	public static <D1,D2> Signal<D2> apply( Signal<D1> s , Function<D1,D2> op ) {
		Signal<D2> toReturn = new Signal<>();
		for (SignalElement<D1> next : s.data) {
			toReturn.add(new SignalElement<D2>(next.getTime(), op.apply(next.getValue())));
		}
		return toReturn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return data.toString();
	}
	

}
