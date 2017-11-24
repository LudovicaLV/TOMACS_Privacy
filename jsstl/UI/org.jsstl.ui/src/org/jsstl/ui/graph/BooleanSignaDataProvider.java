/**
 * 
 */
package org.jsstl.ui.graph;

import org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider;
import org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProviderListener;
import org.eclipse.nebula.visualization.xygraph.dataprovider.ISample;
import org.eclipse.nebula.visualization.xygraph.linearscale.Range;
import org.jsstl.core.formula.Signal;
import org.jsstl.util.signal.BooleanSignal;
import org.jsstl.util.signal.TimeInterval;

/**
 * @author loreti
 *
 */
public class BooleanSignaDataProvider implements IDataProvider {

	private double[][] values;
	private double trueValue;
	private double falseValue;

	public BooleanSignaDataProvider(BooleanSignal signal) {
		this( signal , 0.0 , 100.0 );
	}

	public BooleanSignaDataProvider(BooleanSignal signal, double falseValue , double trueValue ) {
		this.trueValue = trueValue;
		this.falseValue = falseValue;
		computeValues(signal);		
	}

	private void computeValues(BooleanSignal signal) {
		this.values = new double[2*signal.signal.size() ][2];
		int counter = 0;
		for (TimeInterval t : signal.signal) {
			this.values[counter][0] = t.getLower();
			this.values[counter][1] = (t.getValue()?trueValue:0.0);
			this.values[counter+1][0] = t.getUpper();
			this.values[counter+1][1] = (t.getValue()?trueValue:0.0);			
			counter += 2;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider#getSize()
	 */
	@Override
	public int getSize() {
		return values.length;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider#getSample(int)
	 */
	@Override
	public ISample getSample(final int index) {
		return new ISample() {
			
			@Override
			public double getYValue() {
				return values[index][1];
			}
			
			@Override
			public double getYPlusError() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getYMinusError() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getXValue() {
				return values[index][0];
			}
			
			@Override
			public double getXPlusError() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getXMinusError() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getInfo() {
				return "("+values[index][0]+","+values[index][1]+")";
			}
		};
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider#getXDataMinMax()
	 */
	@Override
	public Range getXDataMinMax() {
		return new Range(0.0, values[values.length-1][0]);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider#getYDataMinMax()
	 */
	@Override
	public Range getYDataMinMax() {
		return new Range(falseValue, trueValue);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider#isChronological()
	 */
	@Override
	public boolean isChronological() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider#addDataProviderListener(org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProviderListener)
	 */
	@Override
	public void addDataProviderListener(IDataProviderListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider#removeDataProviderListener(org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProviderListener)
	 */
	@Override
	public boolean removeDataProviderListener(IDataProviderListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

}
