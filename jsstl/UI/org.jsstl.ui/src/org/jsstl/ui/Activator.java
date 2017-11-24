package org.jsstl.ui;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.jsstl.ui"; //$NON-NLS-1$

	// Constants for ImageRegistry
	public static final String LOAD_MODEL_ID = "org.jsstl.ui.images.load_model"; //$NON-NLS-1$
	public static final String LOAD_MODEL_PATH = "icons/fldr_obj.gif"; //$NON-NLS-1$
	public static final String MAKE_MATRIX_ID = "org.jsstl.ui.images.make_matrix"; //$NON-NLS-1$
	public static final String MAKE_MATRIX_PATH = "icons/package_obj.gif"; //$NON-NLS-1$
	public static final String LOAD_DATA_ID = "org.jsstl.ui.images.load_data"; //$NON-NLS-1$
	public static final String LOAD_DATA_PATH = "icons/all_history_mode.gif"; //$NON-NLS-1$
	public static final String LOAD_SCRIPT_ID = "org.jsstl.ui.images.load_script"; //$NON-NLS-1$
	public static final String LOAD_SCRIPT_PATH = "icons/script_wiz.gif"; //$NON-NLS-1$
	public static final String MONITOR_ID = "org.jsstl.ui.images.monitor"; //$NON-NLS-1$
	public static final String MONITOR_PATH = "icons/start_task.gif"; //$NON-NLS-1$
	public static final String VIEW_ID = "org.jsstl.ui.images.view"; //$NON-NLS-1$
	public static final String VIEW_PATH = "icons/read_obj.gif"; //$NON-NLS-1$
	public static final String RESET_ID = "org.jsstl.ui.images.reset.gif"; //$NON-NLS-1$
	public static final String RESET_PATH = "icons/reset.gif"; //$NON-NLS-1$
	public static final String CLEAR_ID = "org.jsstl.ui.images.clear.gif"; //$NON-NLS-1$
	public static final String CLEARE_PATH = "icons/clear.gif"; //$NON-NLS-1$
	public static final String SAVE_TRAJECTORIES_ID = "org.jsstl.ui.images.save_trajectories.gif"; //$NON-NLS-1$
	public static final String SAVE_TRAJECTORIES_PATH = "icons/saveall_edit.gif"; //$NON-NLS-1$
	public static final String SAVE_SAT_ID = "org.jsstl.ui.images.save_sat.gif"; //$NON-NLS-1$
	public static final String SAVE_SAT_PATH = "icons/save_edit.gif"; //$NON-NLS-1$

	
	public static final String[] IMAGE_IDS = new String[] {
			LOAD_MODEL_ID , 
			MAKE_MATRIX_ID , 
			LOAD_DATA_ID , 
			LOAD_SCRIPT_ID , 
			MONITOR_ID , 
			VIEW_ID , 
			RESET_ID , 
			CLEAR_ID ,
			SAVE_TRAJECTORIES_ID ,
			SAVE_SAT_ID
	};

	public static final String[] IMAGE_PATHS = new String[] {
			LOAD_MODEL_PATH , 
			MAKE_MATRIX_PATH , 
			LOAD_DATA_PATH , 
			LOAD_SCRIPT_PATH , 
			MONITOR_PATH , 
			VIEW_PATH , 
			RESET_PATH ,
			CLEARE_PATH ,
			SAVE_TRAJECTORIES_PATH ,
			SAVE_SAT_PATH
	};

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
        Bundle bundle = Platform.getBundle(PLUGIN_ID);
        
        for (int i=0 ; i<IMAGE_IDS.length ; i++) {
            IPath path = new Path(IMAGE_PATHS[i]);
            URL url = FileLocator.find(bundle, path, null);
            ImageDescriptor desc = ImageDescriptor.createFromURL(url);
            reg.put(IMAGE_IDS[i], desc);
        }
             
    }

	
}
