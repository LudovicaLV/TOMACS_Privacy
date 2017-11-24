/**
 * 
 */
package org.jsstl.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.part.ViewPart;
import org.jsstl.core.formula.Signal;
import org.jsstl.core.formula.jSSTLScript;
import org.jsstl.core.monitor.SpatialBooleanSignal;
import org.jsstl.core.monitor.SpatialQuantitativeSignal;
import org.jsstl.core.space.GraphModel;
import org.jsstl.io.CSVSpatialSignalWriter;
import org.jsstl.io.FolderSignalReader;
import org.jsstl.io.GraphModelReader;
import org.jsstl.io.SyntaxErrorExpection;
import org.jsstl.io.TraGraphModelReader;
import org.jsstl.ui.MonitoringArchive.BooleanMonitoringResult;
import org.jsstl.ui.MonitoringArchive.LocationBasedResult;
import org.jsstl.ui.MonitoringArchive.QuantitativeMonitoringResult;
import org.jsstl.util.signal.BooleanSignal;
import org.jsstl.util.signal.QuantitativeSignal;
import org.jsstl.xtext.formulas.ui.util.JSSTLUtil;

/**
 * @author loreti
 *
 */
public class MonitoringView extends ViewPart {

	
	private Action openGraphModel;
	private Action createGridModel;
	private Action openSignalData;
	private Action openScript;
	
	private GraphModel graphmodel;
	private jSSTLScript script;
	private ModelPanel modelPanel;
	private SignalPanel signalPanel;
	private ScriptPanel scriptPanel;
//	private SatPanel satPanel;
	private Signal signal;
	private Action monitoringAction;
	private Action displayAction;
	private Action resetAction;
	private Action clearAction;
	private Action saveTrajectories;
	private Action saveSatisfaction;
	
	/**
	 * 
	 */
	public MonitoringView() {
		// TODO Auto-generated constructor stub
	}
	
	
	private void makeActions() {
		openGraphModel = new Action() {
			public void run() {
				doOpenGraphModel();
			}
		};
		openGraphModel.setText("Open Graph Model");
		openGraphModel.setToolTipText("Open Graph Model");
		openGraphModel.setImageDescriptor(
			Activator.getDefault().getImageRegistry().getDescriptor(Activator.LOAD_MODEL_ID)
		);

		createGridModel = new Action() {
			public void run() {
				doCreateGridModel();
			}
		};
		createGridModel.setText("Create Grid Model");
		createGridModel.setToolTipText("Create Grid Model");
		createGridModel.setImageDescriptor(
			Activator.getDefault().getImageRegistry().getDescriptor(Activator.MAKE_MATRIX_ID)
		);

		openSignalData = new Action() {
			public void run() {
				doOpenSignal();
			}
		};
		openSignalData.setText("Open Signal Data");
		openSignalData.setToolTipText("Open Signal Data");
		openSignalData.setImageDescriptor(
			Activator.getDefault().getImageRegistry().getDescriptor(Activator.LOAD_DATA_ID)
		);

		openScript = new Action() {
			public void run() {
				doOpenScript();
			}
		};
		openScript.setText("Open SSTL Script");
		openScript.setToolTipText("Open SSTL Script");
		openScript.setImageDescriptor(
			Activator.getDefault().getImageRegistry().getDescriptor(Activator.LOAD_SCRIPT_ID)
		);

		monitoringAction = new Action() {
			public void run() {
				doMonitor();
			}
		};
		
		monitoringAction.setText("Execute monitor");
		monitoringAction.setToolTipText("Execute monitor");
		monitoringAction.setImageDescriptor(
			Activator.getDefault().getImageRegistry().getDescriptor(Activator.MONITOR_ID)
		);
		
		displayAction = new Action() {
			public void run() {
				doDisplay();
			}
		};
		displayAction.setText("Display signal");
		displayAction.setToolTipText("Display signal");
		displayAction.setImageDescriptor(
			Activator.getDefault().getImageRegistry().getDescriptor(Activator.VIEW_ID)
		);
		
		resetAction = new Action() {
			public void run() {
				doReset();
			}
		};
		
		resetAction.setText("Reset data");
		resetAction.setToolTipText("Reset data");
		resetAction.setImageDescriptor(
			Activator.getDefault().getImageRegistry().getDescriptor(Activator.RESET_ID)
		);
		
		clearAction = new Action() {
			public void run() {
				doClearGraph();
			}
		};
		
		clearAction.setText("Clear traces");
		clearAction.setToolTipText("Clear data");
		clearAction.setImageDescriptor(
			Activator.getDefault().getImageRegistry().getDescriptor(Activator.CLEAR_ID)
		);
		
		saveSatisfaction = new Action() {
			public void run() {
				try {
					doSaveSatisfaction();
				} catch (FileNotFoundException e) {
					Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					MessageDialog.openError(activeShell, "Error...", e.getMessage());
				}
			}
		};
		saveSatisfaction.setText("Save satisfaction values");
		saveSatisfaction.setToolTipText("Save satisfaction values");
		saveSatisfaction.setImageDescriptor(
			Activator.getDefault().getImageRegistry().getDescriptor(Activator.SAVE_SAT_ID)
		);

		saveTrajectories = new Action() {
			public void run() {
				try {
					doSaveSelected();
				} catch (FileNotFoundException e) {
					Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					MessageDialog.openError(activeShell, "Error...", e.getMessage());
				}
			}
		};
		saveTrajectories.setText("Save trajectories");
		saveTrajectories.setToolTipText("Save trajectories");
		saveTrajectories.setImageDescriptor(
			Activator.getDefault().getImageRegistry().getDescriptor(Activator.SAVE_TRAJECTORIES_ID)
		);
		
		computeActionStatus();
	}



