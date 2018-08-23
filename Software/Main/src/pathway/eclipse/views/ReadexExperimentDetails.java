package pathway.eclipse.views;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import pathway.data.persistence.Experiment;




/** Implements a view that shows details on the currently selected experiment. */
public class ReadexExperimentDetails extends ViewPart {
    @Override
    public void createPartControl(Composite parent) {
        folder = new CTabFolder(parent, SWT.BOTTOM);

        tabOverview = new CTabItem(folder, SWT.NONE);
        tabOverview.setText("Overview");
        tabOverview.setControl(new ExperimentTab(folder, SWT.NONE, OverviewComposite.class));
        tabConfig = new CTabItem(folder, SWT.NONE);
        tabConfig.setText("Configuration");
        tabConfig.setControl(new ExperimentTab(folder, SWT.NONE, ConfigurationComposite.class));
        tabSystem = new CTabItem(folder, SWT.NONE);
        tabSystem.setText("HPC System");
        tabSystem.setControl(new ExperimentTab(folder, SWT.NONE, HPCSystemComposite.class));
        tabPeriscope = new CTabItem(folder, SWT.NONE);
        tabPeriscope.setText("Periscope");
        tabPeriscope.setControl(new ExperimentTab(folder, SWT.NONE, PeriscopeComposite.class));
        tabEnvironment = new CTabItem(folder, SWT.NONE);
        tabEnvironment.setText("Environment");
        tabEnvironment.setControl(new ExperimentTab(folder, SWT.NONE, EnvironmentComposite.class));
        tabStdout = new CTabItem(folder, SWT.NONE);
        tabStdout.setText("Output");
        tabStdout.setControl(new ExperimentTab(folder, SWT.NONE, StdoutComposite.class));
        tabStderr = new CTabItem(folder, SWT.NONE);
        tabStderr.setText("Error log");
        tabStderr.setControl(new ExperimentTab(folder, SWT.NONE, StderrComposite.class));

        folder.setSelection(tabOverview);
    }


    @Override
    public void setFocus() {
    }


    /** Specifies the experiment to be shown in this view. */
    public void setShownExperiment(@Nullable Experiment experiment) {
        ((ExperimentTab)tabOverview.getControl()).setShownExperiment(experiment);
        ((ExperimentTab)tabConfig.getControl()).setShownExperiment(experiment);
        ((ExperimentTab)tabSystem.getControl()).setShownExperiment(experiment);
        ((ExperimentTab)tabPeriscope.getControl()).setShownExperiment(experiment);
        ((ExperimentTab)tabEnvironment.getControl()).setShownExperiment(experiment);
        ((ExperimentTab)tabStdout.getControl()).setShownExperiment(experiment);
        ((ExperimentTab)tabStderr.getControl()).setShownExperiment(experiment);
    }


    private CTabFolder folder;
    private CTabItem tabOverview;
    private CTabItem tabConfig;
    private CTabItem tabSystem;
    private CTabItem tabPeriscope;
    private CTabItem tabEnvironment;
    private CTabItem tabStdout;
    private CTabItem tabStderr;
}
