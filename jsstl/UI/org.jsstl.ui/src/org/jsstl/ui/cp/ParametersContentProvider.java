/**
 * 
 */
package org.jsstl.ui.cp;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.jsstl.core.formula.jSSTLScript;

/**
 * @author loreti
 *
 */
public class ParametersContentProvider implements IStructuredContentProvider {
	
	private jSSTLScript script;
	
	public ParametersContentProvider( ) {
		
	}
	
	public void setScript( jSSTLScript script ) {
		this.script = script;
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
		// TODO Auto-generated method stub
		return null;
	}
	

}
