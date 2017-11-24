/**
 * 
 */
package org.jsstl.ui.cp;

import org.eclipse.jface.viewers.LabelProvider;

/**
 * @author loreti
 *
 */
public class SignalTreeLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof Integer) {
			return getCategoryLabel( (Integer) element ); 
		}
		return super.getText(element);
	}

	private String getCategoryLabel(Integer element) {
		if (element == SignalTreeContentProvider.LOCATION_CATEGORY) {
			return "Locations";
		}
		if (element == SignalTreeContentProvider.VARIABLE_CATEGORY) {
			return "Variables";
		}
		return "Others";
	}
	
	

}
