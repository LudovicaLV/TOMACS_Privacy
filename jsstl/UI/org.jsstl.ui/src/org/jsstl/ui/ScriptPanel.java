/**
 * 
 */
package org.jsstl.ui;

import java.util.LinkedList;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.jsstl.core.formula.ScriptParameter;
import org.jsstl.core.formula.jSSTLScript;

/**
 * @author loreti
 *
 */
public class ScriptPanel extends Composite {
	
	private TableViewer parameterTableViewer;
	private TableViewer formulaTableViewer;
	private TableViewer variableTableViewer;

	public ScriptPanel(Composite parent) {
		super(parent, SWT.NONE);
		buildContent();
	}

	private void buildContent() {
		setLayout(new FillLayout(SWT.VERTICAL));		
		buildVariablesPanel();
		buildParametersPanel();
		buildFormulasPanel();
	}

	private void buildParametersPanel() {
		Group formulasGroup = new Group(this, SWT.SHADOW_ETCHED_OUT );
		formulasGroup.setText("Parameters");
		formulasGroup.setLayout(new FillLayout());
		parameterTableViewer = new TableViewer(formulasGroup,SWT.MULTI | SWT.H_SCROLL
		        | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER); 
		buildParametersColumns();
		Table t = parameterTableViewer.getTable();
		t.setHeaderVisible(true);
		t.setLinesVisible(true);
		parameterTableViewer.setContentProvider(ArrayContentProvider.getInstance());
		
	}
	
	private void buildParametersColumns() {
		TableViewerColumn tvc;
		TableColumn column;
		tvc = new TableViewerColumn(parameterTableViewer, SWT.NONE);
		column = tvc.getColumn();
		column.setText("Name");
		column.setWidth(100);
		column.setResizable(true);
		column.setMoveable(false);
		column.setAlignment(SWT.LEFT);
		tvc.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
		        ScriptParameter sp = (ScriptParameter) element;
		        return sp.getName();
		      }
		});
		tvc = new TableViewerColumn(parameterTableViewer, SWT.NONE);
		column = tvc.getColumn();
		column.setText("Min Value");
		column.setResizable(true);
		column.setMoveable(false);
		column.setAlignment(SWT.LEFT);
		column.setWidth(100);
		tvc.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
		        ScriptParameter sp = (ScriptParameter) element;
		        return sp.getLowerBound()+"";
		      }
		});
		tvc = new TableViewerColumn(parameterTableViewer, SWT.NONE);
		column = tvc.getColumn();
		column.setText("Max Value");
		column.setResizable(true);
		column.setMoveable(false);
		column.setAlignment(SWT.LEFT);
		column.setWidth(100);
		tvc.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
		        ScriptParameter sp = (ScriptParameter) element;
		        return sp.getUpperBound()+"";
		      }
		});
		tvc = new TableViewerColumn(parameterTableViewer, SWT.NONE);
		column = tvc.getColumn();
		column.setText("Current Value");
		column.setResizable(true);
		column.setMoveable(false);
		column.setAlignment(SWT.LEFT);
		column.setWidth(100);
		tvc.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
		        ScriptParameter sp = (ScriptParameter) element;
		        return sp.getValue()+"";
		      }
		});

	}

	private void buildVariablesPanel() {
		Group formulasGroup = new Group(this, SWT.SHADOW_ETCHED_OUT );
		formulasGroup.setText("Variables");
		formulasGroup.setLayout(new FillLayout());
		variableTableViewer = new TableViewer(formulasGroup,SWT.SHADOW_ETCHED_IN );
		variableTableViewer.setContentProvider(ArrayContentProvider.getInstance());
		TableViewerColumn tvc = new TableViewerColumn(variableTableViewer, SWT.NONE);
		TableColumn column = tvc.getColumn();
		column.setText("Name");
		column.setWidth(100);
		column.setResizable(true);
		column.setMoveable(false);
		column.setAlignment(SWT.LEFT);
		tvc.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
		        return (String) element;
		      }
		});
		Table t = variableTableViewer.getTable();
		t.setHeaderVisible(true);
		t.setLinesVisible(true);
	}
	
	private void buildFormulasPanel() {
		Group formulasGroup = new Group(this, SWT.SHADOW_ETCHED_OUT );
		formulasGroup.setText("Formulas");
		formulasGroup.setLayout(new FillLayout());
		formulaTableViewer = new TableViewer(formulasGroup,SWT.SHADOW_ETCHED_IN );
		formulaTableViewer.setContentProvider(ArrayContentProvider.getInstance());
		TableViewerColumn tvc = new TableViewerColumn(formulaTableViewer, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL );
		TableColumn column = tvc.getColumn();
		column.setText("Name");
		column.setWidth(100);
		column.setResizable(true);
		column.setMoveable(false);
		column.setAlignment(SWT.LEFT);
		tvc.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
		        return (String) element;
		      }
		});
		Table t = formulaTableViewer.getTable();
		t.setHeaderVisible(true);
		t.setLinesVisible(true);
	}

	public void setScript( jSSTLScript script ) {
		parameterTableViewer.setInput(script.getParameters());
		formulaTableViewer.setInput(script.getFormulae());
		variableTableViewer.setInput(script.getVariables());
	}
	
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public void reset() {
		parameterTableViewer.setInput(new LinkedList<>());
		formulaTableViewer.setInput(new LinkedList<>());
		variableTableViewer.setInput(new LinkedList<>());
	}
	
	public Object[] getSelectedFormuas() {
		IStructuredSelection selection = formulaTableViewer.getStructuredSelection();
		return selection.toArray();
	}

	public Map<String, Double> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
