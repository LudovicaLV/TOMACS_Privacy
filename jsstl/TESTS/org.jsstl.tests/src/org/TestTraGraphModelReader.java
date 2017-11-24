/**
 * 
 */
package org;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import org.jsstl.core.space.GraphModel;
import org.jsstl.io.SyntaxErrorExpection;
import org.jsstl.io.TraGraphModelReader;
import org.junit.Test;

/**
 * @author loreti
 *
 */
public class TestTraGraphModelReader {

	@Test
	public void testSimpleModel() throws IOException, SyntaxErrorExpection {
		// empty name or space (\n \n not allowed, \n\n allowed because empty line are not counted )
		String code = "LOCATIONS\nciao\ncome\n \n \nstai\nEDGES\n1 2 0.5\n1 2 0.3\n0 1 0.4";
		TraGraphModelReader tgmr = new TraGraphModelReader();
		GraphModel model = tgmr.read(new StringReader(code));
		assertEquals(3,model.getNumberOfLocations());
		assertEquals(3,model.getNumberOfEdges());
		assertEquals("ciao",model.getLocation(0).getLabel());
		assertEquals("come",model.getLocation(1).getLabel());		
		assertEquals("stai",model.getLocation(2).getLabel());
		assertEquals(0.5, model.getWeight(1, 2),0.0);
		
	}

}
