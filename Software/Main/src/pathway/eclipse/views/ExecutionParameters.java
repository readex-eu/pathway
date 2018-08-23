package pathway.eclipse.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.orm.PersistentException;
import pathway.PAThWayPlugin;
import pathway.data.persistence.Application;
import pathway.data.persistence.HPCSystem;
import pathway.data.persistence.Tool;
import pathway.eclipse.UIUtils;




/** Implements a view that lets the user choose workflow parameters, like HPC system and application, for execution. */
public class ExecutionParameters extends ViewPart {
    @SuppressWarnings("unused")
    @Override
    public void createPartControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        layout.numColumns = 1;
        layout.horizontalSpacing = 40;
        layout.verticalSpacing = 10;
        composite.setLayout(layout);
        GridData fillData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        GridData comboData = new GridData(SWT.FILL, SWT.BEGINNING, false, false);

        // description
        Label labelDescription = new Label(composite, SWT.WRAP);
        labelDescription
                .setText("Here, you can specify the parameters for the next workflow execution. If you leave the fields blank, you will be prompted during execution.");
        labelDescription.setLayoutData(fillData);

        // HPC system
        new Label(composite, SWT.NONE); // spacing
        Label labelHPCSystem = new Label(composite, SWT.NONE);
        labelHPCSystem.setText("HPC system:");
        comboHPCSystem = new Combo(composite, SWT.READ_ONLY);
        comboHPCSystem.setLayoutData(comboData);

        // application
        new Label(composite, SWT.NONE); // spacing
        Label labelApplication = new Label(composite, SWT.NONE);
        labelApplication.setText("Application:");
        comboApplication = new Combo(composite, SWT.READ_ONLY);
        comboApplication.setLayoutData(comboData);

        // performance tool
        new Label(composite, SWT.NONE); // spacing
        Label labelTool = new Label(composite, SWT.NONE);
        labelTool.setText("Performance tool:");
        comboTool = new Combo(composite, SWT.READ_ONLY);
        comboTool.setLayoutData(comboData);

        // MPI processes
        new Label(composite, SWT.NONE); // spacing
        Label labelMPI = new Label(composite, SWT.NONE);
        labelMPI.setText("Number of MPI processes:");
        textMPI = new Text(composite, SWT.BORDER);
        textMPI.setLayoutData(comboData);

        // OMP threads
        new Label(composite, SWT.NONE); // spacing
        Label labelOMP = new Label(composite, SWT.NONE);
        labelOMP.setText("Number of OpenMP threads:");
        textOMP = new Text(composite, SWT.BORDER);
        textOMP.setLayoutData(comboData);

