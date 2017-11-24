/**
 * 
 */
package org.jsstl.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.jsstl.core.space.Location;

/**
 * @author loreti
 *
 */
public class SatPanel extends Composite {
	
	private ArrayList<Location> locations;
	private HashMap<String,Integer> formulae;
	private ArrayList<double[]> booleanSat;
	private ArrayList<double[]> quantitativeSat;

	public SatPanel(Composite parent) {
		super(parent, SWT.NONE);
		this.formulae = new HashMap<>();
		this.booleanSat = new ArrayList<>();
		this.quantitativeSat = new ArrayList<>();
		buildContent();
	}

	private void buildContent() {
		setLayout(new FillLayout());
		CTabFolder main = new CTabFolder(this, SWT.NONE);

		CTabItem qualitativeItem = new CTabItem(main, SWT.NONE);
		qualitativeItem.setText("Qualitative");
		Composite qualitativePanel = new ModelPanel( main );
		qualitativePanel.setLayout(new FillLayout());
		TableViewer qualitativeTable = new TableViewer(qualitativePanel, SWT.MULTI | SWT.H_SCROLL
                | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		qualitativeTable.setContentProvider(ArrayContentProvider.getInstance());
		
		
		qualitativeItem.setControl(qualitativePanel);	

		CTabItem quantitativeItem = new CTabItem(main, SWT.NONE);
		quantitativeItem.setText("Quantitative");
		Composite quantitativePanel = new ModelPanel( main );
		quantitativeItem.setControl(quantitativePanel);	

	}

}
