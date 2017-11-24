package org.jsstl.monitor.tests;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.jsstl.monitor.signal.BackwardSignalCursor;
import org.jsstl.monitor.signal.DomainFunction;
import org.jsstl.monitor.signal.DoubleOperators;
import org.jsstl.monitor.signal.ForwardSignalCursor;
import org.jsstl.monitor.signal.Signal;
import org.jsstl.monitor.signal.SignalCursor;
import org.jsstl.monitor.signal.TemporalMonitor;
import org.junit.Test;

public class TestBooleanSignal {

	@Test
	public void testSignal() {
		Signal<Boolean> s = new Signal<>();
		assertEquals(Double.NaN, s.getInitialTime(), 0.0);
		assertEquals(Double.NaN, s.getFinalTime(), 0.0);
	}
	
	@Test
	public void createIncrementalSignalSinglePoint() {
		Signal<Boolean> s = new Signal<>();
		s.add(0.0, true);
		assertEquals(0.0, s.getInitialTime(), 0.0);
		assertEquals(0.0, s.getFinalTime(), 0.0);
	}

	@Test
	public void createIncrementalSignalTwoPoints() {
		Signal<Boolean> s = new Signal<>();
		s.add(0.0, true);
		s.add(1.0, false);
		assertEquals(0.0, s.getInitialTime(), 0.0);
		assertEquals(1.0, s.getFinalTime(), 0.0);
	}

	@Test
	public void createIncrementalSignalMultiplePoints() {
		Signal<Boolean> s = createSignal( 0 , 100 , 1 , id -> Math.floor(id)%2==0 );
		assertEquals(0.0, s.getInitialTime(), 0.0);
		assertEquals(99.0, s.getFinalTime(), 0.0);
	}

	private <D> Signal<D> createSignal(double from, double to, double dt, Function<Double,D> f) {
		Signal<D> signal = new Signal<>();
		for( double x = from ; x<to ; x+=dt ) {
			signal.add(x, f.apply(x));
		}
		return signal;
	}

	@Test
	public void testForwardCursor() {
		Signal<Boolean> s = createSignal( 0 , 100 , 1 , id -> Math.floor(id)%2==0 );
		SignalCursor<Boolean> c = new ForwardSignalCursor<>(s);
		double next = 0.0;
		while (c.hasNext()) {
			c.moveToNext();
			assertEquals(next, c.getTime(), 0.0);
			assertEquals( new Boolean( Math.floor(next)%2==0 ) , c.getValue() );
			next += 1;
		} 
		assertEquals(100, (int) next);
	}

	@Test
	public void testForwardCursorJump() {
		Signal<Boolean> s = createSignal( 0 , 100 , 1 , id -> Math.floor(id)%2==0 );
		SignalCursor<Boolean> c = new ForwardSignalCursor<>(s);
		c.moveTo(55.5);
		assertEquals(55.5, c.getTime(), 0.0);
		assertEquals( false , c.getValue() );
		double next = 56.0;
		while (c.hasNext()) {
			c.moveToNext();
			assertEquals(next, c.getTime(), 0.0);
			assertEquals( new Boolean( Math.floor(next)%2==0 ) , c.getValue() );
			next += 1;
		} 
		assertEquals(100, (int) next);
	}

	@Test
	public void testForwardCursorExactJump() {
		Signal<Boolean> s = createSignal( 0 , 100 , 1 , id -> Math.floor(id)%2==0 );
		SignalCursor<Boolean> c = new ForwardSignalCursor<>(s);
		c.moveTo(55.0);
		assertEquals(55.0, c.getTime(), 0.0);
		assertEquals( false , c.getValue() );
		double next = 56.0;
		while (c.hasNext()) {
			c.moveToNext();
			assertEquals(next, c.getTime(), 0.0);
			assertEquals( new Boolean( Math.floor(next)%2==0 ) , c.getValue() );
			next += 1;
		} 
		assertEquals(100, (int) next);
	}


