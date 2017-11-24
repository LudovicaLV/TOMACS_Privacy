package org.jsstl.xtext.formulas.util

import org.jsstl.xtext.formulas.sSTLSpecification.LiteralExpression
import org.jsstl.xtext.formulas.sSTLSpecification.VariableDeclarations
import org.jsstl.xtext.formulas.sSTLSpecification.Expression
import org.jsstl.xtext.formulas.sSTLSpecification.MulExpression
import java.util.Set
import org.jsstl.xtext.formulas.sSTLSpecification.DivExpression
import org.jsstl.xtext.formulas.sSTLSpecification.NotExpression
import org.jsstl.xtext.formulas.sSTLSpecification.UnaryMinusExpression
import org.jsstl.xtext.formulas.sSTLSpecification.SumExpression
import org.jsstl.xtext.formulas.sSTLSpecification.DiffExpression
import org.jsstl.xtext.formulas.sSTLSpecification.LessRelationExpression
import org.jsstl.xtext.formulas.sSTLSpecification.LessOrEqualRelationExpression
import org.jsstl.xtext.formulas.sSTLSpecification.GreaterOrEqualRelationExpression
import org.jsstl.xtext.formulas.sSTLSpecification.GreaterRelationExpression
import org.jsstl.xtext.formulas.sSTLSpecification.OrFormula
import org.jsstl.xtext.formulas.sSTLSpecification.AndFormula
import org.jsstl.xtext.formulas.sSTLSpecification.UntilFormula
import org.jsstl.xtext.formulas.sSTLSpecification.GloballyFormula
import org.jsstl.xtext.formulas.sSTLSpecification.EventuallyFormula
import org.jsstl.xtext.formulas.sSTLSpecification.SurroundFormula
import org.jsstl.xtext.formulas.sSTLSpecification.SomewhereFormula
import org.jsstl.xtext.formulas.sSTLSpecification.EverywhereFormula
import org.jsstl.xtext.formulas.sSTLSpecification.NotFormula
import org.jsstl.xtext.formulas.sSTLSpecification.ReferencedFormula
import org.jsstl.xtext.formulas.sSTLSpecification.AtomicFormula
import org.jsstl.xtext.formulas.sSTLSpecification.FormulaDeclaration
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.util.URI

class Utilities {
	
	def dispatch Set<VariableDeclarations> collectUsedVariables( LiteralExpression e ) {
		val v = e.reference
		switch v {
			VariableDeclarations: newHashSet( v )
			default: newHashSet()
		}
	}
	
	def dispatch Set<VariableDeclarations> collectUsedVariables( GreaterRelationExpression e ) {
		val s = e.left.collectUsedVariables
		s.addAll( e.right.collectUsedVariables )
		s
	}

	def dispatch Set<VariableDeclarations> collectUsedVariables( GreaterOrEqualRelationExpression e ) {
		val s = e.left.collectUsedVariables
		s.addAll( e.right.collectUsedVariables )
		s
	}

	def dispatch Set<VariableDeclarations> collectUsedVariables( LessOrEqualRelationExpression e ) {
		val s = e.left.collectUsedVariables
		s.addAll( e.right.collectUsedVariables )
		s
	}

	def dispatch Set<VariableDeclarations> collectUsedVariables( LessRelationExpression e ) {
		val s = e.left.collectUsedVariables
		s.addAll( e.right.collectUsedVariables )
		s
	}

	def dispatch Set<VariableDeclarations> collectUsedVariables( DiffExpression e ) {
		val s = e.left.collectUsedVariables
		s.addAll( e.right.collectUsedVariables )
		s
	}

	def dispatch Set<VariableDeclarations> collectUsedVariables( SumExpression e ) {
		val s = e.left.collectUsedVariables
		s.addAll( e.right.collectUsedVariables )
		s
	}

	def dispatch Set<VariableDeclarations> collectUsedVariables( MulExpression e ) {
		val s = e.left.collectUsedVariables
		s.addAll( e.right.collectUsedVariables )
		s
	}
	
	def dispatch Set<VariableDeclarations> collectUsedVariables( DivExpression e ) {
		val s = e.left.collectUsedVariables
		s.addAll( e.right.collectUsedVariables )
		s
	}
	
	def dispatch Set<VariableDeclarations> collectUsedVariables( NotExpression e ) {
		e.arg.collectUsedVariables
	}
	
	def dispatch Set<VariableDeclarations> collectUsedVariables( UnaryMinusExpression e ) {
		e.arg.collectUsedVariables
	}
	
	def dispatch collectUsedVariables( Expression e ) {
		newHashSet()
	}
	
	def dispatch Set<FormulaDeclaration> referencedFormulas( OrFormula formula ) {
		var rf = formula.left.referencedFormulas
		rf.addAll( formula.right.referencedFormulas )
		rf
	}

	def dispatch Set<FormulaDeclaration> referencedFormulas( AndFormula formula ) {
		var rf = formula.left.referencedFormulas
		rf.addAll( formula.right.referencedFormulas )
		rf
	}

	def dispatch Set<FormulaDeclaration> referencedFormulas( UntilFormula formula ) {
		var rf = formula.left.referencedFormulas
		rf.addAll( formula.right.referencedFormulas )
		rf
	}

	def dispatch Set<FormulaDeclaration> referencedFormulas( GloballyFormula formula ) {
		formula.arg.referencedFormulas
	}

	def dispatch Set<FormulaDeclaration> referencedFormulas( EventuallyFormula formula ) {
		formula.arg.referencedFormulas
	}

	def dispatch Set<FormulaDeclaration> referencedFormulas( SurroundFormula formula ) {
		var rf = formula.left.referencedFormulas
		rf.addAll( formula.right.referencedFormulas )
		rf
	}

	def dispatch Set<FormulaDeclaration> referencedFormulas( SomewhereFormula formula ) {
		formula.arg.referencedFormulas
	}

	def dispatch Set<FormulaDeclaration> referencedFormulas( EverywhereFormula formula ) {
		formula.arg.referencedFormulas
	}

	def dispatch Set<FormulaDeclaration> referencedFormulas( NotFormula formula ) {
		formula.arg.referencedFormulas
	}

	def dispatch Set<FormulaDeclaration> referencedFormulas( ReferencedFormula formula ) {
		newHashSet( formula.reference ) 
	}
	
	def dispatch Set<FormulaDeclaration> referencedFormulas( AtomicFormula formula ) {
		newHashSet()
	}
	
	def getPackageName(URI uri) {
		if (uri.isPlatform()) {
			var toReturn = uri.toPlatformString(true);
			if (toReturn.startsWith("/")) {
				toReturn = toReturn.substring(1);
			}
			var lastSlash = toReturn.lastIndexOf('/');
			var packageFolder = "";
			if (lastSlash >= 0) {
				packageFolder = "jsstl"
			}
			packageFolder = packageFolder.replace('.', '_');
			var lastDot = toReturn.lastIndexOf('.');
			if (lastDot >= 0) {
				packageFolder = packageFolder + "/" + (toReturn.substring(lastDot + 1));
			}
			packageFolder;
		} else {
			"jsstl"
		}
	}	
	
	def getClassName(URI uri) {
		if (uri.isPlatform()) {
			var toReturn = uri.toPlatformString(true);
			var start = toReturn.lastIndexOf('/');
			if (start < 0) {
				start = 0;
			} else {
				start = start + 1;
			}
			var end = toReturn.lastIndexOf('.');
			if (end < 0) {
				end = toReturn.length();
			}
			toReturn.substring(start, end);
		} else {
			"Model";			
		}
	}
}