package pathway.eclipse.prefs;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import pathway.PAThWayPlugin;




/**
 * This class represents a preference page that is contributed to the Preferences dialog. By sub-classing <samp>FieldEditorPreferencePage</samp>, we can use the
 * field support built into JFace that allows us to create a page that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that belongs to the main plug-in class. That way, preferences can be
 * accessed directly via the preference store.
 */
public class PAThWayPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
    /** Initializes a new instance. */
    public PAThWayPreferencePage() {
        super(GRID);
        setPreferenceStore(PAThWayPlugin.getDefault().getPreferenceStore());
        setDescription("Pathway: Performance Analysis and Tuning Workflows");
    }


    /**
     * Creates the field editors. Field editors are abstractions of the common GUI blocks needed to manipulate various types of preferences. Each field editor
     * knows how to save and restore itself.
     */
    @Override
    public void createFieldEditors() {
        dbLocationEditor = new FileFieldEditor(PreferenceConstants.P_DB_LOC, "&Database location:", true, getFieldEditorParent()) {
            @Override
            protected boolean checkState() {
                // add the file suffix, if necessary
                String text = getTextControl().getText();
                String path = text == null ? "" : text.trim();
                if( !path.endsWith(PAThWayPlugin.DB_FILE_SUFFIX) )
                    path += PAThWayPlugin.DB_FILE_SUFFIX;
                if( !path.equals(text) )
                    getTextControl().setText(path);

                return true;
            }
        };

        dbLocationEditor.setEmptyStringAllowed(false);
        dbLocationEditor.setFileExtensions(new String[] { "*.h2.db" });
        addField(dbLocationEditor);
    }


    @Override
    public void init(IWorkbench workbench) {
    }


    private FileFieldEditor dbLocationEditor = null;
}
