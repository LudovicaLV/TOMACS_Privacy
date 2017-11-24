package simhyaModels;

import java.util.Map;

import org.jsstl.core.formula.AndFormula;
import org.jsstl.core.formula.AtomicFormula;
import org.jsstl.core.formula.EventuallyFormula;
import org.jsstl.core.formula.EverywhereFormula;
import org.jsstl.core.formula.Formula;
import org.jsstl.core.formula.GloballyFormula;
import org.jsstl.core.formula.NotFormula;
import org.jsstl.core.formula.ParametricExpression;
import org.jsstl.core.formula.ParametricInterval;
import org.jsstl.core.formula.SignalExpression;

public class FormulaGenerator {

	public static Formula eveyWhereGen(double valMin, double valMax,
			double distMin, double distMax) {

		// /// VAR and SIGNAL
		final int VAR_B = 0;
		// int VAR_B = 1;

		// ///// ATOMIC PROP
		AtomicFormula atoMin = genAtom(VAR_B, valMin, true, false);
		AtomicFormula atoMax = genAtom(VAR_B, valMax, false, false);

		// /// AND
		AndFormula and = new AndFormula(atoMin, atoMax);

		// ///// SOMEWHERE and EVERYWHERE
		ParametricInterval spaceInt = spaceIntGen("d1", distMin, "d2", distMax);
		EverywhereFormula everywhere = new EverywhereFormula(spaceInt, and);

		return everywhere;
	}

	public static AtomicFormula genAtom(final int VAR_B, final double val,
			final boolean greater, boolean zeroTrue) {
		ParametricExpression expr = new ParametricExpression() {
			
			@Override
			public SignalExpression eval(Map<String, Double> parameters) {
				return new SignalExpression() {
					@Override
					public double eval(double... variables) {
						if (greater)
							return variables[VAR_B] - val;
						else
							return val - variables[VAR_B];
					}

				};
			}
		};
		AtomicFormula atom = new AtomicFormula(expr, zeroTrue);
		return atom;
	}

	public static ParametricInterval spaceIntGen(String nameMin, double min,
			String nameMax, double max) {
		// /// PARAMETRIC INTERVAL for the everywhere1
		ParametricInterval spaceInt = new ParametricInterval();
		spaceInt.setLower(min);
		spaceInt.setUpper(max);
		return spaceInt;
	}
}
