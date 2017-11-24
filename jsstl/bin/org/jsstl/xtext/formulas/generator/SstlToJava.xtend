package org.jsstl.xtext.formulas.generator

import org.jsstl.xtext.formulas.sSTLSpecification.Model
import org.jsstl.xtext.formulas.sSTLSpecification.ConstDeclaration
import org.jsstl.xtext.formulas.sSTLSpecification.Expression
import org.jsstl.xtext.formulas.sSTLSpecification.IntExpression
import org.jsstl.xtext.formulas.sSTLSpecification.FloatExpression
import org.jsstl.xtext.formulas.sSTLSpecification.TrueFormula
import org.jsstl.xtext.formulas.sSTLSpecification.FalseFormula
import org.jsstl.xtext.formulas.sSTLSpecification.NotExpression
import org.jsstl.xtext.formulas.sSTLSpecification.UnaryMinusExpression
import org.jsstl.xtext.formulas.sSTLSpecification.LiteralExpression
import org.jsstl.xtext.formulas.sSTLSpecification.VariableDeclarations
import org.jsstl.xtext.formulas.sSTLSpecification.ParameterDeclaration
import org.jsstl.xtext.formulas.sSTLSpecification.MulExpression
import org.jsstl.xtext.formulas.sSTLSpecification.DivExpression
import org.jsstl.xtext.formulas.sSTLSpecification.SumExpression
import org.jsstl.xtext.formulas.sSTLSpecification.DiffExpression
import org.jsstl.xtext.formulas.sSTLSpecification.LessRelationExpression
import org.jsstl.xtext.formulas.sSTLSpecification.LessOrEqualRelationExpression
import org.jsstl.xtext.formulas.sSTLSpecification.GreaterOrEqualRelationExpression
import org.jsstl.xtext.formulas.sSTLSpecification.GreaterRelationExpression

class SstlToJava {
	
	public final static String CONST_POSTFIX = "_CONST_";
	public final static String VAR_POSTFIX = "_VAR_";
	public final static String PAR_POSTFIX = "_PAR_";
	
	def generateJavaCode( String packageName , String className , Model model ) {
		'''
		package «packageName»;
		
		public class «className» {
			
			«FOR e:model.elements.filter(typeof(ConstDeclaration))»
			public static final double «e.name+CONST_POSTFIX» = «e.value.generateExpressionCode»;
			«ENDFOR»
			
		}
		'''
	}
	
	def generateExpressionCode( IntExpression e ) {
		'''«e.value»'''
	}
	
	def generateExpressionCode( FloatExpression e ) {
		'''«e.intPart».«e.decPart»'''
	}
	
	def generateExpressionCode( TrueFormula e ) {
		'''true'''
	}
	
	def generateExpressionCode( FalseFormula e ) {
		'''false'''
	}
	
	def generateExpressionCode( NotExpression e ) {
		'''!( «e.arg.generateExpressionCode» )'''
	}
	
	def generateExpressionCode( UnaryMinusExpression e ) {
		'''-( «e.arg.generateExpressionCode» )'''
	}
	
	def generateExpressionCode( LiteralExpression e ) {
		val v = e.reference
		switch v {
			VariableDeclarations: v.name+VAR_POSTFIX
			ConstDeclaration: v.name+CONST_POSTFIX
			ParameterDeclaration: v.name+PAR_POSTFIX
		}	
	}
	
	def generateExpressionCode( MulExpression e ) {
		'''( «e.left.generateExpressionCode» )*( «e.right.generateExpressionCode» )'''
	}
	
	def generateExpressionCode( DivExpression e ) {
		'''( «e.left.generateExpressionCode» )/( «e.right.generateExpressionCode» )'''
	}
	
	def generateExpressionCode( SumExpression e ) {
		'''( «e.left.generateExpressionCode» )+( «e.right.generateExpressionCode» )'''
	}
	
	def generateExpressionCode( DiffExpression e ) {
		'''( «e.left.generateExpressionCode» )-( «e.right.generateExpressionCode» )'''
	}
	
	def generateExpressionCode( LessRelationExpression e ) {
		'''((«e.right.generateExpressionCode») - («e.left.generateExpressionCode»))>0'''
	}
	
	def generateExpressionCode( LessOrEqualRelationExpression e ) {
		'''((«e.right.generateExpressionCode») - («e.left.generateExpressionCode»))>=0'''
	}
	
	def generateExpressionCode( GreaterOrEqualRelationExpression e ) {
		'''((«e.left.generateExpressionCode») - («e.right.generateExpressionCode»))>=0'''
	}
	
	def generateExpressionCode( GreaterRelationExpression e ) {
		'''((«e.left.generateExpressionCode») - («e.right.generateExpressionCode»))>0'''
	}
	
	def generateExpressionCode( Expression e ) {
		'''0'''
	}
	
}