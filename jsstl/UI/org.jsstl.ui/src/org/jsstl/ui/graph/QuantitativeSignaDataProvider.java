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
import org.jsstl.util.signal.QuantitativeSignal;
import org.jsstl.util.signal.TimeInterval;

/**
 * @author loreti
 *
 */
public class QuantitativeSignaDataProvider implements IDataProvider {

	private double[][] values;
	private double min;
	private double max;
	private double startTime;
	private double endTime;

	public QuantitativeSignaDataProvider(QuantitativeSignal signal) {
		computeValues(signal);		
	}

	private void computeValues(QuantitativeSignal signal) {
		this.min = Double.MAX_VALUE;
		this.max = Double.MIN_VALUE;
		this.values = new double[2*signal.getPoints()][2];
		int counter = 0;
		double time = signal.getInitialTime();
		startTime = time;
		for (Double v : signal.getSignal()) {
			if (min > v) {
				min = v;
			}
			if (max < v) {
				max = v;
			}
			this.values[2*counter][0] = time;
			this.values[2*counter][1] = v;
			time = time+signal.getDeltaT();
			this.values[2*counter+1][0] = time;
			this.values[2*counter+1][1] = v;			
			counter++;
		}
		endTime = time;
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
		return new Range(startTime, endTime);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider#getYDataMinMax()
	 */
	@Override
	public Range getYDataMinMax() {
		return new Range(min, max);
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
