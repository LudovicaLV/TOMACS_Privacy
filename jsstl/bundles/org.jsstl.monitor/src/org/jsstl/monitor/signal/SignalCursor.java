package org.jsstl.monitor.signal;

public interface SignalCursor<D> {

	boolean hasNext();

	double getTime();

	double nextTime();

	D getValue();

	void moveTo(double t);

	boolean moveToNext();

}