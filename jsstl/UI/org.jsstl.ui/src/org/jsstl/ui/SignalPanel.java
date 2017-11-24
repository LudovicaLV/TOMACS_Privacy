/**
 * 
 */
package org.jsstl.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.visualization.xygraph.figures.IXYGraph;
import org.eclipse.nebula.visualization.xygraph.figures.ToolbarArmedXYGraph;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.jsstl.core.formula.ScriptParameter;
import org.jsstl.core.formula.Signal;
import org.jsstl.core.monitor.SpatialBooleanSignal;
import org.jsstl.core.monitor.SpatialQuantitativeSignal;
import org.jsstl.core.space.Location;
import org.jsstl.ui.MonitoringArchive.BooleanMonitoringResult;
import org.jsstl.ui.MonitoringArchive.LocationBasedResult;
import org.jsstl.ui.MonitoringArchive.QuantitativeMonitoringResult;
import org.jsstl.ui.cp.MonitoringTreeContentProvider;
import org.jsstl.ui.cp.MonitoringTreeLabelProvider;
import org.jsstl.ui.cp.SignalTreeContentProvider;
import org.jsstl.ui.cp.SignalTreeContentProvider.Leaf;
import org.jsstl.ui.cp.SignalTreeContentProvider.Variable;
import org.jsstl.ui.cp.SignalTreeLabelProvider;
import org.jsstl.ui.graph.BooleanSignaDataProvider;
import org.jsstl.ui.graph.QuantitativeSignaDataProvider;
import org.jsstl.ui.graph.SignalDataProvider;
import org.jsstl.util.signal.BooleanSignal;
import org.jsstl.util.signal.QuantitativeSignal;

/**
 * @author loreti
 *
 */
public class SignalPanel extends Composite {

	private TreeViewer primarySignalTree;
	private TreeViewer monitoringSignalTree;
	private IXYGraph xyGraph;
	private Signal signal;
	private MonitoringArchive results = new MonitoringArchive();
	private LinkedList<Trace> displayedTraces = new LinkedList<>();

	public SignalPanel(Composite parent) {
		super(parent, SWT.NONE);
		buildContent();
	}

	private void buildContent() {
		setLayout(new FillLayout(SWT.VERTICAL));		
		buildSignalTreePanel();
		buildGraphPanel();
	}

	private void buildGraphPanel() {
		Canvas graphPanel = new Canvas(this, SWT.NONE);
		// use LightweightSystem to create the bridge between SWT and draw2D
		final LightweightSystem lws = new LightweightSystem(graphPanel);

		// create a new XY Graph.
		xyGraph = new XYGraph();
		
		xyGraph.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraph.getPrimaryYAxis().setShowMajorGrid(true);

		ToolbarArmedXYGraph toolbarArmedXYGraph = new ToolbarArmedXYGraph(xyGraph);

		//xyGraph.setTitle("Simple Toolbar Armed XYGraph Example");
		// set it as the content of LightwightSystem
		lws.setContents(toolbarArmedXYGraph);

//		xyGraph.primaryXAxis.setShowMajorGrid(true);
//		xyGraph.primaryYAxis.setShowMajorGrid(true);
//		xyGraph.primaryXAxis.setAutoScale(true);
//		xyGraph.primaryYAxis.setAutoScale(true);
//		xyGraph.primaryXAxis.setAutoScaleThreshold(0.0);
//		xyGraph.primaryYAxis.setAutoScaleThreshold(0.0);
		
		// create a trace data provider, which will provide the data to the
		// trace.
//		if(ExperimentResultsView.measures != null){
////				xyGraph.primaryXAxis.setRange(0, experimentJob.getDeadline());
////				xyGraph.primaryYAxis.setRange(0,100);
//				LinkedList<SimulationTimeSeries> data = experimentJob.getCollection().getSimulationTimeSeries();
//				for (SimulationTimeSeries serie : data) {
//					SimulationTrace traceDataProvider = new SimulationTrace(serie,experimentJob.getIterations());
//
//					// create the trace
//					Trace trace = new Trace(serie.getName(), xyGraph.primaryXAxis, xyGraph.primaryYAxis, traceDataProvider);
//
//					// set trace property
//					trace.setPointStyle(PointStyle.XCROSS);
//					trace.setErrorBarEnabled(true);
//					trace.setYErrorBarType(ErrorBarType.BOTH);
//					trace.setXErrorBarType(ErrorBarType.NONE);
//
//					// add the trace to xyGraph
//					xyGraph.addTrace(trace);
//				}
//		}
	}

