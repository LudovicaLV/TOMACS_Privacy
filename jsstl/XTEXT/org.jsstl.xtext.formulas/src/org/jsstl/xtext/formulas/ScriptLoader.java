/**
 * 
 */
package org.jsstl.xtext.formulas;

import java.util.Arrays;
import java.util.LinkedList;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.jsstl.core.formula.Formula;
import org.jsstl.core.formula.jSSTLScript;
import org.jsstl.util.compiler.CharSequenceCompiler;
import org.jsstl.util.compiler.CharSequenceCompilerException;
import org.jsstl.xtext.formulas.generator.SSTLSpecificationGenerator;
import org.jsstl.xtext.formulas.sSTLSpecification.Model;
import org.jsstl.xtext.formulas.util.Utilities;

import com.google.inject.Injector;


/**
 * @author loreti
 *
 */
public class ScriptLoader {

	private XtextResourceSet resourceSet;
	private CharSequenceCompiler<jSSTLScript> compiler;
	private Utilities utilities;
	private SSTLSpecificationGenerator generator;

	public ScriptLoader() {
		Injector injector = new SSTLSpecificationStandaloneSetup().createInjectorAndDoEMFRegistration();
		this.resourceSet = injector.getInstance(XtextResourceSet.class);
		this.resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);		
		this.generator = injector.getInstance(SSTLSpecificationGenerator.class);
		this.utilities = injector.getInstance(Utilities.class);
		this.compiler = new CharSequenceCompiler<>(jSSTLScript.class.getClassLoader(), new LinkedList<>());
    }
	
	public jSSTLScript load( String fileName ) {
		System.out.println("Loading: "+fileName);
		URI uri = URI.createURI(fileName);
		Resource resource = resourceSet.getResource(uri, true);
		EObject object = resource.getContents().get(0);
		if (object instanceof Model) {
			Model model = (Model) object;
			String packageName = utilities.getPackageName(uri); 
			String className = utilities.getClassName(uri);
			String fullClassName = packageName+"."+className;
			CharSequence code = generator.generateJavaCode(model, packageName, className);
			final DiagnosticCollector<JavaFileObject> errs =
		            new DiagnosticCollector<JavaFileObject>();
			try {
				Class<jSSTLScript> compiledScript = compiler.compile(fullClassName, code, errs, new Class<?>[] { jSSTLScript.class } );
				return compiledScript.newInstance();
			} catch (ClassCastException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CharSequenceCompilerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}

		
	    return null;

	}
	
	public static void main(String[] argv) {
		if (argv.length > 0) {
			ScriptLoader loader = new ScriptLoader();
			jSSTLScript script = loader.load(argv[0]);
			String[] formulae = script.getFormulae(); 
			System.out.println(Arrays.toString( formulae ));
			Formula f = script.getFormula( formulae[0] );
		} else {
			throw new IllegalArgumentException("Script file expected!");
		}
	}
	
	class JavaSourceFromString extends SimpleJavaFileObject {
		  final String code;

		  JavaSourceFromString(String name, String code) {
		    super(java.net.URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension),Kind.SOURCE);
		    this.code = code;
		  }

		  @Override
		  public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		    return code;
		  }
	}

}
