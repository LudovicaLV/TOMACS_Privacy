/**
 * 
 */
package org.jsstl.io;

/**
 * @author loreti
 *
 */
public class SyntaxErrorExpection extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 176438192405734003L;
	
	private int line;
	private String expected;
	private String actual;

	public SyntaxErrorExpection(int line, String expected,
			String actual) {
		super( "Syntax error at line "+line+": Expected "+expected+" found "+actual);
		this.line = line;
		this.expected = expected;
		this.actual = actual;
	}

	/**
	 * @return the line
	 */
	public int getLine() {
		return line;
	}

	/**
	 * @return the expected
	 */
	public String getExpected() {
		return expected;
	}

	/**
	 * @return the actual
	 */
	public String getActual() {
		return actual;
	}

}
