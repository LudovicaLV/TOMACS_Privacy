package org.jsstl.core.space;

import java.util.ArrayList;

public class Location {

	private String label;
	private int position;
	private ArrayList<Location> adjList; // edge matrix

	// -------------------------------------------------------------
	public Location() // constructor
	{
		adjList = new ArrayList<Location>();

	}

	public Location(String name, int pos) // constructor 2
	{
		adjList = new ArrayList<Location>();
		setLabel(name);
		setPosition(pos);

	}

	@Override
	public int hashCode() {
		return getPosition();
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj != null) && (obj instanceof Location)) {
			return ((Location) obj).getPosition() == this.getPosition();
		}
		return false;
	}

	@Override
	public String toString() {
		return getLabel();
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	public void addNeighbour(Location location) {
		this.adjList.add(location);
	}

	public ArrayList<Location> getNeighbourd() {
		return this.adjList;
	}

}