	private void buildSignalTreePanel() {
		Composite signalTrees = new Composite(this, SWT.NONE);
		signalTrees.setLayout(new FillLayout(SWT.VERTICAL));
		Group g = new Group(signalTrees, SWT.SHADOW_ETCHED_OUT);
		g.setText("Signals");
		g.setLayout(new FillLayout());
		primarySignalTree = new TreeViewer( g , SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL );
		primarySignalTree.setContentProvider( new SignalTreeContentProvider() );
		primarySignalTree.setLabelProvider( new SignalTreeLabelProvider() );
		g = new Group(signalTrees, SWT.SHADOW_ETCHED_OUT);
		g.setText("Monitoring");
		g.setLayout(new FillLayout());
		monitoringSignalTree = new TreeViewer( g , SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL );		
		monitoringSignalTree.setContentProvider( new MonitoringTreeContentProvider() );
		monitoringSignalTree.setLabelProvider(new MonitoringTreeLabelProvider());
		monitoringSignalTree.setInput(results);
	}


	public void reset() {
//		primarySignalTree.setInput(null);
//		results = new MonitoringArchive();
//		monitoringSignalTree.setInput(results);
		for (Trace trace : displayedTraces) {
			xyGraph.removeTrace(trace);
		}
		this.displayedTraces = new LinkedList<>();
	}

	public void addSignalTrace( Signal signal , String label , int location , int variable ) {
		addTrace(new Trace(label, xyGraph.getPrimaryXAxis() , xyGraph.getPrimaryYAxis() , new SignalDataProvider( signal , location , variable ) ));
		this.redraw();
	}

	public void setSignal(Signal signal) {
		this.signal = signal;
		primarySignalTree.setInput(signal);
		this.redraw();
	}
		
	
	public LinkedList<Object> getSelectedMonitorSignals() {
		TreePath[] paths = monitoringSignalTree.getStructuredSelection().getPaths();
		LinkedList<Object> results = new LinkedList<>();
		for (TreePath treePath : paths) {
			Object lastSegment = treePath.getLastSegment();
			if (lastSegment instanceof LocationBasedResult) {
				results.add(((LocationBasedResult) lastSegment));
			}
			if (lastSegment instanceof BooleanMonitoringResult) {
				results.add(lastSegment);
			}
			if (lastSegment instanceof QuantitativeMonitoringResult) {
				results.add(lastSegment);
			}
		}
		return results;
	}
	
	

	public void paintSelectedSignals() {
		TreePath[] paths = primarySignalTree.getStructuredSelection().getPaths();
		if ((paths != null)&&(paths.length>0)) {
			xyGraph.erase();
			doPaintSignalTraces( paths );
		}
		paths = monitoringSignalTree.getStructuredSelection().getPaths();
		if ((paths != null)&&(paths.length>0)) {
			doPaintMonitoringTraces( paths );
		}
	}

	private void doPaintMonitoringTraces(TreePath[] paths) {
		for( int i=0 ; i<paths.length ; i++ ) {
			paintTracesFromMonitoringTreeSelection( paths[i].getLastSegment() );
		}
	}

