package org.jsstl.xtext.formulas.tests

import org.jsstl.xtext.formulas.SSTLSpecificationInjectorProvider
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.InjectWith
import org.junit.runner.RunWith
import com.google.inject.Inject
import org.eclipse.xtext.junit4.util.ParseHelper
import org.jsstl.xtext.formulas.sSTLSpecification.Model
import org.junit.Test
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.eclipse.xtext.xbase.compiler.CompilationTestHelper
import org.jsstl.core.formula.jSSTLScript
import static extension org.junit.Assert.*

@InjectWith(typeof(SSTLSpecificationInjectorProvider))
@RunWith(typeof(XtextRunner))
class TestSSTLParser {
	
	@Inject extension ParseHelper<Model> parser;
	
	@Inject extension ValidationTestHelper;
	 
    @Inject extension CompilationTestHelper
	
	@Test
	def testParserOne() {
		'''
const N = 10;
		
parameter kappa [ 0 , 100 ];
parameter epsilon [ -0.5 , 0.5 ];
		
variable x;
variable y;
variable z;
		
const M = N;
		
formula critical = <<>>[ -kappa , kappa ] { x > 0 };
formula somewherecritical = !critical;
formula safe = {y > 50};
formula managed = critical S[0,100] safe;
		'''.parse.assertNoErrors		
	}
	
	@Test
	def testCompilerOne() {
		'''
const N = 10;
		
parameter kappa [ 0 , 100 ];
parameter epsilon [ -0.5 , 0.5 ];
		
variable x;
variable y;
variable z;
		
const M = N;
		
formula critical = <<>>[ -kappa , kappa ] { x > 0 };
formula somewherecritical = !critical;
formula safe = {y > 50};
formula managed = critical S[0,100] safe;
		'''.compile[
			var o = getCompiledClass.newInstance 
            assertNotNull( o )
            assertTrue( o instanceof jSSTLScript )
		]		
	}
	
}