package simhyaModels;

import java.util.Map;

import org.jsstl.core.formula.*;
import org.jsstl.core.monitor.*;
import org.jsstl.core.space.GraphModel;
import org.jsstl.util.signal.BooleanSignal;
import org.jsstl.util.signal.QuantitativeSignal;

public class SimhyaTest {

	public static void main(String[] args) throws Exception {

		SpatioTemporalSimHyA model = new SpatioTemporalSimHyA();
		model.loadModel("models/Bcd.txt");

		GraphModel g = model.getGraphModel();
		Formula phi = createFrenchFlagProperty();

		Signal s = model.simulate(2100, 2000);

		// for (double t : s.getTime()) {
		// System.out.print(t + " ");
		// }

		for (int l = 0; l < s.getData().length; l++) {
			System.out.print("l" + l + ": ");
			for (int t = 1990; t < s.getData()[0].length; t++) {
				System.out.print(s.getData()[l][t][0] + " ");
			}
			System.out.print("\n");
		}
		System.out.println("\n");

		checkTrajectory(s, g, phi);
	}

	public static void checkTrajectory(Signal s, GraphModel g, Formula phi) {
		SpatialBooleanSignal b = phi.booleanCheck(null, g, s);
		BooleanSignal bt = b.spatialBoleanSignal.get(g.getLocation(0));
		System.out.println("Boolean signal:" + bt);
		System.out.println("Satisfied: " + bt.getValueAt(0));

		SpatialQuantitativeSignal q = phi.quantitativeCheck(null,g, s);
		QuantitativeSignal qt = q.spatialQuantitativeSignal.get(g.getLocation(0));
		System.out.println("Quantitative signal:" + qt);
		System.out.println("Robustness: " + qt.getValueAt(0));
	}

	public static Formula createFrenchFlagProperty() {

		// /// %%%%%% PROPERTY %%%%%%% /////////////////////////

		// /// VAR and SIGNAL

		final int VAR_B = 0;
		// int VAR_B = 1;

		ParametricExpression expression1 = new ParametricExpression() {
			
			@Override
			public SignalExpression eval(Map<String, Double> parameters) {
				return new SignalExpression() {
					@Override
					public double eval(double... variables) {
						return variables[VAR_B] - 1150;
					}

				};
			}
		};

		ParametricExpression expression2 = new ParametricExpression() {
			
			@Override
			public SignalExpression eval(Map<String, Double> parameters) {
				return new SignalExpression() {
					@Override
					public double eval(double... variables) {
						return variables[VAR_B] - 200;
					}

				};
			}
		};

		ParametricExpression expression2b = new ParametricExpression() {
			
			@Override
			public SignalExpression eval(Map<String, Double> parameters) {
				return new SignalExpression() {
					@Override
					public double eval(double... variables) {
						return 1350 - variables[VAR_B];
					}

				};
			}
		};

		ParametricExpression expression3 = new ParametricExpression() {
			
			@Override
			public SignalExpression eval(Map<String, Double> parameters) {
				return new SignalExpression() {
					@Override
					public double eval(double... variables) {
						return 300 - variables[VAR_B];
					}

				};
			}
		};

		// ///// ATOMIC PROP
		AtomicFormula atom1 = new AtomicFormula(expression1, false);
		AtomicFormula atom2 = new AtomicFormula(expression2, false);
		AtomicFormula atom2b = new AtomicFormula(expression2b, false);
		AtomicFormula atom3 = new AtomicFormula(expression3, false);

		// ///// NOT
		// NotFormula atom1Not = new NotFormula(atom1);
		NotFormula atom2not = new NotFormula(atom2);

		// /// AND
		AndFormula and2 = new AndFormula(atom2, atom2b);

		// /// PARAMETRIC INTERVAL for the everywhere1
		ParametricInterval spaceInt1 = new ParametricInterval();
		spaceInt1.setLower(0);
		spaceInt1.setUpper(32);

		// /// PARAMETRIC INTERVAL for the everywhere2
		ParametricInterval spaceInt2 = new ParametricInterval();
		spaceInt2.setLower(33);
		spaceInt2.setUpper(66);

		// /// PARAMETRIC INTERVAL for the everywhere3
		ParametricInterval spaceInt3 = new ParametricInterval();
		spaceInt3.setLower(67);
		spaceInt3.setUpper(100);

		// ///// SOMEWHERE and EVERYWHERE
		EverywhereFormula everywhere1 = new EverywhereFormula(spaceInt1, atom1);
		EverywhereFormula everywhere2 = new EverywhereFormula(spaceInt2, and2);
		EverywhereFormula everywhere3 = new EverywhereFormula(spaceInt3, atom3);

		// /// AND
		AndFormula andEvery12 = new AndFormula(everywhere1, everywhere2);
		AndFormula andEvery123 = new AndFormula(andEvery12, everywhere3);

		// /// PARAMETRIC INTERVAL for the globally
		ParametricInterval metricInterval1 = new ParametricInterval();
		metricInterval1.setLower(1950);
		metricInterval1.setUpper(1960);

		// /// PARAMETRIC INTERVAL for the eventually
		ParametricInterval metricInterval2 = new ParametricInterval();
		metricInterval2.setLower(0);
		metricInterval2.setUpper(100);

		// ///// GLOBALLY and EVENTUALLY
		GloballyFormula glob = new GloballyFormula(metricInterval2, andEvery123);
		EventuallyFormula eventually = new EventuallyFormula(metricInterval1,
				glob);
		return eventually;
	}

}
