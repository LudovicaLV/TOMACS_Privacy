/**
 * 
 */
package org.jsstl.monitor.signal;

/**
 * This interface defines the basic operations on signal values.
 * 
 * 
 * @author loreti
 *
 */
public interface DomainFunction<D> {

	/**
	 * Defines the conjunction operator over domain <code>D</code>.
	 * 
	 * @param x first argument
	 * @param y second argument
	 * @return conjunction of x and y
	 */
	public D conjunction( D x , D y );
	
	/**
	 * Defines the disjunction operator over domain <code>D</code>.
	 * 
	 * @param x first argument
	 * @param y second argument
	 * @return disjunction of x and y
	 */
	public D disjunction( D x , D y );

	/**
	 * Defines the negation operator over domain <code>D</code>
	 * 
	 * @param x argument
	 * @return negation of x
	 */
	public D negation( D x );
	
	/**
	 * Defines the ordering relation among elements of D. 
	 * 
	 * @param x first argument
	 * @param y second argument
	 * @return a negative value if x>y, 0 if x==y, a positive value if x<y
	 */
	public int compare( D x , D y );
	
}
