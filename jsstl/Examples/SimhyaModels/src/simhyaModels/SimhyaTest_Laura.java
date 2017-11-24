package simhyaModels;

import java.util.ArrayList;

import org.jsstl.core.formula.*;
import org.jsstl.core.monitor.*;
import org.jsstl.core.space.GraphModel;
import org.jsstl.util.signal.BooleanSignal;
import org.jsstl.util.signal.QuantitativeSignal;

public class SimhyaTest_Laura {

	public static void main(String[] args) throws Exception {

		SpatioTemporalSimHyA model = new SpatioTemporalSimHyA();
		model.loadModel("models/Bcd.txt");

		GraphModel g = model.getGraphModel();


		ArrayList<Formula> FormulaList = new ArrayList<Formula>();

		double concStep = 10;
		double minConc= 0;
		double maxConc = 220;
		double spaceStep = 5;
		double minDist = 5;
		double maxDist = 95;
		for (double conc = minConc; conc < maxConc; conc = conc + concStep) {
			for (double dist = minDist; dist < maxDist; dist = dist + spaceStep) {
				Formula phi = FormulaGenerator.eveyWhereGen(conc, conc + concStep,
						dist, dist+spaceStep);
				FormulaList.add(phi);
			}
		}

		Signal s = model.simulate(30, 10);

		checkTrajectory(s, g, FormulaList.get(0));
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

}
