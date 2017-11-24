/**
 * 
 */
package org.jsstl.ui;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IGraphContentProvider;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.jsstl.core.space.GraphModel;
import org.jsstl.core.space.Location;
import org.jsstl.core.space.WeightEdge;
import org.jsstl.ui.graph.ModelLabelProvider;

/**
 * @author loreti
 *
 */
public class ModelPanel extends Composite {
	
	private GraphViewer graph;

	public ModelPanel(Composite parent) {
		super(parent, SWT.NONE);
		buildContent();
	}

	private void buildContent() {
		setLayout(new FillLayout());		
		buildZestPanel();
	}

	private void buildZestPanel() {
		graph = new GraphViewer(this, SWT.NONE);
		graph.setContentProvider( new IGraphContentProvider() {
			
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getSource(Object rel) {
				if (rel instanceof WeightEdge) {
					return ((WeightEdge) rel).lStart;
				}
				return null;
			}
			
			@Override
			public Object[] getElements(Object input) {
				if ((input != null)&&(input instanceof GraphModel)) {
					return ((GraphModel) input).getEdgesArray();
				}
				return new Object[] {};
			}
			
			@Override
			public Object getDestination(Object rel) {
				if (rel instanceof WeightEdge) {
					return ((WeightEdge) rel).lEnd;
				}
				return null;
			}
		});
		graph.setLabelProvider( new ModelLabelProvider() );
		//graph.setConnectionStyle(ZestStyles.);
		graph.setLayoutAlgorithm(new GridLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
	}
	
	public void setModel( final GraphModel model ) {
		graph.setInput(model);
		graph.refresh();
		graph.applyLayout();
		this.redraw();
	}

	public void reset() {
		graph.setInput(null);
		graph.refresh();
		graph.applyLayout();
		this.redraw();
	}

}
