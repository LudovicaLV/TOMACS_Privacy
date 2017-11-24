/**
 * 
 */
package org.jsstl.ui.graph;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.jsstl.core.space.Location;
import org.jsstl.core.space.WeightEdge;

/**
 * @author loreti
 *
 */
public class ModelLabelProvider extends LabelProvider {
	@Override
	  public String getText(Object element) {
	    if (element instanceof Location) {
	      Location myNode = (Location) element;
	      return myNode.getLabel();
	    }
	    // Not called with the IGraphEntityContentProvider
	    if (element instanceof WeightEdge) {
	    	WeightEdge myConnection = (WeightEdge) element;
	      return myConnection.weight+"";
	    }

	    if (element instanceof EntityConnectionData) {
	      //EntityConnectionData test = (EntityConnectionData) element;
	      return "";
	    }
	    throw new RuntimeException("Wrong type: "
	        + element.getClass().toString());
	  }
	} 