	protected void doSaveSelected() throws FileNotFoundException {		
		LinkedList<Object> results = signalPanel.getSelectedMonitorSignals();
		if (!results.isEmpty()) {
			String selected = selectDirectory("Select Directory...");
	        if (selected != null) {
	        	CSVSpatialSignalWriter writer = new CSVSpatialSignalWriter();
	        	for (Object o : results) {
	        		if (o instanceof LocationBasedResult) {
	        			LocationBasedResult locationBasedResult = (LocationBasedResult) o;
						if (locationBasedResult.parent instanceof BooleanMonitoringResult) {
							BooleanMonitoringResult data = (BooleanMonitoringResult) locationBasedResult.parent;
							writer.write(selected,data.formula+"_qual_", data.spatialSignal,locationBasedResult.location);
						}
						if (locationBasedResult.parent instanceof QuantitativeMonitoringResult) {
							QuantitativeMonitoringResult data = (QuantitativeMonitoringResult) locationBasedResult.parent;
							writer.write(selected,data.formula+"_quant_", data.spatialSignal,locationBasedResult.location);
						}
	        		}
	        		if (o instanceof BooleanMonitoringResult) {
	        			BooleanMonitoringResult data = (BooleanMonitoringResult) o;
						writer.write(selected,data.formula+"_qual_", data.spatialSignal);	        			
	        		}
	        		if (o instanceof QuantitativeMonitoringResult) {
	        			QuantitativeMonitoringResult data = (QuantitativeMonitoringResult) o;
						writer.write(selected,data.formula+"_quant_", data.spatialSignal);	        			
	        		}
				}
	        }
	    } else {
			Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openError(activeShell, "Error...", "No signal to save has been selected!");
	    }
	}

	protected void doSaveSatisfaction() throws FileNotFoundException {		
		LinkedList<Object> results = signalPanel.getSelectedMonitorSignals();
		if (!results.isEmpty()) {
			String selected = selectDirectory("Select Directory...");
	        if (selected != null) {
	        	CSVSpatialSignalWriter writer = new CSVSpatialSignalWriter();
	        	for (Object o : results) {
	        		if (o instanceof LocationBasedResult) {
	        			LocationBasedResult locationBasedResult = (LocationBasedResult) o;
						if (locationBasedResult.parent instanceof BooleanMonitoringResult) {
							BooleanMonitoringResult data = (BooleanMonitoringResult) locationBasedResult.parent;
							writer.writeSatisfaction(selected,data.formula, data.spatialSignal);
						}
						if (locationBasedResult.parent instanceof QuantitativeMonitoringResult) {
							QuantitativeMonitoringResult data = (QuantitativeMonitoringResult) locationBasedResult.parent;
							writer.writeSatisfaction(selected,data.formula, data.spatialSignal);
						}
	        		}
	        		if (o instanceof BooleanMonitoringResult) {
	        			BooleanMonitoringResult data = (BooleanMonitoringResult) o;
						writer.writeSatisfaction(selected,data.formula, data.spatialSignal);
	        		}
	        		if (o instanceof QuantitativeMonitoringResult) {
	        			QuantitativeMonitoringResult data = (QuantitativeMonitoringResult) o;
						writer.writeSatisfaction(selected,data.formula, data.spatialSignal);
	        		}
				}
	        }
	    } else {
			Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openError(activeShell, "Error...", "No signal to save has been selected!");
	    }
	}