	private void paintTracesFromMonitoringTreeSelection(Object lastSegment) {
		if (lastSegment instanceof LocationBasedResult) {
			LocationBasedResult lbr = (LocationBasedResult) lastSegment;
			if (lbr.parent instanceof BooleanMonitoringResult) {
				BooleanMonitoringResult data = (BooleanMonitoringResult) lbr.parent;
				BooleanSignal booleanSignal = data.spatialSignal.spatialBoleanSignal.get(lbr.location);
				addTrace(
					new Trace( 
						data.formula+(data.parameters.isEmpty()?"":data.parameters.toString())+"@"+lbr.location.getLabel(), 
						xyGraph.getPrimaryXAxis(), 
						xyGraph.getPrimaryYAxis(), 
						new BooleanSignaDataProvider(booleanSignal)
					)						
				);
//				xyGraph.addTrace( 
//						new Trace( 
//								data.formula+(data.parameters.isEmpty()?"":data.parameters.toString())+"@"+lbr.location.getLabel(), 
//								xyGraph.getPrimaryXAxis(), 
//								xyGraph.getPrimaryYAxis(), 
//								new BooleanSignaDataProvider(booleanSignal)));				
			}
			if (lbr.parent instanceof QuantitativeMonitoringResult) {
				QuantitativeMonitoringResult data = (QuantitativeMonitoringResult) lbr.parent;
				QuantitativeSignal booleanSignal = data.spatialSignal.spatialQuantitativeSignal.get(lbr.location);
				addTrace( 
					new Trace( 
						data.formula+(data.parameters.isEmpty()?"":data.parameters.toString())+"@"+lbr.location.getLabel(), 
						xyGraph.getPrimaryXAxis(), 
						xyGraph.getPrimaryYAxis(), 
						new QuantitativeSignaDataProvider(booleanSignal)
					)				
				);
//				xyGraph.addTrace( 
//						new Trace( 
//								data.formula+(data.parameters.isEmpty()?"":data.parameters.toString())+"@"+lbr.location.getLabel(), 
//								xyGraph.getPrimaryXAxis(), 
//								xyGraph.getPrimaryYAxis(), 
//								new QuantitativeSignaDataProvider(booleanSignal)));				
			}
			
		}
	}

	private void doPaintSignalTraces(TreePath[] paths) {
		for( int i=0 ; i<paths.length ; i++ ) {
			paintTracesFromSignalTreeSelection( paths[i].getLastSegment() );
		}
	}
	
	private void paintTracesFromSignalTreeSelection(Object lastSegment) {
		if (lastSegment instanceof Leaf) {
			Leaf leaf = (Leaf) lastSegment;
			addTrace( 
					new Trace( 
							leaf.variable.name+"@"+leaf.location.getLabel(), 
							xyGraph.getPrimaryXAxis(), 
							xyGraph.getPrimaryYAxis(), 
							new SignalDataProvider(signal, leaf.location.getPosition(), leaf.variable.index)));
		}
		if (lastSegment instanceof Location) {
			Location location = (Location) lastSegment;
			String[] variables = signal.getVariables();
			for (int i=0 ; i< variables.length ; i++ ) {
				addTrace( 
						new Trace( 
								variables[i]+"@"+location.getLabel(), 
								xyGraph.getPrimaryXAxis(), 
								xyGraph.getPrimaryYAxis(), 
								new SignalDataProvider(signal, location.getPosition(), i)));
			}
		}
		if (lastSegment instanceof Variable) {
			Variable variable = (Variable) lastSegment;
			ArrayList<Location> locations = signal.getLocations();
			for (Location location : locations) {
				addTrace( 
						new Trace( 
								variable.name+"@"+location.getLabel(), 
								xyGraph.getPrimaryXAxis(), 
								xyGraph.getPrimaryYAxis(), 
								new SignalDataProvider(signal, location.getPosition(), variable.index)));
			}
		}
	}

	public void addBooleanResult(Map<String, Double> parameterValues, String formula, SpatialBooleanSignal sbs) {
		results.addBooleanResult(parameterValues, formula, sbs);
		monitoringSignalTree.setInput(results);
	}
	
	public void addQuantitativeResult(Map<String, Double> parameterValues, String formula, SpatialQuantitativeSignal sbs) {
		results.addQuantitativeResult(parameterValues, formula, sbs);
		monitoringSignalTree.setInput(results);
	}

	public void addTrace( Trace trace ) {
		xyGraph.addTrace(trace);
		displayedTraces.add(trace);
	}
	
	public int displayedTraces() {
		return displayedTraces.size();
	}

}
