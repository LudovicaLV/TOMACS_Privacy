/**
 * 
 */
package org.jsstl.ui.cp;

import org.eclipse.jface.viewers.LabelProvider;
import org.jsstl.ui.MonitoringArchive;

/**
 * @author loreti
 *
 */
public class MonitoringTreeLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof Integer) {
			return getCategoryLabel( (Integer) element ); 
		}
		return super.getText(element);
	}

	private String getCategoryLabel(Integer element) {
		if (element == MonitoringArchive.BOOLEAN_SIGNAL_CATEGORY) {
			return "Boolean";
		}
		if (element == MonitoringArchive.QUANTITATIVE_SIGNAL_CATEGORY) {
			return "Quantitative";
		}
		return "Others";
	}
	
	

}
