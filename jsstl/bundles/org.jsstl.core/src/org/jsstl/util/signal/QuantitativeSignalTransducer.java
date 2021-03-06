package org.jsstl.util.signal;


public class QuantitativeSignalTransducer {

	// ///////// AND ///////////////////////////////
	public static QuantitativeSignal and(QuantitativeSignal s1,
			QuantitativeSignal s2) {
		if (s1.isEmptySignal() || s2.isEmptySignal()) {
			QuantitativeSignal s0 = new QuantitativeSignal(0, 0, 0);
			return s0;
		}
		double t0 = Math.max(s1.initialTime, s2.initialTime);
		double tf = Math.min(s1.finalTime, s2.finalTime);
		int points = (int) (((tf - t0) / s1.deltaT) + 1);
		QuantitativeSignal s0 = new QuantitativeSignal(t0, tf, points);

		int k = (int) (t0/s0.getDeltaT());
		for (int i = k; i < points+k; i++) {
			double signal = Math.min(s1.getSignal().get(i), s2.getSignal()
					.get(i));
			s0.addNextPoint(i, signal);
		}

		return s0;
	}

	// ///////// OR ///////////////////////////////
	public static QuantitativeSignal or(QuantitativeSignal s1,
			QuantitativeSignal s2) {
		if (s1.isEmptySignal() || s2.isEmptySignal()) {
			QuantitativeSignal s0 = new QuantitativeSignal(0, 0, 0);
			return s0;
		}
		double t0 = Math.max(s1.initialTime, s2.initialTime);
		double tf = Math.min(s1.finalTime, s2.finalTime);

		int points = (int) (((tf - t0) / s1.deltaT) + 1);
		QuantitativeSignal s0 = new QuantitativeSignal(t0, tf, points);

		int k = (int) (t0/s0.getDeltaT());

		for (int i = k; i < points + k; i++) {
			double signal = Math.max(s1.getSignal().get(i), s2.getSignal()
					.get(i));
			s0.addNextPoint(i, signal);
		}

		return s0;
	}

	// ///////// NOT ///////////////////////////////
	public static QuantitativeSignal not(QuantitativeSignal s1) {
		if (s1.isEmptySignal()) {
			QuantitativeSignal s0 = new QuantitativeSignal(0, 0, 0);
			return s0;
		}

		QuantitativeSignal s0 = new QuantitativeSignal(s1.initialTime,
				s1.finalTime, s1.points);
		s0.setSignal(s1.complement());
		return s0;
	}

	// ///////// UNTIL ///////////////////////////////
	public static QuantitativeSignal until(QuantitativeSignal s1,
			QuantitativeSignal s2, double t1, double t2) {
		if (s1.isEmptySignal() || s2.isEmptySignal()) {
			QuantitativeSignal s0 = new QuantitativeSignal(0, 0, 0);
			return s0;
		}
		double t0 = Math.max(s1.initialTime, s2.initialTime);
		double tf = Math.min(s1.finalTime, s2.finalTime) - t2;
		int points = (int) (((tf - t0) / s1.deltaT) + 1);
		QuantitativeSignal s0 = new QuantitativeSignal(t0, tf, points);
		
		int indexInit = (int) (t0 / s0.deltaT);
		for (int indexT = indexInit; indexT < points + indexInit; indexT++) {
			double minPhi1 = Double.POSITIVE_INFINITY;
			double value = Double.NEGATIVE_INFINITY;
			int indexT1 = (int) (t1 / s0.deltaT) + indexT;
			int indexT2 = (int) (t2 / s0.deltaT) + indexT;
			for (int tPhi1 = indexT; tPhi1 <= indexT1; tPhi1++) {
  			minPhi1 = Math.min(s1.getSignal().get(tPhi1), minPhi1);
		}
			for (int tPhi2 = indexT1; tPhi2 <= indexT2 ; tPhi2++) {
				
				minPhi1 = Math.min(s1.getSignal().get(tPhi2), minPhi1);
				value = Math.max(Math.min(s2.getSignal().get(tPhi2), minPhi1),value);
			}
			s0.addNextPoint(value);
		}
		return s0;
	}

	// ///////// EVENTUALLY ///////////////////////////////
	public static QuantitativeSignal eventually(QuantitativeSignal s,
			double t1, double t2) {
		if (s.isEmptySignal()) {
			QuantitativeSignal s0 = new QuantitativeSignal(0, 0, 0);
			return s0;
		}
		double t0 = s.getInitialTime();
//		if (t2 > s.getFinalTime())
//			///// 
		double tf = s.getFinalTime() - t2;
		int points = (int) (((tf - t0) / s.deltaT) + 1);
		QuantitativeSignal s0 = new QuantitativeSignal(t0, tf, points);

		int indexInit = (int) (t0 / s.deltaT);
		int indexT1 = (int) (t1 / s.deltaT);
		int indexT2 = (int) (t2 / s.deltaT);
		for (int indexT = indexInit; indexT < points + indexInit; indexT++) {
			double signal = Double.NEGATIVE_INFINITY;
			for (int tPhi2 = indexT1 + indexT; tPhi2 <= indexT2 + indexT; tPhi2++) {
				signal = Math.max(s.getSignal().get(tPhi2), signal);
			}
			s0.addNextPoint(signal);
		}

		return s0;
	}

	// ///////// ALWAYS ///////////////////////////////
	public static QuantitativeSignal always(QuantitativeSignal s, double t1,
			double t2) {
		if (s.isEmptySignal()) {
			QuantitativeSignal s0 = new QuantitativeSignal(0, 0, 0);
			return s0;
		}

		QuantitativeSignal ss = s;
		ss.setSignal(s.complement());
		QuantitativeSignal s0 = eventually(s, t1, t2);
		s0.setSignal(s0.complement());
		return s0;

	}

}
