/**
 * 
 */
package org.jsstl.monitor.signal;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * This class implements a sliding window that is used to analyse a signal. 
 *
 * @author loreti
 *
 */
public class SignalWindow<D> {

	@Override
	public String toString() {
		return window.toString();
	}

	/**
	 * Window starting time
	 */
	private final double a;
		
	/**
	 * Current window size
	 */
	private final double size;
	
	/**
	 * Operator used to compare signal values
	 */
	private final Comparator<D> op;

	/**
	 * Signal values in the window
	 */
	private LinkedList<SignalElement<D>> window;
	
	/**
	 * First signal element after the window
	 */
	private SignalElement<D> next;
		
	public SignalWindow( double a , double b , Comparator<D> op ) {
		this.a = a;
		this.op = op;
		this.size = b-a;
		reset();
	}
	
	private boolean add( SignalElement<D> element ) {
		if ((element.getTime()-getWindowStartingTime())>size) {
			if (next == null) {
				next = new SignalElement<D>(element.getTime(), element.getValue());
				return true;
			} else {
				throw new IllegalStateException();
			}
		} else {
			doAdd(element);
			//window.add( new SignalElement<D>(element.getTime(), element.getValue()));
			return false;
		}
	}
	
	public double getWindowStartingTime() {
		if (window.isEmpty()) {
			return Double.NaN;
		}
		return window.peekFirst().getTime();
	}
	
	public double getWindowEndingTime() {
		if (window.isEmpty()) {
			return Double.NaN;
		}
		double last =  window.peekLast().getTime();
		if (next != null) {
			return Math.min(next.getTime(), getWindowStartingTime()+size);
		}
		return last;
	}

	public boolean isFull() {
		return (getWindowEndingTime()-getWindowStartingTime())==size;
	}
	
	private SignalElement<D> getComputedElement() {
		SignalElement<D> first = window.peekFirst();
		return new SignalElement<D>(first.getTime()-a, first.getValue());
	}
	
	private void doAdd( SignalElement<D> element ) {
		if (!window.isEmpty()) { 
			double init = getWindowStartingTime();
			SignalElement<D> last = window.peekLast();
			while ((last != null)&&(op.compare(last.getValue(), element.getValue())<=0)) {
				window.removeLast();
				last = window.peekLast();
			}		
			if (window.isEmpty()) {
				//We have removed all the elements in the window! 
				//We add a "dummy" element at the beginning of the window.
				window.addFirst(new SignalElement<D>(init, element.getValue()));
			}
		}
		window.add(element);
	}
	
	private void shift() {
		if (window.size()<2) {
			System.out.println("next = "+next+" size = "+window.size());
			throw new IllegalStateException("The window is not full!");
		}
		SignalElement<D> first = window.poll();
		if (next != null) {
			SignalElement<D> second = window.peekFirst();
			SignalElement<D> last = window.peekLast();
			double initialGap = second.getTime()-first.getTime();
			double finalGap = next.getTime()-last.getTime();
			//The window will be shifted by min{ initialGap , finalGap }
			if (initialGap<finalGap) {
				//The next element is not reached after the shift.
				//For this reason we have to add a "dummy" element at the end of the window.
				//Since the value store in the new element is equal to the last, to preserve
				//the ordering condition in the window we have only to remove the last element 
				//in the window.
				//window.removeLast();
				window.addLast(new SignalElement<D>(last.getTime()+initialGap,last.getValue()));
			} 		
			if (finalGap<initialGap){
				//The second element in the window is not reached after the shift.
				//For this reason we have to add a "dummy" element at the beginning of the window.
				//Ordering of values stored in the window is preserved.
				window.addFirst(new SignalElement<D>(first.getTime()+finalGap,first.getValue()));
			}
			if (finalGap<=initialGap) {
				//The next element is added to the window via the private method 'doAdd'.
				//This method guarantees that all the elements in the window that are less or equal 
				//to next removed from the window.
				doAdd(next);
				next = null;
			}
		}
	}

	private void add(double time, D value) {
		add( new SignalElement<D>(time, value));
	}
	
	public synchronized Signal<D> apply( Signal<D> input ) {
		reset();
		Signal<D> computed = new Signal<>();
		ForwardSignalCursor<D> cursor = input.getCursor();
		double time = (cursor.getTime()>=a?cursor.getTime():a);
		cursor.moveTo(time);
		SignalElement<D> current = cursor.getCurrent();
		while (current!=null) {
			while ((!window.isEmpty())&&((getWindowStartingTime()+size)<current.getTime())) {
				computed.add(getComputedElement());
				window.poll();
			}
			add(current);
			cursor.moveToNext();
			current = cursor.getCurrent();
		}
		if ((!window.isEmpty())&&((getWindowEndingTime()-getWindowStartingTime())==size)) {
			computed.add(getComputedElement());
		}
		return computed;
	}

	private void reset() {
		this.window = new LinkedList<SignalElement<D>>();
	}
}
