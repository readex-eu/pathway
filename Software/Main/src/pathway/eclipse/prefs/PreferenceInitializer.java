package pathway.eclipse.prefs;

import java.io.File;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import pathway.PAThWayPlugin;




/** Initializes the Pathway preferences to their default values. */
public class PreferenceInitializer extends AbstractPreferenceInitializer {
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = PAThWayPlugin.getDefault().getPreferenceStore();

        // location of the experiment database
        File dbFolder = new File(System.getProperty("user.home"), ".pathway");
        File dbPath = new File(dbFolder, "experiments.h2.db");
        store.setDefault(PreferenceConstants.P_DB_LOC, dbPath.getAbsolutePath());
    }
}
