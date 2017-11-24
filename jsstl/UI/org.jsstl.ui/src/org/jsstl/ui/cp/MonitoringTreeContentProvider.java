/**
 * 
 */
package org.jsstl.ui.cp;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.jsstl.core.formula.Signal;
import org.jsstl.core.formula.jSSTLScript;
import org.jsstl.core.space.GraphModel;
import org.jsstl.core.space.Location;
import org.jsstl.ui.MonitoringArchive;
import org.jsstl.ui.MonitoringArchive.BooleanMonitoringResult;
import org.jsstl.ui.MonitoringArchive.LocationBasedResult;
import org.jsstl.ui.MonitoringArchive.QuantitativeMonitoringResult;

/**
 * @author loreti
 *
 */
public class MonitoringTreeContentProvider implements ITreeContentProvider {

	
	private MonitoringArchive archive;
	
	public MonitoringTreeContentProvider( ) {
		
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if ((inputElement != null)&&(inputElement instanceof MonitoringArchive)) {
			this.archive = (MonitoringArchive) inputElement;
			return this.archive.getCategories();
		} else {
			this.archive = null;
			return new Object[] {};
		}
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Integer) {
			return _getChildren( (Integer) parentElement );
		}
		if (parentElement instanceof QuantitativeMonitoringResult) {
			return ((QuantitativeMonitoringResult) parentElement).getChildren();
		}
		if (parentElement instanceof BooleanMonitoringResult) {
			return ((BooleanMonitoringResult) parentElement).getChildren();
		}
		return new Object[0];
	}


	private Object[] _getChildren(Integer parentElement) {
		if (parentElement == MonitoringArchive.BOOLEAN_SIGNAL_CATEGORY) {
			return archive.getBooleanResults().toArray();
		}
		if (parentElement == MonitoringArchive.QUANTITATIVE_SIGNAL_CATEGORY) {
			return archive.getQuantitativeResults().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof QuantitativeMonitoringResult) {
			return (Integer) MonitoringArchive.QUANTITATIVE_SIGNAL_CATEGORY;
		}
		if (element instanceof BooleanMonitoringResult) {
			return (Integer) MonitoringArchive.BOOLEAN_SIGNAL_CATEGORY;
		}
		if (element instanceof LocationBasedResult) {
			return ((LocationBasedResult) element).parent;
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return (element != null)&&(!(element instanceof LocationBasedResult));
	}

}
