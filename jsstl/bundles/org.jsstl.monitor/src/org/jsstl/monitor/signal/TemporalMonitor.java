/**
 * 
 */
package org.jsstl.monitor.signal;

import java.util.function.Function;

/**
 * @author loreti
 *
 */
public class TemporalMonitor<D1,D2> {
	
	private final DomainFunction<D2> functions;
	
	private final Function<D1, D2> atomic;
	
	public TemporalMonitor( Function<D1,D2> atomic , DomainFunction<D2> functions ) {
		this.functions = functions;
		this.atomic = atomic;
	}
	
	public Signal<D2> not( Signal<D2> s ) {
		return Signal.apply(s , functions::negation );
	}
	
	public Signal<D2> and( Signal<D2> s1 , Signal<D2> s2 ) {
		return Signal.apply(s1, s2,functions::conjunction);
	}
	
	public Signal<D2> or( Signal<D2> s1 , Signal<D2> s2 ) {
		return Signal.apply(s1, s2,functions::disjunction);
	}
	
	public Signal<D2> eventually( double a , double b , Signal<D2> s ) {
		SignalWindow<D2> window = new SignalWindow<D2>(a, b, functions::compare);
		return window.apply(s);	
	}
	
	public Signal<D2> until( Signal<D2> s1 , Signal<D2> s2 ) {
		BackwardSignalCursor<D2> cursor1 = new BackwardSignalCursor<>(s1);
		BackwardSignalCursor<D2> cursor2 = new BackwardSignalCursor<>(s2);
		Signal<D2> result = new Signal<>();
		cursor1.moveToNext();
		cursor2.moveToNext();
		double time = Math.min(cursor1.getTime(), cursor2.getTime()); // non max?
		cursor1.moveTo(time);
		cursor2.moveTo(time);		
		D2 last = cursor2.getValue();
		result.add(time,last);
		while( cursor1.hasNext() && cursor2.hasNext() ) {
			time = Math.max(cursor1.nextTime(), cursor2.nextTime());
			cursor1.moveTo(time);
			cursor2.moveTo(time);		
			last = functions.disjunction(functions.conjunction(cursor1.getValue(), last), cursor2.getValue());
			result.add( time , last );
		}
		return result;
	}
	
	public Signal<D2> atomic( Signal<D1> s , Function<D1, D2> op ) {
		return Signal.apply(s, op);
	}
	
}
