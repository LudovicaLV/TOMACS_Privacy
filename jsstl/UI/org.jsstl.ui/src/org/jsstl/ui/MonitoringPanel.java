/**
 * 
 */
package org.jsstl.ui;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.xygraph.figures.IXYGraph;
import org.eclipse.nebula.visualization.xygraph.figures.ToolbarArmedXYGraph;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jsstl.core.space.GraphModel;

/**
 * @author loreti
 *
 */
public class MonitoringPanel extends Composite {

	private SignalPanel signalPanel;
	private ModelPanel modelPanel;

	public MonitoringPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		buildPanel();
	}

	private void buildPanel() {
//		GridLayout containerLayout = new GridLayout(1, false);
//		FillLayout containerLayout = new FillLayout();
		CTabFolder folder = new CTabFolder(this, SWT.NONE);
		
		CTabItem modelItem = new CTabItem(folder, SWT.NONE);
		modelItem.setText("Model");
		modelPanel = new ModelPanel( folder );
		modelItem.setControl(modelPanel);
		CTabItem scriptItem = new CTabItem(folder, SWT.NONE);
		scriptItem.setText("Script");
		scriptItem.setControl(buildScriptPanel(folder));
		CTabItem signalItem = new CTabItem(folder, SWT.NONE);
		signalItem.setText("Signal");
		signalPanel = new SignalPanel( folder );
		signalItem.setControl(signalPanel);
		folder.setSelection(modelItem);
		folder.pack();
		this.pack();
	}

    public static void main(String[] args) {
        Display display = new Display();

        Shell shell = new Shell(display);
        
        // the layout manager handle the layout
        // of the widgets in the container
        shell.setLayout(new FillLayout());
        MonitoringPanel mcPanel = new MonitoringPanel(shell, SWT.NONE);
        mcPanel.pack();
        
        //TODO add some widgets to the Shell
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
	
    
    private Composite buildScriptPanel( CTabFolder folder ) {
		Composite modelPanel = new Composite(folder, SWT.BORDER);
		
		
		modelPanel.setBackground(this.getDisplay().getSystemColor(SWT.COLOR_BLUE));
    	
		return modelPanel;
    }

	public void setGraphModel(GraphModel graphmodel) {
		this.modelPanel.setModel( graphmodel ); 
		this.signalPanel.reset();
	}

    
}
