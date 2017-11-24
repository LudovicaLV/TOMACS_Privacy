/**
 * 
 */
package org.jsstl.ui.graph;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;
import org.jsstl.core.space.Location;

/**
 * @author loreti
 *
 */
public class ModelContentProvider extends  ArrayContentProvider  implements IGraphEntityContentProvider {

	@Override
	public Object[] getConnectedTo(Object entity) {
		if (entity instanceof Location) {
			return ((Location) entity).getNeighbourd().toArray();
		}
		throw new RuntimeException("Type not supported");
	}

}
