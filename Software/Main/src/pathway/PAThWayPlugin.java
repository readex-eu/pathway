package pathway;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.orm.ORMDatabaseInitiator;
import org.orm.PersistentException;
import org.orm.cfg.JDBCConnectionSetting;
import org.osgi.framework.BundleContext;
import pathway.data.InitData;
import pathway.data.persistence.PathwayPersistentManager;
import pathway.eclipse.prefs.PreferenceConstants;




/** This is the activator class; it controls the plug-in life cycle. */
public class PAThWayPlugin extends AbstractUIPlugin {
    public static final String PLUGIN_ID = "de.tum.in.pathway";
    public static final String PERSPECTIVE_DEVELOPMENT = "pathway.Perspective";
    public static final String PERSPECTIVE_EXECUTION = "pathway.ExecutionPerspective";
    public static final String PERSPECTIVE_BROWSING = "pathway.BrowsePerspective";
    public static final String PERSPECTIVE_READEX_BROWSING = "pathway.ReadexBrowsePerspective";

    public static final String VIEW_EXPERIMENT_BROWSER = "de.tum.in.pathway.eclipse.views.ExperimentsList";
    public static final String VIEW_READEX_BROWSER = "de.tum.in.pathway.eclipse.views.ReadexExperimentsList";

    public static final String VIEW_EXPERIMENT_DETAILS = "de.tum.in.pathway.eclipse.views.ExperimentDetails";
    public static final String VIEW_READEX_EXPERIMENT_DETAILS = "de.tum.in.pathway.eclipse.views.ReadexExperimentDetails";

    public static final String VIEW_EXECUTION_PARAMETERS = "de.tum.in.pathway.eclipse.views.ExecutionParameters";
    public static final String VIEW_HIST_NOTES = "de.tum.in.pathway.eclipse.views.HistNotesTreeView";
    public static final String VIEW_PROCESS_INSTANCES = "org.drools.eclipse.debug.ProcessInstanceViewer";
    public static final String VIEW_WIKI_EDITOR = "com.teaminabox.eclipse.wiki.editors.WikiEditor";

    public static final String DB_FILE_SUFFIX = ".h2.db";
    public static final String ICONS_PATH = "resources/icons/";
    public static final String PATHWAY_INTERNAL_PROJECT = "PAThWayData";
    public static final String PATHWAY_PROJ_HISTNOTES_FOLDER = "HistNotes";

    private final IPropertyChangeListener dbLocationChangeListener = new IPropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            if( event.getProperty().equals(PreferenceConstants.P_DB_LOC) ) {
                try {
                    PAThWayPlugin.setDatabaseLocation(getPreferenceStore().getString(PreferenceConstants.P_DB_LOC));
                }
                catch( Exception e ) {
                    e.printStackTrace();
                }
            }
        }
    };


    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;

        // apply the stored DB connection settings
        IPreferenceStore store = getPreferenceStore();
        PAThWayPlugin.setDatabaseLocation(store.getString(PreferenceConstants.P_DB_LOC));
        store.addPropertyChangeListener(dbLocationChangeListener);
    }


    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        getPreferenceStore().removePropertyChangeListener(dbLocationChangeListener);
        PathwayPersistentManager.instance().disposePersistentManager();

        super.stop(context);
    }


    /** Gets the global instance of this plug-in. */
    public static PAThWayPlugin getDefault() {
        return plugin;
    }


    /**
     * To use dynamic tracing: Create a file ".options" in the same directory as your eclipse executable. Put this in the file:
     * 
     * <pre>
     *  de.tum.in.lrr.periscope.eclipse/debug = true
     * </pre>
     * 
     * @param option
     *            Option to check if set
     * @return State of that trace option (enabled/disabled)
     */
    public static boolean getTraceOption(String option) {
        if( getDefault() != null && getDefault().isDebugging() && Platform.getDebugOption(PLUGIN_ID + option) != null )
            return Platform.getDebugOption(PLUGIN_ID + option).equalsIgnoreCase("TRUE");

        return false;
    }


    /** Configures the location of the experiment database (set from the preferences window). */
    public static void setDatabaseLocation(String path) throws PersistentException, SQLException {
        // strip the suffix as it is automatically added by the DB engine
        String url;
        if( path.endsWith(DB_FILE_SUFFIX) )
            url = path.substring(0, path.length() - 6);
        else {
            url = path;
            path += DB_FILE_SUFFIX;
        }

        // apply new connection settings
        JDBCConnectionSetting settings = new JDBCConnectionSetting();
        settings.setDialect("org.hibernate.dialect.H2Dialect");
        settings.setDriverClass("org.h2.Driver");
        settings.setConnectionURL("jdbc:h2:" + url);
        settings.setUserName("sa");
        settings.setPassword("");

        // Only by trying, we can find out if an instance already exists. If an instance already exists, we have to dispose it before changing settings. We
        // cannot construct and dispose it always, because it could create an empty database file in the wrong location.
        try {
            PathwayPersistentManager.setJDBCConnectionSetting(settings);
        }
        catch( PersistentException ex ) {
            PathwayPersistentManager.instance().disposePersistentManager();
            PathwayPersistentManager.setJDBCConnectionSetting(settings);
        }

        // create the database file, if it does not yet exist
        File dbFile = new File(path);
        if( !dbFile.exists() ) {
            System.out.println("Creating new database file: " + path);
            PathwayPersistentManager.instance();
            PathwayPersistentManager.instance().disposePersistentManager();
            ORMDatabaseInitiator.createSchema(PathwayPersistentManager.instance());
            PathwayPersistentManager.instance().disposePersistentManager();
            System.out.println("Done, creating sample data...");
            InitData.createInitialData();
            
            System.out.println("Done.");
        }
        else
            System.out.println("Using database file: " + path);
    }


    /** Gets the Eclipse shell for the currently active window. */
    public static Shell getShell() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if( window == null )
            throw new IllegalStateException("Must be called from the UI thread.");

        return window.getShell();
    }


    /** Gets the URL for a specific resource bundled with this plug-in. */
    public static URL getResource(String name) {
        return plugin.getBundle().getResource(name);
    }


    /** Returns an image descriptor for the image file at the given plug-in relative path. */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
    }


    private static PAThWayPlugin plugin;
}