	protected void doClearGraph() {
		signalPanel.reset();
	}


	private void computeActionStatus() {
		openSignalData.setEnabled((graphmodel!=null)&&(script != null));
		monitoringAction.setEnabled((graphmodel!=null)&&(script != null)&&(signal!=null));
		displayAction.setEnabled((graphmodel!=null)&&(script != null)&&(signal!=null));
		saveTrajectories.setEnabled((graphmodel!=null)&&(script != null)&&(signal!=null));
		saveSatisfaction.setEnabled((graphmodel!=null)&&(script != null)&&(signal!=null));
		clearAction.setEnabled(signalPanel.displayedTraces()>0);
	}


	protected void doReset() {
//		this.graphmodel = null;
//		this.script = null;
//		this.signal = null;
//		this.modelPanel.reset();
//		this.scriptPanel.reset();
		this.signalPanel.reset();
		this.computeActionStatus();
	}


	protected void doDisplay() {
		signalPanel.paintSelectedSignals();
		computeActionStatus();
	}


	protected void doMonitor() {
		Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		JSSTLUtil util = new JSSTLUtil();

		ElementListSelectionDialog dialog = 
				new ElementListSelectionDialog(
						activeShell, 
						new LabelProvider() );

		dialog.setElements(new String[] { "Boolean Monitoring" , "Quantitative Monitoring" } );
		dialog.setTitle( "Select monitoring..." );
		if (dialog.open() != Window.OK) {
			return ;
		}
		Object[] selected = (dialog.getResult());
		if ((selected == null)||(selected.length == 0)) {
			return ;
		}
		
		if (selected[0].equals("Boolean Monitoring")) {
			Object[] formulas = scriptPanel.getSelectedFormuas();
			for (Object object : formulas) {
				Map<String, Double> parameters = script.getParameterValues();
				SpatialBooleanSignal sbs = script.booleanCheck(parameters, ((String) object), graphmodel, signal);
				signalPanel.addBooleanResult( script.getParameterValues() , ((String) object) , sbs );
			}
		} else {
			Object[] formulas = scriptPanel.getSelectedFormuas();
			for (Object object : formulas) {
				Map<String, Double> parameters = script.getParameterValues();
				SpatialQuantitativeSignal sbs = script.quantitativeCheck(parameters, ((String) object), graphmodel, signal);
				signalPanel.addQuantitativeResult(script.getParameterValues() , ((String) object) , sbs );
			}
		}
		
	}

