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
import org.jsstl.ui.cp.SignalTreeContentProvider.Variable;

/**
 * @author loreti
 *
 */
public class SignalTreeContentProvider implements ITreeContentProvider {

	
	public static final int LOCATION_CATEGORY = 0;
	public static final int VARIABLE_CATEGORY = 1;
	private Signal signal;
	
	public SignalTreeContentProvider( ) {
		
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
		if ((inputElement != null)&&(inputElement instanceof Signal)) {
			this.signal = (Signal) inputElement;
			return new Integer[] { LOCATION_CATEGORY , VARIABLE_CATEGORY };
		} else {
			this.signal = null;
			return new Object[] {};
		}
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Integer) {
			return _getChildren( (Integer) parentElement );
		}
		if (parentElement instanceof Location) {
			return _getChildren( (Location) parentElement );
		}
		if (parentElement instanceof Variable) {
			return _getChildren( (Variable) parentElement );
		}
		return new Object[0];
	}

	private Object[] _getChildren(Variable parentElement) {
		ArrayList<Location> locations = signal.getLocations();
		Leaf[] elements = new Leaf[locations.size()];
		for( int i=0 ; i<elements.length ; i++ ) {
			elements[i] = new Leaf(VARIABLE_CATEGORY, locations.get(i), parentElement );
		}
		return elements;
	}

	private Object[] _getChildren(Location parentElement) {
		String[] variables = signal.getVariables();
		Leaf[] elements = new Leaf[ variables.length ];
		for( int i=0 ; i<variables.length ; i++ ) {
			elements[i] = new Leaf(LOCATION_CATEGORY, parentElement, new Variable( i , variables[i] ) );
		}
		return elements;
	}

	private Object[] _getChildren(Integer parentElement) {
		if (parentElement == LOCATION_CATEGORY) {
			return signal.getLocations().toArray();
		}
		if (parentElement == VARIABLE_CATEGORY) {
			String[] variables = signal.getVariables();
			Variable[] toReturn = new Variable[variables.length];
			for( int i=0 ; i<variables.length ; i++ ) {
				toReturn[i] = new Variable(i, variables[i]);
			}
			return toReturn;
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Location) {
			return (Integer) LOCATION_CATEGORY;
		}
		if (element instanceof Variable) {
			return (Integer) VARIABLE_CATEGORY;
		}
		if (element instanceof Leaf) {
			return ((Leaf) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return (element != null)&&(!(element instanceof Leaf));
	}
	
	
	public class Leaf {

		public int category;
		public Location location;
		public Variable variable;
		
		public Leaf( int category , Location location , Variable variable ) {
			this.category = category;
			this.location = location;
			this.variable = variable;
		}
		
		public Object getParent() {
			if (category == LOCATION_CATEGORY) {
				return variable;
			} else {
				return location;
			}
		}

		public String toString() {
			if (category == LOCATION_CATEGORY) {
				return variable.name;
			} else {
				return location.getLabel();
			}
		}
	}
	
	public class Variable {
		
		public int index;
		public String name;
		
		public Variable( int index , String name ) {
			this.index = index;
			this.name = name;
		}
		
		public String toString() {
			return name;
		}
		
	}

}