	@Test
	public void testBackwardCursorJump() {
		Signal<Boolean> s = createSignal( 0 , 100 , 1 , id -> Math.floor(id)%2==0 );
		SignalCursor<Boolean> c = new BackwardSignalCursor<>(s);
		c.moveTo(55.5);
		assertEquals(55.5, c.getTime(), 0.0);
		assertEquals( false , c.getValue() );
		double next = 55.0;
		while (c.hasNext()) {
			c.moveToNext();
			assertEquals(next, c.getTime(), 0.0);
			assertEquals( new Boolean( Math.floor(next)%2==0 ) , c.getValue() );
			next += -1;
		} 
		assertEquals(-1, (int) next);
	}

	@Test
	public void testBackwardCursorExactJump() {
		Signal<Boolean> s = createSignal( 0 , 100 , 1 , id -> Math.floor(id)%2==0 );
		SignalCursor<Boolean> c = new BackwardSignalCursor<>(s);
		c.moveTo(55.0);
		assertEquals(55.0, c.getTime(), 0.0);
		assertEquals( false , c.getValue() );
		double next = 54.0;
		while (c.hasNext()) {
			c.moveToNext();
			assertEquals(next, c.getTime(), 0.0);
			assertEquals( new Boolean( Math.floor(next)%2==0 ) , c.getValue() );
			next += -1;
		} 
		assertEquals(-1, (int) next);
	}

	@Test
	public void testBackwardCursor() {
		Signal<Boolean> s = createSignal( 0 , 100 , 1 , id -> Math.floor(id)%2==0 );
		SignalCursor<Boolean> c = new BackwardSignalCursor<>(s);
		double next = 99.0;
		while (c.hasNext()) {
			c.moveToNext();
			assertEquals(next, c.getTime(), 0.0);
			assertEquals( new Boolean( Math.floor(next)%2==0 ) , c.getValue() );
			next += -1;
		} 
		assertEquals(-1, (int) next);
	}

	@Test
	public void testSignalUnaryApply() {
		Signal<Boolean> s = createSignal( 0 , 100 , 1 , id -> Math.floor(id)%2==0 );
		Signal<Boolean> sNot = Signal.apply(s, x -> !x);
		SignalCursor<Boolean> c = new ForwardSignalCursor<>(sNot);
		double next = 0.0;
		while (c.hasNext()) {
			c.moveToNext();
			assertEquals( new Boolean( Math.floor(c.getTime())%2!=0 ) , c.getValue() );
			assertEquals( next , c.getTime() , 0.0 );
			next += 1;
		} 
		assertEquals(100 , next , 0.0 );
		
	}

	@Test
	public void testSignalBinaryApply() {
		int end = 5;
		Signal<Boolean> s1 = createSignal( 0 , end , 1 , id -> Math.floor(id)%2==0 );
		Signal<Boolean> s2 = createSignal( 0 , end , 1 , id -> Math.floor(id)%2!=0 );
		Signal<Boolean> s = Signal.apply(s1, s2, (x, y) -> x&&y );
		SignalCursor<Boolean> c = new ForwardSignalCursor<>(s);
		double next = 0.0;
		while (c.hasNext()) {
			c.moveToNext();
			assertFalse( c.getValue() );
			assertEquals( next , c.getTime() , 0.0 );
			next += 1;
		} 
		assertEquals(end , next , 0.0 );
		
	}
	
	@Test
	public void testEventually() {
		Signal<Double> s1 = createSignal(0, 20, 1, id -> Math.sin(id));
		TemporalMonitor<Double, Double>  monitor = new TemporalMonitor<>(null, new DoubleOperators());
		Signal<Double> s2 = monitor.eventually(5, 10, s1);
		System.out.println(s1);
		System.out.println(s2);
		assertNotNull(s2);
 	}
	
	@Test
	public void testEventually2() {
		Signal<Double> s1 = createSignal(0, 20, 1, id -> Math.sin(id));
		TemporalMonitor<Double, Double>  monitor = new TemporalMonitor<>(null, new DoubleOperators());
		Signal<Double> s2 = monitor.eventually(5.25, 9.75, s1);
		System.out.println(s1);
		System.out.println(s2);
		assertNotNull(s2);
 	}
	
}