	protected String selectDirectory( String title ) {
		Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		DirectoryDialog fd = new DirectoryDialog(activeShell, SWT.OPEN);
        fd.setText(title);
        fd.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());
        return fd.open();
	}

	protected void doOpenSignal() {
		String selected = selectDirectory("Select Signal Directory...");
        if (selected != null) {
        	FolderSignalReader fsr = new FolderSignalReader(graphmodel, script.getVariables());
        	try {
				this.signal = fsr.read(selected);
				this.signal.setVariables(this.script.getVariables());
				this.signalPanel.setSignal( this.signal );
			} catch (Exception e) {	
				Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openError(activeShell, "Error...", e.getMessage());
			}
        }
        computeActionStatus();
	}


	protected void doOpenScript() {
		Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		JSSTLUtil util = new JSSTLUtil();
		
		HashMap<IResource, jSSTLScript> models = util.getActiveModels();
		
		if (models.size() > 0) {
			ElementListSelectionDialog dialog = 
					new ElementListSelectionDialog(
							activeShell, 
							new LabelProvider() {
								
								@Override
								public String getText( Object o ) {
									if (o instanceof IResource) {
										return ((IResource) o).getFullPath().toString();
									}
									return super.getText(o);
								}
					
							}
					);

			dialog.setElements(models.keySet().toArray());
			dialog.setTitle( "Select script..." );
			if (dialog.open() != Window.OK) {
				return ;
			}
			Object[] selected = (dialog.getResult());
			if (selected.length > 0) {
				script = models.get(selected[0]);
				scriptPanel.setScript(script);
				this.signal = null;
				this.signalPanel.reset();
				computeActionStatus();
			} 
		} else {
			MessageDialog.openError(activeShell, "Error...", "Please, open a SSTL script first!");
		}
	}


	protected void doCreateGridModel() {
		Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		int row = 0;
		int column = 0;
		InputDialog rowInput = new InputDialog(activeShell,
	            "", "Number of rows:", "10", new RowColumnValidator());
        if (rowInput.open() != Window.OK) {
        	return ;
        }
        row = Integer.parseInt( rowInput.getValue() );
		InputDialog columnInput = new InputDialog(activeShell,
	            "", "Number of columns:", "10", new RowColumnValidator());
        if (columnInput.open() != Window.OK) {
        	return ;
        }
        column = Integer.parseInt( columnInput.getValue() );
        setGraphModel(GraphModel.createGrid(row, column, 1.0));
	}


	protected void doOpenGraphModel() {
		Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        FileDialog fd = new FileDialog(activeShell, SWT.OPEN);
        fd.setText("Open");
        fd.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());
        String[] filterExt = { "*.tra" , "*.*" };
        fd.setFilterExtensions(filterExt);
        String selected = fd.open();
        if (selected != null) {
        	doOpenGraphModel( selected );
        }
    }


	private void doOpenGraphModel(String selected) {
		TraGraphModelReader gmr = new TraGraphModelReader();
		try {
			setGraphModel( gmr.read(selected) );
		} catch (Exception e) {
			Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openError(activeShell,"Error...", e.getMessage());
		} 
	}


	private void setGraphModel(GraphModel graphmodel) {
		this.graphmodel = graphmodel;		
		this.graphmodel.dMcomputation();
		this.modelPanel.setModel(graphmodel);
		this.signal = null;
		this.signalPanel.reset();
		computeActionStatus();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		//mp = new MonitoringPanel(parent, SWT.NONE);
		buildPanel( parent );
		makeActions();
		contributeToActionBars();
		//mp.pack();
	}

	private void buildPanel(Composite parent) {
		CTabFolder folder = new CTabFolder(parent, SWT.NONE);
		
		CTabItem modelItem = new CTabItem(folder, SWT.NONE);
		modelItem.setText("Model");
		modelPanel = new ModelPanel( folder );
		modelItem.setControl(modelPanel);

		CTabItem scriptItem = new CTabItem(folder, SWT.NONE);
		scriptItem.setText("Script");
		scriptPanel = new ScriptPanel(folder);
		scriptItem.setControl( scriptPanel );

		CTabItem signalItem = new CTabItem(folder, SWT.NONE);
		signalItem.setText("Signal");
		signalPanel = new SignalPanel( folder );
		signalItem.setControl(signalPanel);
		folder.setSelection(modelItem);

//		CTabItem satItem = new CTabItem(folder, SWT.NONE);
//		satItem.setText("Satisfaction");
//		satPanel = new SatPanel( folder );
//		satItem.setControl(satPanel);
//		folder.setSelection(satItem);

		folder.pack();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(openGraphModel);
		manager.add(createGridModel);
		manager.add(openScript);
		manager.add(openSignalData);
		manager.add(monitoringAction);
		manager.add(displayAction);
		manager.add(resetAction);
		manager.add(clearAction);
		manager.add(saveSatisfaction);
		manager.add(saveTrajectories);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(openGraphModel);
//		manager.add(action2);
//		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(openGraphModel);
		manager.add(createGridModel);
		manager.add(openScript);
		manager.add(openSignalData);
		manager.add(monitoringAction);
		manager.add(displayAction);
//		manager.add(resetAction);
		manager.add(clearAction);
		manager.add(saveSatisfaction);
		manager.add(saveTrajectories);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	
	public class RowColumnValidator implements IInputValidator {

		@Override
		public String isValid(String newText) {
			try {
				int val = Integer.parseInt(newText);
				if (val <= 0) {
					return "Enter a positive number";
				}
			} catch (NumberFormatException e) {
				return "Enter a positive number";
			}
			return null;
		}
		
	}

}
