/**
 * 
 */
package org.jsstl.ui.graph;

import org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider;
import org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProviderListener;
import org.eclipse.nebula.visualization.xygraph.dataprovider.ISample;
import org.eclipse.nebula.visualization.xygraph.linearscale.Range;
import org.jsstl.core.formula.Signal;

/**
 * @author loreti
 *
 */
public class SignalDataProvider implements IDataProvider {

	private double[][] values;
	private double min;
	private double max;

	public SignalDataProvider(Signal signal, int location, int variable) {
		computeValues( signal, location, variable);
	}

	private void computeValues(Signal signal, int location, int variable) {
		this.max = Double.MIN_VALUE;
		this.min = Double.MAX_VALUE;
		this.values = new double[signal.getSize()][2];
		for( int i=0 ; i<signal.getSize() ; i++ ) {
			this.values[i][0] = signal.getTime(i);
			this.values[i][1] = signal.getValue(i, location, variable);
			if (this.values[i][1]<min) {
				min = this.values[i][1];
			}
			if (this.values[i][1]>max) {
				max = this.values[i][1];
			}
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