        // update all combo boxes
        update();
    }


    @Override
    public void setFocus() {
    }


    private void update() {
        try {
            updateHPCSystems();
            updateApplications();
            updateTools();
        }
        catch( PersistentException ex ) {
            UIUtils.showErrorMessage("Unable to populate some of the fields. This is bad. :-/", ex);
        }
    }


    private void updateHPCSystems() throws PersistentException {
        // get currently selected item
        String selectedItem = comboHPCSystem.getText();

        // update list
        List<HPCSystem> systems = HPCSystem.queryHPCSystem(null, null);
        List<String> systemNames = new ArrayList<String>();
        for( HPCSystem system: systems )
            systemNames.add(system.getName());
        Collections.sort(systemNames);
        comboHPCSystem.removeAll();
        comboHPCSystem.add(UNSPECIFIED_COMBO_VALUE);
        for( String system: systemNames )
            comboHPCSystem.add(system);
        comboHPCSystem.select(0);

        // re-select item
        if( !UNSPECIFIED_COMBO_VALUE.equals(selectedItem) ) {
            int index = comboHPCSystem.indexOf(selectedItem);
            if( index != -1 )
                comboHPCSystem.select(index);
        }
    }


    private void updateApplications() throws PersistentException {
        // get currently selected item
        String selectedItem = comboApplication.getText();

        // update list
        List<Application> applications = Application.queryApplication(null, null);
        final List<String> applicationNames = (List<String>)CollectionUtils.collect(applications, new Transformer() {
            @Override
            public Object transform(Object obj) {
                Application app = ((Application)obj);
                return app.getName() + (app.getConfig().equalsIgnoreCase("default") ? "" : "|" + app.getConfig());
            }
        });
        Collections.sort(applicationNames);

        comboApplication.removeAll();
        comboApplication.add(UNSPECIFIED_COMBO_VALUE);
        for( String name: applicationNames )
            comboApplication.add(name);
        comboApplication.select(0);

        // re-select item
        if( !UNSPECIFIED_COMBO_VALUE.equals(selectedItem) ) {
            int index = comboApplication.indexOf(selectedItem);
            if( index != -1 )
                comboApplication.select(index);
        }
    }


    private void updateTools() throws PersistentException {
        // get currently selected item
        String selectedItem = comboTool.getText();

        // update list
        List<Tool> tools = Tool.queryTool(null, null);
        List<String> toolNames = new ArrayList<String>();
        for( Tool tool: tools )
            toolNames.add(tool.getName() + "|" + tool.getVersion());
        Collections.sort(toolNames);

        comboTool.removeAll();
        comboTool.add(UNSPECIFIED_COMBO_VALUE);
        for( String name: toolNames )
            comboTool.add(name);
        comboTool.select(0);

        // re-select item
        if( !UNSPECIFIED_COMBO_VALUE.equals(selectedItem) ) {
            int index = comboTool.indexOf(selectedItem);
            if( index != -1 )
                comboTool.select(index);
        }
    }


    private Combo comboHPCSystem;
    private Combo comboApplication;
    private Combo comboTool;
    private Text textMPI;
    private Text textOMP;


    /** Updates all the combo boxes, if the view is currently shown. */
    public static void updateComboBoxes() {
        ExecutionParameters view = (ExecutionParameters)UIUtils.findView(PAThWayPlugin.VIEW_EXECUTION_PARAMETERS);
        if( view == null )
            return;

        view.update();
    }


    /** Gets the HPC system currently selected by the user, or null. */
    public static String getHPCSystem() {
        ExecutionParameters view = (ExecutionParameters)UIUtils.findView(PAThWayPlugin.VIEW_EXECUTION_PARAMETERS);
        if( view == null )
            return null;

        return view.comboHPCSystem.getSelectionIndex() == 0 ? null : view.comboHPCSystem.getText();
    }


    /** Gets the application currently selected by the user, or null. */
    public static String getApplication() {
        ExecutionParameters view = (ExecutionParameters)UIUtils.findView(PAThWayPlugin.VIEW_EXECUTION_PARAMETERS);
        if( view == null )
            return null;

        return view.comboApplication.getSelectionIndex() == 0 ? null : view.comboApplication.getText();
    }


    /** Gets the performance tool currently selected by the user, or null. */
    public static String getTool() {
        ExecutionParameters view = (ExecutionParameters)UIUtils.findView(PAThWayPlugin.VIEW_EXECUTION_PARAMETERS);
        if( view == null )
            return null;

        return view.comboTool.getSelectionIndex() == 0 ? null : view.comboTool.getText();
    }


    /** Gets the MPI configuration currently selected by the user, or null. */
    public static String getMPI() {
        ExecutionParameters view = (ExecutionParameters)UIUtils.findView(PAThWayPlugin.VIEW_EXECUTION_PARAMETERS);
        if( view == null )
            return null;

        String mpi = view.textMPI.getText().trim();
        return mpi.isEmpty() ? null : mpi;
    }


    /** Gets the OMP configuration currently selected by the user, or null. */
    public static String getOMP() {
        ExecutionParameters view = (ExecutionParameters)UIUtils.findView(PAThWayPlugin.VIEW_EXECUTION_PARAMETERS);
        if( view == null )
            return null;

        String omp = view.textOMP.getText().trim();
        return omp.isEmpty() ? null : omp;
    }


    private static final String UNSPECIFIED_COMBO_VALUE = "(not specified)";
}
