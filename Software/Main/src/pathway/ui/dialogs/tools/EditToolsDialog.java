package pathway.ui.dialogs.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.orm.PersistentException;
import pathway.PAThWayPlugin;
import pathway.data.persistence.Tool;
import pathway.eclipse.UIUtils;
import pathway.eclipse.views.ExecutionParameters;




/** Represents a dialog that can be used to configure performance tools. */
public class EditToolsDialog extends Dialog {
    /** Initializes a new instance. */
    public EditToolsDialog(Shell parentShell) {
        super(parentShell);
        updateCache();
    }


    @Override
    protected Control createDialogArea(Composite parent) {
        // add the controls
        final Composite container = (Composite)super.createDialogArea(parent);
        container.setLayout(createGridLayout());
        final GridData fillData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        final Composite selectionArea = new Composite(container, SWT.NONE);
        selectionArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        selectionArea.setLayout(new GridLayout(3, false));

        Composite nameComposite = new Composite(selectionArea, SWT.NONE);
        nameComposite.setLayout(new GridLayout(2, false));
        nameComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        Label nameLabel = new Label(nameComposite, SWT.NONE);
        nameLabel.setText("Tool name:");
        nameCombo = new Combo(nameComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
        nameCombo.setItems(cache.keySet().toArray(new String[cache.size()]));
        nameCombo.select(0);
        nameCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Composite versionComposite = new Composite(selectionArea, SWT.NONE);
        versionComposite.setLayout(new GridLayout(2, false));
        versionComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        Label versionLabel = new Label(versionComposite, SWT.NONE);
        versionLabel.setText("Version:");
        versionCombo = new Combo(versionComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
        versionCombo.select(0);
        versionCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Composite buttonComposite = new Composite(selectionArea, SWT.NONE);
        buttonComposite.setLayout(new FillLayout());
        Button newButton = new Button(buttonComposite, SWT.NONE);
        newButton.setText("New...");
        removeButton = new Button(buttonComposite, SWT.NONE);
        removeButton.setText("Remove");

        // spacer
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        Label websiteLabel = new Label(container, SWT.NONE);
        websiteLabel.setText("Website:");
        websiteText = new Text(container, SWT.BORDER);
        websiteText.setLayoutData(fillData);

        Label instrumenterLabel = new Label(container, SWT.NONE);
        instrumenterLabel.setText("Instrumenter:");
        instrumenterText = new Text(container, SWT.BORDER);
        instrumenterText.setLayoutData(fillData);

        Label suffixLabel = new Label(container, SWT.NONE);
        suffixLabel.setText("Instrument suffix:");
        suffixText = new Text(container, SWT.BORDER);
        suffixText.setLayoutData(fillData);

        // spacer
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        profileButton = new Button(container, SWT.CHECK);
        profileButton.setText("Profile support");
        profileButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        Label profileCmdLabel = new Label(container, SWT.NONE);
        profileCmdLabel.setText("Profile CMD:");
        profileCmdText = new Text(container, SWT.BORDER);
        profileCmdText.setLayoutData(fillData);
        profileCmdText.setEnabled(false);

        Label profileArgsLabel = new Label(container, SWT.NONE);
        profileArgsLabel.setText("Profile ARGS:");
        profileArgsText = new Text(container, SWT.BORDER);
        profileArgsText.setLayoutData(fillData);
        profileArgsText.setEnabled(false);

        // spacer
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        traceButton = new Button(container, SWT.CHECK);
        traceButton.setText("Trace support");
        traceButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        Label traceCmdLabel = new Label(container, SWT.NONE);
        traceCmdLabel.setText("Trace CMD:");
        traceCmdText = new Text(container, SWT.BORDER);
        traceCmdText.setLayoutData(fillData);
        traceCmdText.setEnabled(false);

        Label traceArgsLabel = new Label(container, SWT.NONE);
        traceArgsLabel.setText("Trace ARGS:");
        traceArgsText = new Text(container, SWT.BORDER);
        traceArgsText.setLayoutData(fillData);
        traceArgsText.setEnabled(false);

        // spacer
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        Label modulesLabel = new Label(container, SWT.NONE);
        modulesLabel.setText("Modules required for this tool:");
        modulesText = new Text(container, SWT.BORDER);
        modulesText.setLayoutData(fillData);
        modulesText.setEnabled(false);
        
        //Label useModulesLabel = new Label(container, SWT.NONE);
        //useModulesLabel.setText("Module use");
        //useModulesText = new Text(container, SWT.BORDER);
        //useModulesText.setLayoutData(fillData);
        //useModulesText.setEnabled(false);

        Label envVarsLabel = new Label(container, SWT.NONE);
        envVarsLabel.setText("Environment variables:");
        envVarsTable = new ToolEnvironmentTable(container, SWT.NONE);
        envVarsTable.setEnabled(false);
        envVarsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        // add the listeners
        nameCombo.addModifyListener(new ToolSelectionListener());
        versionCombo.addModifyListener(versionListener);
        newButton.addListener(SWT.Selection, newListener);
        removeButton.addListener(SWT.Selection, removeListener);
        profileButton.addListener(SWT.Selection, checkListener);
        traceButton.addListener(SWT.Selection, checkListener);
        onToolSelected();
        onVersionSelected();

        return container;
    }


    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Performance Tools");
    }


    @Override
    protected boolean isResizable() {
        return true;
    }


    @Override
    protected Point getInitialSize() {
        return new Point(680, 720);
    }


    @Override
    protected void okPressed() {
        if( isDirty() )
            saveCurrentTool();

        ExecutionParameters.updateComboBoxes();
        super.okPressed();
    }


    @NonNullByDefault
    private void onToolSelected() {
        final int selectionIndex = nameCombo.getSelectionIndex();
        if( selectionIndex == -1 ) {
            versionCombo.setItems(new String[0]);
            return;
        }

        ArrayList<Tool> sameName = cache.get(nameCombo.getItem(selectionIndex));
        if( sameName == null )
            throw new IllegalArgumentException();

        ArrayList<String> items = new ArrayList<String>();
        for( Tool tool: sameName )
            items.add(tool.getVersion());

        versionCombo.setItems(items.toArray(new String[items.size()]));
        versionCombo.select(items.size() - 1);
    }


    /** Updates all the text fields, when a new tool and version is selected. */
    private void onVersionSelected() {
        if( isDirty() && askForSave() )
            saveCurrentTool();

        final int toolIndex = nameCombo.getSelectionIndex();
        if( toolIndex == -1 ) {
            selected = null;

            websiteText.setText("");
            instrumenterText.setText("");
            suffixText.setText("");
            profileButton.setSelection(false);
            profileCmdText.setText("");
            profileArgsText.setText("");
            traceButton.setSelection(false);
            traceCmdText.setText("");
            traceArgsText.setText("");
            modulesText.setText("");
            envVarsTable.setSerializedEnvVars(null);

            websiteText.setEnabled(false);
            instrumenterText.setEnabled(false);
            suffixText.setEnabled(false);
            profileButton.setEnabled(false);
            profileCmdText.setEnabled(false);
            profileArgsText.setEnabled(false);
            traceButton.setEnabled(false);
            traceCmdText.setEnabled(false);
            traceArgsText.setEnabled(false);
            modulesText.setEnabled(false);
            envVarsTable.setEnabled(false);
            removeButton.setEnabled(false);
        }
        else {
            // happens as an intermediate state after setting the list of versions and before selecting one of them
            final int versionIndex = versionCombo.getSelectionIndex();
            if( versionIndex == -1 )
                return;

            ArrayList<Tool> sameName = cache.get(nameCombo.getItem(toolIndex));
            selected = sameName.get(versionIndex);

            websiteText.setText(selected.getWebsite() == null ? "" : selected.getWebsite());
            instrumenterText.setText(selected.getInstrumentCMD() == null ? "" : selected.getInstrumentCMD());
            suffixText.setText(selected.getInstrSuffix());
            profileButton.setSelection(selected.getProfiling());
            profileCmdText.setText(selected.getProfileCMD() == null ? "" : selected.getProfileCMD());
            profileArgsText.setText(selected.getProfileArgs() == null ? "" : selected.getProfileArgs());
            traceButton.setSelection(selected.getTracing());
            traceCmdText.setText(selected.getTraceCMD() == null ? "" : selected.getTraceCMD());
            traceArgsText.setText(selected.getTraceArgs() == null ? "" : selected.getTraceArgs());
            modulesText.setText(selected.getReqModules() == null ? "" : selected.getReqModules());
            //useModulesText.setText(selected.getUseModules() == null ? "" : selected.getUseModules());
            envVarsTable.setSerializedEnvVars(selected.getReqEnvVars() == null ? null : selected.getReqEnvVars());

            websiteText.setEnabled(true);
            instrumenterText.setEnabled(true);
            suffixText.setEnabled(true);
            profileButton.setEnabled(true);
            profileCmdText.setEnabled(true);
            profileArgsText.setEnabled(true);
            traceButton.setEnabled(true);
            traceCmdText.setEnabled(true);
            traceArgsText.setEnabled(true);
            modulesText.setEnabled(true);
            envVarsTable.setEnabled(true);
            removeButton.setEnabled(true);
        }

        checkListener.handleEvent(null);
    }


    /** Saves the currently selected tool to the database. */
    private void saveCurrentTool() {
        selected.setWebsite(websiteText.getText());
        selected.setInstrumentCMD(instrumenterText.getText());
        selected.setInstrSuffix(suffixText.getText());
        selected.setProfiling(profileButton.getSelection());
        selected.setProfileCMD(profileCmdText.getText());
        selected.setProfileArgs(profileArgsText.getText());
        selected.setTracing(traceButton.getSelection());
        selected.setTraceCMD(traceCmdText.getText());
        selected.setTraceArgs(traceArgsText.getText());
        selected.setReqModules(modulesText.getText());
        selected.setReqEnvVars(envVarsTable.getSerializedEnvVars());

        try {
            selected.save();
        }
        catch( PersistentException ex ) {
            ex.printStackTrace();
            UIUtils.showErrorMessage("Unable to save the tool configuration.", ex);
        }
        updateCache();
    }


    /** Updates the tools cache. */
    private void updateCache() {
        try {
            pathway.data.persistence.PathwayPersistentManager.instance().getSession().flush();
            List<Tool> temp = Tool.queryTool(null, null);

            cache.clear();
            for( Tool tool: temp ) {
                ArrayList<Tool> sameName = cache.get(tool.getName());
                if( sameName == null ) {
                    sameName = new ArrayList<Tool>();
                    cache.put(tool.getName(), sameName);
                }

                sameName.add(tool);
            }
        }
        catch( PersistentException ex ) {
            ex.printStackTrace();
            throw new RuntimeException("Unable to query the tools from the database.", ex);
        }
    }


    /** Indicates whether the currently selected tool has been changed. */
    private boolean isDirty() {
        return !isClean();
    }


    /** Indicates whether the currently selected tool has NOT been changed. */
    private boolean isClean() {
        if( selected == null )
            return true;

        return websiteText.getText().equals(selected.getWebsite()) && instrumenterText.getText().equals(selected.getInstrumentCMD())
                && suffixText.getText().equals(selected.getInstrSuffix()) && profileButton.getSelection() == selected.getProfiling()
                && profileCmdText.getText().equals(selected.getProfileCMD()) && profileArgsText.getText().equals(selected.getProfileArgs())
                && traceButton.getSelection() == selected.getTracing() && traceCmdText.getText().equals(selected.getTraceCMD())
                && traceArgsText.getText().equals(selected.getTraceArgs()) && modulesText.getText().equals(selected.getReqModules())
                //&& useModulesText.getText().equals(selected.getUseModules())
                && envVarsTable.getSerializedEnvVars().equals(selected.getReqEnvVars());
    }


    private boolean askForSave() {
        MessageBox messageDialog = new MessageBox(getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
        messageDialog.setText("Tool configuration");
        messageDialog.setMessage("The tool \"" + selected.getName() + "\" has been modified. Do you want to save the changes?");

        return messageDialog.open() == SWT.YES;
    }


    private void addTool(String name, String version) {
        try {
            Tool newTool = Tool.createTool();
            newTool.setName(name);
            newTool.setVersion(version);
            newTool.setInstrumentCMD("");
            newTool.setInstrSuffix("");
            newTool.setProfileCMD("");
            newTool.setProfileArgs("");
            newTool.setTraceCMD("");
            newTool.setTraceArgs("");
            newTool.setWebsite("http://www.example.com/");
            newTool.setReqModules("[]");
            newTool.setReqEnvVars("{}");
            newTool.save();
        }
        catch( PersistentException ex ) {
            ex.printStackTrace();
            UIUtils.showErrorMessage("Unable to save the new tool.", ex);
        }

        updateCache();
        nameCombo.setItems(cache.keySet().toArray(new String[cache.size()]));
        selectTool(name, version);
    }


    private void removeTool() {
        if( selected == null )
            throw new IllegalArgumentException();

        try {
            selected.delete();
        }
        catch( PersistentException ex ) {
            ex.printStackTrace();
            UIUtils.showErrorMessage("Unable to remove the tool configuration.", ex);
        }

        String name = selected.getName();
        updateCache();
        nameCombo.setItems(cache.keySet().toArray(new String[cache.size()]));
        selectToolName(name);
        ExecutionParameters.updateComboBoxes();
    }


    private void selectTool(String name, String version) {
        selectToolName(name);
        selectToolVersion(version);
    }


    private void selectToolName(String name) {
        String[] tools = nameCombo.getItems();
        for( int i = 0; i != tools.length; ++i )
            if( tools[i].equals(name) ) {
                nameCombo.select(i);
                return;
            }

        // fallback, even valid when there are no items
        nameCombo.select(0);
    }


    private void selectToolVersion(String version) {
        String[] versions = versionCombo.getItems();
        for( int i = 0; i != versions.length; ++i )
            if( versions[i].equals(version) ) {
                versionCombo.select(i);

                // this is a workaround; when the first tool is added, it is automatically selected and selecting it here will not trigger the change listener
                if( versions.length == 1 )
                    onVersionSelected();

                return;
            }

        // fallback, even valid when there are no items
        versionCombo.select(0);
    }


    private boolean toolExists(String name, String version) {
        ArrayList<Tool> tools = cache.get(name);
        if( tools == null )
            return false;

        for( Tool tool: tools )
            if( tool.getVersion().equals(version) )
                return true;

        return false;
    }


    private final HashMap<String, ArrayList<Tool>> cache = new HashMap<String, ArrayList<Tool>>();
    private final VersionSelectionListener versionListener = new VersionSelectionListener();
    private final NewListener newListener = new NewListener();
    private final RemoveListener removeListener = new RemoveListener();
    private final CheckListener checkListener = new CheckListener();
    private Tool selected;
    private Combo nameCombo;
    private Combo versionCombo;
    private Text websiteText;
    private Text instrumenterText;
    private Text suffixText;
    private Button profileButton;
    private Text profileCmdText;
    private Text profileArgsText;
    private Button traceButton;
    private Text traceCmdText;
    private Text traceArgsText;
    private Text modulesText;
    //private Text useModulesText;
    private ToolEnvironmentTable envVarsTable;
    private Button removeButton;


    @NonNullByDefault
    private static GridLayout createGridLayout() {
        GridLayout layout = new GridLayout();
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        layout.numColumns = 2;
        layout.horizontalSpacing = 40;
        layout.verticalSpacing = 10;

        return layout;
    }


    /** Calls onToolSelected(), when the user selects another tool in the combo box. */
    private class ToolSelectionListener implements ModifyListener {
        @Override
        public void modifyText(ModifyEvent evt) {
            onToolSelected();
        }
    }


    /** Calls onVersionSelected(), when the user selects another version in the combo box. */
    private class VersionSelectionListener implements ModifyListener {
        @Override
        public void modifyText(ModifyEvent evt) {
            onVersionSelected();
        }
    }


    /** Enables and disables the text fields when the check boxes are changed. */
    private class CheckListener implements Listener {
        @Override
        public void handleEvent(Event event) {
            boolean profile = profileButton.getSelection();
            profileCmdText.setEnabled(profile);
            profileArgsText.setEnabled(profile);

            boolean trace = traceButton.getSelection();
            traceCmdText.setEnabled(trace);
            traceArgsText.setEnabled(trace);
        }
    }


    /** Creates a new tool configuration. */
    private class NewListener implements Listener {
        @Override
        public void handleEvent(Event event) {
            AddToolDialog dialog;

            while( true ) {
                dialog = new AddToolDialog(PAThWayPlugin.getShell());
                dialog.create();
                if( dialog.open() != Window.OK )
                    return;

                if( toolExists(dialog.toolName, dialog.toolVersion) )
                    UIUtils.showErrorMessage("This tool version already exists.");
                else
                    break;
            }

            addTool(dialog.toolName, dialog.toolVersion);
        }
    }


    /** Removes a tool configuration. */
    private class RemoveListener implements Listener {
        @Override
        public void handleEvent(Event event) {
            if( !MessageDialog.openConfirm(PAThWayPlugin.getShell(), "Removing performance tool", "Are you sure you want to remove this tool?") )
                return;

            removeTool();
        }
    }
}
