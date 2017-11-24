/**
 * 
 */
package org.jsstl.xtext.formulas.ui.wizard;

import java.util.Collection;
import java.util.List;

/**
 * @author loreti
 *
 */
public class CustomJSSTLProjectCreator extends SSTLSpecificationProjectCreator {

	private static final String JSSTL_CORE 	= "org.jsstl.core";
	
	/**
	 * @param bundles 
	 * @return
	 */
	public static Collection<? extends String> addCARMABundles(List<String> bundles ) {
		bundles.add(JSSTL_CORE);
		return bundles;
	}
	
	@Override
	protected List<String> getRequiredBundles() {
		List<String> result = super.getRequiredBundles();
		result.addAll(addCARMABundles(result));
		return result;
	}
	
}
